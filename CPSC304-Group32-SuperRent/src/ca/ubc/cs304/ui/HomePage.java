package ca.ubc.cs304.ui;

import javax.swing.*;

public class HomePage extends JPanel {

    private JButton customer, clerk;

    public HomePage(){
        setupHomePage();
    }

    private void setupHomePage() {
        customer = new JButton("Customer");
        clerk = new JButton("Clerk");

        add(customer);
        add(clerk);
    }

    public JButton getCustomer() {
        return customer;
    }

    public JButton getClerk() {
        return clerk;
    }
}
