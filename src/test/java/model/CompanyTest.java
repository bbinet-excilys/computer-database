package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CompanyTest {

  public static final Integer VALID_ID   = 0;
  public static final String  VALID_NAME = "Company";

  public static final Integer INVALID_ID   = -1;
  public static final String  INVALID_NAME = " ";

  @Test
  public void Company_Valid_NotNull() {
    Company company = new Company(CompanyTest.VALID_ID, CompanyTest.VALID_NAME);
    assertNotNull(company);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Company_InvalidId() {
    Company company = new Company(CompanyTest.INVALID_ID, CompanyTest.VALID_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Company_InvalidName_Null() {
    Company company = new Company(CompanyTest.VALID_ID, CompanyTest.INVALID_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Company_IdNull() {
    Company company = new Company(null, CompanyTest.VALID_NAME);
  }

  @Test
  public void Company_NameNull() {
    Company company = new Company(CompanyTest.VALID_ID, null);
    assertNull(company.getName());
  }

}
