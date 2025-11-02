/**
 * @package com.nopaper.work.dijkstra.model -> dijkstra
 * @author saikatbarman
 * @date 2025 01-Nov-2025 12:52:35 am
 * @git 
 */
package com.nopaper.work.dijkstra.model;

/**
 * 
 */

import java.util.Objects;

/**
 * Represents a geographic location with latitude and longitude coordinates.
 */
public class Location {

    private final String name;
    private final double latitude;
    private final double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    /**
     * Calculates the great-circle distance to another location using the Haversine formula.
     * @param other target location
     * @return distance in kilometers
     */
    public double distanceTo(Location other) {
        final double EARTH_RADIUS_KM = 6371.0;
        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(other.latitude);
        double deltaLat = Math.toRadians(other.latitude - this.latitude);
        double deltaLon = Math.toRadians(other.longitude - this.longitude);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return Double.compare(location.latitude, latitude) == 0
            && Double.compare(location.longitude, longitude) == 0
            && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude);
    }

    @Override
    public String toString() {
        return name + " (" + latitude + ", " + longitude + ")";
    }
}
