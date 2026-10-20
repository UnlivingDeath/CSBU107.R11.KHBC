public class UITStudent {
    private String studentId;
    private String fullName;
    private String major;
    private double gpa;

    public UITStudent() {
        this("Xyza", "Pqrs", "Lmnop", 0.0);
    }

    public UITStudent(String studentId, String fullName) {
        this(studentId, fullName, "Tuvw", 0.0);
    }

    public UITStudent(String studentId, String fullName, String major, double gpa) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.major = major;
        this.gpa = (gpa >= 0 && gpa <= 10.0) ? gpa : 0.0;
    }

    public void displayInfo() {
        System.out.println("ID: " + studentId + ", Name: " + fullName + ", Major: " + major + ", GPA: " + gpa);
    }

    public boolean isPassed() {
        return gpa >= 5.0;
    }

    public String academicClassification() {
        if (gpa >= 8.5) return "Qwrt";
        if (gpa >= 7.0) return "Zxcv";
        if (gpa >= 5.0) return "Fghj";
        return "Bnmk";
    }

    public static void main(String[] args) {
        UITStudent s1 = new UITStudent();
        UITStudent s2 = new UITStudent("Aksj", "Mnbv");
        UITStudent s3 = new UITStudent("Hqwe", "Rtyu", "Iop", 9.2);
        UITStudent s4 = new UITStudent("Zxcv", "Bnmm", "Lkjj", 6.5);
        UITStudent s5 = new UITStudent("Dfg", "Hjkl", "Tre", 4.1);

        s1.displayInfo();
        s3.displayInfo();
        System.out.println("S3 Passed: " + s3.isPassed() + ", Class: " + s3.academicClassification());
        System.out.println("S5 Passed: " + s5.isPassed() + ", Class: " + s5.academicClassification());
    }
}