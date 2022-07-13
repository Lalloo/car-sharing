package dao;

import domain.Company;

import java.util.List;

public interface CompanyDao {
    void save (Company company);


    List<Company> getAll();
}
