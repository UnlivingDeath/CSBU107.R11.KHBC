public class Circle {
    private double centerX;
    private double centerY;
    private double radius;

    public Circle() {
        this.centerX = 0.0;
        this.centerY = 0.0;
        this.radius = 1.0;
    }

    public Circle(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = Math.max(radius, 0.0);
    }

    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public boolean contains(double x, double y) {
        double distanceSquared = Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2);
        return distanceSquared <= Math.pow(radius, 2);
    }

    public static void main(String[] args) {
        Circle c1 = new Circle();
        Circle c2 = new Circle(3.0, 4.0, 5.0);

        System.out.println("C1 Area: " + c1.area());
        System.out.println("C2 contains (3, 9): " + c2.contains(3.0, 9.0));
        System.out.println("C2 contains (10, 10): " + c2.contains(10.0, 10.0));
    }
}