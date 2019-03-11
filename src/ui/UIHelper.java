package ui;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class UIHelper {

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

    public static String promptString(String message) {
        Scanner mScanner = new Scanner(System.in);
        System.out.println(message);
        String rString = mScanner.nextLine().trim();
        return rString.isEmpty() ? null : rString;
    }

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
//                e.printStackTrace();
            }
        }
        return date;
    }

    public static void displayMenu() {
        System.out.println("---------- Computer DataBase ----------");
        System.out.println("0 - Exit");
        System.out.println("1 - List Companies");
        System.out.println("2 - List Computers");
        System.out.println("3 - Create Computer");
        System.out.println("4 - Read Computer");
        System.out.println("5 - Update Computer");
        System.out.println("6 - Delete Computer");
    }

    public static void displayError(String message) {
        System.err.println(message);
    }

    public static boolean promptValidation(String message) {
        System.out.println(message);
        Scanner mScanner = new Scanner(System.in);
        String  tString  = mScanner.nextLine();
        return tString.matches("^[yY]");
    }

}
