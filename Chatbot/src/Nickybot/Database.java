package Nickybot;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String HOST = "db.sanderkastelein.nl";
    private static final String PORT = "3306";
    private static final String DATABASE = "NickyBotUtf82";
    private static final String USERNAME = "riley";
    private static final String PASSWORD = "jayden";

    public static String query(String sql) {
        String result = "[{";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true&useSSL=false", USERNAME, PASSWORD);
            statement = (Statement) connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int length = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= length; i++) {
                    if (i != 1) {
                        if (resultSet.getMetaData().getColumnName(i).equals("Plot"))
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
