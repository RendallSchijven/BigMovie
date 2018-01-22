package Nickybot;

import com.rivescript.macro.Subroutine;


/**
 *
 * @author Groep 16
 */
public class JdbcSubroutine implements Subroutine {

    @Override
    public String call(com.rivescript.RiveScript rs, String[] args) {

        String sql = "";
        String result = "";

        for (String arg : args) sql = sql + " " + arg;
        sql = sql.trim();
        System.out.println(sql);
        result = Database.query(sql);

        return result;
    }

}