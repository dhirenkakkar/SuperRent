package ca.ubc.cs304.database;

import ca.ubc.cs304.model.BranchModel;
import ca.ubc.cs304.model.Customers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomersManipulation {
    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public CustomersManipulation(Connection connection) {
        this.connection = connection;
    }

    public void insertCustomer(Customers customer){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?,?)");
            if(customer.getCellphone() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, customer.getCellphone());
            }
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getDlicense());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteCustomer(String dlicense){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE DLICENSE = ?");
            ps.setString(1,dlicense);
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateCustomer(Customers newCustomer) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE CUSTOMERS SET CELLPHONE = ?, NAME = ?, ADDRESS = ?, DLICENSE = ? WHERE  DLICENSE = ?");
            if(newCustomer.getCellphone() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, newCustomer.getCellphone());
            }
            ps.setString(2, newCustomer.getName());
            ps.setString(3, newCustomer.getAddress());
            ps.setString(4, newCustomer.getDlicense());
            ps.setString(5, newCustomer.getDlicense());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public List<Customers> viewCustomers(){
        List<Customers> customers = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS");

            while(rs.next()) {
                Customers customer = new Customers(rs.getInt("cellphone"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("dlicense"));
                customers.add(customer);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return customers;
    }

    public Customers viewCustomers(String dlicense){
        Customers customer = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE DLICENSE = ?");
            ps.setString(1, dlicense);

            ResultSet rs = ps.executeQuery();


            while(rs.next()) {
                customer = new Customers(rs.getInt("cellphone"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("dlicense"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return customer;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

}
