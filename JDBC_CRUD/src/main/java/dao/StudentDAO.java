package dao;

import java.sql.*;
import java.util.*;
import model.Student;
import util.DBUtil;

public class StudentDAO {

    public static int insertStudent(Student s) {
        int status = 0;
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO student(name,email,city) VALUES (?,?,?)");
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCity());
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
        return status;
    }

    public static List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setCity(rs.getString("city"));
                list.add(s);
            }
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static int updateStudent(Student s) {
        int status = 0;
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE student SET name=?, email=?, city=? WHERE id=?");
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getCity());
            ps.setInt(4, s.getId());
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
        return status;
    }

    public static int deleteStudent(int id) {
        int status = 0;
        try {
            Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();
            con.close();
        } catch (Exception e) { e.printStackTrace(); }
        return status;
    }
}
