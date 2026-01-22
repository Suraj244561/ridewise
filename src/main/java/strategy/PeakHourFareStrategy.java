package strategy;

import model.Ride;

import java.time.LocalTime;

public class PeakHourFareStrategy implements FareStrategy {
    private final FareStrategy baseStrategy;
    private final double multiplier;

    public PeakHourFareStrategy(FareStrategy baseStrategy, double multiplier) {
        this.baseStrategy = baseStrategy;
        this.multiplier = multiplier;
    }

    @Override
    public double calculateFare(Ride ride) {
        double base = baseStrategy.calculateFare(ride);

        LocalTime now = LocalTime.now();
        boolean isPeak = !now.isBefore(LocalTime.of(17, 0)) && !now.isAfter(LocalTime.of(20, 0));

        return isPeak ? base * multiplier : base;
    }
}
