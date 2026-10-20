public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public int max(int a, int b) {
        return Math.max(a, b);
    }

    public double max(double a, double b) {
        return Math.max(a, b);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println(calc.add(5, 10));
        System.out.println(calc.add(2.5, 3.8));
        System.out.println(calc.add(1, 2, 3));
        System.out.println(calc.max(15, 8));
        System.out.println(calc.max(9.9, 14.2));
    }
}