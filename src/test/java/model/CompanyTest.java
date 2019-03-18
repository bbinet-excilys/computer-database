package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class CompanyTest {

  private final Integer VALID_ID   = 0;
  private final String  VALID_NAME = "Company";

  private final Integer INVALID_ID   = -1;
  private final String  INVALID_NAME = " ";

  @Test
  public void Company_Valid_NotNull() {
    Company company = new Company(this.VALID_ID, this.VALID_NAME);
    assertNotNull(company);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Company_InvalidId() {
    Company company = new Company(this.INVALID_ID, this.VALID_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Company_InvalidName_Null() {
    Company company = new Company(this.VALID_ID, this.INVALID_NAME);
  }

  @Test(expected = IllegalArgumentException.class)
  public void Company_IdNull() {
    Company company = new Company(null, this.VALID_NAME);
  }

  @Test
  public void Company_NameNull() {
    Company company = new Company(this.VALID_ID, null);
    assertNull(company.getName());
  }

}
