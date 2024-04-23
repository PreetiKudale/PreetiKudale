import java.util.Scanner;

public class ElevatorSimulation {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Number of floors
        final int FLOORS = 10;

        // Current floor
        int currentFloor = 1;

        while (true) {
            System.out.println("Current Floor: " + currentFloor);
            System.out.print("Enter the floor number to go to (1-" + FLOORS + ", 0 to exit): ");

            // Get user input
            int destination = scanner.nextInt();

            // Check for exit condition
            if (destination == 0) {
                System.out.println("Exiting elevator simulation. Goodbye!");
                break;
            }

            // Check if the destination is valid
            if (destination < 1 || destination > FLOORS) {
                System.out.println("Invalid floor. Please enter a floor between 1 and " + FLOORS + ".");
                continue;
            }

            // Move the elevator to the destination floor
            moveElevator(currentFloor, destination);
            currentFloor = destination;
        }

        scanner.close();
    }

    private static void moveElevator(int currentFloor, int destination) {
        System.out.println("Moving from floor " + currentFloor + " to floor " + destination);
        // Additional logic for moving the elevator (not implemented in this basic example)
    }
}

