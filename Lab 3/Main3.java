import java.util.Scanner;

class Person {
    private String fullName;
    private int age;

    public Person(String name, int age) {
        this.fullName = name;
        this.age = age;
    }
}

class Guest extends Person {
    private String cccd;

    public Guest(String cccd, String name, int age) {
        super(name, age);
        this.cccd = cccd;
    }
}

class Staff extends Person {
    private String licenseId;
    private String employeeId;

    public Staff(String license, String empId, String name, int age) {
        super(name, age);
        this.licenseId = license;
        this.employeeId = empId;
    }
}

class Room {
    private String roomType;
    private double dailyPrice;
    private Guest currentGuest;

    public Room(String roomType) {
        this.roomType = roomType;
        switch (roomType) {
            case "VIP1": this.dailyPrice = 900000; break;
            case "VIP2": this.dailyPrice = 700000; break;
            case "VIP3": this.dailyPrice = 500000; break;
            default: this.dailyPrice = 0;
        }
    }

    public void addGuest(Guest guest) { 
        this.currentGuest = guest; 
    }
    
    public void removeGuest() { 
        this.currentGuest = null; 
    }
    
    public double calculateRentalCost(int days) {
        return dailyPrice * days;
    }
}

public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Guest CCCD: ");
        String cccd = scanner.nextLine();
        System.out.print("Enter Guest Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Guest Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        Guest guest = new Guest(cccd, name, age);

        System.out.print("Enter Room Type (VIP1, VIP2, VIP3): ");
        String roomType = scanner.nextLine();
        Room room = new Room(roomType);
        room.addGuest(guest);

        System.out.print("Enter number of rental days: ");
        int days = Integer.parseInt(scanner.nextLine());
        
        System.out.println("Total Rental Cost: " + room.calculateRentalCost(days) + " VND");
        room.removeGuest();
        
        scanner.close();
    }
}