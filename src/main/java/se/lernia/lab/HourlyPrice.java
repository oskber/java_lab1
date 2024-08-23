package se.lernia.lab;

public class HourlyPrice implements Comparable<HourlyPrice> {
    private final int hour;
    private int price;

    public HourlyPrice(int hour, int price) {
        this.hour = hour;
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public int getPrice() {
        return price;
    }

    public String toString() {
        return "Timme " + hour + "-" + (hour + 1) + ": " + price + " öre";
    }

    public int compareTo(HourlyPrice other) {
        return Integer.compare(this.price, other.price);
    }
}
