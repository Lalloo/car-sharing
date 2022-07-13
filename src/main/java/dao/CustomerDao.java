package dao;

import domain.Customer;

import java.util.List;
import java.util.Set;

public interface CustomerDao {
    void save(Customer customer);

    List<Customer> getAll();

    void update(Customer customer);

    void delete(Customer customer);

    Set<Integer> getRentedCarIds();
}
