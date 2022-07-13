import dao.CarDao;
import dao.CompanyDao;
import domain.Car;
import domain.Company;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ManagerAccount {

    private static final Scanner SCANNER = new Scanner(System.in);

    private final CompanyDao companyDao;
    private final CarDao carDao;

    public ManagerAccount(CompanyDao companyDao, CarDao carDao) {
        this.companyDao = companyDao;
        this.carDao = carDao;
    }


    public void logInAsManager() {
        while (true) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            try {
                switch (Integer.parseInt(SCANNER.nextLine())) {
                    case 1:
                        companyList();
                        break;
                    case 2:
                        addCompany();
                        break;
                    case 0:
                        System.out.println();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease enter number 0-2!");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addCompany() {
        System.out.println("\nEnter the company name:");
        String companyName = SCANNER.nextLine();
        Company company = new Company(companyName);
        companyDao.save(company);
        System.out.println("The company was created!");
    }

    private void companyList() {
        List<Company> companies = companyDao.getAll();
        if (companies.isEmpty())
            throw new IllegalStateException("\nThe company list is empty!");
        System.out.println("\nChoose the company:");
        AtomicInteger i = new AtomicInteger(1);
        companies.forEach(company -> System.out.println(i.getAndIncrement() + ". " + company.getName()));
        System.out.println("0. Back");
        int userInput = Integer.parseInt(SCANNER.nextLine());
        if (userInput == 0)
            return;
        companies.stream()
                .filter(c -> c.getId().equals(userInput))
                .findFirst()
                .ifPresentOrElse(
                        this::companyLogin,
                        () -> {
                            throw new IllegalArgumentException("Not found company with id:" + userInput);
                        });
    }

    private void companyLogin(Company company) {
        while (true) {
            System.out.println("\n'" + company.getName() + "' company");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            try {
                switch (Integer.parseInt(SCANNER.nextLine())) {
                    case 1:
                        carList(company);
                        break;
                    case 2:
                        addCar(company);
                        break;
                    case 0:
                        System.out.println();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nPlease write number 0-2!\n");
            } catch (IllegalStateException e) {
                if (!e.getMessage().equals(""))
                    System.out.println(e.getMessage());
            }
        }
    }

    private void carList(Company company) {
        List<Car> cars = carDao.getAllByCompany(company);
        if (cars.isEmpty())
            throw new IllegalStateException("\nThe car list is empty!");
        AtomicInteger i = new AtomicInteger(1);
        cars.forEach(car -> System.out.println(i.getAndIncrement() + ". " + car.getName()));
    }

    private void addCar(Company company) {
        System.out.println("\nEnter the car name:");
        Car car = new Car(SCANNER.nextLine(), company);
        carDao.save(car);
        System.out.println("The car was added!");
    }

}
