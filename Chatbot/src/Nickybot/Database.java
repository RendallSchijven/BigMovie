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

    public static String query(String sql){
        String result = "";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection=(Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true&useSSL=false", USERNAME, PASSWORD);
            statement=(Statement) connection.createStatement();
            resultSet=statement.executeQuery(sql);
            while(resultSet.next()) {
                int i = resultSet.getMetaData().getColumnCount();
                for (int j = 1; j <= i; j++) {
                    if (result.equals("")) {
                        result = resultSet.getString(j);
                    } else {
                        result += resultSet.getString(j) + " ";
                    }
                }
                if (!result.equals(""))
                    result += "\n";
            }
        } catch (SQLException ex) {
        } finally{
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
            }
        }

        return result;
    }
}
