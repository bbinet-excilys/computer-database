package com.excilys.cdb.main.model;

import java.sql.Date;

/**
 * 
 * @author bbinet Model class for db computer entity
 */
public class Computer extends Entity {

    private Integer companyId;
    private String  name;
    private Date    introduced, discontinued;
    private Company company;

    /**
     * 
     * @return The ID of the Company the computer belongs to
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 
     * @param companyId The ID of the Company the computer belongs to
     */
    public void setCompanyId(Integer companyId) {
        if (companyId != null)
            this.companyId = companyId;
    }

    /**
     * 
     * @return The name of the Computer
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name The name of the Computer
     */
    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    /**
     * 
     * @return The java.sql.Date object of introduction of the Computer
     */
    public Date getIntroduced() {
        return introduced;
    }

    /**
     * 
     * @param introduced The java.sql.Date object of introduction of the Computer
     */
    public void setIntroduced(Date introduced) {
        if (introduced != null)
            this.introduced = introduced;
    }

    /**
     * 
     * @return The java.sql.Date object of discontinuation of the Computer
     */
    public Date getDiscontinued() {
        return discontinued;
    }

    /**
     * 
     * @param discontinued The java.sql.Date object of discontinuation of the Computer
     */
    public void setDiscontinued(Date discontinued) {
        if (discontinued != null)
            this.discontinued = discontinued;
    }

    /**
     * 
     * @return The Company object the Computer belongs to
     */
    public Company getCompany() {
        return company;
    }

    /**
     * 
     * @param company The Company object the Computer belongs to
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return String.format("%5s | %30.30s | %10s | %10s | %s", this.id, this.name, this.introduced, this.discontinued,
                (this.company != null) ? this.company.toString() : String.format("%5s", "null"));
    }

}
