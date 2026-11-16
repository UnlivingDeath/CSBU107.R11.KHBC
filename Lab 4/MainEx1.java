import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class LibraryItem {
    private String id;
    private String title;
    private String author;
    private int daysBorrowed;

    public LibraryItem(String id, String title, String author, int daysBorrowed) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be empty.");
        }
        if (daysBorrowed < 1) {
            throw new IllegalArgumentException("Days borrowed must be at least 1.");
        }
        this.id = id.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.daysBorrowed = daysBorrowed;
    }

    public LibraryItem(String id, String title, String author) {
        this(id, title, author, 1);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getDaysBorrowed() {
        return daysBorrowed;
    }

    public abstract double calculateFee();

    public void displayInfo() {
        System.out.printf("ID: %s | Title: %s | Author: %s | Days: %d | Fee: %,.0f VND",
                id, title, author, daysBorrowed, calculateFee());
    }
}

class PrintedBook extends LibraryItem {
    private int pages;

    public PrintedBook(String id, String title, String author, int daysBorrowed, int pages) {
        super(id, title, author, daysBorrowed);
        if (pages < 1) {
            throw new IllegalArgumentException("Pages must be at least 1.");
        }
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public double calculateFee() {
        return 5000.0 * getDaysBorrowed() + 10.0 * pages;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf(" | Type: PrintedBook | Pages: %d%n", pages);
    }
}

class Magazine extends LibraryItem {
    private int issueNumber;

    public Magazine(String id, String title, String author, int daysBorrowed, int issueNumber) {
        super(id, title, author, daysBorrowed);
        if (issueNumber < 1) {
            throw new IllegalArgumentException("Issue number must be at least 1.");
        }
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public double calculateFee() {
        return 3000.0 * getDaysBorrowed() + 2000.0;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf(" | Type: Magazine | Issue: %d%n", issueNumber);
    }
}

public class MainEx1 {
    private static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
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

    private static boolean idExists(List<LibraryItem> items, String id) {
        for (LibraryItem item : items) {
            if (item.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<LibraryItem> items = new ArrayList<>();

        while (true) {
            System.out.println("\n--- LIBRARY BORROWING MENU ---");
            System.out.println("1. Add Library Item");
            System.out.println("2. Display All Items");
            System.out.println("3. Search Item by ID");
            System.out.println("4. Report Statistics");
            System.out.println("5. Exit");
            int choice = readIntMin(scanner, "Choose an option: ", 1);

            if (choice == 1) {
                int type;
                while (true) {
                    type = readIntMin(scanner, "Select type (1 = PrintedBook, 2 = Magazine): ", 1);
                    if (type == 1 || type == 2) break;
                    System.out.println("Please enter 1 or 2.");
                }

                String id;
                while (true) {
                    id = readNonEmptyString(scanner, "Enter Item ID: ");
                    if (!idExists(items, id)) break;
                    System.out.println("Duplicate ID! Enter a unique ID.");
                }

                String title = readNonEmptyString(scanner, "Enter Title: ");
                String author = readNonEmptyString(scanner, "Enter Author: ");
                int days = readIntMin(scanner, "Enter Days Borrowed (>= 1): ", 1);

                if (type == 1) {
                    int pages = readIntMin(scanner, "Enter Number of Pages (>= 1): ", 1);
                    items.add(new PrintedBook(id, title, author, days, pages));
                } else {
                    int issue = readIntMin(scanner, "Enter Issue Number (>= 1): ", 1);
                    items.add(new Magazine(id, title, author, days, issue));
                }
                System.out.println("Item added successfully.");

            } else if (choice == 2) {
                if (items.isEmpty()) {
                    System.out.println("No library items in the list.");
                } else {
                    for (LibraryItem item : items) {
                        item.displayInfo();
                    }
                }

            } else if (choice == 3) {
                if (items.isEmpty()) {
                    System.out.println("No library items to search.");
                    continue;
                }
                String searchId = readNonEmptyString(scanner, "Enter ID to search: ");
                boolean found = false;
                for (LibraryItem item : items) {
                    if (item.getId().equalsIgnoreCase(searchId)) {
                        item.displayInfo();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Item with ID '" + searchId + "' not found.");
                }

            } else if (choice == 4) {
                if (items.isEmpty()) {
                    System.out.println("No items available for statistics.");
                    continue;
                }
                int bookCount = 0;
                int magCount = 0;
                double totalRevenue = 0;
                LibraryItem highestItem = items.get(0);

                for (LibraryItem item : items) {
                    if (item instanceof PrintedBook) {
                        bookCount++;
                    } else if (item instanceof Magazine) {
                        magCount++;
                    }
                    double fee = item.calculateFee();
                    totalRevenue += fee;
                    if (fee > highestItem.calculateFee()) {
                        highestItem = item;
                    }
                }

                System.out.println("PrintedBooks count: " + bookCount);
                System.out.println("Magazines count: " + magCount);
                System.out.printf("Total Borrowing Revenue: %,.0f VND%n", totalRevenue);
                System.out.print("Highest Fee Item -> ");
                highestItem.displayInfo();

            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid menu choice.");
            }
        }
        scanner.close();
    }
}