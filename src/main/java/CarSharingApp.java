
import dao.impl.CompanyDaoImpl;
import util.DataBaseUtil;

public class CarSharingApp {
    private static final String JDBC_DRIVER = "org.h2.Driver";
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
    }
}
