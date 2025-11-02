/**
 * @package com.nopaper.work.dijkstra.service -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:55:03 am
 * @git 
 */
package com.nopaper.work.dijkstra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nopaper.work.dijkstra.dto.LocationDTO;
import com.nopaper.work.dijkstra.dto.PathRequest;
import com.nopaper.work.dijkstra.dto.PathResponse;
import com.nopaper.work.dijkstra.model.GeographicGraph;
import com.nopaper.work.dijkstra.model.Location;

/**
 * Service class for handling path finding operations using Dijkstra's algorithm
 */
@Service
public class PathFinderService {
    
    private static final Logger logger = LoggerFactory.getLogger(PathFinderService.class);
    private static final double KM_TO_MILES = 0.621371;
    
    /**
     * Finds the shortest path between two locations from a set of connected locations
     * 
     * @param request PathRequest containing locations, connections, and start/end indices
     * @return PathResponse with the shortest path and distance information
     */
    public PathResponse findShortestPath(PathRequest request) {
        logger.info("Processing path finding request with {} locations", 
                    request.getLocations().size());
        
        PathResponse response = new PathResponse();
        
        try {
            // Validate indices
            validateIndices(request);
            
            // Convert DTOs to domain objects
            List<Location> locations = convertToLocations(request.getLocations());
            
            // Build graph
            GeographicGraph graph = buildGraph(locations, request.getConnections());
            
            // Find shortest path
            Location start = locations.get(request.getStartIndex());
            Location end = locations.get(request.getEndIndex());
            
            logger.debug("Finding path from {} to {}", start.getName(), end.getName());
            
            List<Location> shortestPath = graph.findShortestPath(start, end);
            
            if (shortestPath.isEmpty()) {
                response.setPathFound(false);
                response.setMessage("No path found between the specified locations");
                logger.warn("No path found from {} to {}", start.getName(), end.getName());
                return response;
            }
            
            // Convert path to DTOs
            List<LocationDTO> pathDTOs = convertToLocationDTOs(shortestPath);
            
            // Calculate segments and total distance
            List<PathResponse.PathSegment> segments = new ArrayList<>();
            double totalDistance = 0.0;
            
            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Location from = shortestPath.get(i);
                Location to = shortestPath.get(i + 1);
                double segmentDistance = from.distanceTo(to);
                
                segments.add(new PathResponse.PathSegment(
                    pathDTOs.get(i),
                    pathDTOs.get(i + 1),
                    segmentDistance
                ));
                
                totalDistance += segmentDistance;
            }
            
            response.setPathFound(true);
            response.setPath(pathDTOs);
            response.setSegments(segments);
            response.setTotalDistanceKm(Math.round(totalDistance * 100.0) / 100.0);
            response.setTotalDistanceMiles(Math.round(totalDistance * KM_TO_MILES * 100.0) / 100.0);
            response.setMessage("Shortest path found successfully");
            
            logger.info("Path found with total distance: {} km", response.getTotalDistanceKm());
            
        } catch (Exception e) {
            logger.error("Error processing path request", e);
            response.setPathFound(false);
            response.setMessage("Error: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Validates that start and end indices are within bounds
     */
    private void validateIndices(PathRequest request) {
        int size = request.getLocations().size();
        if (request.getStartIndex() < 0 || request.getStartIndex() >= size) {
            throw new IllegalArgumentException("Start index out of bounds");
        }
        if (request.getEndIndex() < 0 || request.getEndIndex() >= size) {
            throw new IllegalArgumentException("End index out of bounds");
        }
    }
    
    /**
     * Converts LocationDTOs to Location domain objects
     */
    private List<Location> convertToLocations(List<LocationDTO> dtos) {
        return dtos.stream()
            .map(dto -> new Location(dto.getName(), dto.getLatitude(), dto.getLongitude()))
            .collect(Collectors.toList());
    }
    
    /**
     * Converts Location domain objects to LocationDTOs
     */
    private List<LocationDTO> convertToLocationDTOs(List<Location> locations) {
        return locations.stream()
            .map(loc -> new LocationDTO(loc.getName(), loc.getLatitude(), loc.getLongitude()))
            .collect(Collectors.toList());
    }
    
    /**
     * Builds a geographic graph from locations and connections
     */
    private GeographicGraph buildGraph(List<Location> locations, 
                                       List<PathRequest.Connection> connections) {
        GeographicGraph graph = new GeographicGraph();
        
        // Add all locations
        locations.forEach(graph::addLocation);
        
        // Add connections
        for (PathRequest.Connection conn : connections) {
            if (conn.getFrom() < 0 || conn.getFrom() >= locations.size() ||
                conn.getTo() < 0 || conn.getTo() >= locations.size()) {
                throw new IllegalArgumentException("Invalid connection indices");
            }
            
            graph.addEdge(locations.get(conn.getFrom()), 
                         locations.get(conn.getTo()));
        }
        
        return graph;
    }
}
