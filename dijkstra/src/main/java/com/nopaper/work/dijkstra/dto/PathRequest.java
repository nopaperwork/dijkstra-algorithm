/**
 * @package com.nopaper.work.dijkstra.dto -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:49:34 am
 * @git 
 */
package com.nopaper.work.dijkstra.dto;

/**
 * 
 */

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Request DTO for finding shortest path
 */
public class PathRequest {
    
    @NotEmpty(message = "Locations list cannot be empty")
    @Size(min = 2, message = "At least 2 locations are required")
    @Valid
    private List<LocationDTO> locations;
    
    @NotEmpty(message = "Connections list cannot be empty")
    private List<Connection> connections;
    
    @NotNull(message = "Start location index is required")
    private Integer startIndex;
    
    @NotNull(message = "End location index is required")
    private Integer endIndex;
    
    // Inner class for connections
    public static class Connection {
        @NotNull
        private Integer from;
        
        @NotNull
        private Integer to;
        
        public Connection() {}
        
        public Connection(Integer from, Integer to) {
            this.from = from;
            this.to = to;
        }
        
        public Integer getFrom() { return from; }
        public void setFrom(Integer from) { this.from = from; }
        
        public Integer getTo() { return to; }
        public void setTo(Integer to) { this.to = to; }
    }
    
    // Constructors
    public PathRequest() {}
    
    // Getters and Setters
    public List<LocationDTO> getLocations() { return locations; }
    public void setLocations(List<LocationDTO> locations) { this.locations = locations; }
    
    public List<Connection> getConnections() { return connections; }
    public void setConnections(List<Connection> connections) { this.connections = connections; }
    
    public Integer getStartIndex() { return startIndex; }
    public void setStartIndex(Integer startIndex) { this.startIndex = startIndex; }
    
    public Integer getEndIndex() { return endIndex; }
    public void setEndIndex(Integer endIndex) { this.endIndex = endIndex; }
}
