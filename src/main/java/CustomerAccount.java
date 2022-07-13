import dao.CarDao;
import dao.CompanyDao;
import dao.CustomerDao;
import domain.Car;
import domain.Company;
import domain.Customer;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerAccount {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    private final static Scanner SCANNER = new Scanner(System.in);

    public CustomerAccount(CompanyDao companyDao, CarDao carDao, CustomerDao customerDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
        this.customerDao = customerDao;
    }


    public void customerLogin(Customer customer) {
        while (true) {
            System.out.println("\n1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            try {
                switch (Integer.parseInt(SCANNER.nextLine())) {
                    case 1:
                        customerRentCar(customer);
                        break;
                    case 2:
                        customerReturnRentedCar(customer);
                        break;
                    case 3:
                        customerRentedCar(customer);
                        break;
                    case 0:
                        System.out.println();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter number 0-3!");
            } catch (IllegalStateException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void customerRentCar(Customer customer) {
        Optional<Car> car = Optional.ofNullable(customer.getCar());
        if (car.isPresent()) {
            throw new IllegalStateException("\nYou've already rented a car!\n");
        }
        customerChooseCompany(customer);
    }

    private void customerChooseCompany(Customer customer) {
        List<Company> companies = companyDao.getAll();
        if (companies.isEmpty())
            throw new IllegalStateException("\nThe company list is empty!");
        System.out.println("\nChoose the company:");
        AtomicInteger i = new AtomicInteger(1);
        companies.forEach(company -> System.out.println(i.getAndIncrement() + ". " + company.getName()));
        System.out.println("0. Back");
        int userInput = 0;
        try {
            userInput = Integer.parseInt(SCANNER.nextLine());
            if (userInput == 0)
                return;
            if (userInput > companies.size() || userInput < 0) {
                throw new IllegalArgumentException("Not found company with id:" + userInput);
            }
        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter number 0-" + companies.size() + "!\n");
        }
        customerChooseCar(companies.get(userInput - 1), customer);
    }

    private void customerChooseCar(Company company, Customer customer) {
        List<Car> cars = carDao.getAllByCompany(company);
        System.out.println("\nChoose a car:");
        AtomicInteger i = new AtomicInteger(1);
        Set<Integer> rentedCarIds = customerDao.getRentedCarIds();

        cars.stream()
                .filter(c -> !rentedCarIds.contains(c.getId()))
                .forEach(car -> System.out.println(i.getAndIncrement() + ". " + car.getName()));
        System.out.println("0. Back");
        int userInput = 0;
        try {
            userInput = Integer.parseInt(SCANNER.nextLine());
            if (userInput == 0)
                return;
            if (userInput > cars.size() || userInput < 0) {
                throw new IllegalArgumentException("Not found company with id:" + userInput);
            }
        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter number 0-" + cars.size() + "!\n");
        }

        customer.setCar(cars.get(userInput - 1));
        customerDao.update(customer);
        System.out.println("\nYou rented " + "'" + cars.get(userInput - 1).getName() + "'");
    }

    private void customerReturnRentedCar(Customer customer) {
        Optional<Car> car = Optional.ofNullable(customer.getCar());
        if (car.isEmpty()) {
            throw new IllegalStateException("\nYou didn't rent a car!");
        }
        customerDao.delete(customer);
        customer.setCar(null);
        System.out.println("\nYou've returned a rented car");
    }

    private void customerRentedCar(Customer customer) {
        Optional<Car> car = Optional.ofNullable(customer.getCar());
        if (car.isEmpty()) {
            throw new IllegalStateException("\nYou didn't rent a car!");
        }
        System.out.println("\nYour rented car:\n" + car.get().getName());
        List<Company> companies = companyDao.getAll();
        Optional<Company> company = companies.stream()
                .filter(c -> c.getId().equals(car.get().getCompanyId()))
                .findFirst();
        company.ifPresent(value -> car.get().setCompany(value));
        System.out.println("Company:\n" + car.get().getCompany().getName());
    }
}
