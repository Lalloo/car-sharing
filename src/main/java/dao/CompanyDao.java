package dao;

import domain.Company;

public interface CompanyDao {
    void save (Company company);

    void deleteAll();

    void printAll();
}
