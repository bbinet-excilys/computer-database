package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.sql.Date;

import org.junit.Test;

public class ComputerTest {

  private final Integer VALID_ID      = 0;
  private final String  VALID_NAME    = "Computer";
  private final Date    VALID_IN_DATE = new Date(0);
  private final Date    VALID_DI_DATE = new Date(1);
  private final Integer VALID_C_ID    = 0;
  private final Company VALID_COMPANY = mock(Company.class);

  private final Integer INVALID_ID      = -1;
  private final String  INVALID_NAME    = " ";
  private final Date    INVALID_DI_DATE = new Date(-1);
  private final Integer INVALID_C_ID    = -1;

  @Test
  public void Computer_Valid() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE,
        this.VALID_C_ID, this.VALID_COMPANY);
    assertEquals("ID :", this.VALID_ID, computer.getId());
    assertEquals("Name : ", this.VALID_NAME, computer.getName());
    assertEquals("Introduction Date :", this.VALID_IN_DATE, computer.getIntroduced());
    assertEquals("Discontinuation Date :", this.VALID_DI_DATE, computer.getDiscontinued());
    assertEquals("Company Id :", this.VALID_C_ID, computer.getCompanyId());
    assertEquals("Company", this.VALID_COMPANY, computer.getCompany());
  }

  @Test(expected = IllegalArgumentException.class)
  public void Computer_Id_Invalid() {
    Computer computer = new Computer(this.INVALID_ID, this.VALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE,
        this.VALID_C_ID, this.VALID_COMPANY);
  }

  @Test
  public void Computer_Name_Invalid() {
    Computer computer = new Computer(this.VALID_ID, this.INVALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE,
        this.VALID_C_ID, this.VALID_COMPANY);
    assertNull(computer.getName());
  }

  @Test
  public void Computer_DiDate_BeforeInDate() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, this.VALID_IN_DATE, this.INVALID_DI_DATE,
        this.VALID_C_ID, this.VALID_COMPANY);
    assertNull(computer.getDiscontinued());

  }

  @Test
  public void Computer_InDateNull_DiDateNotNull() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, null, this.INVALID_DI_DATE, this.VALID_C_ID,
        this.VALID_COMPANY);
    assertNull(computer.getIntroduced());
    assertNull(computer.getDiscontinued());
  }

  @Test
  public void Computer_InDateNull_DiDateNull() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, null, null, this.VALID_C_ID, this.VALID_COMPANY);
    assertNull(computer.getIntroduced());
    assertNull(computer.getDiscontinued());
  }

  @Test
  public void Computer_CId_Invalid() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE,
        this.INVALID_C_ID, this.VALID_COMPANY);
    assertNull(computer.getCompanyId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void Computer_IdNull() {
    Computer computer = new Computer(null, this.VALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE, this.VALID_C_ID,
        this.VALID_COMPANY);
    assertNull(computer.getId());
  }

  @Test
  public void Computer_NameNull() {
    Computer computer = new Computer(this.VALID_ID, null, this.VALID_IN_DATE, this.VALID_DI_DATE, this.VALID_C_ID,
        this.VALID_COMPANY);
    assertNull(computer.getName());
  }

  @Test
  public void Computer_CIdNull() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE, null,
        this.VALID_COMPANY);
    assertNull(computer.getCompanyId());
  }

  @Test
  public void Computer_CompanyNull() {
    Computer computer = new Computer(this.VALID_ID, this.VALID_NAME, this.VALID_IN_DATE, this.VALID_DI_DATE,
        this.VALID_C_ID, null);
    assertNull(computer.getCompany());
  }

}
