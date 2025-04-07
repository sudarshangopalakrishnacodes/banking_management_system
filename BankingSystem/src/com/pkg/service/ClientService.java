package com.pkg.service;

import com.pkg.model.Client;
import com.pkg.repository.ClientRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ClientService {

    private static  final Logger LOGGER  = Logger.getLogger(ClientService.class.getName());
    private ClientRepository clientRepository = new ClientRepository();

    public  boolean registerClient(Client client) throws SQLException {

        String name = client.getClientName();
        String address = client.getClientAddress();
        if(name == null || name.isBlank() || address == null || address.isBlank()){
            LOGGER.info("Name and address cannot be null or blank");
            return false;
        }
        else {
            boolean success =clientRepository.createClient(client);
            if(success){
                LOGGER.info("Client registered successfully");
            }
            else {
                LOGGER.info("Client registration failed");
            }
            return success;
        }

    }
    public Client getClient(int id) throws SQLException {

        if(id <= 0)
        {
            LOGGER.warning("ID Cannot be negative");
            return null;
        }

            Client client = clientRepository.getClientById(id);
            if(client == null)
            {
                LOGGER.warning("Client not found with ID "+id);
            }
            else {
                LOGGER.info("Client found with ID "+id);

            }
            return client;
    }
    public List<Client> getAllClients(){
       List<Client> clients =  clientRepository.getAllClients();
       if(clients.isEmpty())
       {
           LOGGER.warning("No clients found");
       }
       LOGGER.info(clients.size() +" clients found");
       return clients;
    }

    public boolean updateClient(Client client){
        String name = client.getClientName();
        int id = client.getClientId();
        String address = client.getClientAddress();
        boolean success =false;
        if(name == null || name.isBlank() || address == null || address.isBlank() || id <=0)
        {
            LOGGER.warning("ID ,NAME ,ADDRESS CANNOT BE NULL OR BLANK");
            return false;
        }
        else {
          success=  clientRepository.updateClient(client);
          if(success){
              LOGGER.info("Client updated successfully");
          }
          else {
              LOGGER.info("Client update failed");
          }

        }
        return success;
    }
    public boolean deleteClient(int id)
    {
        boolean success = false;
        if(id <= 0)
        {
            LOGGER.warning("ID Cannot be negative");
            return false;
        }
        else {
         success =clientRepository.deleteClient(id);
         if(success)
         {
             LOGGER.info("Client deleted successfully");
         }
         else {
             LOGGER.info("Client delete failed");
         }
        }
        return success;
    }

}
