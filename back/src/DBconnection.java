import java.sql.*;
import java.util.Scanner;


public class DBconnection {
    private Scanner input = new Scanner(System.in);
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
        this.showTable();
    }

    private void showTable() {
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Users";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String name = rs.getString("name");
                System.out.print("name: " + name);
                System.out.print("\n");
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }


    public static void main(String [] args) throws Exception {
        DBconnection menu = new DBconnection(args);
        menu.mainMenu(args);
        menu.exit();
    }
}
