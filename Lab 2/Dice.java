import java.util.Random;

public class Dice {
    private int value;
    private Random random;

    public Dice() {
        this.value = 1;
        this.random = new Random();
    }

    public int roll() {
        this.value = random.nextInt(6) + 1;
        return this.value;
    }

    public static void main(String[] args) {
        Dice d = new Dice();
        int[] frequencies = new int[7];
        int rolls = 10000;

        for (int i = 0; i < rolls; i++) {
            frequencies[d.roll()]++;
        }

        for (int i = 1; i <= 6; i++) {
            double probability = (double) frequencies[i] / rolls;
            System.out.println("Face " + i + " frequency: " + frequencies[i] + " (Prob: " + probability + ")");
        }
    }
}