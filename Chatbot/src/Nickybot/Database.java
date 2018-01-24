package Nickybot;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    public static String query(String sql) {
        // Load properties file
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "resources/app.properties";

        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = "[{";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + appProps.getProperty("db_host") + ":" + appProps.getProperty("db_port") + "/" + appProps.getProperty("db_database") + "?autoReconnect=true&useSSL=false", appProps.getProperty("db_username"), appProps.getProperty("db_password"));
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int length = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= length; i++) {
                    if (i != 1) {
                        if (resultSet.getMetaData().getColumnName(i).equals("Plot") && !resultSet.getString(i).equals(null))
                            result += ",\"" + resultSet.getMetaData().getColumnName(i) + "\":\"" + resultSet.getString(i).replaceAll("\"", "\'") + "\"";
                        else
                            result += ",\"" + resultSet.getMetaData().getColumnName(i) + "\":\"" + resultSet.getString(i) + "\"";
                    } else {
                        result += "\"" + resultSet.getMetaData().getColumnName(i) + "\":\"" + resultSet.getString(i) + "\"";
                    }
                }
                result += "},{";
            }
        } catch (SQLException ex) {
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
            }
        }

        result = result.substring(0, result.length() - 2);
        return result + "]";
    }
}
