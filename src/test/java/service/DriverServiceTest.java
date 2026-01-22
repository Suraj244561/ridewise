package service;

import model.Driver;
import model.VehicleType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceTest {

    @Test
    void availabilityAndListAvailableDrivers() {
        DriverService driverService = new DriverService();

        Driver d1 = driverService.registerDriver("D1", "Delhi", VehicleType.CAR);
        Driver d2 = driverService.registerDriver("D2", "Noida", VehicleType.BIKE);

        assertEquals(2, driverService.listAvailableDrivers().size());

        driverService.setAvailability(d1.getId(), false);
        assertEquals(1, driverService.listAvailableDrivers().size());

        driverService.setAvailability(d1.getId(), true);
        assertEquals(2, driverService.listAvailableDrivers().size());

        assertTrue(d2.isAvailable());
    }
}
