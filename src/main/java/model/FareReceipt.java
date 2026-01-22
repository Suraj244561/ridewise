package model;

import java.time.LocalDateTime;

public class FareReceipt {
    private final int rideId;
    private final double amount;
    private final LocalDateTime generatedAt;

    public FareReceipt(int rideId, double amount, LocalDateTime generatedAt) {
        this.rideId = rideId;
        this.amount = amount;
        this.generatedAt = generatedAt;
    }

    public int getRideId() { return rideId; }
    public double getAmount() { return amount; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }

    @Override
    public String toString() {
        return "FareReceipt{rideId=" + rideId + ", amount=" + amount + ", generatedAt=" + generatedAt + "}";
    }
}
