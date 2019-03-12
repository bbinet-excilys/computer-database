package com.excilys.cdb.main.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.main.model.Company;
import com.excilys.cdb.main.model.Computer;

public class ComputerMapper extends Mapper<Computer> {

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
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rComputerList;
    }

}
