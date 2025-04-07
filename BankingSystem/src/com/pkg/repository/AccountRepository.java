package com.pkg.repository;

import com.mysql.cj.protocol.Resultset;
import com.pkg.database.DBConnection;
import com.pkg.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class AccountRepository {

    private static final Logger LOGGER = Logger.getLogger(AccountRepository.class.getName());

    public boolean createAccount(Account account) throws SQLException {

        String sql = "INSERT INTO accounts(balance, account_type, client_id) VALUES (?,?,?)";
        boolean success = false;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =connection.prepareStatement(sql)){
             double balance = account.getAccountBalance();
             String account_type = account.getAccountType();
             int client_id = account.getClientId();

             preparedStatement.setDouble(1,balance);
             preparedStatement.setString(2,account_type);
             preparedStatement.setInt(3,client_id);

             int row =preparedStatement.executeUpdate();
                if(row > 0)
                {
                    LOGGER.info("Account created successfully");
                    success= true;
                }
                else {
                    LOGGER.info("Account creation failed");
                }
        }
        catch (SQLException ex)
        {
            LOGGER.log(Level.SEVERE, "Failed to create Account", ex);
        }
      return success;
    }

    public Account getAccountByNumber(int accountNumber)
    {
        String sql = "SELECT * FROM accounts WHERE account_number =?";
        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))   {

            preparedStatement.setInt(1,accountNumber);
            ResultSet resultset = preparedStatement.executeQuery();

            if(resultset.next())
            {
                int acc_number = resultset.getInt("account_number");
                double bal =   resultset.getDouble("balance");
                String type = resultset.getString("account_type");
                int id =   resultset.getInt("client_id");


                Account account = new Account(acc_number,bal,type,id);
                return account;
            }
            else {
                LOGGER.warning("No account found with account number " +accountNumber);
            }


        } catch (SQLException e) {
           LOGGER.log(Level.SEVERE,"Failed to fetch the account", e);
        }
        return null;
    }

    public List<Account> getAllAccounts()
    {
        String sql = "SELECT * FROM accounts";
        List<Account> accounts= new ArrayList<>();

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultset = preparedStatement.executeQuery();

            while(resultset.next())
            {
               int acc_num =  resultset.getInt("account_number");
                double bal = resultset.getDouble("balance");
                String type = resultset.getString("account_type");
                int id = resultset.getInt("client_id");

                Account account = new Account(acc_num,bal,type,id);
                accounts.add(account);
            }



        } catch (SQLException e) {
           LOGGER.log(Level.SEVERE,"Failed to fetch accounts",e);

        }
        LOGGER.info(accounts.size()+" accounts fetched");
        return accounts;

    }

    public boolean updateAccount(Account account){

        String sql = "UPDATE accounts set balance = ? WHERE account_number = ?";
        boolean success = false;

        try(Connection connection = DBConnection.getConnection();
           PreparedStatement preparedStatement = connection.prepareStatement(sql) ) {

                preparedStatement.setDouble(1,account.getAccountBalance());
                preparedStatement.setInt(2,account.getAccountNumber());
                int row = preparedStatement.executeUpdate();
                if(row > 0)
                {
                    LOGGER.info("Update successful");
                    success = true;
                }
                else {
                    LOGGER.info("Update unsuccessful");
                }
        } catch (SQLException e) {
           LOGGER.log(Level.SEVERE,"Failed to update account",e);
        }
        return success;
    }

    public boolean deleteAccount(int accountNumber){
        String sql = "DELETE FROM accounts  WHERE account_number = ?";
        boolean success = false;
        try(Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1,accountNumber);
            int row = preparedStatement.executeUpdate();
            if(row > 0){
               LOGGER.info("Deletion successful");
               success =true;
            }
            else {
                LOGGER.info("DELETION UNSUCCESSFUL");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Failed to delete account",e);
        }
        return success;
    }

}
