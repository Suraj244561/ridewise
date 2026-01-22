package service;

import model.Driver;
import model.VehicleType;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DriverService {
    private final Map<Integer, Driver> drivers = new HashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    public Driver registerDriver(String name, String location, VehicleType vehicleType) {
        int id = idGen.getAndIncrement();
        Driver driver = new Driver(id, name, location, vehicleType);
        drivers.put(id, driver);
        return driver;
    }

    public Driver getDriverById(int id) {
        return drivers.get(id);
    }

    public void setAvailability(int driverId, boolean available) {
        Driver d = drivers.get(driverId);
        if (d != null) d.setAvailable(available);
    }

    public List<Driver> listAvailableDrivers() {
        return drivers.values().stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }
}
