package ui;

import java.util.List;

import model.Company;

public class CompanyUI {
    
    public static void print(Company company) {
        System.out.println(company.toString());
    }
    
    public static void printList(List<Company> companyList) {
        for(Company tCompany : companyList) {
            print(tCompany);
        }
    }

}
