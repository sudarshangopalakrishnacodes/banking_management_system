package com.pkg.repository;

import com.pkg.database.DBConnection;
import com.pkg.model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ClientRepository {

    private static final Logger LOGGER = Logger.getLogger(ClientRepository.class.getName());

    public boolean createClient(Client client) throws SQLException {
        boolean success = false;
        String sql = "INSERT INTO clients(name,address) VALUES (?,?)";
        int row;
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, client.getClientName());
            preparedStatement.setString(2, client.getClientAddress());
            row = preparedStatement.executeUpdate();
            if (row > 0) {
                LOGGER.info("Insertion complete");
                success = true;
            } else {
                LOGGER.info("Insertion incomplete");

            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to insert client", e);
        }

        return success;
    }

    public Client getClientById(int id) throws SQLException {
        String sql = "SELECT * FROM clients WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                int client_id = resultSet.getInt("id");
                Client client = new Client(client_id, name, address);
                LOGGER.info("Client data present in database");
                return client;
            }
            else {
                LOGGER.info("Client data not present in database");
            }

        } catch (SQLException e) {

            LOGGER.log(Level.SEVERE, "Failed to get client", e);
        }
     return null;

    }

    public List<Client> getAllClients()
    {
        String sql = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement =connection.prepareStatement(sql)){

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    Client client = new Client(id,name,address);
                    clients.add(client);
            }
            LOGGER.info(clients.size() +" clients found");


        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to fetch clients", e);

        }
        return clients;
    }

    public boolean updateClient(Client client){
        boolean success = false;
        int rows;
        String sql = " UPDATE clients set name =? ,address = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            String name = client.getClientName();
            String address = client.getClientAddress();
            int id = client.getClientId();

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,address);
            preparedStatement.setInt(3,id);

            rows= preparedStatement.executeUpdate();

            if(rows > 0)
            {
                LOGGER.info("Update successful");
                success = true;

            }
            else {

                LOGGER.info("Update failed");

            }


        } catch (SQLException e) {
            LOGGER.warning("No client found with id: " + client.getClientId() + ". Update not applied.");

        }
        return success;
    }

    public boolean deleteClient(int id)
    {
        String sql = "DELETE FROM clients WHERE id =?";
        boolean success = false;
        int row;
        try (Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql))   {
            preparedStatement.setInt(1,id);
            row = preparedStatement.executeUpdate();
            if(row > 0)
            {
                LOGGER.info("Deletion successful");
                success =true;
            }
            else {
                LOGGER.info("Deletion failed");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete client with id: " + id, e);
        }
        return success;
    }

}
