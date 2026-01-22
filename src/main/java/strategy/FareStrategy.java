package strategy;

import model.Ride;

public interface FareStrategy {
    double calculateFare(Ride ride);
}
