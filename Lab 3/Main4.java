import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class Singer {
    private String fullName;
    private int yearsOfExperience;
    private int albumsSold;
    private int performances;

    public Singer(String name, int years, int albums, int perfs) {
        this.fullName = name;
        this.yearsOfExperience = years;
        this.albumsSold = albums;
        this.performances = perfs;
    }

    public String getFullName() {
        return fullName;
    }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public int getAlbumsSold() { return albumsSold; }
    public int getPerformances() { return performances; }

    public abstract double calculateSalary();
}

class RegularSinger extends Singer {
    public RegularSinger(String name, int years, int albums, int perfs) {
        super(name, years, albums, perfs);
    }

    @Override
    public double calculateSalary() {
        return 3000000 + (500000.0 * getYearsOfExperience()) + (1000.0 * getAlbumsSold()) + (200000.0 * getPerformances());
    }
}

class FamousSinger extends Singer {
    private int gameShowsAttended;

    public FamousSinger(String name, int years, int albums, int perfs, int gameShows) {
        super(name, years, albums, perfs);
        this.gameShowsAttended = gameShows;
    }

    @Override
    public double calculateSalary() {
        return 5000000 + (500000.0 * getYearsOfExperience()) + (1200.0 * getAlbumsSold()) + (500000.0 * getPerformances()) + (500000.0 * gameShowsAttended);
    }
}

public class Main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Singer> singers = new ArrayList<>();

        System.out.print("Enter number of singers: ");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.print("Enter type (1 for Regular, 2 for Famous): ");
            int type = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Years of Experience: ");
            int years = Integer.parseInt(scanner.nextLine());
            System.out.print("Albums Sold: ");
            int albums = Integer.parseInt(scanner.nextLine());
            System.out.print("Performances: ");
            int perfs = Integer.parseInt(scanner.nextLine());

            if (type == 1) {
                singers.add(new RegularSinger(name, years, albums, perfs));
            } else {
                System.out.print("Game Shows Attended: ");
                int gameShows = Integer.parseInt(scanner.nextLine());
                singers.add(new FamousSinger(name, years, albums, perfs, gameShows));
            }
        }

        Singer highestPaid = null;
        double maxSalary = 0;

        for (Singer s : singers) {
            double salary = s.calculateSalary();
            System.out.println(s.getFullName() + " Salary: " + salary + " VND");
            if (highestPaid == null || salary > maxSalary) {
                highestPaid = s;
                maxSalary = salary;
            }
        }

        if (highestPaid != null) {
            System.out.println("Highest Paid Singer: " + highestPaid.getFullName());
        }
        scanner.close();
    }
}