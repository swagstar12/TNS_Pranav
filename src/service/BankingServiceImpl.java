// File: service/BankingServiceImpl.java
package service;

import model.Customer;
import util.DBConnection;
import java.sql.*;

public class BankingServiceImpl implements BankingService {
    private Connection conn;

    public BankingServiceImpl() {
        this.conn = DBConnection.getConnection();
    }

    @Override
    public int registerCustomer(Customer customer) {
        try {
            String sql = "INSERT INTO customers (name, email, balance) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setDouble(3, 0.0); // Initial balance
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error registering customer:");
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void deposit(int customerId, double amount) {
        try {
            String sql = "UPDATE customers SET balance = balance + ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, amount);
            stmt.setInt(2, customerId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Amount deposited successfully.");
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error in deposit:");
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(int customerId, double amount) {
        try {
            // Check balance
            String checkSql = "SELECT balance FROM customers WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, customerId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance >= amount) {
                    String withdrawSql = "UPDATE customers SET balance = balance - ? WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(withdrawSql);
                    stmt.setDouble(1, amount);
                    stmt.setInt(2, customerId);
                    stmt.executeUpdate();
                    System.out.println("Amount withdrawn successfully.");
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error in withdraw:");
            e.printStackTrace();
        }
    }

    @Override
    public void transfer(int fromCustomerId, int toCustomerId, double amount) {
        try {
            conn.setAutoCommit(false);

            // Check sender's balance
            String checkSql = "SELECT balance FROM customers WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, fromCustomerId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance >= amount) {
                    // Withdraw from sender
                    String withdrawSql = "UPDATE customers SET balance = balance - ? WHERE id = ?";
                    PreparedStatement withdrawStmt = conn.prepareStatement(withdrawSql);
                    withdrawStmt.setDouble(1, amount);
                    withdrawStmt.setInt(2, fromCustomerId);
                    withdrawStmt.executeUpdate();

                    // Deposit to receiver
                    String depositSql = "UPDATE customers SET balance = balance + ? WHERE id = ?";
                    PreparedStatement depositStmt = conn.prepareStatement(depositSql);
                    depositStmt.setDouble(1, amount);
                    depositStmt.setInt(2, toCustomerId);
                    depositStmt.executeUpdate();

                    conn.commit();
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient balance for transfer.");
                    conn.rollback();
                }
            } else {
                System.out.println("Sender not found.");
                conn.rollback();
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed.");
            }
            System.out.println("Error in transfer:");
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void viewAccountDetails(int customerId) {
        try {
            String sql = "SELECT * FROM customers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Customer ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing account details:");
            e.printStackTrace();
        }
    }
}
