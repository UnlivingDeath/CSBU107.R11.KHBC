public class Rectangle {
    private double width;
    private double height;

    public Rectangle() {
        this.width = 1.0;
        this.height = 1.0;
    }

    public Rectangle(double width, double height) {
        this.width = (width > 0) ? width : 1.0;
        this.height = (height > 0) ? height : 1.0;
    }

    public double area() {
        return width * height;
    }

    public double perimeter() {
        return 2 * (width + height);
    }

    public void displayInfo() {
        System.out.println("Rectangle: " + width + " x " + height + ", Area: " + area() + ", Perimeter: " + perimeter());
    }

    public static void main(String[] args) {
        Rectangle r1 = new Rectangle();
        Rectangle r2 = new Rectangle(5.5, 3.2);
        Rectangle r3 = new Rectangle(-4.0, 7.0);

        r1.displayInfo();
        r2.displayInfo();
        r3.displayInfo();
    }
}
