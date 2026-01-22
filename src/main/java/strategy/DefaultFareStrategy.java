package strategy;

import model.Ride;

public class DefaultFareStrategy implements FareStrategy {
    private final double baseFare;
    private final double perKm;

    public DefaultFareStrategy(double baseFare, double perKm) {
        this.baseFare = baseFare;
        this.perKm = perKm;
    }

    @Override
    public double calculateFare(Ride ride) {
        return baseFare + (perKm * ride.getDistance());
    }
}
