import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer {
    private String id, name, address, phone;
    
    public Customer(String id, String name, String addr, String phone) {
        this.id = id; 
        this.name = name; 
        this.address = addr; 
        this.phone = phone;
    }
    
    public String getName() {
        return name;
    }
}

class Product {
    private String id, type;
    private int volume;
    private double pricePerMl;

    public Product(String id, String type, int volume, double pricePerMl) {
        this.id = id; 
        this.type = type; 
        this.volume = volume; 
        this.pricePerMl = pricePerMl;
    }
    
    public String getType() {
        return type;
    }
    
    public double getPrice() { 
        return volume * pricePerMl; 
    }
}

abstract class Package {
    private List<Product> products = new ArrayList<>();
    
    public void addProduct(Product p) { 
        products.add(p); 
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public double calculateTotalPrice() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }
    public abstract boolean isValid();
}

class RomanticPackage extends Package {
    @Override
    public boolean isValid() {
        for (Product p : getProducts()) {
            if (p.getType().equals("Rose Perfume")) {
                return true;
            }
        }
        return false;
    }
}

class FreshAirPackage extends Package {
    @Override
    public boolean isValid() {
        for (Product p : getProducts()) {
            if (p.getType().contains("Oily Hair Shampoo")) {
                return true;
            }
        }
        return false;
    }
}

class Order {
    private String orderId, invoiceDate;
    private Customer customer;
    private Package pkg;

    public Order(String orderId, Customer customer, String invoiceDate, Package pkg) {
        this.orderId = orderId; 
        this.customer = customer; 
        this.invoiceDate = invoiceDate; 
        this.pkg = pkg;
    }
    
    public double getOrderPrice() { 
        return pkg.calculateTotalPrice(); 
    }
    
    public String getOrderDetails() {
        return "Order: " + orderId + ", Customer: " + customer.getName() + ", Total Price: " + getOrderPrice() + " VND";
    }
}

public class Main5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Order> orders = new ArrayList<>();

        System.out.print("Enter number of orders: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Order ID: ");
            String orderId = scanner.nextLine();
            System.out.print("Invoice Date: ");
            String date = scanner.nextLine();
            
            System.out.print("Customer ID: ");
            String cId = scanner.nextLine();
            System.out.print("Customer Name: ");
            String cName = scanner.nextLine();
            System.out.print("Customer Address: ");
            String cAddr = scanner.nextLine();
            System.out.print("Customer Phone: ");
            String cPhone = scanner.nextLine();
            
            Customer customer = new Customer(cId, cName, cAddr, cPhone);
            
            System.out.print("Package Type (1 for Romantic, 2 for Fresh-Air): ");
            int pkgType = Integer.parseInt(scanner.nextLine());
            Package pkg = (pkgType == 1) ? new RomanticPackage() : new FreshAirPackage();
            
            System.out.print("Does the package include Rose Perfume? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                pkg.addProduct(new Product("P1", "Rose Perfume", 50, 20000));
            }
            System.out.print("Does the package include Oily Hair Shampoo? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                pkg.addProduct(new Product("P2", "Oily Hair Shampoo - Standard 1", 100, 30000));
            }
            
            if (pkg.isValid()) {
                orders.add(new Order(orderId, customer, date, pkg));
                System.out.println("Order added successfully.");
            } else {
                System.out.println("Invalid package configuration. Order discarded.");
            }
        }

        try (FileWriter writer = new FileWriter("order_list.txt")) {
            for (Order o : orders) {
                System.out.println(o.getOrderDetails());
                writer.write(o.getOrderDetails() + "\n");
            }
            System.out.println("Orders saved to order_list.txt");
        } catch (IOException e) {
            System.out.println("I/O error occurred: " + e.getMessage());
        }
        scanner.close();
    }
}