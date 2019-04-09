package ui;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.MenuEnum;

/**
 * Class containing static methods for user interactions (mostly prompts and
 * prints).
 *
 * @author bbinet
 */
public class UIHelper {

  private static Logger logger = LoggerFactory.getLogger("ui");

  /**
   * Prompts the user for an Integer.
   *
   * @param message
   *                The message to display
   * @return the Integer object corresponding to what the user typed (until
   *         carriage return was typed). May return null if it was empty or not a
   *         numeric.
   */
  public static Optional<Long> promptLong(String message) {
    Scanner mScanner = new Scanner(System.in);
    System.out.println(message);
    String         tString = mScanner.nextLine();
    Optional<Long> oLong   = Optional.empty();
    if (!tString.isBlank() && tString.matches("[0-9]*")) {
      oLong = Optional.of(Long.parseLong(tString));
    }
    return oLong;
  }

  public static Optional<Integer> promptInt(String message) {
    Scanner mScanner = new Scanner(System.in);
    System.out.println(message);
    String            tString = mScanner.nextLine();
    Optional<Integer> rOInt   = Optional.empty();
    if (!tString.isBlank() && tString.matches("[0-9]*")) {
      rOInt = Optional.of(Integer.parseInt(tString));
    }
    return rOInt;
  }

  /**
   * Prompts the user for a String.
   *
   * @param message
   *                The message to display
   * @return the String object corresponding to what the user typed (until
   *         carriage return was typed). Null if it was empty.
   */
  public static Optional<String> promptString(String message) {
    Scanner mScanner = new Scanner(System.in);
    System.out.println(message);
    String tString = mScanner.nextLine().trim();
    if (tString.isBlank()) {
      return Optional.empty();
    }
    return Optional.of(tString);
  }

  /**
   * Prompts the User for a Date.
   *
   * @param message
   *                The message to Display
   * @return Tries to parse the user input to a sql.Date
   */
  public static Optional<Date> promptDate(String message) {
    SimpleDateFormat sdf      = new SimpleDateFormat("yyyy-MM-dd");
    Scanner          mScanner = new Scanner(System.in);
    System.out.println(message);
    Date   date    = null;
    String tString = mScanner.nextLine().trim();
    if (!tString.isBlank()) {
      try {
        Long time = sdf.parse(tString).getTime();
        date = new Date(time);
        logger.debug(time.toString());
        logger.debug(date.getClass().toString());
      }
      catch (ParseException e) {
        displayError("Couldn't parse the date");
        logger.warn("Uparsable date :" + tString);
      }
    }
    return Optional.ofNullable(date);
  }

  /**
   * Displays the application CLI Menu.
   */
  public static void displayMenu() {
    System.out.println("========== Computer DataBase ==========");
    Stream
          .of(MenuEnum.values())
          .forEach(
                   item -> System.out.println(String.format("%d - %s", item.ordinal(),
                                                            item.toString())));
  }

  /**
   * Displays a message on the default error output.
   *
   * @param message
   *                The message to display.
   */
  public static void displayError(String message) {
    System.err.println(message);
  }

  /**
   * Prompts the user for a validation.
   *
   * @param message
   *                The message to display.
   * @return True if input was y or Y. False otherwise.
   */
  public static boolean promptValidation(String message) {
    System.out.println(message);
    Scanner mScanner = new Scanner(System.in);
    String  tString  = mScanner.nextLine().trim().toLowerCase();
    return tString.matches("^[y]");
  }

  /**
   * Prompts the user for page navigation.
   *
   * @param page
   *             The current page, viewed by the user.
   * @return The direction to scroll page.
   *         <ul>
   *         <li>-1 if input was p or P (previous).</li>
   *         <li>1 if input was n or N (Next). 0 otherwise.</li>
   *         </ul>
   */
  public static Integer promptPage(Integer page) {
    System.out.println("<- p | p." + page + " | n ->");
    System.out.println(" anything else to exit ");
    Scanner mScanner = new Scanner(System.in);
    String  rString  = mScanner.nextLine().trim().toLowerCase();
    if (rString.matches("^[p]")) {
      return -1;
    }
    if (rString.matches("^[n]")) {
      return 1;
    }
    return 0;
  }

}
