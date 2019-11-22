package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.*;

public class CustomerPage extends JPanel{

    private JButton viewAvailableVehicles,makeReservation,home;

    public CustomerPage() {
        setupCustomerPage();
    }

    private void setupCustomerPage() {
        viewAvailableVehicles = new JButton("View Available Vehicles");
        makeReservation = new JButton("Make Reservation");
        home = new JButton("Home");

        add(makeReservation);
        add(viewAvailableVehicles);
        add(home);
    }

    public JButton getViewAvailableVehicles() {
        return viewAvailableVehicles;
    }

    public JButton getMakeReservation() {
        return makeReservation;
    }

    public JButton getHome() {
        return home;
    }
}
