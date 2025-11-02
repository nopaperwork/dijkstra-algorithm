/**
 * @package com.nopaper.work.dijkstra.model -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:52:44 am
 * @git 
 */
package com.nopaper.work.dijkstra.model;

/**
 * 
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a node in the graph for Dijkstra's algorithm with adjacent connections.
 */
public class Node implements Comparable<Node> {

    private final Location location;
    private double distance = Double.MAX_VALUE;
    private Node previous;
    private final Map<Node, Double> adjacentNodes = new HashMap<>();

    public Node(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public Node getPrevious() {
        return previous;
    }
    public void setPrevious(Node previous) {
        this.previous = previous;
    }
    public Map<Node, Double> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addAdjacentNode(Node destination, double distance) {
        adjacentNodes.put(destination, distance);
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.distance, other.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return Objects.equals(location, node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
