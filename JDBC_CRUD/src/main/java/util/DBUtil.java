package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static String url = "jdbc:mysql://MAJJI:3306/studentdb"; // Host from your image
    private static String user = "java"; // Change if needed
    private static String pass = "JAVA123"; // Change to your actual password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
