package Server.DB;

import java.sql.*;
import java.util.ArrayList;

public final class ConnectionService {

    public ConnectionService() {
    }
    public static  Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost:3306/chat";

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(connectionString, "root", "");
    }

    public static void create(String login, String password,String name){
        Connection connection = null;
        try{
            connection = getDbConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (login,password,name) VALUES (?,?,?)"
            );
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, name);

            statement.executeUpdate();
            connection.commit();
        }catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            throw new RuntimeException("SWW", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    public static boolean checkPasswordbyLogin (String login,String password){
        Connection connection = null;
        String pass = null;
            try {
                connection = getDbConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT password from users WHERE login = ?"
                );
                statement.setString(1 , login);
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    pass = rs.getString("password");
                }
                if(password.equals(pass)){
                    return true;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return false;
    }
    public static boolean isLoginFree(String login){
        Connection connection = null;
        try{
            connection = getDbConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * from users WHERE login = ?"
            );
            statement.setString(1, login);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String value = resultSet.getString("login");
                if(value != null){
                    return false;
                }
            }
        }catch (SQLException e){
            throw new RuntimeException("SWW", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;
    }

   public static ArrayList getAllauthLogins(){
        Connection connection = null;
        ArrayList<String> logins = new ArrayList<>();
        try{
            connection = getDbConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT login from users"
            );

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                logins.add(resultSet.getString("login"));
            }
        }catch (SQLException e){
            throw new RuntimeException("SWW", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return logins;

   }public static String getName(String login){
        Connection connection = null;
        String nik = null;
        try{
            connection = getDbConnection();

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT name from users WHERE login = ?"
            );
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                nik = resultSet.getString("name");
            }
        }catch (SQLException e){
            throw new RuntimeException("SWW", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return nik;
   }
}
