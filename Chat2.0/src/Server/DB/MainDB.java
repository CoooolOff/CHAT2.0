package Server.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainDB {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            System.out.println(ConnectionService.getName("kull"));
            connection = ConnectionService.getDbConnection();
            ConnectionService.checkPasswordbyLogin("old","124122");
            ArrayList<String> logins = ConnectionService.getAllauthLogins();
            for (String login : logins){
                System.out.println(login);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
