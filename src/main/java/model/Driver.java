package model;

public class Driver {
    private final int id;
    private final String name;
    private String currentLocation;
    private boolean available;
    private final VehicleType vehicleType;
    private int totalRides;

    public Driver(int id, String name, String currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true;
        this.totalRides = 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public VehicleType getVehicleType() { return vehicleType; }
    public int getTotalRides() { return totalRides; }
    public void incrementTotalRides() { this.totalRides++; }

    @Override
    public String toString() {
        return "Driver{id=" + id + ", name='" + name + "', loc='" + currentLocation + "', type=" + vehicleType +
                ", available=" + available + ", totalRides=" + totalRides + "}";
    }
}
