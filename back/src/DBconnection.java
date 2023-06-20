import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class DBconnection {
    private Connection connection = null;

    public DBconnection(String[] args) {
        // loading the DBMS driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Missing DBMS driver.");
            e.printStackTrace();
        }

        try {
            // connecting to the a database
            String url = "jdbc:mysql://localhost:3306/test";
            String name = "test";
            String password = "test";
            connection = DriverManager
                    .getConnection(url, name, password);
            System.out.println("Database connection open.\n");

            // setting auto commit to false
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("DBMS connection failed.");
            e.printStackTrace();
        }
    }

    public void exit() {
        try {
            // close database connection
            connection.commit();
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mainMenu(String[] args) throws SQLException {
        this.showAllMusics();
    }

    private void showAllMusics() {
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT mName FROM Musics";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String mName = rs.getString("mName");
                System.out.print("Music name: " + mName);
                System.out.print("\n");
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    private void searchMusics(String lyrics) {
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT mName FROM Musics where lyrics LIKE \'%" + lyrics + "%\'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) { System.out.println("No music found in the database"); }
            else {
                do {
                    String mName = rs.getString("mName");
                    System.out.print("Found music name: " + mName);
                    System.out.print("\n");
                } while(rs.next());
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }


    public static void main(String [] args) throws Exception {
        DBconnection menu = new DBconnection(args);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Search music by lyrics");
        System.out.println("Enter some lyrics: ");
        try {
            String lyrics = input.readLine();

            menu.searchMusics(lyrics);
        }
        catch (Exception e) {
            System.out.println("Something went wrong!");
        }
        menu.mainMenu(args);
        menu.exit();
    }
}
