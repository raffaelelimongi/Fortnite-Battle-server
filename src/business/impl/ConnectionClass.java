package business.impl;

import java.sql.DriverManager;
import com.mysql.jdbc.Connection;

public class ConnectionClass {
public static Connection connection;
private static String db="jdbc:mysql://localhost:3306/fortnitebattledb";
private static String root="root";
private static String pass="fofo";

    public static Connection getConnection()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();

           connection= (Connection) DriverManager.getConnection(db,root,pass);

        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}