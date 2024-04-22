/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmgt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Gertrude
 */


public class StudentMGT {
    public static void main(String[] args) {
        String URL = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String passwd = "root";

        try (Scanner sc = new Scanner(System.in);
             Connection con = DriverManager.getConnection(URL, username, passwd)) {
            System.out.println("Welcome to student management system");
            System.out.println("======================================");

            int choice;
            do {
                System.out.println("====================");
                System.out.println("1. Register Student");
                System.out.println("2. Update Student");
                System.out.println("3. Delete Student");
                System.out.println("4. List of Students");
                System.out.println("0. Exit");
                System.out.println("=====================");
                System.out.print("Enter a choice of what you want: ");

                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        registerStudent(sc, con);
                        break;
                    case 2:
                        updateStudent(sc, con);
                        break;
                    case 3:
                        deleteStudent(sc, con);
                        break;
                    case 4:
                        studentList(sc, con);
                        break;    
                    case 0:
                        System.out.println("Thank you for using our system\n\n");
                        break;
                    default:
                        System.out.println("\nEnter a valid value\n");
                        break;
                }
            } while (choice != 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void registerStudent(Scanner sc, Connection con) throws SQLException {
        System.out.print("Enter Student ID: ");
        int st_id = sc.nextInt();
        System.out.print("Enter first name: ");
        String st_fname = sc.next();
        System.out.print("Enter last name: ");
        String st_lname = sc.next();
        System.out.print("Enter a faculty: ");
        String st_faculty = sc.next();
        System.out.print("Enter number of credits: ");
        int st_credits = sc.nextInt();
        System.out.print("Enter GPA: ");
        double st_GPA = sc.nextDouble();

        String query = "INSERT INTO student_info VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, st_id);
            pstmt.setString(2, st_fname);
            pstmt.setString(3, st_lname);
            pstmt.setString(4, st_faculty);
            pstmt.setInt(5, st_credits);
            pstmt.setDouble(6, st_GPA);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected >= 1) {
                System.out.println("Inserted row successfully\n");
            } else {
                System.out.println("Not inserted successfully\n");
            }
        }
    }

    private static void updateStudent(Scanner sc, Connection con) throws SQLException {
        System.out.print("Enter Student ID: ");
        int st_id = sc.nextInt();
        if (!isStudentIdExists(con, st_id)) {
            System.out.println("Student ID does not exist");
            return;
        }
        int choice;
        do {
            System.out.println("====================");
            System.out.println("1. Update first name");
            System.out.println("2. Update last name");
            System.out.println("3. Update faculty");
            System.out.println("4. Update credits");
            System.out.println("5. Update GPA");
            System.out.println("0. Exit");
            System.out.println("=====================");
            System.out.print("Enter a choice of what you want: ");
            choice = sc.nextInt();
            String columnName = "";
            String newValue = "";
            switch (choice) {
                case 1:
                    System.out.print("Enter new first name: ");
                    columnName = "st_fname";
                    newValue = sc.next();
                break;
                case 2:
                    System.out.print("Enter new last name: ");
                    columnName = "st_lname";
                    newValue = sc.next();
                break;
                case 3:
                    System.out.print("Enter new faculty: ");
                    columnName = "st_faculty";
                    newValue = sc.next();
                break;
                case 4:
                    System.out.print("Enter new credits: ");
                    columnName = "st_credits";
                    newValue = Integer.toString(sc.nextInt());
                break;
                case 5:
                    System.out.print("Enter new GPA: ");
                    columnName = "st_GPA";
                    newValue = Double.toString(sc.nextDouble());
                break;
                case 0:
                    System.out.println("Exiting update process");
                    return;
                default:
                System.out.println("Invalid choice");
                continue;
            }
            String query = "UPDATE student_info SET " + columnName + " = ? WHERE st_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, newValue);
                pstmt.setInt(2, st_id);
                
                int rowAffected = pstmt.executeUpdate();
                if (rowAffected >= 1) {
                    System.out.println("Data Updated");
                } else {
                    System.out.println("No rows updated");
                }
            }
        } while (choice != 0);
    }


    private static void deleteStudent(Scanner sc, Connection con) throws SQLException {
        System.out.print("Enter Student ID to delete: ");
        int st_id = sc.nextInt();
        
        if (!isStudentIdExists(con, st_id)) {
            System.out.println("Student ID does not exist");
            return;
        }

        String query = "DELETE FROM student_info WHERE st_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, st_id);
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected >= 1) {
                System.out.println("Data Deleted");
            } else {
                System.out.println("No rows deleted");
            }
        }
    }
    private static boolean isStudentIdExists(Connection con, int st_id) throws SQLException {
        String query = "SELECT COUNT(*) FROM student_info WHERE st_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, st_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
   private static boolean studentList(Scanner sc, Connection con) throws SQLException {
    String query = "SELECT * FROM student_info";
    try (PreparedStatement pstmt = con.prepareStatement(query)) {
        ResultSet result = pstmt.executeQuery();

        while (result.next()) {
            int id = result.getInt(1);
            String name = result.getString(2);
            String address = result.getString(3);
            String email = result.getString(4);
            int age = result.getInt(5);
            double gpa = result.getDouble(6);

            // Print out the student information
            System.out.println("ID: " + id);
            System.out.println("Name: " + name);
            System.out.println("Address: " + address);
            System.out.println("Email: " + email);
            System.out.println("Age: " + age);
            System.out.println("GPA: " + gpa);
            System.out.println(); // Add a line break for readability
        }

        con.close();
    }
    return false;
}


}
