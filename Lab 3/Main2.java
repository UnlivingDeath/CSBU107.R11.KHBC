import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Ticket {
    private String ticketId;
    private String holderName;
    private int birthYear;
    private int gamesPlayed;

    public Ticket(String id, String name, int year, int games) {
        this.ticketId = id;
        this.holderName = name;
        this.birthYear = year;
        this.gamesPlayed = games;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public abstract double calculatePrice();
}

class FullPackageTicket extends Ticket {
    public FullPackageTicket(String id, String name, int year, int games) {
        super(id, name, year, games);
    }

    @Override
    public double calculatePrice() {
        return 200000.0;
    }
}

class PartialTicket extends Ticket {
    public PartialTicket(String id, String name, int year, int games) {
        super(id, name, year, games);
    }

    @Override
    public double calculatePrice() {
        return 70000.0 + (20000.0 * getGamesPlayed());
    }
}

public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ticket> tickets = new ArrayList<>();

        System.out.print("Enter the number of tickets: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter ticket type (1 for Full Package, 2 for Partial): ");
            int type = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Ticket ID: ");
            String id = scanner.nextLine();
            System.out.print("Holder Name: ");
            String name = scanner.nextLine();
            System.out.print("Birth Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Games Played: ");
            int games = Integer.parseInt(scanner.nextLine());

            if (type == 1) {
                tickets.add(new FullPackageTicket(id, name, year, games));
            } else {
                tickets.add(new PartialTicket(id, name, year, games));
            }
        }

        double totalRevenue = 0;
        int partialCount = 0;

        for (Ticket t : tickets) {
            totalRevenue += t.calculatePrice();
            if (t instanceof PartialTicket) {
                partialCount++;
            }
        }

        System.out.println("Total Revenue: " + totalRevenue + " VND");
        System.out.println("Partial Tickets Sold: " + partialCount);
        scanner.close();
    }
}