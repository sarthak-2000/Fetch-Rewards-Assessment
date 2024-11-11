package com.fetchRewards.apiApplication.Utils;

import com.fetchRewards.apiApplication.model.Receipt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

public class Utils {

    /**
     * Calculates the total points for a given receipt based on various criteria.
     *
     * @param receipt The receipt object containing purchase details.
     * @return Total points calculated.
     */
    public static int calculatePoints(Receipt receipt) {
        int points = 0;

        // 1. Retailer Name Points: 1 point for every alphanumeric character in the retailer name
        points += calculateRetailerPoints(receipt.getRetailer());
        logPoints(points, "Retailer Name Points (1 point for every alphanumeric character)");

        // 2. Round Dollar Total Points: 50 points if total is a whole number
        if (isWholeNumber(receipt.getTotal())) {
            points += 50;
        }
        logPoints(points, "Round Dollar Total Points (50 points if total is a whole number)");

        // 3. Multiple of 0.25 Total Points: 25 points if total is a multiple of 0.25
        if (isMultipleOfQuarter(receipt.getTotal())) {
            points += 25;
        }
        logPoints(points, "Multiple of 0.25 Total Points (25 points if total is a multiple of 0.25)");

        // 4. Item Pair Points: 5 points for every two items
        points += calculateItemPairPoints(receipt.getItems().size());
        logPoints(points, "Item Pair Points (5 points for every two items)");

        // 5. Odd Purchase Date Points: 6 points if the purchase day is odd
        points += calculateOddDatePoints(receipt.getPurchaseDate());
        logPoints(points, "Odd Purchase Date Points (6 points if purchase day is odd)");

        // 6. Time of Purchase Points: 10 points if purchase time is between 2:00 PM and 4:00 PM
        if (isWithinTimeRange(receipt.getPurchaseTime(), 14, 16)) {
            points += 10;
        }
        logPoints(points, "Time of Purchase Points (10 points if between 2:00 PM and 4:00 PM)");

        // 7. Points for Item Description Length: Points based on item description length multiple of 3
        points += calculateDescriptionPoints(receipt);
        logPoints(points, "Points for item description length multiple of 3");

        return points;
    }

    private static int calculateRetailerPoints(String retailer) {
        return retailer.replaceAll("[^a-zA-Z0-9]", "").length();
    }

    private static boolean isWholeNumber(String receiptTotal) {
        try {
            BigDecimal total = new BigDecimal(receiptTotal);
            return total.stripTrailingZeros().scale() <= 0;
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + receiptTotal);
            return false;
        }
    }

    private static boolean isMultipleOfQuarter(String receiptTotal) {
        try {
            BigDecimal total = new BigDecimal(receiptTotal);
            return total.remainder(new BigDecimal("0.25")).compareTo(BigDecimal.ZERO) == 0;
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format: " + receiptTotal);
            return false;
        }
    }

    private static int calculateItemPairPoints(int itemCount) {
        return (itemCount / 2) * 5;
    }

    private static int calculateOddDatePoints(String purchaseDate) {
        int day = Integer.parseInt(purchaseDate.split("-")[2]);
        return (day % 2 != 0) ? 6 : 0;
    }

    private static boolean isWithinTimeRange(String purchaseTime, int startHour, int endHour) {
        LocalTime time = LocalTime.parse(purchaseTime);
        return time.isAfter(LocalTime.of(startHour, 0)) && time.isBefore(LocalTime.of(endHour, 0));
    }

    private static int calculateDescriptionPoints(Receipt receipt) {
        int points = 0;
        for (Receipt.Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                points += price.multiply(new BigDecimal("0.2")).setScale(0, RoundingMode.UP).intValue();
            }
        }
        return points;
    }

    private static void logPoints(int points, String message) {
        System.out.println(String.format("Current Points: %d -> %s", points, message));
    }
}
