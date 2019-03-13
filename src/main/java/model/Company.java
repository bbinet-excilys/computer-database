package model;
/**
 * 
 * @author bbinet
 * Model class for database company entity
 */
public class Company extends Entity {

    /**
     * The Name of the Company
     */
    private String  name;

    /**
     * @return The Name of the Company
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name The name of the Company to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%5s | %30s", this.id, this.name);
    }

}
