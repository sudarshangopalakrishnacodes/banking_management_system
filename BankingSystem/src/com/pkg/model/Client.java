package com.pkg.model;

import java.util.Objects;

public class Client {

    private int clientId;
    private String clientName;
    private String clientAddress;


    public Client(int clientId,String clientName, String clientAddress) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientAddress = clientAddress;

    }

    public Client()
    {

    }
    public  int getClientId(){
        return clientId;
    }

    public String getClientName()
    {
        return clientName;
    }

    public String getClientAddress()
    {
        return clientAddress;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    public void setClientAddress(String clientAddress)
    {
        this.clientAddress = clientAddress;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", clientName='" + clientName + '\'' +
                ", clientAddress='" + clientAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return clientId == client.clientId && Objects.equals(clientName, client.clientName) && Objects.equals(clientAddress, client.clientAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, clientName, clientAddress);
    }
}
