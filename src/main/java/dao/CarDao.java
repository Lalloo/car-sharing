package dao;

import domain.Car;
import domain.Company;
import domain.Customer;

import java.util.List;

public interface CarDao {
    void save (Car car);

    List<Car> getAllByCompany(Company company);

    Car get(Customer customer);
}
