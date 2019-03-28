package validation;

import static validation.ValidatorHelper.datesAreValid;
import static validation.ValidatorHelper.greaterEqual;
import static validation.ValidatorHelper.lowerThan;
import static validation.ValidatorHelper.notEmptyOrNullString;
import static validation.ValidatorHelper.notNull;
import static validation.ValidatorHelper.stringShorterThan;

import model.Computer;

public class ComputerValidator {

  public static void computerIsValid(Computer computer) {
    notEmptyOrNullString().and(stringShorterThan(255))
                          .test(computer.getName())
                          .throwIfInvalid("name");

    datesAreValid(computer.getDiscontinued()).test(computer.getIntroduced())
                                             .throwIfInvalid("Introduced and Discontinued");
  }

  public static void computerWithIdIsValid(Computer computer) {
    notNull().and(greaterEqual(0L))
             .and(lowerThan(Long.MAX_VALUE))
             .test(computer.getId())
             .throwIfInvalid("id");

    notEmptyOrNullString().and(stringShorterThan(255))
                          .test(computer.getName())
                          .throwIfInvalid("name");

    datesAreValid(computer.getDiscontinued()).test(computer.getIntroduced())
                                             .throwIfInvalid("Introduced and Discontinued");
  }

}
