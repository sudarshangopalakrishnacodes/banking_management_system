package com.pkg.main;

import com.pkg.database.DBConnection;
import com.pkg.model.Account;
import com.pkg.model.Client;
import com.pkg.service.AccountService;
import com.pkg.service.ClientService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppLauncher {

    public static void main(String [] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ClientService clientService = new ClientService();
        AccountService accountService = new AccountService();

        while(true)
        {
            System.out.println("\n====== BANKING SYSTEM MENU ======");
            System.out.println("1. Register New Client");
            System.out.println("2. Create New Account");
            System.out.println("3. View Client by ID");
            System.out.println("4. View All Clients");
            System.out.println("5. Deposit");
            System.out.println("6. Withdraw");
            System.out.println("7. View All Accounts");
            System.out.println("8. Delete Client");
            System.out.println("9. Apply Interest (Savings)");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            boolean success;
            switch(choice)
            {
                case 1:

                    System.out.println("Enter your name");
                    String name = scanner.nextLine();
                    System.out.println("Enter you address");
                    String address = scanner.nextLine();
                    Client client = new Client();
                    client.setClientName(name);
                    client.setClientAddress(address);
                    success = clientService.registerClient(client);
                    if (success) {
                        System.out.println(" Customer registered successfully.");
                    } else {
                        System.out.println(" Customer registration failed.");
                    }
                    break;
                case 2:
                    System.out.println("Enter your account type (e.g., savings):");
                    String type = scanner.nextLine();

                    System.out.println("Enter the initial deposit amount (Minimum 50 euros):");
                    double balance = scanner.nextDouble();

                    if (balance < 50) {
                        System.out.println(" Minimum balance to open an account is 50 euros. Try again.");
                        break;
                    }

                    System.out.println("Enter your client ID:");
                    int clientId = scanner.nextInt();
                    scanner.nextLine();

                    Account account = new Account();
                    account.setAccountType(type);
                    account.setAccountBalance(balance);
                    account.setClientId(clientId);

                    success = accountService.createAccount(account);

                    if (success) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account creation failed.");
                    }
                    break;



                case 3:
                    System.out.println("Enter your client ID");
                    int client_id = scanner.nextInt();
                    Client client1 = clientService.getClient(client_id);
                    if(client1 == null)
                    {
                        System.out.println("Client not found");
                    }
                    else {

                        System.out.println("Client Details are "+ client1);
                    }
                    break;
                case 4:
                    List<Client> clients = clientService.getAllClients();
                    if(clients.isEmpty())
                    {
                        System.out.println("No clients found ");
                    }
                    else {

                        System.out.println("List of all clients : " +clients);
                    }
                    break;
                case 5:
                    System.out.println("Enter your Account number");
                    int accNum = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter the amount you want to deposit");
                    double deposit = scanner.nextDouble();
                   success = accountService.deposit(accNum,deposit);
                   if(success)
                   {
                       System.out.println("Amount deposited");

                   }else{
                       System.out.println("Deposit failed");
                   }
                   break;
                case 6:
                    System.out.println("Enter your Account number");
                    int accNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Enter the amount you want to withdraw");
                    double withdraw = scanner.nextDouble();
                    success = accountService.withdraw(accNumber,withdraw);
                    if(success)
                    {
                        System.out.println("Amount withdrawn");

                    }else{
                        System.out.println("withdrawal failed");
                    }
                    break;
                case 7:
                    List<Account> accounts = accountService.getAllAccounts();
                    if(accounts.isEmpty())
                    {
                        System.out.println("No accounts found ");
                    }
                    else {

                        System.out.println("List of all accounts : " +accounts);
                    }
                    break;
                case 8:
                    System.out.println("Enter your client id");
                    int clientID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(" Deleting this client will also delete all their accounts.");
                    System.out.print("Are you sure you want to proceed? (yes/no): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("yes")) {
                        success = clientService.deleteClient(clientID);
                        if (success) {
                            System.out.println(" Client deleted successfully.");
                        } else {
                            System.out.println(" Client not deleted.");
                        }
                    } else {
                        System.out.println(" Deletion cancelled.");
                    }
                    break;
                case 9:
                    System.out.println("Enter your Account Number:");
                    int acnumber = scanner.nextInt();

                    System.out.println("Enter Interest Rate (e.g., 4.5 for 4.5%):");
                    double rate = scanner.nextDouble();
                    scanner.nextLine(); // consume newline

                    boolean successApply = accountService.applyInterest(acnumber, rate);

                    if (successApply) {
                        System.out.println(" Interest applied successfully.");
                    } else {
                        System.out.println(" Interest application failed.");
                    }
                    break;
                case 10: System.exit(0);

            }

        }
    }
}
