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
        String host = "db.sanderkastelein.nl";
        String port = "3306";
        String db = "NickyBot";
        String username = "riley";
        String password = "jayden";
        String sql = "";
        String result = "";
        for (String arg : args) sql = sql + " " + arg;
        sql = sql.trim();
        System.out.println(sql);

        return Database.query(sql);
    }

}