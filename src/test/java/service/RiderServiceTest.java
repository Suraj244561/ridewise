package service;

import model.Rider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiderServiceTest {

    @Test
    void registerAndGetRider() {
        RiderService riderService = new RiderService();

        Rider r = riderService.registerRider("Suraj", "Delhi");
        assertNotNull(r);
        assertTrue(r.getId() > 0);

        Rider fetched = riderService.getRiderById(r.getId());
        assertNotNull(fetched);
        assertEquals("Suraj", fetched.getName());
        assertEquals("Delhi", fetched.getLocation());
    }
}
