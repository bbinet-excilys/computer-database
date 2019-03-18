package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import model.Company;
import model.Computer;

public class ComputerMapperTest {

  private final Integer ID       = 0;
  private final String  NAME     = "Computer";
  private final Date    IN_DATE  = new Date(0);
  private final Date    DI_DATE  = new Date(1);
  private final Integer COM_ID   = 7;
  private final String  COM_NAME = "Company";
  private final Company COMPANY  = mock(Company.class);

  @Test
  public void ComputerMapper_map() {

    try {
      when(this.COMPANY.getId()).thenReturn(this.COM_ID);
      when(this.COMPANY.getName()).thenReturn(this.COM_NAME);

      ResultSet rSet = mock(ResultSet.class);
      when(rSet.first()).thenReturn(true);
      when(rSet.getInt("computer.id")).thenReturn(this.ID);
      when(rSet.getString("computer.name")).thenReturn(this.NAME);
      when(rSet.getDate("computer.introduced")).thenReturn(this.IN_DATE);
      when(rSet.getDate("computer.discontinued")).thenReturn(this.DI_DATE);
      when(rSet.getInt("computer.company_id")).thenReturn(this.COM_ID);
      when(rSet.getInt("company.id")).thenReturn(this.COM_ID);
      when(rSet.getString("company.name")).thenReturn(this.COM_NAME);

      ComputerMapper computerMapper = new ComputerMapper();
      Computer       computer       = computerMapper.map(rSet);

      Computer mComputer = mock(Computer.class);
      when(mComputer.getId()).thenReturn(this.ID);
      when(mComputer.getName()).thenReturn(this.NAME);
      when(mComputer.getIntroduced()).thenReturn(this.IN_DATE);
      when(mComputer.getDiscontinued()).thenReturn(this.DI_DATE);
      when(mComputer.getCompanyId()).thenReturn(this.COM_ID);
      when(mComputer.getCompany()).thenReturn(this.COMPANY);

      assertTrue(computer.equals(mComputer));
    }
    catch (SQLException e) {
      e.printStackTrace();
      fail();
    }
  }

}
