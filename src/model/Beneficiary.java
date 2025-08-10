package model;

public class Beneficiary {
    private int id, customerId;
    private String name;

    public Beneficiary(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public int getCustomerId() { return customerId; }
    public String getName() { return name; }
}
