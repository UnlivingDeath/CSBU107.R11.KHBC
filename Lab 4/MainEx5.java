import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class CourseRegistration {
    private String courseCode;
    private String courseName;
    private int credits;
    private int registeredStudents;

    public CourseRegistration(String courseCode, String courseName, int credits, int registeredStudents) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be empty.");
        }
        if (credits < 1 || registeredStudents < 0) {
            throw new IllegalArgumentException("Credits must be >= 1 and registered students >= 0.");
        }
        this.courseCode = courseCode.trim();
        this.courseName = courseName.trim();
        this.credits = credits;
        this.registeredStudents = registeredStudents;
    }

    public CourseRegistration(String courseCode, String courseName, int credits) {
        this(courseCode, courseName, credits, 0);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getCredits() {
        return credits;
    }

    public int getRegisteredStudents() {
        return registeredStudents;
    }

    public abstract double tuitionPerStudent();

    public double expectedTotalCollection() {
        return tuitionPerStudent() * registeredStudents;
    }

    public void displayInfo() {
        System.out.printf("Code: %s | Name: %s | Credits: %d | Students: %d | Tuition/Student: %,.0f VND | Expected Total: %,.0f VND",
                courseCode, courseName, credits, registeredStudents, tuitionPerStudent(), expectedTotalCollection());
    }
}

class TheoryCourse extends CourseRegistration {
    public TheoryCourse(String courseCode, String courseName, int credits, int registeredStudents) {
        super(courseCode, courseName, credits, registeredStudents);
    }

    @Override
    public double tuitionPerStudent() {
        return getCredits() * 450000.0;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(" | Type: Theory");
    }
}

class LabCourse extends CourseRegistration {
    private double equipmentFee;

    public LabCourse(String courseCode, String courseName, int credits, int registeredStudents, double equipmentFee) {
        super(courseCode, courseName, credits, registeredStudents);
        if (equipmentFee < 0) {
            throw new IllegalArgumentException("Equipment fee must be non-negative.");
        }
        this.equipmentFee = equipmentFee;
    }

    public double getEquipmentFee() {
        return equipmentFee;
    }

    @Override
    public double tuitionPerStudent() {
        return getCredits() * 600000.0 + equipmentFee;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.printf(" | Type: Lab | Equipment Fee: %,.0f VND%n", equipmentFee);
    }
}

public class MainEx5 {
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

    private static double readDoubleMin(Scanner scanner, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val >= min) {
                    return val;
                }
                System.out.println("Value must be at least " + min + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    private static boolean courseCodeExists(List<CourseRegistration> courses, String code) {
        for (CourseRegistration c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<CourseRegistration> courses = new ArrayList<>();

        while (true) {
            System.out.println("\n--- UNIVERSITY COURSE REGISTRATION MENU ---");
            System.out.println("1. Add Course Registration");
            System.out.println("2. Display All Courses");
            System.out.println("3. Search by Course Code");
            System.out.println("4. Report Tuition Statistics");
            System.out.println("5. Exit");
            int choice = readIntMin(scanner, "Choose an option: ", 1);

            if (choice == 1) {
                int type;
                while (true) {
                    type = readIntMin(scanner, "Select type (1 = TheoryCourse, 2 = LabCourse): ", 1);
                    if (type == 1 || type == 2) break;
                    System.out.println("Please enter 1 or 2.");
                }

                String code;
                while (true) {
                    code = readNonEmptyString(scanner, "Enter Course Code: ");
                    if (!courseCodeExists(courses, code)) break;
                    System.out.println("Duplicate Course Code! Enter a unique code.");
                }
                String name = readNonEmptyString(scanner, "Enter Course Name: ");
                int credits = readIntMin(scanner, "Enter Credits (>= 1): ", 1);
                int students = readIntMin(scanner, "Enter Registered Students (>= 0): ", 0);

                if (type == 1) {
                    courses.add(new TheoryCourse(code, name, credits, students));
                } else {
                    double fee = readDoubleMin(scanner, "Enter Equipment Fee per Student (>= 0): ", 0);
                    courses.add(new LabCourse(code, name, credits, students, fee));
                }
                System.out.println("Course added successfully.");

            } else if (choice == 2) {
                if (courses.isEmpty()) {
                    System.out.println("No courses registered.");
                } else {
                    for (CourseRegistration c : courses) {
                        c.displayInfo();
                    }
                }

            } else if (choice == 3) {
                if (courses.isEmpty()) {
                    System.out.println("No courses to search.");
                    continue;
                }
                String searchCode = readNonEmptyString(scanner, "Enter Course Code: ");
                boolean found = false;
                for (CourseRegistration c : courses) {
                    if (c.getCourseCode().equalsIgnoreCase(searchCode)) {
                        c.displayInfo();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Course code '" + searchCode + "' not found.");
                }

            } else if (choice == 4) {
                if (courses.isEmpty()) {
                    System.out.println("No courses available for statistics.");
                    continue;
                }
                int theoryCount = 0;
                int labCount = 0;
                int totalStudents = 0;
                double totalCollection = 0;
                CourseRegistration largestCourse = courses.get(0);

                for (CourseRegistration c : courses) {
                    if (c instanceof TheoryCourse) theoryCount++;
                    else if (c instanceof LabCourse) labCount++;

                    totalStudents += c.getRegisteredStudents();
                    double collected = c.expectedTotalCollection();
                    totalCollection += collected;

                    if (collected > largestCourse.expectedTotalCollection()) {
                        largestCourse = c;
                    }
                }

                System.out.println("Theory Courses: " + theoryCount);
                System.out.println("Laboratory Courses: " + labCount);
                System.out.printf("Total Expected Collection: %,.0f VND%n", totalCollection);
                System.out.print("Largest Expected Collection Course -> ");
                largestCourse.displayInfo();

                if (totalStudents == 0) {
                    System.out.println("Weighted Average Tuition per Student: N/A");
                } else {
                    System.out.printf("Weighted Average Tuition per Student: %,.2f VND%n",
                            totalCollection / totalStudents);
                }

            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid menu choice.");
            }
        }
        scanner.close();
    }
}