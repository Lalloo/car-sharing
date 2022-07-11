package domain;

public class Car {
    private Integer id = 0;
    private final String name;

    public Car(String name) {
        id++;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
