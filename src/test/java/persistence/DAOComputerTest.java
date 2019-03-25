package persistence;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.BeforeClass;
import org.junit.Test;

import model.Company;
import model.CompanyTest;
import model.Computer;
import model.ComputerTest;

public class DAOComputerTest {

  static final Computer VALID_MCOMPUTER = mock(Computer.class);
  static final Company  VALID_MCOMPANY  = mock(Company.class);

  @BeforeClass
  public static void init() {
    when(VALID_MCOMPUTER.getId()).thenReturn(ComputerTest.VALID_ID);
    when(VALID_MCOMPUTER.getName()).thenReturn(ComputerTest.VALID_NAME);
    when(VALID_MCOMPUTER.getIntroduced()).thenReturn(ComputerTest.VALID_IN_DATE);
    when(VALID_MCOMPUTER.getDiscontinued()).thenReturn(ComputerTest.VALID_DI_DATE);
    when(VALID_MCOMPUTER.getCompanyId()).thenReturn(ComputerTest.VALID_C_ID);
    when(VALID_MCOMPUTER.getCompany()).thenReturn(VALID_MCOMPANY);
    when(VALID_MCOMPANY.getId()).thenReturn(CompanyTest.VALID_ID);
    when(VALID_MCOMPANY.getName()).thenReturn(CompanyTest.VALID_NAME);
  }

  @Test
  public void Create_Valid() {
    DAOComputer daoComputer = new DAOComputer();
    daoComputer.dbConnection = mock(Connection.class);
    PreparedStatement mPrepedStatement = mock(PreparedStatement.class);
//    when(daoComputer.dbConnection.prepareStatement(anyString()).thenReturn((Object) mPrepedStatement));
//    daoComputer.create(VALID_MCOMPUTER);
  }

}
