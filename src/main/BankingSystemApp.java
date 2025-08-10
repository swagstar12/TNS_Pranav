package main;

import model.Customer;
import service.BankingService;
import service.BankingServiceImpl;
import java.util.Scanner;

public class BankingSystemApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingService bankingService = new BankingServiceImpl();

        while (true) {
            System.out.println("\nBanking System Menu:");
            System.out.println("1. Register Customer");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Account Details");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    scanner.nextLine();
                    System.out.print("Enter customer name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter customer email: ");
                    String email = scanner.nextLine();
                    Customer customer = new Customer(name, email);
                    int customerId = bankingService.registerCustomer(customer);
                    if (customerId != -1) {
                        System.out.println("Please note your Customer ID for future transactions: " + customerId);
                    } else {
                        System.out.println("Registration failed.");
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter customer ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter deposit amount: ");
                    double amount = scanner.nextDouble();
                    bankingService.deposit(id, amount);
                    break;
                }
                case 3: {
                    System.out.print("Enter customer ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter withdrawal amount: ");
                    double amount = scanner.nextDouble();
                    bankingService.withdraw(id, amount);
                    break;
                }
                case 4: {
                    System.out.print("Enter sender ID: ");
                    int sender = scanner.nextInt();
                    System.out.print("Enter receiver ID: ");
                    int receiver = scanner.nextInt();
                    System.out.print("Enter amount to transfer: ");
                    double amount = scanner.nextDouble();
                    bankingService.transfer(sender, receiver, amount);
                    break;
                }
                case 5: {
                    System.out.print("Enter customer ID: ");
                    int id = scanner.nextInt();
                    bankingService.viewAccountDetails(id);
                    break;
                }
                case 6:
                    System.out.println("Thank you for using Banking System.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
