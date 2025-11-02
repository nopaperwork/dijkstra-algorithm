/**
 * @package com.nopaper.work.dijkstra.dto -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:50:21 am
 * @git 
 */
package com.nopaper.work.dijkstra.dto;

import java.util.List;

/**
 * Response DTO containing the shortest path result
 */
public class PathResponse {
    
    private boolean pathFound;
    private List<LocationDTO> path;
    private double totalDistanceKm;
    private double totalDistanceMiles;
    private List<PathSegment> segments;
    private String message;
    
    // Inner class for path segments
    public static class PathSegment {
        private LocationDTO from;
        private LocationDTO to;
        private double distanceKm;
        
        public PathSegment(LocationDTO from, LocationDTO to, double distanceKm) {
            this.from = from;
            this.to = to;
            this.distanceKm = distanceKm;
        }
        
        // Getters and Setters
        public LocationDTO getFrom() { return from; }
        public void setFrom(LocationDTO from) { this.from = from; }
        
        public LocationDTO getTo() { return to; }
        public void setTo(LocationDTO to) { this.to = to; }
        
        public double getDistanceKm() { return distanceKm; }
        public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    }
    
    // Constructors
    public PathResponse() {}
    
    // Getters and Setters
    public boolean isPathFound() { return pathFound; }
    public void setPathFound(boolean pathFound) { this.pathFound = pathFound; }
    
    public List<LocationDTO> getPath() { return path; }
    public void setPath(List<LocationDTO> path) { this.path = path; }
    
    public double getTotalDistanceKm() { return totalDistanceKm; }
    public void setTotalDistanceKm(double totalDistanceKm) { 
        this.totalDistanceKm = totalDistanceKm; 
    }
    
    public double getTotalDistanceMiles() { return totalDistanceMiles; }
    public void setTotalDistanceMiles(double totalDistanceMiles) { 
        this.totalDistanceMiles = totalDistanceMiles; 
    }
    
    public List<PathSegment> getSegments() { return segments; }
    public void setSegments(List<PathSegment> segments) { this.segments = segments; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
