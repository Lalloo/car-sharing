
import dao.impl.CarDaoImpl;
import dao.impl.CompanyDaoImpl;
import domain.Car;
import domain.Company;
import util.DataBaseUtil;

import java.util.*;

public class CarSharingApp {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final Scanner SCANNER = new Scanner(System.in);
    List<Company> companyList = new ArrayList<>();
    List<Car> carList = new ArrayList<>();

    private static CompanyDaoImpl companyDao;
    private static CarDaoImpl carDao;

    public CarSharingApp(String[] args) {
        DataBaseUtil.setDbUrl(args);
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        companyDao = new CompanyDaoImpl();
        carDao = new CarDaoImpl();
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            try {
                switch (SCANNER.nextInt()) {
                    case 1:
                        logInAsManager();
                        break;
                    case 0:
                        companyDao.deleteAll();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter number 0-1!");
            }
        }
    }

    private void logInAsManager() {
        while (true) {
            System.out.println("\n1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            try {
                switch (SCANNER.nextInt()) {
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
                if (!e.getMessage().equals(""))
                    System.out.println(e.getMessage());
            }
        }
    }

    private void addCompany() {
        System.out.println("\nEnter the company name:");
        SCANNER.nextLine();
        String companyName = SCANNER.nextLine();
        System.out.println("The company was created!");
        Company company = new Company(companyName);
        companyList.add(company);
        companyDao.save(company);
    }

    private void companyList() {
        if (companyList.isEmpty())
            throw new IllegalStateException("\nThe company list is empty!");
        System.out.println("\nChoose the company:");
        companyList.forEach(company -> System.out.println(company.getId() + ". " + company.getName()));
        System.out.println("0. Back");
        int userInput = SCANNER.nextInt();
        if (userInput == 0)
            throw new IllegalStateException("");
        companyList.forEach(company -> {
            if (company.getId() == userInput)
                companyLogin(companyList.get(userInput-1));
        });

    }

    private void companyLogin(Company company) {
        while (true) {
            System.out.println("\n'" + company.getName() + "' company");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            try {
                switch (SCANNER.nextInt()) {
                    case 1:
                        carList();
                        break;
                    case 2:
                        addCar();
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

    private void carList() {
        if (carList.isEmpty())
            throw new IllegalStateException("\nThe car list is empty!");
        carList.forEach(car -> System.out.println(car.getId() + ". " + car.getName()));
    }

    private void addCar() {
        System.out.println("\nEnter the car name:");
        SCANNER.nextLine();
        String carName = SCANNER.nextLine();
        System.out.println("The car was added!");
        Car car = new Car(carName);
        carList.add(car);
        carDao.save(car);
        carDao.printAll();
    }
}
