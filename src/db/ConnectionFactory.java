package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Синглтон класс, который отдает Connection
 */

public class ConnectionFactory {
    private static ConnectionFactory connectionFactory;

    private static Properties properties;


    private ConnectionFactory() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("resources/db.properties"));
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            try {
                connectionFactory = new ConnectionFactory();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connectionFactory;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty("jdbc.driver"));
        return DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
    }
}