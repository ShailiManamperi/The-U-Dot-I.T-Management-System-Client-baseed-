package lk.system.it.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;

    private final Connection connection;

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/studentmanagement","root","1234");
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException("Failed to load the database");
        }

    }

    public static DBConnection getDbConnection() {
        return dbConnection==null?dbConnection=new DBConnection():dbConnection;
    }

    public Connection getConnection(){
        return connection;
    }
}
