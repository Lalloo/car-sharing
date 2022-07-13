package domain;

import java.util.Optional;

public class Customer {
    private Integer id;
    private final String name;
    private Car car;

    public Customer(Integer id, String name, Car car) {
        this.id = id;
        this.name = name;
        this.car = car;
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Optional<Car> getCar() {
        return Optional.ofNullable(car);
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
