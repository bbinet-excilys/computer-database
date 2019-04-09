package validation;

import static validation.ValidatorHelper.greaterEqual;
import static validation.ValidatorHelper.longNotNull;
import static validation.ValidatorHelper.lower;
import static validation.ValidatorHelper.notEmptyOrBlank;
import static validation.ValidatorHelper.strNotNull;
import static validation.ValidatorHelper.stringShorter;

import model.Company;

public class CompanyValidator {

  public static void companyIsValid(Company company) {
    strNotNull.and(notEmptyOrBlank())
              .and(stringShorter(255))
              .test(company.getName())
              .throwIfInvalid("name");
  }

  public static void companyWithIdIsValid(Company company) {
    longNotNull.and(greaterEqual(0L))
               .and(lower(Long.MAX_VALUE))
               .test(company.getId())
               .throwIfInvalid("id");

    strNotNull.and(notEmptyOrBlank())
              .and(stringShorter(255))
              .test(company.getName())
              .throwIfInvalid("name");
  }

}
