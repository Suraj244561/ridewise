package strategy;

import model.Driver;
import model.Rider;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        for (Driver d : drivers) {
            if (d.getCurrentLocation() != null &&
                    d.getCurrentLocation().equalsIgnoreCase(rider.getLocation())) {
                return d;
            }
        }
        return drivers.isEmpty() ? null : drivers.get(0);
    }
}
