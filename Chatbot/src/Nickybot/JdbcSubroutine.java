package Nickybot;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.rivescript.macro.Subroutine;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Groep 16
 */
public class JdbcSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {
        String host = "136.144.128.126";
        String port = "3306";
        String db = "NickyBot";
        String username = "NickyBot";
        String password = "s9qD9iaohMkbo8c8";
        String sql = "";
        String result = "";
        for (String arg : args) sql = sql + " " + arg;
        sql = sql.trim();
        System.out.println(sql);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection=(Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + port + "/" + db + "?autoReconnect=true&useSSL=false",
                    username, password);
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