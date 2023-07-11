package wwan.back;

import java.sql.*;
import java.sql.Date;
import java.util.*;


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

    public Boolean searchMusic(String name) {
        Statement stmt = null;
        Boolean result = false;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Musics where mName LIKE '" + name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) result = true;
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    public Map<String, String> songInfo(String name) {
        Statement stmt = null;
        String mName = "";
        String aName = "";
        String genre = "";
        String topic = "";
        String lyrics = "";
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Musics where mName LIKE '" + name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            mName = rs.getString("mName");
            aName = rs.getString("aName");
            genre = rs.getString("genre");
            topic = rs.getString("topic");
            lyrics = rs.getString("lyrics");
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return Map.of("mName", mName, "aName", aName, "genre", genre, "topic", topic, "lyrics", lyrics);
    }

    public List<Map<String, String>> topFive() {
        Statement stmt = null;
        List<Map<String, String>> result = new ArrayList<>();
        int num;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT count(*) FROM Reviews";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            num = rs.getInt(1);
            if (num < 5) sql = "SELECT mName, aName, genre FROM Musics";
            else sql = "SELECT mName, aName, genre FROM Reviews NATURAL JOIN Musics GROUP BY mID ORDER BY AVG(rating) DESC";
            rs = stmt.executeQuery(sql);
            int i = 1;
            while (i <= 5) {
                rs.next();
                result.add(Map.of("songName", rs.getString(1), "artist", rs.getString(2), "genre", rs.getString(3)));
                i++;
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    public List<Map<String, String>> userFive(String userName) {
        Statement stmt = null;
        List<Map<String, String>> result = new ArrayList<>();
        int num;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT count(*) FROM Reviews WHERE userName LIKE '" + userName + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            num = rs.getInt(1);
            if (num < 5) sql = "SELECT mName, aName, genre FROM Musics";
            else sql = "SELECT mName, aName, genre FROM Reviews NATURAL JOIN Musics WHERE userName LIKE '" + userName + "'";
            rs = stmt.executeQuery(sql);
            int i = 1;
            while (i <= 5) {
                rs.next();
                result.add(Map.of("songName", rs.getString(1), "artist", rs.getString(2), "genre", rs.getString(3)));
                i++;
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    public List<Map<String, String>> recommend(String userName) {
        Statement stmt = null;
        List<Map<String, String>> result = new ArrayList<>();
        int num;
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Reviews WHERE userName LIKE '" + userName + "' order by dt DESC";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                int rating = rs.getInt("rating");
                int mID = rs.getInt("mID");
                ResultSet rss = stmt.executeQuery("SELECT mName, aName, genre FROM Musics WHERE mID = " + mID);
                rss.next();
                String genre = rss.getString("genre");
                rss.close();
                if (rating >= 3) {
                    sql = "SELECT mName, aName, genre FROM Musics WHERE genre LIKE '" + genre + "'";
                } else {
                    sql = "SELECT mName, aName, genre FROM Musics WHERE genre NOT LIKE '" + genre + "'";
                }
            } else sql = "SELECT mName, aName, genre FROM Musics";
            rs = stmt.executeQuery(sql);
            int i = 1;
            while (i <= 5 && rs.next()) {
                result.add(Map.of("songName", rs.getString(1), "artist", rs.getString(2), "genre", rs.getString(3)));
                i++;
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return result;
    }

    public Map<String, List<Map<String, String>>> userData(String userName) {
        Statement stmt = null;
        List<Map<String, String>> comments = new ArrayList<>();
        List<Map<String, String>> highestRatedSongs = new ArrayList<>();
        List<Map<String, String>> favoriteArtists = new ArrayList<>();
        List<Map<String, String>> preferredGenres = new ArrayList<>();
        try{
            stmt = connection.createStatement();
            String sql;
            sql = "SELECT * FROM Reviews NATURAL JOIN Musics WHERE userName LIKE '" + userName + "' order by dt DESC";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String mName = rs.getString("mName");
                Date dt = rs.getDate("dt");
                String comment = rs.getString("comment");
                comments.add(Map.of("song", mName, "text", comment, "data", dt.toString()));
            }
            sql = "SELECT mName, aName, genre, AVG(rating) as rating FROM Reviews NATURAL JOIN Users NATURAL JOIN Musics WHERE userName LIKE '" + userName + "' GROUP BY mID HAVING AVG(rating) > 3 ORDER BY AVG(rating) DESC";
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String mName = rs.getString("mName");
                String aName = rs.getString("aName");
                String genre = rs.getString("genre");
                Integer rating = rs.getInt("rating");
                highestRatedSongs.add(Map.of("name", mName, "rating", rating.toString()));
                favoriteArtists.add(Map.of("name", aName));
                preferredGenres.add(Map.of("genre", genre));
            }
            rs.close();
            stmt.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return Map.of("comments", comments, "highestRatedSongs", highestRatedSongs, "favoriteArtists", favoriteArtists, "preferredGenres", preferredGenres);
    }

    public void rateMusics(String userName, String mName, String rate, String comment) {
        PreparedStatement stmt1 = null;
        Statement stmt2 = null;
        try{
            String sql;
            stmt2 = connection.createStatement();
            sql = "SELECT * FROM Musics WHERE mName LIKE '" + mName + "'";
            ResultSet rs = stmt2.executeQuery(sql);
            rs.next();
            int mID = rs.getInt("mID");
            sql = "INSERT INTO Reviews (userName, mID, rating, comment, dt) VALUES (?, ?, ?, ?, ?)";
            stmt1 = connection.prepareStatement(sql);
            stmt1.setString(1, userName);
            stmt1.setInt(2, mID);
            stmt1.setInt(3, Integer.parseInt(rate));
            stmt1.setString(4, comment);
            java.util.Date date = new java.util.Date();
            stmt1.setDate(5, new Date(date.getTime()));
            stmt1.execute();
            connection.commit();
            rs.close();
            stmt1.close();
            stmt2.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public List<Map<String, String>> getComments(String name) {
        Statement stmt = null;
        List<Map<String, String>> comments = new ArrayList<>();
        try{
            String sql;
            stmt = connection.createStatement();
            sql = "SELECT * FROM Musics WHERE mName LIKE '" + name + "'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int mID = rs.getInt("mID");
            sql = "SELECT * FROM Reviews WHERE mID = " + mID + " order by dt DESC";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String userName = rs.getString("userName");
                Integer rating = rs.getInt("rating");
                String comment = rs.getString("comment");
                comments.add(Map.of("username", userName, "rating", rating.toString(), "comment", comment));
            }
            rs.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return comments;
    }
}
