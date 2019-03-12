package model;

import java.sql.Date;

public class Computer extends Entity {

    private Integer companyId;
    private String  name;
    private Date    introduced, discontinued;

    public Computer() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        if(id!=null)
            this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        if(companyId!=null)
            this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name!=null)
            this.name = name;
    }

    public Date getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Date introduced) {
        if(introduced!=null)
            this.introduced = introduced;
    }

    public Date getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Date discontinued) {
        if(discontinued!=null)
            this.discontinued = discontinued;
    }

    @Override
    public String toString() {
        return String.format("%5s | %30.30s | %10s | %10s | %5s", this.id, this.name, this.introduced, this.discontinued,
                this.companyId);
    }

}
