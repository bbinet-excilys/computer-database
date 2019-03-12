package com.excilys.cdb.main.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.main.model.Company;

public class CompanyMapper extends Mapper<Company> {

    @Override
    public Company map(ResultSet result) {
        Company rCompany = null;
        try {
            if(result.first()) {
                rCompany = new Company();
                rCompany.setId(result.getInt("id"));
                rCompany.setName(result.getString("name"));
            }
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rCompany;
    }

    @Override
    public List<Company> mapList(ResultSet resultSet) {
        List<Company> rCompanyList = null;
        try {
            rCompanyList = new ArrayList<Company>();
            while (resultSet.next()) {
                Company tCompany = new Company();
                tCompany.setId(resultSet.getInt("id"));
                tCompany.setName(resultSet.getString("name"));
                rCompanyList.add(tCompany);
            }
        }
        catch (SQLException e) {
            //TODO Logger !
            e.printStackTrace();
        }
        return rCompanyList;
    }

}
