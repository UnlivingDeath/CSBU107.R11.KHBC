import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Student {
    private String studentId;
    private String fullName;
    private String address;
    private int totalCredits;
    private double gpa;

    public Student(String studentId, String fullName, String address, int totalCredits, double gpa) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.address = address;
        this.totalCredits = totalCredits;
        this.gpa = gpa;
    }

    public double getGpa() {
        return gpa;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public abstract boolean isEligibleForGraduation();
}

class CollegeStudent extends Student {
    private double graduationExamScore;

    public CollegeStudent(String id, String name, String addr, int credits, double gpa, double examScore) {
        super(id, name, addr, credits, gpa);
        this.graduationExamScore = examScore;
    }

    @Override
    public boolean isEligibleForGraduation() {
        return getTotalCredits() >= 120 && getGpa() >= 5.0 && graduationExamScore >= 5.0;
    }
}

class UniversityStudent extends Student {
    private String thesisTitle;
    private double thesisScore;

    public UniversityStudent(String id, String name, String addr, int credits, double gpa, String thesis, double thesisScore) {
        super(id, name, addr, credits, gpa);
        this.thesisTitle = thesis;
        this.thesisScore = thesisScore;
    }

    @Override
    public boolean isEligibleForGraduation() {
        return getTotalCredits() >= 170 && getGpa() >= 5.0 && thesisScore >= 5.0;
    }
}

public class Main1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = new ArrayList<>();

        System.out.print("Enter the number of students: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter program type (1 for College, 2 for University): ");
            int type = Integer.parseInt(scanner.nextLine());
            
            System.out.print("ID: ");
            String id = scanner.nextLine();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Address: ");
            String address = scanner.nextLine();
            System.out.print("Total Credits: ");
            int credits = Integer.parseInt(scanner.nextLine());
            System.out.print("GPA: ");
            double gpa = Double.parseDouble(scanner.nextLine());

            if (type == 1) {
                System.out.print("Graduation Exam Score: ");
                double exam = Double.parseDouble(scanner.nextLine());
                students.add(new CollegeStudent(id, name, address, credits, gpa, exam));
            } else {
                System.out.print("Thesis Title: ");
                String thesis = scanner.nextLine();
                System.out.print("Thesis Score: ");
                double score = Double.parseDouble(scanner.nextLine());
                students.add(new UniversityStudent(id, name, address, credits, gpa, thesis, score));
            }
        }

        int eligibleCount = 0;
        UniversityStudent topUniStudent = null;

        for (Student s : students) {
            if (s.isEligibleForGraduation()) {
                eligibleCount++;
            }
            if (s instanceof UniversityStudent) {
                if (topUniStudent == null || s.getGpa() > topUniStudent.getGpa()) {
                    topUniStudent = (UniversityStudent) s;
                }
            }
        }

        System.out.println("Total eligible students: " + eligibleCount);
        if (topUniStudent != null) {
            System.out.println("Highest University Student GPA: " + topUniStudent.getGpa());
        }
        scanner.close();
    }
}