package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;

    // Instantiate the config file
    private static ResourceBundle config;


    private ConnectionFactory() {
        config = ResourceBundle.getBundle("db");
    }


    //Connection Singleton..
    public static ConnectionFactory getInstance() {

        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;

    }

    // Connection with SQL using: java.sql.Connection!
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(config.getString("jdbc.driver"));
        return DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.username"), config.getString("jdbc.password"));

    }
}