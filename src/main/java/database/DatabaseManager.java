package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseManager {

    private static final String PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            props.load(DatabaseManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
            // "Найди файл db.properties, открой его как поток, и прочитай все параметры внутрь props"

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
            // Используя url, user, password идет подключение к базе данных
            // DriverManager - это стандартный JDBC-менеджер соединений, встроенный в Java
        } catch (Exception e) {
            throw new RuntimeException("DB connection error: " + e.getMessage(), e);
        }
    }
}