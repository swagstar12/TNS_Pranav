package model;

public class Account {
    private int id, customerId;
    private double balance;

    public Account(int customerId, double balance) {
        this.customerId = customerId;
        this.balance = balance;
    }

    public Account(int id, int customerId, double balance) {
        this(customerId, balance);
        this.id = id;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public double getBalance() { return balance; }
}
