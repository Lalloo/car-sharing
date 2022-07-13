package domain;

public class Customer {
    private final String name;
    private Integer id;
    private Car car;
    private Integer rentCarId;


    public Customer(String name, Integer id, Car car, Integer rentCarId) {
        this.id = id;
        this.name = name;
        this.car = car;
        this.rentCarId = rentCarId;
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

    public Car getCar() {
        return car;
    }

    public Integer getRentCarId() {
        return rentCarId;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
