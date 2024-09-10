package se.lernia.lab;

import java.time.LocalTime;

public record HourlyPrice(LocalTime hour, int price) implements Comparable<HourlyPrice> {

    public String toString() {
        return "Timme " + hour + "-" + (hour.plusHours(1) + ": " + price + " öre");
    }

    public int compareTo(HourlyPrice other) {
        return Integer.compare(this.price, other.price);
    }
}
