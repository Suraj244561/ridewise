package service;

import model.Rider;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RiderService {
    private final Map<Integer, Rider> riders = new HashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    public Rider registerRider(String name, String location) {
        int id = idGen.getAndIncrement();
        Rider rider = new Rider(id, name, location);
        riders.put(id, rider);
        return rider;
    }

    public Rider getRiderById(int id) {
        return riders.get(id);
    }

    public List<Rider> getAllRiders() {
        return new ArrayList<>(riders.values());
    }
}
