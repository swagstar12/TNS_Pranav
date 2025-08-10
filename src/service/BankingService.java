package service;

import model.Customer;

public interface BankingService {
    int registerCustomer(Customer customer);
    void deposit(int customerId, double amount);
    void withdraw(int customerId, double amount);
    void transfer(int fromCustomerId, int toCustomerId, double amount);
    void viewAccountDetails(int customerId);
}
