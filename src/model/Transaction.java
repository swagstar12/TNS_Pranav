package model;

import java.sql.Timestamp;

public class Transaction {
    private int id, accountId;
    private double amount;
    private String type;
    private Timestamp timestamp;

    public Transaction(int id, int accountId, double amount, String type, Timestamp timestamp) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public int getAccountId() { return accountId; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
    public Timestamp getTimestamp() { return timestamp; }
}
