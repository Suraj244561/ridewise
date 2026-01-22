package app;

import model.*;
import service.DriverService;
import service.RideService;
import service.RiderService;
import strategy.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();

        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy baseFare = new DefaultFareStrategy(30, 10);
        FareStrategy fareStrategy = new PeakHourFareStrategy(baseFare, 1.5);

        RideService rideService = new RideService(riderService, driverService, matchingStrategy, fareStrategy);

        while (true) {
            printMenu();
            int choice = readInt("Choose option: ");

            try {
                switch (choice) {
                    case 1 -> addRider(riderService);
                    case 2 -> addDriver(driverService);
                    case 3 -> viewAvailableDrivers(driverService);
                    case 4 -> requestRide(rideService);
                    case 5 -> completeRide(rideService);
                    case 6 -> cancelRide(rideService);
                    case 7 -> updateDriverAvailability(driverService);
                    case 8 -> viewRides(rideService);
                    case 9 -> {
                        System.out.println("Bye!");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("==== RideWise Menu ====");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. Cancel Ride");
        System.out.println("7. Update Driver Availability");
        System.out.println("8. View Rides");
        System.out.println("9. Exit");
    }

    private static void addRider(RiderService riderService) {
        String name = readLine("Rider name: ");
        String loc = readLine("Rider location: ");
        Rider r = riderService.registerRider(name, loc);
        System.out.println("Added: " + r);
    }

    private static void addDriver(DriverService driverService) {
        String name = readLine("Driver name: ");
        String loc = readLine("Driver location: ");
        VehicleType type = readVehicleType("Vehicle type (BIKE/AUTO/CAR): ");
        Driver d = driverService.registerDriver(name, loc, type);
        System.out.println("Added: " + d);
    }

    private static void viewAvailableDrivers(DriverService driverService) {
        List<Driver> list = driverService.listAvailableDrivers();
        if (list.isEmpty()) {
            System.out.println("No drivers available.");
            return;
        }
        list.forEach(System.out::println);
    }

    private static void requestRide(RideService rideService) {
        int riderId = readInt("Rider ID: ");
        double distance = readDouble("Distance (km): ");
        Ride ride = rideService.requestRide(riderId, distance);

        System.out.println("Ride created: " + ride);
        if (ride.getStatus() == RideStatus.ASSIGNED) {
            double est = rideService.estimateFare(ride.getId());
            System.out.println("Estimated fare: " + est);
        } else {
            System.out.println("Ride could not be assigned, status = " + ride.getStatus());
        }
    }

    private static void completeRide(RideService rideService) {
        int rideId = readInt("Ride ID to complete: ");
        FareReceipt receipt = rideService.completeRide(rideId);
        System.out.println("Completed. Receipt: " + receipt);
    }

    private static void cancelRide(RideService rideService) {
        int rideId = readInt("Ride ID to cancel: ");
        rideService.cancelRide(rideId);
        System.out.println("Cancelled. Current ride: " + rideService.getRideById(rideId));
    }

    private static void updateDriverAvailability(DriverService driverService) {
        int driverId = readInt("Driver ID: ");
        int val = readInt("Available? (1 = Yes, 0 = No): ");
        boolean available = (val == 1);
        driverService.setAvailability(driverId, available);
        System.out.println("Updated: " + driverService.getDriverById(driverId));
    }

    private static void viewRides(RideService rideService) {
        List<Ride> rides = rideService.getAllRides();
        if (rides.isEmpty()) {
            System.out.println("No rides yet.");
            return;
        }
        rides.forEach(System.out::println);
    }

    // ---------- Input helpers ----------
    private static int readInt(String msg) {
        System.out.print(msg);
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid integer: ");
            sc.next();
        }
        int val = sc.nextInt();
        sc.nextLine();
        return val;
    }

    private static double readDouble(String msg) {
        System.out.print(msg);
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        double val = sc.nextDouble();
        sc.nextLine();
        return val;
    }

    private static String readLine(String msg) {
        System.out.print(msg);
        String s = sc.nextLine();
        while (s.trim().isEmpty()) {
            System.out.print("Cannot be empty. " + msg);
            s = sc.nextLine();
        }
        return s.trim();
    }

    private static VehicleType readVehicleType(String msg) {
        while (true) {
            String s = readLine(msg).toUpperCase();
            try {
                return VehicleType.valueOf(s);
            } catch (Exception e) {
                System.out.println("Invalid type. Use BIKE/AUTO/CAR.");
            }
        }
    }
}
