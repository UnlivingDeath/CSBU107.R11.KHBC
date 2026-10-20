public class Time {
    private int hour;
    private int minute;
    private int second;

    public Time() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
    }

    public Time(int hour, int minute, int second) {
        this.hour = (hour >= 0 && hour <= 23) ? hour : 0;
        this.minute = (minute >= 0 && minute <= 59) ? minute : 0;
        this.second = (second >= 0 && second <= 59) ? second : 0;
    }

    public void addSeconds(int seconds) {
        int total = hour * 3600 + minute * 60 + this.second + seconds;
        total = total % 86400;
        if (total < 0) total += 86400;
        this.hour = total / 3600;
        this.minute = (total % 3600) / 60;
        this.second = total % 60;
    }

    public void subtractSeconds(int seconds) {
        addSeconds(-seconds);
    }

    public void display() {
        System.out.printf("%02d:%02d:%02d\n", hour, minute, second);
    }

    public static void main(String[] args) {
        Time t1 = new Time(23, 59, 50);
        t1.addSeconds(20);
        t1.display();

        Time t2 = new Time(0, 0, 10);
        t2.subtractSeconds(20);
        t2.display();
    }
}