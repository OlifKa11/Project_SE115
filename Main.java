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
                sum = sum + profits[month][d][c];
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
        day = day - 1;
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
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}