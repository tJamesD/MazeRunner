/**
 * WeatherReport reads temperatures from a file and prints statistics for the month on the console.
 *
 * WeatherReport has all static methods (and no constructor) including main. It is run via command-line and will ask the user for a file.
 * It will output high low and mean temperatures for a single week, a specific weekday(S,M,T,W,T,F,S) for the month, and the whole month.
 * It will not output any data for weeks that have 0 days.
 * Week and day indexes from which the statistics are grabbed are set in main, and must be set prior to running.
 *
 * @author Tim Davey
 * @email daveytj206@potsdam.edu
 * @course CIS 203 Computer Science II
 * @assignment 2
 * @due 02/03/2023
 */

import java.io.File;
import java.util.Scanner;

public class WeatherReport {

    public static void main(String args[]) {
        File userFile = askForFile();
        double[][] monthData = new double[5][7];
        String monthName = readDataFromFile(userFile, monthData);
        //printMonthDataArray(monthData);

        int daysInLastWeek = daysInAWeek(4, monthData);

        //Set weekIndex and dayIndex below. NOTE: 0 <= weekIndex < 5. 0 <= dayIndex < 7.
        int weekIndex = 4;
        int dayIndex = 2;

        int daysInAWeek = daysInAWeek(weekIndex, monthData);
        int numberOfThisWeekDay = numberOfWeekDays(dayIndex, daysInLastWeek, monthData);
        int daysInMonth = numberOfMonthDays(daysInLastWeek);

        double lowestInWeek = lowestInWeek(weekIndex, daysInLastWeek, monthData);
        double highesetInWeek = highestInWeek(weekIndex, daysInLastWeek, monthData);
        double meanOfWeek = meanOfWeek(weekIndex, daysInLastWeek, monthData);

        double lowestOnWeekDay = lowestOnWeekDay(dayIndex, daysInLastWeek, monthData);
        double highestOnWeekDay = highestOnWeekDay(dayIndex, daysInLastWeek, monthData);
        double meanOnWeekDay = meanOnWeekDay(dayIndex, daysInLastWeek, monthData);

        double lowest = lowest(daysInLastWeek, monthData);
        double highest = highest(daysInLastWeek, monthData);
        double mean = mean(daysInLastWeek, monthData);

        if (!(weekIndex == 4 && daysInLastWeek == 0)) {
            reportAWeek(monthName, weekIndex, daysInAWeek, lowestInWeek, highesetInWeek, meanOfWeek);
        }
        reportAWeekDay(monthName, dayIndex, numberOfThisWeekDay, lowestOnWeekDay, highestOnWeekDay, meanOnWeekDay);
        report(monthName, daysInMonth, lowest, highest, mean);
    }

    /**
     * Asks user for a file. Returns a file.
     *
     * This method is used to ask the user for a file, check that the file exists, and returns the file.
     *
     * @return Returns an existing file.
     * @pre User must enter an existing file.
     * @post An existing file will be returned.
     */

    public static File askForFile() {

        File userFile = new File("");
        Scanner scanner = new Scanner(System.in);

        while (!userFile.exists()) {
            System.out.print("Name of file to read: ");
            String fileName = scanner.nextLine();
            userFile = new File(fileName);
            System.out.println();
        }

        return userFile;

    }

    /**
     * This method reads a file, fills a double[][], and returns a month name;
     *
     * This method is meant read temperature data from a file where the format of the file starts with the month name.
     * Each following line should have temperature data. This method assumes no more than 35 temperatures exist for a month.
     * Temperatures are stored as doubles. For days where there are no temperatures -999.99 is used as a placeholder value.
     *
     * @param userFile  the file to be read; file should exist and be in correct format.
     * @param monthData double[][] to store temperatures; should be of size double[5][7].
     * @return Will return a monthName, or an exception if an error has occured with reading the file.
     * @pre A double[][] must exist, existing file must be applied in a format as described above.
     * @post A month name will be returned, and a double[][] will be filled.
     */

    public static String readDataFromFile(File userFile, double[][] monthData) {

        String monthName = "ERROR";

        try {
            Scanner fileReader = new Scanner(userFile);


            monthName = fileReader.next();

            for (int rows = 0; rows < monthData.length; rows++) {
                for (int cols = 0; cols < monthData[rows].length; cols++) {
                    if (fileReader.hasNext()) {
                        monthData[rows][cols] = fileReader.nextDouble();
                    } else {
                        monthData[rows][cols] = -999.99;
                    }
                }
            }

            return monthName;


        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return monthName;
        }
    }

    /**
     * Determines the number of days in any given week.
     *
     * This method loops through temperature data, and counts the days. Days with placeholder value(-999.99) are not counted.
     * This method is used to generate the daysInLastWeek variable by using weekIndex of 4.
     *
     * @param weekIndex week to be passed; week starts at 0. 0 <= weekIndex < 5
     * @param monthData double[][] to store temperatures; should be of size double[5][7].
     * @return number of days as an int in the week
     * @pre 0 <= weekIndex < 5, double[][] must be passed.
     * @post method will return total number of days in a month.
     */

    public static int daysInAWeek(int weekIndex, double[][] monthData) {
        int dayCount = 0;
        for (int rows = weekIndex; rows <= weekIndex; rows++) {
            for (int cols = 0; cols < monthData[rows].length; cols++) {
                if (monthData[rows][cols] != -999.99) {
                    dayCount++;
                }
            }
        }
        return dayCount;
    }

    /**
     * Returns lowest temperature in a week
     *
     * Determines and returns the lowest temperature in a given week.
     *
     * @param weekIndex      week to be passed; week starts at 0. 0 <= weekIndex < 5
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return lowest temperature as a double in the specified week.
     * @pre 0 <= weekIndex < 5, double[][] must be passed, daysInLastWeek must be passed.
     * @post method will return lowest temperature in specifed week.
     */
    public static double lowestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData) {
        double lowest = monthData[weekIndex][0];

        int columnCount = monthData[weekIndex].length;

        if (weekIndex == 4) {
            columnCount = daysInLastWeek;
        }

        for (int rows = weekIndex; rows <= weekIndex; rows++) {
            for (int cols = 0; cols < columnCount; cols++) {
                if (monthData[rows][cols] < lowest) {
                    lowest = monthData[rows][cols];
                }
            }
        }
        return lowest;
    }

    /**
     * Returns highest temperature in a week
     *
     * Determines and returns the highest temperature in a given week.
     *
     * @param weekIndex      week to be passed; week starts at 0. 0 <= weekIndex < 5
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return highest temperature as a double in the specified week.
     * @pre 0 <= weekIndex < 5, double[][] must be passed, daysInLastWeek must be passed.
     * @post method will return highest temperature in specifed week.
     */
    public static double highestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData) {
        double highest = monthData[weekIndex][0];

        int columnCount = monthData[weekIndex].length;

        if (weekIndex == 4) {
            columnCount = daysInLastWeek;
        }

        for (int rows = weekIndex; rows <= weekIndex; rows++) {
            for (int cols = 0; cols < columnCount; cols++) {
                if (monthData[rows][cols] > highest) {
                    highest = monthData[rows][cols];
                }
            }
        }
        return highest;
    }

    /**
     * Returns mean temperature in a week
     *
     * Determines and returns the mean temperature in a given week.
     *
     * @param weekIndex      week to be passed; week starts at 0. 0 <= weekIndex < 5
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return mean temperature as a double in the specified week.
     * @pre 0 <= weekIndex < 5, double[][] must be passed, daysInLastWeek must be passed.
     * @post method will return mean temperature in specifed week.
     */

    public static double meanOfWeek(int weekIndex, int daysInLastWeek, double[][] monthData) {

        double currentSum = 0.0;

        int columnCount = monthData[weekIndex].length;

        if (weekIndex == 4) {
            columnCount = daysInLastWeek;
        }


        for (int rows = weekIndex; rows <= weekIndex; rows++) {
            for (int cols = 0; cols < columnCount; cols++) {
                currentSum = currentSum + monthData[rows][cols];
            }
        }
        return currentSum / columnCount;
    }

    /**
     * Returns lowest temperature of a specific weekday(S,M,T,W,T,F,S) for the month.
     *
     * Determines and returns the lowest temperature of a specific weekday(S,M,T,W,T,F,S) for the month based on dayIndex and daysInLastWeek.
     *
     * @param dayIndex       day to be passed; days starts at 0. 0 <= dayIndex < 7
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return lowest temperature as a double on a specific weekday for the month.
     * @pre 0 <= dayIndex < 7, double[][] must be passed, daysInLastWeek must be passed.
     * @post method will return lowest temperature on a specific weekday for the month.
     */

    public static double lowestOnWeekDay(int dayIndex, int daysInLastWeek, double[][] monthData) {

        double lowest = monthData[0][dayIndex];

        int rowCount = monthData.length;

        if (dayIndex >= daysInLastWeek) {
            rowCount = rowCount - 1;
        }


        for (int rows = 0; rows < rowCount; rows++) {
            for (int cols = dayIndex; cols <= dayIndex; cols++) {
                if (lowest > monthData[rows][cols]) {
                    lowest = monthData[rows][cols];
                }
            }
        }
        return lowest;
    }

    /**
     * Returns highest temperature of a specific weekday(S,M,T,W,T,F,S) for the month.
     *
     * Determines and returns the highest temperature of a specific weekday(S,M,T,W,T,F,S) for the month based on dayIndex and daysInLastWeek.
     *
     * @param dayIndex       day to be passed; days starts at 0. 0 <= dayIndex < 7
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return highest temperature as a double on a specific weekday for the month.
     * @pre 0 <= dayIndex < 7, double[][] must be passed, daysInLastWeek must be passed.
     * @post method will return lowest temperature on a specific weekday for the month.
     */

    public static double highestOnWeekDay(int dayIndex, int daysInLastWeek, double[][] monthData) {

        double highest = monthData[0][dayIndex];

        int rowCount = monthData.length;

        if (dayIndex >= daysInLastWeek) {
            rowCount = rowCount - 1;
        }


        for (int rows = 0; rows < rowCount; rows++) {
            for (int cols = dayIndex; cols <= dayIndex; cols++) {
                if (highest < monthData[rows][cols]) {
                    highest = monthData[rows][cols];
                }
            }
        }
        return highest;
    }

    /**
     * Returns mean temperature of a specific weekday(S,M,T,W,T,F,S) for the month.
     *
     * Determines and returns the mean temperature of a specific weekday(S,M,T,W,T,F,S) for the month based on dayIndex and daysInLastWeek.
     *
     * @param dayIndex       day to be passed; days starts at 0. 0 <= dayIndex < 7
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return mean temperature as a double on a specific weekday for the month.
     * @pre 0 <= dayIndex < 7, double[][] must be passed, daysInLastWeek must be passed.
     * @post method will return mean temperature on a specific weekday for the month.
     */

    public static double meanOnWeekDay(int dayIndex, int daysInLastWeek, double[][] monthData) {

        double currentSum = 0.0;

        int rowCount = monthData.length;

        if (dayIndex >= daysInLastWeek) {
            rowCount = rowCount - 1;
        }


        for (int rows = 0; rows < rowCount; rows++) {
            for (int cols = dayIndex; cols <= dayIndex; cols++) {
                currentSum = currentSum + monthData[rows][cols];
            }
        }
        return currentSum / rowCount;
    }

    /**
     * Returns lowest temperature for the month.
     *
     * Determines and returns the lowest temperature for the whole month based on daysInLastWeek and monthData.
     *
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return lowest temperature as a double for the whole month.
     * @pre 0 <= daysInLastWeek < 8 daysInLastWeek must be passed, double[][] must be passed.
     * @post method will return lowest temperature for the whole month.
     */

    public static double lowest(int daysInLastWeek, double[][] monthData) {

        double lowest = lowestInWeek(0, daysInLastWeek, monthData);

        int rowCount = monthData.length;

        if (daysInLastWeek == 0) {
            rowCount = rowCount - 1;
        }

        for (int rows = 0; rows < rowCount; rows++) {

            if (lowest > lowestInWeek(rows, daysInLastWeek, monthData)) {
                lowest = lowestInWeek(rows, daysInLastWeek, monthData);
            }
        }

        return lowest;
    }

    /**
     * Returns highest temperature for the month.
     *
     * Determines and returns the highest temperature for the whole month based on daysInLastWeek and monthData.
     *
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return highest temperature as a double for the whole month.
     * @pre 0 <= daysInLastWeek < 8 daysInLastWeek must be passed, double[][] must be passed.
     * @post method will return highest temperature for the whole month.
     */

    public static double highest(int daysInLastWeek, double[][] monthData) {

        double highest = highestInWeek(0, daysInLastWeek, monthData);

        int rowCount = monthData.length;

        if (daysInLastWeek == 0) {
            rowCount = rowCount - 1;
        }

        for (int rows = 0; rows < rowCount; rows++) {

            if (highest < highestInWeek(rows, daysInLastWeek, monthData)) {
                highest = highestInWeek(rows, daysInLastWeek, monthData);
            }
        }

        return highest;
    }

    /**
     * Returns mean temperature for the month.
     *
     * Determines and returns the mean temperature for the whole month based on daysInLastWeek and monthData.
     *
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return highest temperature as a double for the whole month.
     * @pre 0 <= daysInLastWeek < 8 daysInLastWeek must be passed, double[][] must be passed.
     * @post method will return mean temperature for the whole month.
     */

    public static double mean(int daysInLastWeek, double[][] monthData) {

        double currentSum = 0.0;

        int rowCount = monthData.length;


        if (daysInLastWeek == 0) {
            rowCount = rowCount - 1;
        }

        for (int rows = 0; rows < monthData.length; rows++) {
            for (int cols = 0; cols < monthData[rows].length; cols++) {
                if (rows == 4 && cols >= daysInLastWeek) {
                    continue;
                } else {
                    currentSum = currentSum + monthData[rows][cols];
                }
            }
        }

        return currentSum / (28 + daysInLastWeek);


    }

    /**
     * Returns count of how many of a specific weekday(S,M,T,W,T,F,S) exist for a month.
     *
     * Determines and returns count of how many of a specific weekday(S,M,T,W,T,F,S) exit for a month based on dayIndex and daysInLastWeek.
     *
     * @param dayIndex       day to be passed; days starts at 0. 0 <= dayIndex < 7
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @param monthData      double[][] to store temperatures; should be of size double[5][7].
     * @return int count of how many of a specific weekday(S,M,T,W,T,F,S) exit for a month.
     * @pre 0 <= dayIndex < 7, 0 <= daysInLastWeek < 8 daysInLastWeek must be passed, double[][] must be passed.
     * @post method will return mean temperature for the whole month.
     */

    public static int numberOfWeekDays(int dayIndex, int daysInLastWeek, double[][] monthData) {

        int dayCount = 0;

        if (dayIndex < daysInLastWeek) {
            dayCount = 5;
        } else {
            dayCount = 4;
        }

        return dayCount;
    }

    /**
     * Prints statistics for a given week.
     *
     * Prints month, week selected, #of days in week, and low/high/mean for week. Values are formatted via printf to the tenth decimal.
     *
     * @param monthName  name of month; determined from file.
     * @param weekNumber week number; 0 <=weekNumber < 5.
     * @param daysInWeek number of days in specified week; calculated from daysInAWeek(int weekIndex, double[][] monthData);
     * @param low        low for the week; calculated from lowestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @param high       high for the week; calculated from highestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @param mean       mean for the week; calculated from meanOfWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @pre monthName, weekNumber, daysInWeek, low, high, mean, will all need to have been calcuated and passed as params.
     * @post Will print the out month name, week and statistics to the tenth decimal.
     */

    public static void reportAWeek(String monthName, int weekNumber, int daysInWeek, double low, double high, double mean) {

        weekNumber = weekNumber + 1;

        System.out.printf(monthName + ", Week " + weekNumber + " (" + daysInWeek + " days): Low: %.1f High: %.1f Mean: %.1f ", low, high, mean);
        System.out.println();

    }

    /**
     * Prints statistics for a specific weekday(S,M,T,W,T,F,S) for the month.
     *
     * Prints month, day selected, #of days in moth, and low/high/mean for specified day. Values are formatted via printf to the tenth decimal.
     *
     * @param monthName           name of month; determined from file.
     * @param dayNumber           day of the week will be mapped via switch case; Days start at 0. 0 <= weekDay < 7;
     * @param numberOfThisWeekday how many days of this specific day exist for the month; calculated from numberOfWeekDays(int dayIndex, int daysInLastWeek, double[][] monthData);
     * @param low                 low for the week; calculated from lowestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @param high                high for the week; calculated from highestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @param mean                mean for the week; calculated from meanOfWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @pre monthName, dayNumber, numberOfThisWeekday, low, high, mean, will all need to have been calcuated and passed as params.
     * @post Will print the out month name, week and statistics to the tenth decimal.
     */

    public static void reportAWeekDay(String monthName, int dayNumber, int numberOfThisWeekday, double low, double high, double mean) {

        String weekDay;
        switch (dayNumber) {
            case 0:
                weekDay = "Sunday";
                break;

            case 1:
                weekDay = "Monday";
                break;

            case 2:
                weekDay = "Tuesday";
                break;

            case 3:
                weekDay = "Wednesday";
                break;

            case 4:
                weekDay = "Thursday";
                break;

            case 5:
                weekDay = "Friday";
                break;

            case 6:
                weekDay = "Saturday";
                break;

            default:
                weekDay = "Invalid Day";
                break;
        }

        System.out.printf(monthName + ", " + weekDay + "s (" + numberOfThisWeekday + " days): Low: %.1f High: %.1f Mean: %.1f ", low, high, mean);
        System.out.println();
    }

    /**
     * Returns how many days exist in the month.
     *
     * Returns an integer value specifing how many days there are in the month using daysInLastWeek.
     *
     * @param daysInLastWeek days in last week of the month; 0 <= daysInLastWeek < 8
     * @return Returns int that is equal to the number of the days in the month.
     * @pre daysInLastWeek will have to have been calculated.
     * @post Method will have retruned total number of days in the month.
     */

    public static int numberOfMonthDays(int daysInLastWeek) {
        return 28 + daysInLastWeek;
    }

    /**
     * Prints statistics for the entire month.
     *
     * Prints month, #of days in month, and low/high/mean for month. Values are formatted via printf to the tenth decimal.
     *
     * @param monthName   name of month; determined from file.
     * @param daysInMonth number of days in specified week; calculated from numberOfMonthDays(int daysInLastWeek)
     * @param low         low for the week; calculated from lowestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @param high        high for the week; calculated from highestInWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @param mean        mean for the week; calculated from meanOfWeek(int weekIndex, int daysInLastWeek, double[][] monthData);
     * @pre monthName, daysInMonth, low, high, mean, will all need to have been calcuated and passed as params.
     * @post Will print the out month name, days in the month, and low high and mean values.
     */

    public static void report(String monthName, int daysInMonth, double low, double high, double mean) {
        System.out.printf(monthName + " (" + daysInMonth + " days): Low: %.1f High: %.1f Mean: %.1f ", low, high, mean);
        System.out.println();
    }

    /**
     * Prints out temperatures.
     *
     * Prints out temperatures in monthData array. Mostly used for troubleshooting and testing. Not needed for statistics.
     *
     * @param monthData double[][] to store temperatures; should be of size double[5][7].
     * @pre Array has to have been created.
     * @post Will output values of the array to the screen.
     */

    public static void printMonthDataArray(double[][] monthData) {
        for (int rows = 0; rows < monthData.length; rows++) {
            for (int cols = 0; cols < monthData[rows].length; cols++) {
                System.out.print(monthData[rows][cols] + " ");
            }
            System.out.println();
        }
    }

}
