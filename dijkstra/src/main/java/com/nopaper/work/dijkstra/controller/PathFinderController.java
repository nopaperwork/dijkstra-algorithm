/**
 * @package com.nopaper.work.dijkstra.controller -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:56:16 am
 * @git 
 */
package com.nopaper.work.dijkstra.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nopaper.work.dijkstra.dto.PathRequest;
import com.nopaper.work.dijkstra.dto.PathResponse;
import com.nopaper.work.dijkstra.service.PathFinderService;

/**
 * 
 */

import jakarta.validation.Valid;

/**
 * REST Controller for Dijkstra's shortest path API endpoints
 */
@RestController
@RequestMapping("/v1/path")
@CrossOrigin(origins = "*")
public class PathFinderController {
    
    private static final Logger logger = LoggerFactory.getLogger(PathFinderController.class);
    
    @Autowired
    private PathFinderService pathFinderService;
    
    /**
     * Find shortest path endpoint
     * 
     * POST /api/v1/path/shortest
     * 
     * @param request PathRequest with locations and connections
     * @return PathResponse with shortest path details
     */
    @PostMapping("/shortest")
    public ResponseEntity<PathResponse> findShortestPath(@Valid @RequestBody PathRequest request) {
        logger.info("Received request to find shortest path");
        
        PathResponse response = pathFinderService.findShortestPath(request);
        
        if (response.isPathFound()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    /**
     * Health check endpoint
     * 
     * GET /api/v1/path/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Dijkstra Path Finder API is running");
    }
    
    /**
     * API info endpoint
     * 
     * GET /api/v1/path/info
     */
    @GetMapping("/info")
    public ResponseEntity<ApiInfo> getApiInfo() {
        ApiInfo info = new ApiInfo(
            "Dijkstra's Algorithm Path Finder API",
            "1.0.0",
            "Finds shortest geographic paths using Dijkstra's algorithm"
        );
        return ResponseEntity.ok(info);
    }
    
    // Inner class for API info
    private record ApiInfo(String name, String version, String description) {}
}
