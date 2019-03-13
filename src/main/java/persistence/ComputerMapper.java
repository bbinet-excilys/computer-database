package persistence;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Computer;

public class ComputerMapper extends Mapper<Computer> {
    
    static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);

    @Override
    public Computer map(ResultSet resultSet) {
        Computer rComputer = null;
        try {
            if (resultSet.first()) {
                rComputer = new Computer();
                Company rCompany = new Company();
                rComputer.setId(resultSet.getInt("computer.id"));
                rComputer.setName(resultSet.getString("computer.name"));
                rComputer.setIntroduced(resultSet.getDate("computer.introduced"));
                rComputer.setDiscontinued(resultSet.getDate("computer.discontinued"));
                rComputer.setCompanyId(resultSet.getInt("computer.company_id"));
                rCompany = new Company();
                rCompany.setId(resultSet.getInt("company.id"));
                rCompany.setName(resultSet.getString("company.name"));
                rComputer.setCompany(rCompany);
            }
        }
        catch (SQLException e) {
            LOG.warn("Error in mapping : "+e.getMessage());
        }
        return rComputer;
    }

    @Override
    public List<Computer> mapList(ResultSet resultSet) {
        List<Computer> rComputerList = null;
        try {
            rComputerList = new ArrayList<Computer>();
            while (resultSet.next()) {
                Computer rComputer = new Computer();
                Company rCompany = new Company();
                rComputer.setId(resultSet.getInt("computer.id"));
                rComputer.setName(resultSet.getString("computer.name"));
                rComputer.setIntroduced(resultSet.getDate("computer.introduced"));
                rComputer.setDiscontinued(resultSet.getDate("computer.discontinued"));
                rComputer.setCompanyId(resultSet.getInt("computer.company_id"));
                rCompany = new Company();
                rCompany.setId(resultSet.getInt("company.id"));
                rCompany.setName(resultSet.getString("company.name"));
                rComputer.setCompany(rCompany);
                rComputerList.add(rComputer);
            }
        }
        catch (SQLException e) {
            LOG.warn("Error in list mapping : "+e.getMessage());
        }
        return rComputerList;
    }

}
