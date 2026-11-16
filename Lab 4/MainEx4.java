import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Ticket {
    private String ticketId;
    private String movieTitle;
    private int quantity;

    public Ticket(String ticketId, String movieTitle, int quantity) {
        if (ticketId == null || ticketId.trim().isEmpty()) {
            throw new IllegalArgumentException("Ticket ID cannot be empty.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        this.ticketId = ticketId.trim();
        this.movieTitle = movieTitle.trim();
        this.quantity = quantity;
    }

    public Ticket(String ticketId, String movieTitle) {
        this(ticketId, movieTitle, 1);
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract double getUnitPrice();
    public abstract double getDiscountAmount();
    public abstract double calculateTotalPrice();

    public void displayInfo() {
        System.out.printf("ID: %s | Movie: %s | Qty: %d | Unit: %,.0f VND | Discount: %,.0f VND | Final: %,.0f VND",
                ticketId, movieTitle, quantity, getUnitPrice(), getDiscountAmount(), calculateTotalPrice());
    }
}

class RegularTicket extends Ticket {
    public RegularTicket(String ticketId, String movieTitle, int quantity) {
        super(ticketId, movieTitle, quantity);
    }

    public RegularTicket(String ticketId, String movieTitle) {
        super(ticketId, movieTitle);
    }

    @Override
    public double getUnitPrice() {
        return 80000.0;
    }

    @Override
    public double getDiscountAmount() {
        double subtotal = getUnitPrice() * getQuantity();
        return (getQuantity() >= 5) ? subtotal * 0.10 : 0.0;
    }

    @Override
    public double calculateTotalPrice() {
        return (getUnitPrice() * getQuantity()) - getDiscountAmount();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Type: Regular");
    }
}

class VIPTicket extends Ticket {
    public VIPTicket(String ticketId, String movieTitle, int quantity) {
        super(ticketId, movieTitle, quantity);
    }

    public VIPTicket(String ticketId, String movieTitle) {
        super(ticketId, movieTitle);
    }

    @Override
    public double getUnitPrice() {
        return 150000.0;
    }

    @Override
    public double getDiscountAmount() {
        double subtotal = getUnitPrice() * getQuantity();
        return (getQuantity() >= 3) ? subtotal * 0.15 : 0.0;
    }

    @Override
    public double calculateTotalPrice() {
        return (getUnitPrice() * getQuantity()) - getDiscountAmount();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Type: VIP");
    }
}

public class MainEx4 {
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

    private static int readIntMin(Scanner scanner, String prompt, int min) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min) {
                    return val;
                }
                System.out.println("Value must be at least " + min + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    private static boolean ticketIdExists(List<Ticket> tickets, String id) {
        for (Ticket t : tickets) {
            if (t.getTicketId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ticket> tickets = new ArrayList<>();

        while (true) {
            System.out.println("\n--- CINEMA TICKET SALES MENU ---");
            System.out.println("1. Add Ticket Purchase");
            System.out.println("2. Display All Purchases");
            System.out.println("3. Search Purchases by Movie Title");
            System.out.println("4. Report Revenue Statistics");
            System.out.println("5. Display Purchases Sorted (Highest to Lowest Total)");
            System.out.println("6. Exit");
            int choice = readIntMin(scanner, "Choose an option: ", 1);

            if (choice == 1) {
                int type;
                while (true) {
                    type = readIntMin(scanner, "Select type (1 = Regular, 2 = VIP): ", 1);
                    if (type == 1 || type == 2) break;
                    System.out.println("Please enter 1 or 2.");
                }

                String id;
                while (true) {
                    id = readNonEmptyString(scanner, "Enter Ticket ID: ");
                    if (!ticketIdExists(tickets, id)) break;
                    System.out.println("Duplicate Ticket ID! Enter a unique ID.");
                }
                String movie = readNonEmptyString(scanner, "Enter Movie Title: ");
                int qty = readIntMin(scanner, "Enter Quantity (>= 1): ", 1);

                if (type == 1) {
                    tickets.add(new RegularTicket(id, movie, qty));
                } else {
                    tickets.add(new VIPTicket(id, movie, qty));
                }
                System.out.println("Purchase recorded.");

            } else if (choice == 2) {
                if (tickets.isEmpty()) {
                    System.out.println("No ticket purchases recorded.");
                } else {
                    for (Ticket t : tickets) {
                        t.displayInfo();
                    }
                }

            } else if (choice == 3) {
                if (tickets.isEmpty()) {
                    System.out.println("No purchases to search.");
                    continue;
                }
                String query = readNonEmptyString(scanner, "Enter Movie Title to search: ");
                boolean found = false;
                for (Ticket t : tickets) {
                    if (t.getMovieTitle().equalsIgnoreCase(query)) {
                        t.displayInfo();
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No purchases found for movie '" + query + "'.");
                }

            } else if (choice == 4) {
                if (tickets.isEmpty()) {
                    System.out.println("No purchases available for statistics.");
                    continue;
                }
                double totalRevenue = 0;
                List<String> distinctMovies = new ArrayList<>();
                List<Double> movieRevenues = new ArrayList<>();

                for (Ticket t : tickets) {
                    double price = t.calculateTotalPrice();
                    totalRevenue += price;

                    int idx = -1;
                    for (int i = 0; i < distinctMovies.size(); i++) {
                        if (distinctMovies.get(i).equalsIgnoreCase(t.getMovieTitle())) {
                            idx = i;
                            break;
                        }
                    }
                    if (idx == -1) {
                        distinctMovies.add(t.getMovieTitle());
                        movieRevenues.add(price);
                    } else {
                        movieRevenues.set(idx, movieRevenues.get(idx) + price);
                    }
                }

                int bestIdx = 0;
                for (int i = 1; i < movieRevenues.size(); i++) {
                    if (movieRevenues.get(i) > movieRevenues.get(bestIdx)) {
                        bestIdx = i;
                    }
                }

                System.out.printf("Total Cinema Revenue: %,.0f VND%n", totalRevenue);
                System.out.printf("Top Movie by Revenue: %s (%,.0f VND)%n",
                        distinctMovies.get(bestIdx), movieRevenues.get(bestIdx));

            } else if (choice == 5) {
                if (tickets.isEmpty()) {
                    System.out.println("No purchases to sort.");
                    continue;
                }
                List<Ticket> sorted = new ArrayList<>(tickets);
                for (int i = 0; i < sorted.size() - 1; i++) {
                    int maxIdx = i;
                    for (int j = i + 1; j < sorted.size(); j++) {
                        if (sorted.get(j).calculateTotalPrice() > sorted.get(maxIdx).calculateTotalPrice()) {
                            maxIdx = j;
                        }
                    }
                    Ticket temp = sorted.get(i);
                    sorted.set(i, sorted.get(maxIdx));
                    sorted.set(maxIdx, temp);
                }
                for (Ticket t : sorted) {
                    t.displayInfo();
                }

            } else if (choice == 6) {
                break;
            } else {
                System.out.println("Invalid menu choice.");
            }
        }
        scanner.close();
    }
}