package wwan.back;

import java.sql.*;
import java.util.Objects;
import java.util.Scanner;


public class DBconnection {
    private Connection connection = null;

    public DBconnection() {
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

    public String checkUserName(String name) {
        Statement stmt = null;
        String result = "";
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Users where userName LIKE '" + name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) result = rs.getString("pwd");
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    public void addUser(String userName, String pwd) {
        PreparedStatement stmt = null;
        try{
            String sql;
            sql = "INSERT INTO Users (userName, pwd) VALUES (?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, pwd);
            stmt.execute();
            connection.commit();
            System.out.println("User added!");
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public String searchMusic(String name) {
        Statement stmt = null;
        String result = "";
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT mName FROM Users where userName LIKE $name";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) result = rs.getString("mName");
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    public String topFive() {
        Statement stmt = null;
        String result = "";
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT mName, aName, genre FROM Musics NATURAL JOIN Reviews where userName LIKE $name GROUP BY mID";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) result = rs.getString("mName");
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    private void searchMusicsByLyrics(String lyrics) {
        Statement stmt = null;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT mName FROM Musics where lyrics LIKE '%" + lyrics + "%'";
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

    private void rateMusics(String userName, Integer mID, double rate, String comment) {
        PreparedStatement stmt = null;
        try{
            String sql;
            sql = "INSERT INTO Reviews (userName, mID, rating, comment) VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setInt(2, mID);
            stmt.setDouble(3, rate);
            stmt.setString(4, comment);
            stmt.execute();
            connection.commit();
            System.out.println("Comment added!");
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public static void main(String [] args) throws Exception {
        DBconnection db = new DBconnection();
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the command (q to quit)");
        System.out.println("\"search\" to search a song by lyrics");
        System.out.println("\"rate\" to rate a song");
        System.out.println("\"show\" to show all musics in the database");
        while (true) {
            String line = input.nextLine();
            if (Objects.equals(line, "q")) break;
            if (Objects.equals(line, "search")) {
                System.out.println("Please type some lyrics");
                String lyrics = input.nextLine();
                db.searchMusicsByLyrics(lyrics);
            } else if (Objects.equals(line, "rate")) {
                // Assume the given mID exists in the database
                String userName = "test1";
                System.out.println("Please type mID");
                Integer mID = input.nextInt();
                input.nextLine();
                System.out.println("Please type your rating (out of 100)");
                double rate = input.nextDouble();
                input.nextLine();
                System.out.println("Please type your comment");
                String comment = input.nextLine();
                db.rateMusics(userName, mID, rate, comment);
            } else if (Objects.equals(line, "show")) {
                db.showAllMusics();
            }
            else System.out.println("Unknown command, please try again!");
        }
        db.exit();
    }
}
