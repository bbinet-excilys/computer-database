package com.excilys.cdb.main.ui;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


/**
 * 
 * @author bbinet
 *
 * Class containing static methods for user interactions (mostly prompts and prints)
 */
public class UIHelper {

    /**
     * Prompts the user for an Integer.
     * @param message The message to display
     * @return the Integer object corresponding to what the user typed (until carriage return was typed). May return null if it was empty or not a numeric.
     */
    public static Integer promptInt(String message) {
        Scanner mScanner = new Scanner(System.in);
        Integer rInt     = null;
        System.out.println(message);
        try {
            rInt = Integer.parseInt(mScanner.nextLine());
        }
        catch (Exception e) {
        }
        return rInt;
    }

    /**
     * Prompts the user for a String.
     * @param message The message to display
     * @return the String object corresponding to what the user typed (until carriage return was typed). Null if it was empty.
     */
    public static String promptString(String message) {
        Scanner mScanner = new Scanner(System.in);
        System.out.println(message);
        String rString = mScanner.nextLine().trim();
        return rString.isEmpty() ? null : rString;
    }

    /**
     * Prompts the User for a Date
     * @param message The message to Display
     * @return Tries to parse the user input to a sql.Date. Returns null if the input couldn't be parsed.
     */
    public static Date promptDate(String message) {
        Date             date     = null;
        SimpleDateFormat sdf      = new SimpleDateFormat("yyyy-MM-dd");
        Scanner          mScanner = new Scanner(System.in);
        System.out.println(message);
        if (mScanner.hasNextLine()) {
            String input = mScanner.nextLine();
            try {
                date = new Date(sdf.parse(input).getTime());
            }
            catch (ParseException e) {
            }
        }
        return date;
    }

    /**
     * Displays the application CLI Menu
     */
    public static void displayMenu() {
        System.out.println("========== Computer DataBase ==========");
        System.out.println("0 - Exit");
        System.out.println("1 - List Companies");
        System.out.println("2 - List Computers");
        System.out.println("3 - Create Computer");
        System.out.println("4 - Read Computer");
        System.out.println("5 - Update Computer");
        System.out.println("6 - Delete Computer");
        System.out.println("7 - PageList Computers");
        System.out.println("8 - PageList Companies");
    }

    /**
     * Displays a message on the default error output
     * @param message
     */
    public static void displayError(String message) {
        System.err.println(message);
    }

    /**
     * Prompts the user for a validation
     * @param message The message to display
     * @return True if input was y or Y. False otherwise.
     */
    public static boolean promptValidation(String message) {
        System.out.println(message);
        Scanner mScanner = new Scanner(System.in);
        String  tString  = mScanner.nextLine();
        return tString.matches("^[yY]");
    }

    /**
     * Prompts the user for page navigation
     * @param page The current page, viewed by the user.
     * @return The direction to scroll page. -1 if input was p or P (previous). 1 if input was n or N (Next). 0 otherwise;
     */
    public static Integer promptPage(Integer page) {
        System.out.println("<- p | p." + page + " | n ->");
        System.out.println(" anything else to exit ");
        Scanner mScanner = new Scanner(System.in);
        String  rString  = mScanner.nextLine().trim();
        if (rString.matches("^[pP]")) {
            return -1;
        }
        if (rString.matches("^[nN]")) {
            return 1;
        }
        return 0;
    }

}