import java.sql.*;
import java.util.Scanner;

public class SimpleCRUD {
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root"; // your MySQL username
    static final String PASS = "your_password"; // your MySQL password

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== SIMPLE JDBC CRUD ===");
            System.out.println("1. Insert student");
            System.out.println("2. View students");
            System.out.println("3. Update student");
            System.out.println("4. Delete student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: insertStudent(sc); break;
                case 2: viewStudents(); break;
                case 3: updateStudent(sc); break;
                case 4: deleteStudent(sc); break;
                case 5: System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    static void insertStudent(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter name: ");
            String name = sc.nextLine();
            System.out.print("Enter age: ");
            int age = sc.nextInt();

            String sql = "INSERT INTO student (name, age) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.executeUpdate();
            System.out.println("✅ Student added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void viewStudents() {
        try (Connection con = getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM student");

            System.out.println("\n--- Student List ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                                   rs.getString("name") + " | " +
                                   rs.getInt("age"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void updateStudent(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter student ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new name: ");
            String name = sc.nextLine();
            System.out.print("Enter new age: ");
            int age = sc.nextInt();

            String sql = "UPDATE student SET name=?, age=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("✅ Student updated!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteStudent(Scanner sc) {
        try (Connection con = getConnection()) {
            System.out.print("Enter student ID to delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM student WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Student deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
