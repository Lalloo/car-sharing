import dao.CarDao;
import dao.CompanyDao;
import dao.CustomerDao;
import dao.impl.CarDaoImpl;
import dao.impl.CompanyDaoImpl;
import dao.impl.CustomerDaoImpl;
import domain.Car;
import domain.Company;
import domain.Customer;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CarSharingApp {
    private static final Scanner SCANNER = new Scanner(System.in);

    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    private final ManagerAccount manager;
    private final CustomerAccount customer;

    public CarSharingApp(String[] args) {
        companyDao = new CompanyDaoImpl();
        carDao = new CarDaoImpl();
        customerDao = new CustomerDaoImpl();
        manager = new ManagerAccount(companyDao, carDao);
        customer = new CustomerAccount(companyDao, carDao, customerDao);
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            try {
                switch (Integer.parseInt(SCANNER.nextLine())) {
                    case 1:
                        manager.logInAsManager();
                        break;
                    case 2:
                        customerList();
                        break;
                    case 3:
                        createCustomer();
                        break;
                    case 0:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter number 0-1!");
            }
        }
    }

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        Customer customer = new Customer(SCANNER.nextLine());
        customerDao.save(customer);
        System.out.println("The customer was added!");
    }


    private void customerList() {
        List<Customer> customers = customerDao.getAll();
//        customers.forEach(c -> {
//            if (c.getRentCarId() != 0) {
//                c.setCar(carDao.get(c));
//            }
//        });
        if (customers.isEmpty())
            throw new IllegalStateException("\nThe customer list is empty!");
        System.out.println("\nCustomer list:");
        AtomicInteger i = new AtomicInteger(1);
        customers.forEach(customer -> System.out.println(i.getAndIncrement() + ". " + customer.getName()));
        System.out.println("0. Back");
        int userInput = 0;
        try {
            userInput = Integer.parseInt(SCANNER.nextLine());
            if (userInput == 0)
                return;
            if (userInput > customers.size() || userInput < 0) {
                throw new IllegalArgumentException("Not found company with id:" + userInput);
            }
        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter number 0-" + customers.size() + "!\n");
        }
        customer.customerLogin(customers.get(userInput - 1));
    }
}
