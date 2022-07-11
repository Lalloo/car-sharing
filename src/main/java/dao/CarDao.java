package dao;

import domain.Car;

public interface CarDao {
    void save (Car car);

    void deleteAll();

    void printAll();
}
