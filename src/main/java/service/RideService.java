package service;

import model.*;
import strategy.FareStrategy;
import strategy.RideMatchingStrategy;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RideService {
    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;

    private final Map<Integer, Ride> rides = new HashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    public RideService(RiderService riderService,
                       DriverService driverService,
                       RideMatchingStrategy matchingStrategy,
                       FareStrategy fareStrategy) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(int riderId, double distance) {
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) throw new IllegalArgumentException("Rider not found: " + riderId);
        if (distance <= 0) throw new IllegalArgumentException("Distance must be > 0");

        int rideId = idGen.getAndIncrement();
        Ride ride = new Ride(rideId, rider, distance);
        rides.put(rideId, ride);

        List<Driver> available = driverService.listAvailableDrivers();
        Driver chosen = matchingStrategy.findDriver(rider, available);
        if (chosen == null) {
            ride.setStatus(RideStatus.CANCELLED);
            return ride;
        }

        ride.setDriver(chosen);
        ride.setStatus(RideStatus.ASSIGNED);
        chosen.setAvailable(false);

        return ride;
    }

    public FareReceipt completeRide(int rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + rideId);

        if (ride.getStatus() == RideStatus.CANCELLED) {
            throw new IllegalStateException("Ride already CANCELLED");
        }
        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new IllegalStateException("Ride already COMPLETED");
        }
        if (ride.getDriver() == null) {
            throw new IllegalStateException("Ride has no driver assigned");
        }

        ride.setStatus(RideStatus.COMPLETED);

        Driver d = ride.getDriver();
        d.setAvailable(true);
        d.incrementTotalRides();

        double amount = fareStrategy.calculateFare(ride);
        return new FareReceipt(rideId, amount, LocalDateTime.now());
    }

    public void cancelRide(int rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + rideId);

        if (ride.getStatus() == RideStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel COMPLETED ride");
        }

        ride.setStatus(RideStatus.CANCELLED);
        if (ride.getDriver() != null) {
            ride.getDriver().setAvailable(true);
        }
    }

    public Ride getRideById(int rideId) {
        return rides.get(rideId);
    }

    public List<Ride> getAllRides() {
        return new ArrayList<>(rides.values());
    }

    public double estimateFare(int rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + rideId);
        return fareStrategy.calculateFare(ride);
    }
}
