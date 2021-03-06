package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Budget;
import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class DatabaseUser {
    //Get user info from a given username and password
    public static User getUserInfo(String username, String password) throws DatabaseException {
        User result = null;
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement userQuery = conn.prepareCall("SELECT * FROM loggedUser WHERE username = ? AND password = ?");
            userQuery.setString(1, username);
            userQuery.setString(2, password);
            ResultSet userResult = userQuery.executeQuery();
            if (userResult.first()) {
                result = new User(
                        userResult.getString("userId"),
                        userResult.getString("username"),
                        userResult.getString("password"),
                        userResult.getString("email"),
                        userResult.getDate("birthday").toLocalDate(),
                        null   //FIXME: should overloaded constructor or not?
                );
                //attach budget into user
                result.setBudget(DatabaseBudget.getBudget(result));
            }
            else {
                throw new DatabaseException(2);
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return result;
    }

    public static boolean registerUser(User registeringUser) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement registerCall = conn.prepareCall("INSERT INTO loggedUser VALUES (?, ?, ?, ?, ?)");

            if (registeringUser.getId().equals("")) registeringUser.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(3);

            registerCall.setString(1, registeringUser.getId());
            registerCall.setString(2, registeringUser.getUsername());
            registerCall.setString(3, registeringUser.getPassword());
            registerCall.setString(4, registeringUser.getEmail());
            registerCall.setDate(5, Date.valueOf(registeringUser.getBirthday()));
            registerCall.execute();
            int result = registerCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(3);

            DatabaseBudget.addBudget(new Budget(null, null, null, null), registeringUser);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean removeUser(User user) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM loggedUser WHERE userId = ?");
            DatabaseBudget.removeBudget(user.getBudget());

            if (user.getId().equals("")) throw new DatabaseException(16);

            removeCall.setString(1, user.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(16);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean updateUser(User user) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement updateCall = conn.prepareCall(
                    "UPDATE loggedUser " +
                            "SET username = ?, " +
                            "password = ?, " +
                            "email = ?, " +
                            "birthday = ? " +
                            "WHERE userId = ?"
            );
            if (user.getId().equals("")) throw new DatabaseException(22);
            updateCall.setString(1, user.getUsername());
            updateCall.setString(2, user.getPassword());
            updateCall.setString(3, user.getEmail());
            updateCall.setDate(4, Date.valueOf(user.getBirthday()));
            updateCall.setString(5, user.getId());

            updateCall.execute();
            int result = updateCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(22);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static String getUserPassword(String username, String email, LocalDate dob) throws DatabaseException {
        String result = "";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement userQuery = conn.prepareCall("SELECT * FROM loggedUser WHERE username = ? AND email = ? AND birthday = ?");
            userQuery.setString(1, username);
            userQuery.setString(2, email);
            userQuery.setDate(3, Date.valueOf(dob));
            ResultSet userResult = userQuery.executeQuery();
            if (userResult.first()) {
                result = userResult.getString("password");
            }
            else {
                throw new DatabaseException(2);
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return result;
    }
}
