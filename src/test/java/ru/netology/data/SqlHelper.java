package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SqlHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SqlHelper() {

    }

    private static Connection getConn() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }
    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode(){
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var connection = getConn();
        var code = runner.query(connection, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }
    @SneakyThrows
    public static void cleanDB() {
        var connection = getConn();
        runner.update(connection, "DELETE FROM auth_codes");
        runner.update(connection, "DELETE FROM card_transactions");
        runner.update(connection, "DELETE FROM cards");
        runner.update(connection, "DELETE FROM users");
    }
}
