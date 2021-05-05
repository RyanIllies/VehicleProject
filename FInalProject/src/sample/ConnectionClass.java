package sample;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionClass {

    public static Connection connection;

    public Connection getConnection(){
        String url = "jdbc:mysql://puff.mnstate.edu:3306/ryan-illies_vehicles?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=FALSE";
        String userName = "ryan-illies";
        String pw = "Dragon27";
        System.out.println("Loading Driver...");
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find Driver", e);
        }
        System.out.println("Driver loaded");
        connection = null;

        System.out.println("Trying to connect to database...");
        try {
            connection = DriverManager.getConnection(url,userName,pw);
        }catch (SQLException e) {e.printStackTrace();}
        System.out.println("Connected to the database");
        return connection;
    }

    public static void closeConnection() throws SQLException{
        try{
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
            
        }catch (Exception e){throw e;}
    }
}
