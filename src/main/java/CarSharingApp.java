
import dao.impl.CompanyDaoImpl;
import domain.Company;
import util.DataBaseUtil;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class CarSharingApp {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final Scanner SCANNER = new Scanner(System.in);
    private static Integer id = 1;
    Map<Integer, String> company = new HashMap<>();
    private static CompanyDaoImpl companyDao;

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
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            try {
                switch (SCANNER.nextInt()) {
                    case 1:
                        logInAsManager();
                        break;
                    case 0:
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
                        return;
                    case 2:
                        createCompany();
                        return;
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

    private void createCompany() {
        System.out.println("\nEnter the company name:");
        SCANNER.nextLine();
        String companyName = SCANNER.nextLine();
        company.put(id, companyName);
        Company company = new Company(id, companyName);
        companyDao.save(company);
        id++;
    }

    private void companyList() {
        if (id == 1)
            throw new IllegalStateException("The company list is empty!");
        for (int i = 1; i < id; i++) {
            System.out.println(i + ". " + company.get(i));
        }
    }
}
