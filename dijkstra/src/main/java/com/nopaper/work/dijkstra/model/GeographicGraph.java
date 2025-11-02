/**
 * @package com.nopaper.work.dijkstra.model -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:52:25 am
 * @git 
 */
package com.nopaper.work.dijkstra.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Graph implementation for storing geographic locations and finding shortest paths.
 */
public class GeographicGraph {

    private final Map<Location, Node> nodes = new HashMap<>();

    /**
     * Adds a new location to the graph.
     */
    public void addLocation(Location location) {
        nodes.putIfAbsent(location, new Node(location));
    }

    /**
     * Creates a bidirectional edge (connection) between two locations.
     */
    public void addEdge(Location source, Location destination) {
        Node sourceNode = nodes.get(source);
        Node destNode = nodes.get(destination);

        if (sourceNode == null || destNode == null)
            throw new IllegalArgumentException("Both locations must be added to the graph first");

        double distance = source.distanceTo(destination);

        sourceNode.addAdjacentNode(destNode, distance);
        destNode.addAdjacentNode(sourceNode, distance);
    }

    /**
     * Finds the shortest path between two locations using Dijkstra's algorithm.
     * @return ordered list of locations in path; empty if unreachable
     */
    public List<Location> findShortestPath(Location start, Location end) {
        Node startNode = nodes.get(start);
        Node endNode = nodes.get(end);

        if (startNode == null || endNode == null)
            throw new IllegalArgumentException("Start and end locations must exist in the graph");

        // Initialization
        resetDistances();
        startNode.setDistance(0);
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Set<Node> visited = new HashSet<>();
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (visited.contains(current))
                continue;
            visited.add(current);

            if (current.equals(endNode))
                break;

            for (Map.Entry<Node, Double> entry : current.getAdjacentNodes().entrySet()) {
                Node neighbor = entry.getKey();
                double edgeWeight = entry.getValue();

                if (visited.contains(neighbor))
                    continue;

                double newDist = current.getDistance() + edgeWeight;
                if (newDist < neighbor.getDistance()) {
                    neighbor.setDistance(newDist);
                    neighbor.setPrevious(current);
                    queue.add(neighbor);
                }
            }
        }

        return buildPath(endNode);
    }

    private List<Location> buildPath(Node endNode) {
        List<Location> path = new ArrayList<>();
        Node current = endNode;

        if (current.getDistance() == Double.MAX_VALUE)
            return path; // Unreachable

        while (current != null) {
            path.add(current.getLocation());
            current = current.getPrevious();
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Resets distances for all nodes for a fresh calculation.
     */
    public void resetDistances() {
        for (Node node : nodes.values()) {
            node.setDistance(Double.MAX_VALUE);
            node.setPrevious(null);
        }
    }
}
