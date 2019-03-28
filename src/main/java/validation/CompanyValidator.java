package validation;

import static validation.ValidatorHelper.greaterEqual;
import static validation.ValidatorHelper.lowerThan;
import static validation.ValidatorHelper.notEmptyOrNullString;
import static validation.ValidatorHelper.notNull;
import static validation.ValidatorHelper.stringShorterThan;

import model.Company;

public class CompanyValidator {

  public static void companyIsValid(Company company) {
    notEmptyOrNullString().and(stringShorterThan(255))
                          .test(company.getName())
                          .throwIfInvalid("name");
  }

  public static void companyWithIdIsValid(Company company) {
    notNull().and(greaterEqual(0L))
             .and(lowerThan(Long.MAX_VALUE))
             .test(company.getId())
             .throwIfInvalid("id");

    notEmptyOrNullString().and(stringShorterThan(255))
                          .test(company.getName())
                          .throwIfInvalid("name");
  }

}
