package se.lernia.lab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.io.*;


public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static List<HourlyPrice> hourlyPrices = new ArrayList<>();


    public static void main(String[] args) {
        String selection = "";

        while (!selection.equalsIgnoreCase("e")) {
            selection = menu();
            switch (selection.toLowerCase()) {
                case "1":
                    handleInput();
                    break;
                case "2":
                    handleMinMaxMid();
                    break;
                case "3":
                    handleSort();
                    break;
                case "4":
                    handleBestLoadingTime(hourlyPrices);
                    break;
                case "5":
                    handleElectricityPrices();
                    break;
                case "e":
                    System.out.println("Programmet avslutas.");
                    break;
                default:
                    System.out.println("Ogiltigt val, försök igen.");

            }
        }
    }

    public static String menu() {

        System.out.println();
        System.out.println("Elpriser");
        System.out.println("========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa laddningstid (4h)");
        System.out.println("5. Elpriser för elområde SE1");
        System.out.println("e. Avsluta");

        return sc.next();

    }

    public static void handleInput() {
        hourlyPrices.clear();
        for (int i = 0; i < 24; i++) {
            int price = getPriceForHour(i);
            hourlyPrices.add(new HourlyPrice(i, price));
        }
        System.out.println("Priser för dygnet har registrerats");

    }

    public static int getPriceForHour(int hour) {
        while (true) {
            System.out.print("Ange priset i öre för timmen " + hour + "-" + (hour + 1) + ": ");
            String input = sc.next();

            try {
                int price = Integer.parseInt(input);
                if (price < 0) {
                    System.out.println("Priset kan inte vara negativt. Försök igen.");
                } else {
                    return price;
                }
            } catch (NumberFormatException e) {
                System.out.println("Ogiltigt värde, ange ett heltal i öre.");
            }
        }
    }

    public static void handleMinMaxMid() {
        if (hourlyPrices.isEmpty()) {
            System.out.println("Inga priser har registrerats ännu.");
            return;
        }
        int minPrice = Integer.MAX_VALUE;
        int maxPrice = Integer.MIN_VALUE;
        int totalPrice = 0;
        int minHour = -1;
        int maxHour = -1;

        for (HourlyPrice hourlyPrice : hourlyPrices) {
            int price = hourlyPrice.getPrice();
            int hour = hourlyPrice.getHour();

            if (price < minPrice) {
                minPrice = price;
                minHour = hour;
            }
            if (price > maxPrice) {
                maxPrice = price;
                maxHour = hour;
            }
            totalPrice += price;
        }

        double averagePrice = totalPrice / hourlyPrices.size();

        System.out.println("Lägsta priset: " + minPrice + " öre kl: " + minHour + ":00-" + (minHour + 1) + ":00");
        System.out.println("Högsta priset: " + maxPrice + " öre kl: " + maxHour + ":00-" + (maxHour + 1) + ":00");
        System.out.printf("Genomsnittligt pris: %.2f öre\n", averagePrice);
    }

    private static void handleSort() {
        if (hourlyPrices.isEmpty()) {
            System.out.println("Inga priser har registrerats ännu.");
            return;
        }
        Collections.sort(hourlyPrices);

        System.out.println("Priser sorterade i stigande ordning:");
        for (HourlyPrice hourlyPrice : hourlyPrices) {
            System.out.println(hourlyPrice);
        }
    }


    private static void handleBestLoadingTime(List<HourlyPrice> hourlyPrices) {

        Collections.sort(hourlyPrices, Comparator.comparingInt(HourlyPrice::getHour));

        int k = 4;
        int n = hourlyPrices.size();

        if (n < k) {
            System.out.println("Inte tillräckligt med timmar för att beräkna laddningstid.");
            return;
        }

        int minSum = Integer.MAX_VALUE;
        int bestStartHour = 0;
        double bestAverage = 0.0;

        for (int i = 0; i <= n - k; i++) {
            int sum = 0;
            for (int j = 0; j < k; j++) {
                sum += hourlyPrices.get(i + j).getPrice();
            }

            double average = (double) sum / k;

            if (sum < minSum) {
                minSum = sum;
                bestStartHour = hourlyPrices.get(i).getHour();
                bestAverage = average;
            }
        }

        System.out.println("Bästa laddningstid för " + k + " timmar är från " + bestStartHour + ":00 till " + (bestStartHour + k) + ":00 med totalt " + minSum + " öre.");
        System.out.println("Medelpriset under dessa timmar är: " + String.format("%.2f", bestAverage) + " öre per kWh.");
    }

    private static void handleElectricityPrices() {
        String file = "src/main/resources/elpriser.csv";
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");

                for (String index : row) {
                    System.out.printf("%-10s", index);
                }
                System.out.println();

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}



