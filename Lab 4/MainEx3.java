import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Delivery {
    private static int createdCount = 0;

    private String orderId;
    private String customerName;
    private double distance;
    private double weight;

    public Delivery(String orderId, String customerName, double distance, double weight) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty.");
        }
        if (distance <= 0 || weight <= 0) {
            throw new IllegalArgumentException("Distance and weight must be positive.");
        }
        this.orderId = orderId.trim();
        this.customerName = customerName.trim();
        this.distance = distance;
        this.weight = weight;
        createdCount++;
    }

    public Delivery(String orderId, double distance, double weight) {
        this(orderId, "Guest Customer", distance, weight);
    }

    public static int getCreatedCount() {
        return createdCount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getDistance() {
        return distance;
    }

    public double getWeight() {
        return weight;
    }

    public abstract double calculateShippingFee();

    public void displayInfo() {
        System.out.printf("Order ID: %s | Customer: %s | Distance: %.2f km | Weight: %.2f kg | Fee: %,.0f VND",
                orderId, customerName, distance, weight, calculateShippingFee());
    }
}

class StandardDelivery extends Delivery {
    public StandardDelivery(String orderId, String customerName, double distance, double weight) {
        super(orderId, customerName, distance, weight);
    }

    @Override
    public double calculateShippingFee() {
        return 15000.0 + 3000.0 * getDistance() + 2000.0 * getWeight();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Type: Standard");
    }
}

class ExpressDelivery extends Delivery {
    public ExpressDelivery(String orderId, String customerName, double distance, double weight) {
        super(orderId, customerName, distance, weight);
    }

    @Override
    public double calculateShippingFee() {
        return 30000.0 + 5000.0 * getDistance() + 4000.0 * getWeight();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Type: Express");
    }
}

public class MainEx3 {
    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private static double readDoublePositive(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val > 0) {
                    return val;
                }
                System.out.println("Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static int readIntRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.println("Value must be between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    private static boolean orderExists(List<Delivery> deliveries, String id) {
        for (Delivery d : deliveries) {
            if (d.getOrderId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Delivery> deliveries = new ArrayList<>();

        while (true) {
            System.out.println("\n--- ONLINE DELIVERY MANAGEMENT MENU ---");
            System.out.println("1. Add Delivery Order");
            System.out.println("2. Display All Orders");
            System.out.println("3. Search by Order ID");
            System.out.println("4. Filter Orders Exceeding Fee Threshold");
            System.out.println("5. Report Statistics");
            System.out.println("6. Exit");
            int choice = readIntRange(scanner, "Choose an option: ", 1, 6);

            if (choice == 1) {
                int type = readIntRange(scanner, "Select type (1 = Standard, 2 = Express): ", 1, 2);
                String orderId;
                while (true) {
                    orderId = readNonEmptyString(scanner, "Enter Order ID: ");
                    if (!orderExists(deliveries, orderId)) break;
                    System.out.println("Duplicate Order ID! Try again.");
                }
                String customer = readNonEmptyString(scanner, "Enter Customer Name: ");
                double dist = readDoublePositive(scanner, "Enter Distance in km (> 0): ");
                double weight = readDoublePositive(scanner, "Enter Weight in kg (> 0): ");

                if (type == 1) {
                    deliveries.add(new StandardDelivery(orderId, customer, dist, weight));
                } else {
                    deliveries.add(new ExpressDelivery(orderId, customer, dist, weight));
                }
                System.out.println("Delivery order added. Total created objects: " + Delivery.getCreatedCount());

            } else if (choice == 2) {
                if (deliveries.isEmpty()) {
                    System.out.println("No delivery orders found.");
                } else {
                    for (Delivery d : deliveries) {
                        d.displayInfo();
                    }
                }

            } else if (choice == 3) {
                if (deliveries.isEmpty()) {
                    System.out.println("No orders to search.");
                    continue;
                }
                String searchId = readNonEmptyString(scanner, "Enter Order ID: ");
                boolean found = false;
                for (Delivery d : deliveries) {
                    if (d.getOrderId().equalsIgnoreCase(searchId)) {
                        d.displayInfo();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Order ID '" + searchId + "' not found.");
                }

            } else if (choice == 4) {
                if (deliveries.isEmpty()) {
                    System.out.println("No orders available.");
                    continue;
                }
                double threshold = readDoublePositive(scanner, "Enter fee threshold (VND): ");
                boolean any = false;
                for (Delivery d : deliveries) {
                    if (d.calculateShippingFee() > threshold) {
                        d.displayInfo();
                        any = true;
                    }
                }
                if (!any) {
                    System.out.printf("No orders exceed %,.0f VND.%n", threshold);
                }

            } else if (choice == 5) {
                System.out.println("Static Created Delivery Objects Counter: " + Delivery.getCreatedCount());
                if (deliveries.isEmpty()) {
                    System.out.println("No orders in list to compute revenue.");
                    continue;
                }
                int stdCount = 0;
                int expCount = 0;
                double totalRevenue = 0;
                for (Delivery d : deliveries) {
                    if (d instanceof StandardDelivery) stdCount++;
                    else if (d instanceof ExpressDelivery) expCount++;
                    totalRevenue += d.calculateShippingFee();
                }
                System.out.println("Standard Deliveries: " + stdCount);
                System.out.println("Express Deliveries: " + expCount);
                System.out.printf("Total Shipping Revenue: %,.0f VND%n", totalRevenue);

            } else if (choice == 6) {
                break;
            }
        }
        scanner.close();
    }
}