import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Employee {
    private String id;
    private String fullName;
    private String department;

    public Employee(String id, String fullName, String department) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be empty.");
        }
        this.id = id.trim();
        this.fullName = fullName.trim();
        this.department = department.trim();
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public abstract String getType();
    public abstract double calculateSalary();

    public void displayInfo() {
        System.out.printf("ID: %s | Name: %s | Type: %s | Dept: %s | Salary: %,.0f VND%n",
                id, fullName, getType(), department, calculateSalary());
    }
}

class FullTimeEmployee extends Employee {
    private double baseSalary;
    private double allowance;
    private int workdays;

    public FullTimeEmployee(String id, String fullName, String department,
                            double baseSalary, double allowance, int workdays) {
        super(id, fullName, department);
        if (baseSalary < 0 || allowance < 0 || workdays < 0 || workdays > 31) {
            throw new IllegalArgumentException("Invalid salary, allowance, or workdays.");
        }
        this.baseSalary = baseSalary;
        this.allowance = allowance;
        this.workdays = workdays;
    }

    @Override
    public String getType() {
        return "Full-Time";
    }

    @Override
    public double calculateSalary() {
        return baseSalary * (Math.min(workdays, 26) / 26.0) + allowance;
    }
}

class PartTimeEmployee extends Employee {
    private double hoursWorked;
    private double hourlyRate;

    public PartTimeEmployee(String id, String fullName, String department,
                            double hoursWorked, double hourlyRate) {
        super(id, fullName, department);
        if (hoursWorked < 0 || hourlyRate < 0) {
            throw new IllegalArgumentException("Hours and hourly rate must be non-negative.");
        }
        this.hoursWorked = hoursWorked;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String getType() {
        return "Part-Time";
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }
}

public class MainEx2 {
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

    private static double readDoubleMin(Scanner scanner, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val >= min) {
                    return val;
                }
                System.out.println("Value must be >= " + min + ".");
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

    public static Employee findEmployee(List<Employee> employees, String id) {
        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(id)) {
                return e;
            }
        }
        return null;
    }

    public static List<Employee> findEmployee(List<Employee> employees, String name, String department) {
        List<Employee> matches = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getFullName().equalsIgnoreCase(name) && e.getDepartment().equalsIgnoreCase(department)) {
                matches.add(e);
            }
        }
        return matches;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Employee> employees = new ArrayList<>();

        while (true) {
            System.out.println("\n--- EMPLOYEE PAYROLL MENU ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Payroll");
            System.out.println("3. Search Employees");
            System.out.println("4. Payroll Statistics");
            System.out.println("5. Exit");
            int choice = readIntRange(scanner, "Choose an option: ", 1, 5);

            if (choice == 1) {
                int type = readIntRange(scanner, "Select type (1 = Full-Time, 2 = Part-Time): ", 1, 2);
                String id;
                while (true) {
                    id = readNonEmptyString(scanner, "Enter Employee ID: ");
                    if (findEmployee(employees, id) == null) break;
                    System.out.println("Duplicate ID! Enter a unique ID.");
                }
                String name = readNonEmptyString(scanner, "Enter Full Name: ");
                String dept = readNonEmptyString(scanner, "Enter Department: ");

                if (type == 1) {
                    double base = readDoubleMin(scanner, "Enter Base Salary (>= 0): ", 0);
                    double allowance = readDoubleMin(scanner, "Enter Allowance (>= 0): ", 0);
                    int workdays = readIntRange(scanner, "Enter Workdays (0 - 31): ", 0, 31);
                    employees.add(new FullTimeEmployee(id, name, dept, base, allowance, workdays));
                } else {
                    double hours = readDoubleMin(scanner, "Enter Hours Worked (>= 0): ", 0);
                    double rate = readDoubleMin(scanner, "Enter Hourly Rate (>= 0): ", 0);
                    employees.add(new PartTimeEmployee(id, name, dept, hours, rate));
                }
                System.out.println("Employee added successfully.");

            } else if (choice == 2) {
                if (employees.isEmpty()) {
                    System.out.println("Payroll list is empty.");
                } else {
                    for (Employee e : employees) {
                        e.displayInfo();
                    }
                }

            } else if (choice == 3) {
                if (employees.isEmpty()) {
                    System.out.println("No employees to search.");
                    continue;
                }
                int sub = readIntRange(scanner, "Search by: 1 = ID, 2 = Name & Department, 3 = Department: ", 1, 3);
                if (sub == 1) {
                    String id = readNonEmptyString(scanner, "Enter ID: ");
                    Employee found = findEmployee(employees, id);
                    if (found != null) {
                        found.displayInfo();
                    } else {
                        System.out.println("Employee not found.");
                    }
                } else if (sub == 2) {
                    String name = readNonEmptyString(scanner, "Enter Full Name: ");
                    String dept = readNonEmptyString(scanner, "Enter Department: ");
                    List<Employee> results = findEmployee(employees, name, dept);
                    if (results.isEmpty()) {
                        System.out.println("No matching employees found.");
                    } else {
                        for (Employee e : results) e.displayInfo();
                    }
                } else {
                    String dept = readNonEmptyString(scanner, "Enter Department: ");
                    boolean any = false;
                    for (Employee e : employees) {
                        if (e.getDepartment().equalsIgnoreCase(dept)) {
                            e.displayInfo();
                            any = true;
                        }
                    }
                    if (!any) {
                        System.out.println("No employees found in department '" + dept + "'.");
                    }
                }

            } else if (choice == 4) {
                if (employees.isEmpty()) {
                    System.out.println("No employees available for statistics.");
                    continue;
                }
                double totalPayroll = 0;
                Employee highest = employees.get(0);
                for (Employee e : employees) {
                    double sal = e.calculateSalary();
                    totalPayroll += sal;
                    if (sal > highest.calculateSalary()) {
                        highest = e;
                    }
                }
                System.out.printf("Total Monthly Payroll: %,.0f VND%n", totalPayroll);
                System.out.print("Highest-Paid Employee -> ");
                highest.displayInfo();

            } else if (choice == 5) {
                break;
            }
        }
        scanner.close();
    }
}