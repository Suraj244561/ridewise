# RideWise — Modular Ride‑Sharing System

Console-based Java app that demonstrates OOP + SOLID with Strategy pattern for:
- Driver matching (`RideMatchingStrategy`)
- Fare calculation (`FareStrategy`)

## Features
- Register Riders
- Register Drivers
- Show available drivers
- Request ride (REQUESTED → ASSIGNED)
- Complete ride (COMPLETED) + Fare receipt
- Cancel ride (CANCELLED)

## Project structure
- `src/main/java/model` — domain entities + enums
- `src/main/java/strategy` — strategy interfaces + implementations
- `src/main/java/service` — services (RiderService, DriverService, RideService)
- `src/main/java/app/Main.java` — console menu

## Run (IntelliJ)
1. Open repo in IntelliJ.
2. Run: `src/main/java/app/Main.java`

## Strategy swap
In `Main.java`, switch strategies like:
```java
RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
// OR
// RideMatchingStrategy matchingStrategy = new LeastActiveDriverStrategy();
```

## Notes
- In-memory storage using Maps (no DB).
- Peak hour fare is applied between 5pm–8pm (local time).
