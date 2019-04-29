package com.excilys.validation;

import static com.excilys.validation.ValidatorHelper.greaterEqual;
import static com.excilys.validation.ValidatorHelper.longNotNull;
import static com.excilys.validation.ValidatorHelper.lower;
import static com.excilys.validation.ValidatorHelper.notEmptyOrBlank;
import static com.excilys.validation.ValidatorHelper.strNotNull;
import static com.excilys.validation.ValidatorHelper.stringShorter;

import com.excilys.model.Company;

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
