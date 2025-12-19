// Main.java — Students version
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[][][] profits = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        Scanner reader = null;
        for (int m = 0; m < MONTHS; m++) {
            try {
                File file = new File("Data_Files/" + months[m] + ".txt");
                reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String[] info = reader.nextLine().split(",");
                    int day = Integer.parseInt(info[0].trim());
                    String commodity = info[1].trim();
                    int profit = Integer.parseInt(info[2].trim());
                    day = day - 1;
                    for (int c = 0; c < COMMS; c++) {
                        if (commodities[c].equals(commodity)) {
                            profits[m][day][c] = profit;
                        }
                    }
                }
            } catch (Exception e) {
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        int maxProfit = profits[month][0][0];
        int bestCommodityIndex = 0;
        for (int c = 0; c < COMMS; c++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum += profits[month][d][c];
            }
            if (sum > maxProfit) {
                maxProfit = sum;
                bestCommodityIndex = c;
            }
        }
        return commodities[bestCommodityIndex] + " " + maxProfit;;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }
        int total = 0;
        day--;
        for (int c = 0; c < COMMS; c++) {
            total = total + profits[month][day][c];
        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int fromDay, int toDay) {
        int commodityIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(commodity)) {
                commodityIndex = c;
            }
        }
        if (commodityIndex == -1 || fromDay < 1 || toDay > DAYS || fromDay > toDay) {
            return -99999;
        }
        int total = 0;
        fromDay = fromDay - 1;
        toDay = toDay - 1;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = fromDay; d <= toDay; d++) {
                total = total + profits[m][d][commodityIndex];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        int bestDayIndex = 0;
        int maxTotal = 0;
        for (int c = 0; c < COMMS; c++) {
            maxTotal = maxTotal + profits[month][0][c];
        }
        for (int d = 1; d < DAYS; d++) {
            int total = 0;
            for (int c = 0; c < COMMS; c++) {
                total = total + profits[month][d][c];
            }
            if (total > maxTotal) {
                maxTotal = total;
                bestDayIndex = d;
            }
        }
        return bestDayIndex + 1;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int commodityIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                commodityIndex = c;
            }
        }
        if (commodityIndex == -1) {
            return "INVALID_COMMODITY";
        }
        int bestMonthIndex = 0;
        int maxProfit = 0;
        for (int d = 0; d < DAYS; d++) {
            maxProfit = maxProfit + profits[0][d][commodityIndex];
        }
        for (int m = 1; m < MONTHS; m++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) {
                sum = sum + profits[m][d][commodityIndex];
            }
            if (sum > maxProfit) {
                maxProfit = sum;
                bestMonthIndex = m;
            }
        }
        return months[bestMonthIndex];
    }

    public static int consecutiveLossDays(String comm) {
        int commodityIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                commodityIndex = c;
            }
        }
        if (commodityIndex == -1) {
            return -1;
        }
        int currentStreak = 0;
        int maxStreak = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][commodityIndex] < 0) {
                    currentStreak = currentStreak + 1;

                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                } else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int commodityIndex = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(comm)) {
                commodityIndex = c;
            }
        }
        if (commodityIndex == -1) {
            return -1;
        }
        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][commodityIndex] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) {
            return -9999;
        }
        int prevTotal = 0;

        for (int c = 0; c < COMMS; c++) {
            prevTotal += profits[month][0][c];
        }
        int maxSwing = 0;
        for (int d = 1; d < DAYS; d++) {
            int currentTotal = 0;
            for (int c = 0; c < COMMS; c++) {
                currentTotal += profits[month][d][c];
            }
            int diff = currentTotal - prevTotal;
            if (diff < 0) {
                diff = -diff;
            }
            if (diff > maxSwing) {
                maxSwing = diff;
            }
            prevTotal = currentTotal;
        }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int index1 = -1;
        int index2 = -1;
        for (int c = 0; c < COMMS; c++) {
            if (commodities[c].equals(c1)) {
                index1 = c;
            }
            if (commodities[c].equals(c2)) {
                index2 = c;
            }
        }
        if (index1 == -1 || index2 == -1) {
            return "INVALID_COMMODITY";
        }
        int total1 = 0;
        int total2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                total1 += profits[m][d][index1];
                total2 += profits[m][d][index2];
            }
        }
        if (total1 > total2) {
            return c1 + " is better by " + (total1 - total2);
        } else if (total2 > total1) {
            return c2 + " is better by " + (total2 - total1);
        } else {
            return "Equal";
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        int bestWeek = 1;
        int maxProfit = 0;
        for (int w = 0; w < 4; w++) {
            int weekProfit = 0;
            int startDay = w * 7;
            int endDay = startDay + 7;

            for (int d = startDay; d < endDay; d++) {
                for (int c = 0; c < COMMS; c++) {
                    weekProfit += profits[month][d][c];
                }
            }
            if (w == 0 || weekProfit > maxProfit) {
                maxProfit = weekProfit;
                bestWeek = w + 1;
            }
        }
        return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}