package service;

import model.*;
import org.junit.jupiter.api.Test;
import strategy.DefaultFareStrategy;
import strategy.FareStrategy;
import strategy.NearestDriverStrategy;
import strategy.RideMatchingStrategy;

import static org.junit.jupiter.api.Assertions.*;

class RideServiceTest {

    @Test
    void requestRide_assignsDriver_whenAvailable() {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        Rider rider = riderService.registerRider("R1", "Delhi");
        Driver driver = driverService.registerDriver("D1", "Delhi", VehicleType.CAR);

        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy fareStrategy = new DefaultFareStrategy(30, 10);
        RideService rideService = new RideService(riderService, driverService, matchingStrategy, fareStrategy);

        Ride ride = rideService.requestRide(rider.getId(), 5);
        assertEquals(RideStatus.ASSIGNED, ride.getStatus());
        assertNotNull(ride.getDriver());
        assertEquals(driver.getId(), ride.getDriver().getId());
        assertFalse(driverService.getDriverById(driver.getId()).isAvailable());
    }

    @Test
    void completeRide_marksCompleted_andReturnsReceipt() {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        Rider rider = riderService.registerRider("R1", "Delhi");
        Driver driver = driverService.registerDriver("D1", "Delhi", VehicleType.AUTO);

        RideService rideService = new RideService(
                riderService,
                driverService,
                new NearestDriverStrategy(),
                new DefaultFareStrategy(30, 10)
        );

        Ride ride = rideService.requestRide(rider.getId(), 5);
        assertEquals(RideStatus.ASSIGNED, ride.getStatus());

        FareReceipt receipt = rideService.completeRide(ride.getId());
        assertNotNull(receipt);
        assertEquals(ride.getId(), receipt.getRideId());
        assertEquals(80.0, receipt.getAmount(), 0.0001);

        Ride updated = rideService.getRideById(ride.getId());
        assertEquals(RideStatus.COMPLETED, updated.getStatus());

        Driver updatedDriver = driverService.getDriverById(driver.getId());
        assertTrue(updatedDriver.isAvailable());
        assertEquals(1, updatedDriver.getTotalRides());
    }

    @Test
    void cancelRide_releasesDriver() {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        Rider rider = riderService.registerRider("R1", "Delhi");
        Driver driver = driverService.registerDriver("D1", "Delhi", VehicleType.BIKE);

        RideService rideService = new RideService(
                riderService,
                driverService,
                new NearestDriverStrategy(),
                new DefaultFareStrategy(30, 10)
        );

        Ride ride = rideService.requestRide(rider.getId(), 2);
        assertEquals(RideStatus.ASSIGNED, ride.getStatus());
        assertFalse(driverService.getDriverById(driver.getId()).isAvailable());

        rideService.cancelRide(ride.getId());

        Ride updated = rideService.getRideById(ride.getId());
        assertEquals(RideStatus.CANCELLED, updated.getStatus());
        assertTrue(driverService.getDriverById(driver.getId()).isAvailable());
    }

    @Test
    void requestRide_cancels_whenNoDriversAvailable() {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        Rider rider = riderService.registerRider("R1", "Delhi");

        RideService rideService = new RideService(
                riderService,
                driverService,
                new NearestDriverStrategy(),
                new DefaultFareStrategy(30, 10)
        );

        Ride ride = rideService.requestRide(rider.getId(), 2);
        assertEquals(RideStatus.CANCELLED, ride.getStatus());
        assertNull(ride.getDriver());
    }
}
