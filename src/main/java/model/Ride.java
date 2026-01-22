package model;

public class Ride {
    private final int id;
    private final Rider rider;
    private Driver driver;
    private final double distance;
    private RideStatus status;

    public Ride(int id, Rider rider, double distance) {
        this.id = id;
        this.rider = rider;
        this.distance = distance;
        this.status = RideStatus.REQUESTED;
    }

    public int getId() { return id; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
    public double getDistance() { return distance; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Ride{id=" + id +
                ", rider=" + rider.getName() +
                ", driver=" + (driver == null ? "NA" : driver.getName()) +
                ", distance=" + distance +
                ", status=" + status + "}";
    }
}
