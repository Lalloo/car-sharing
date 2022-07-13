package domain;

public class Car {
    private Integer id;
    private final String name;
    private Company company;
    private Integer companyId;

    public Car(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public Car (String name, Integer companyId) {
        this.name = name;
        this.companyId = companyId;
    }



    public Car(Integer id, String name, Company company) {
        this.id = id;
        this.name = name;
        this.company = company;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
