import java.sql.*;
import java.util.Scanner;

public class PreparedCallableCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/testdb?serverTimezone=UTC";
    static final String USER = "root";           // change
    static final String PASS = "your_password";  // change

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            while (true) {
                System.out.println("\n1.Insert (Callable)");
                System.out.println("2.List all (Prepared)");
                System.out.println("3.Get by id (Callable)");
                System.out.println("4.Update (Callable)");
                System.out.println("5.Delete (Callable)");
                System.out.println("6.Exit");
                System.out.print("Choice: ");
                int ch = Integer.parseInt(sc.nextLine());
                switch (ch) {
                    case 1 -> insertUsingCallable(sc);
                    case 2 -> listAllUsingPrepared();
                    case 3 -> getByIdUsingCallable(sc);
                    case 4 -> updateUsingCallable(sc);
                    case 5 -> deleteUsingCallable(sc);
                    case 6 -> { System.out.println("Bye"); return; }
                    default -> System.out.println("Invalid");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------- PreparedStatement example (SELECT) ----------
    static void listAllUsingPrepared() {
        String sql = "SELECT id, name, age FROM student ORDER BY id";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID | Name | Age");
            while (rs.next()) {
                System.out.printf("%d | %s | %d%n",
                    rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
            }
        } catch (SQLException e) {
            System.err.println("Error listing: " + e.getMessage());
        }
    }

    // ---------- CallableStatement examples (stored procedures) ----------
    static void insertUsingCallable(Scanner sc) {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine());

        String call = "{ call sp_insert_student(?, ?, ?) }";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = con.prepareCall(call)) {

            cs.setString(1, name);           // IN p_name
            cs.setInt(2, age);               // IN p_age
            cs.registerOutParameter(3, Types.INTEGER); // OUT p_id

            cs.execute();
            int newId = cs.getInt(3);
            System.out.println("Inserted with id = " + newId);
        } catch (SQLException e) {
            System.err.println("Insert error: " + e.getMessage());
        }
    }

    static void getByIdUsingCallable(Scanner sc) {
        System.out.print("Enter id: ");
        int id = Integer.parseInt(sc.nextLine());
        String call = "{ call sp_get_student_by_id(?) }";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = con.prepareCall(call)) {

            cs.setInt(1, id);
            boolean hasResult = cs.execute();
            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        System.out.printf("%d | %s | %d%n",
                                rs.getInt("id"), rs.getString("name"), rs.getInt("age"));
                    } else {
                        System.out.println("Not found.");
                    }
                }
            } else {
                System.out.println("No result returned.");
            }
        } catch (SQLException e) {
            System.err.println("GetById error: " + e.getMessage());
        }
    }

    static void updateUsingCallable(Scanner sc) {
        System.out.print("Enter id to update: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New name: ");
        String name = sc.nextLine();
        System.out.print("New age: ");
        int age = Integer.parseInt(sc.nextLine());

        String call = "{ call sp_update_student(?, ?, ?) }";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = con.prepareCall(call)) {

            cs.setInt(1, id);
            cs.setString(2, name);
            cs.setInt(3, age);

            int rows = cs.executeUpdate(); // executeUpdate for procedures that do update/delete
            System.out.println("Update executed. (rows affected may be DB-specific) " + rows);
        } catch (SQLException e) {
            System.err.println("Update error: " + e.getMessage());
        }
    }

    static void deleteUsingCallable(Scanner sc) {
        System.out.print("Enter id to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        String call = "{ call sp_delete_student(?) }";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             CallableStatement cs = con.prepareCall(call)) {

            cs.setInt(1, id);
            int rows = cs.executeUpdate();
            System.out.println("Delete executed. (rows affected may be DB-specific) " + rows);
        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
        }
    }
}
