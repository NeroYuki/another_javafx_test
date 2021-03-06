package database;

import exception.DatabaseException;
import helper.UUIDHelper;
import model.Expense;
import model.Income;
import model.Saving;
import model.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseTransaction {

    //Get a list of all transaction based on transaction's applied balance id
    public static ArrayList<Transaction> getTransaction(String balanceId) throws DatabaseException {
        //TODO: error check
        ArrayList<Transaction> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE applyBalance = ?");
            transactionQuery.setString(1, balanceId);
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getDouble("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(incomeEntry);
                }
                else if (queryResult.getInt("transType") == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getDouble("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(expenseEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            System.out.println(e.getMessage());
            throw new DatabaseException(0);
        }
        return result;
    }

    public static ArrayList<Transaction> getTransaction(String balanceId, LocalDate startDate, LocalDate endDate) throws DatabaseException {
        //TODO: error check
        ArrayList<Transaction> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE applyBalance = ? AND occurDate >= ? AND occurDate <= ?");
            transactionQuery.setString(1, balanceId);
            transactionQuery.setDate(2, Date.valueOf(startDate));
            transactionQuery.setDate(3, Date.valueOf(endDate));
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getDouble("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(incomeEntry);
                }
                else if (queryResult.getInt("transType") == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getDouble("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(expenseEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            System.out.println(e.getMessage());
            throw new DatabaseException(0);
        }
        return result;
    }

    //Get a list of all income transaction based on transaction's applied balance id (Might not necessary)
    public static ArrayList<Income> getIncome(String balanceId) throws DatabaseException {
        ArrayList<Income> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE applyBalance = ?");
            transactionQuery.setString(1, balanceId);
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(incomeEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static ArrayList<Income> getIncome(String balanceId, LocalDate startDate, LocalDate endDate) throws DatabaseException {
        ArrayList<Income> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE applyBalance = ? AND occurDate >= ? AND occurDate <= ?");
            transactionQuery.setString(1, balanceId);
            transactionQuery.setDate(2, Date.valueOf(startDate));
            transactionQuery.setDate(3, Date.valueOf(endDate));
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(incomeEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    //Get a list of all expense transaction based on transaction's applied balance id (Might not necessary)
    public static ArrayList<Expense> getExpense(String balanceId) throws DatabaseException {
        ArrayList<Expense> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE applyBalance = ?");
            transactionQuery.setString(1, balanceId);
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(expenseEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static ArrayList<Expense> getExpense(String balanceId, LocalDate startDate, LocalDate endDate) throws DatabaseException {
        ArrayList<Expense> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE applyBalance = ? AND occurDate >= ? AND occurDate <= ?");
            transactionQuery.setString(1, balanceId);
            transactionQuery.setDate(2, Date.valueOf(startDate));
            transactionQuery.setDate(3, Date.valueOf(endDate));
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(expenseEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static ArrayList<Transaction> getTransactionByCategory(String categoryId) throws DatabaseException {
        //TODO: error check
        ArrayList<Transaction> result = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement transactionQuery = conn.prepareStatement("SELECT * FROM transHistory WHERE transCategoryId = ?");
            transactionQuery.setString(1, categoryId);
            ResultSet queryResult = transactionQuery.executeQuery();
            while (queryResult.next()) {
                //check transaction type (1 = income, 2 = expense)
                if (queryResult.getInt("transType") == 1) {
                    Income incomeEntry = new Income(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(incomeEntry);
                } else if (queryResult.getInt("transType") == 2) {
                    Expense expenseEntry = new Expense(
                            queryResult.getString("transId"),
                            queryResult.getDate("occurDate").toLocalDate(),
                            queryResult.getFloat("value"),
                            queryResult.getString("description"),
                            DatabaseCategories.getCategoryById(queryResult.getString("transCategoryId"))
                    );
                    result.add(expenseEntry);
                }
            }
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            //if this happen then oh god oh fuck
            throw new DatabaseException(0);
        }
        return result;
    }

    public static boolean addIncome(Income trans) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement createCall = conn.prepareCall("INSERT INTO transHistory VALUES (?, ?, ?, ?, 1, ?, ?, 1)");
            if (trans.getId().equals("")) trans.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(8);

            createCall.setString(1, trans.getId());
            createCall.setString(2, trans.getApplyingBalance().getId());
            createCall.setString(3, trans.getTransDescription());
            createCall.setDouble(4, trans.getTransValue());
            createCall.setString(5, trans.getCategory().getId());
            createCall.setDate(6, Date.valueOf(trans.getTransDate()));
            createCall.execute();

            int result = createCall.getUpdateCount();
            //TODO: new error code (8)
            if (result == 0) throw new DatabaseException(8);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean addExpense(Expense trans) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement createCall = conn.prepareCall("INSERT INTO transHistory VALUES (?, ?, ?, ?, 2, ?, ?, 1)");
            if (trans.getId().equals("")) trans.setId(UUIDHelper.newUUIDString());
            else throw new DatabaseException(8);

            createCall.setString(1, trans.getId());
            createCall.setString(2, trans.getApplyingBalance().getId());
            createCall.setString(3, trans.getTransDescription());
            createCall.setDouble(4, trans.getTransValue());
            createCall.setString(5, trans.getCategory().getId());
            createCall.setDate(6, Date.valueOf(trans.getTransDate()));
            createCall.execute();

            int result = createCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(8);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean removeTransaction(Transaction trans) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement removeCall = conn.prepareCall("DELETE FROM transHistory WHERE transId = ?");
            if (trans.getId().equals("")) throw new DatabaseException(15);

            removeCall.setString(1, trans.getId());
            removeCall.execute();
            int result = removeCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(15);
            //TODO: update balance to revert removed Transaction's effect
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }

    public static boolean updateTransaction(Transaction trans) throws DatabaseException {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement updateCall = conn.prepareCall(
                    "UPDATE transHistory " +
                            "SET description = ?, " +
                            "value = ?, " +
                            "occurDate = ?, " +
                            "transCategoryId = ?" +
                            "WHERE transId = ? "
            );
            if (trans.getId().equals("")) throw new DatabaseException(21);
            updateCall.setString(1, trans.getTransDescription());
            updateCall.setDouble(2, trans.getTransValue());
            updateCall.setDate(3, Date.valueOf(trans.getTransDate()));
            if(trans.getType().equals("Income")) {
                updateCall.setString(4, ((Income)trans).getCategory().getId());
            }
            else if (trans.getType().equals("Expense")){
                updateCall.setString(4, ((Expense)trans).getCategory().getId());
            }
            updateCall.setString(5, trans.getId());
            updateCall.execute();
            int result = updateCall.getUpdateCount();
            if (result == 0) throw new DatabaseException(21);
        }
        catch (DatabaseException de) {
            throw de;
        }
        catch (Exception e) {
            throw new DatabaseException(0);
        }
        return true;
    }
}
