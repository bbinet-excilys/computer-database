package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.sql.Date;

import org.junit.Test;

public class ComputerTest {

  public static final Integer VALID_ID      = 0;
  public static final String  VALID_NAME    = "Computer";
  public static final Date    VALID_IN_DATE = new Date(0);
  public static final Date    VALID_DI_DATE = new Date(1);
  public static final Integer VALID_C_ID    = 0;
  public static final Company VALID_COMPANY = mock(Company.class);

  public static final Integer INVALID_ID      = -1;
  public static final String  INVALID_NAME    = " ";
  public static final Date    INVALID_DI_DATE = new Date(-1);
  public static final Integer INVALID_C_ID    = -1;

  @Test
  public void Computer_Valid() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertEquals("ID :", ComputerTest.VALID_ID, computer.getId());
    assertEquals("Name : ", ComputerTest.VALID_NAME, computer.getName());
    assertEquals("Introduction Date :", ComputerTest.VALID_IN_DATE, computer.getIntroduced());
    assertEquals("Discontinuation Date :", ComputerTest.VALID_DI_DATE, computer.getDiscontinued());
    assertEquals("Company Id :", ComputerTest.VALID_C_ID, computer.getCompanyId());
    assertEquals("Company", ComputerTest.VALID_COMPANY, computer.getCompany());
  }

  @Test(expected = IllegalArgumentException.class)
  public void Computer_Id_Invalid() {
    Computer computer = new Computer(ComputerTest.INVALID_ID, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
  }

  @Test
  public void Computer_Name_Invalid() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.INVALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getName());
  }

  @Test
  public void Computer_DiDate_BeforeInDate() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.INVALID_DI_DATE, ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getDiscontinued());

  }

  @Test
  public void Computer_InDateNull_DiDateNotNull() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, null, ComputerTest.INVALID_DI_DATE,
        ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getIntroduced());
    assertNull(computer.getDiscontinued());
  }

  @Test
  public void Computer_InDateNull_DiDateNull() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, null, null,
        ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getIntroduced());
    assertNull(computer.getDiscontinued());
  }

  @Test
  public void Computer_CId_Invalid() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.INVALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getCompanyId());
  }

  @Test(expected = IllegalArgumentException.class)
  public void Computer_IdNull() {
    Computer computer = new Computer(null, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getId());
  }

  @Test
  public void Computer_NameNull() {
    Computer computer = new Computer(ComputerTest.VALID_ID, null, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.VALID_C_ID, ComputerTest.VALID_COMPANY);
    assertNull(computer.getName());
  }

  @Test
  public void Computer_CIdNull() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, null, ComputerTest.VALID_COMPANY);
    assertNull(computer.getCompanyId());
  }

  @Test
  public void Computer_CompanyNull() {
    Computer computer = new Computer(ComputerTest.VALID_ID, ComputerTest.VALID_NAME, ComputerTest.VALID_IN_DATE,
        ComputerTest.VALID_DI_DATE, ComputerTest.VALID_C_ID, null);
    assertNull(computer.getCompany());
  }

}
