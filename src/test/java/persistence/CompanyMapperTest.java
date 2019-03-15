package persistence;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import model.Company;

/**
 * Test class for CompanyMapper
 *
 * @author bbinet
 */
public class CompanyMapperTest {

  private final String NAME = "string";
  private final int    ID   = 0;

  @Test
  public void mapTest() {
    ResultSet rSet = mock(ResultSet.class);
    try {
      when(rSet.first()).thenReturn(true);
      when(rSet.getInt(anyString())).thenReturn(this.ID);
      when(rSet.getString(anyString())).thenReturn(this.NAME);
      CompanyMapper companyMapper = new CompanyMapper();
      Company       company       = companyMapper.map(rSet);
      Company       mCompany      = mock(Company.class);
      when(mCompany.getId()).thenReturn(this.ID);
      when(mCompany.getName()).thenReturn(this.NAME);
      assertTrue(mCompany.equals(company));

    }
    catch (SQLException e) {
      e.printStackTrace();
      fail();
    }

  }

}
