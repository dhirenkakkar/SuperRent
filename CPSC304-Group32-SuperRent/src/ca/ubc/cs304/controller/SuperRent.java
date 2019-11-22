package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.AvailableVehicles;
import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.database.ReservationRent;
import ca.ubc.cs304.delegates.ClerkWindowDelegate;
import ca.ubc.cs304.delegates.CustomerWindowDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.Gui;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TerminalTransactions;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;

public class SuperRent implements LoginWindowDelegate, ClerkWindowDelegate, CustomerWindowDelegate {

    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;
    private SuperRent instance = null;
    private ReservationRent reservationRent;

    public SuperRent() {
        dbHandler = new DatabaseConnectionHandler();
        instance = this;
    }

    private void start() {
//        loginWindow = new LoginWindow();
//        loginWindow.showFrame(this);
        login("ora_samiri98", "a43602671");
    }

    /**
     * LoginWindowDelegate Implementation
     *
     * connects to Oracle database with supplied username and password
     */
    public void login(String username, String password) {
        boolean didConnect = dbHandler.login(username, password);
        if (didConnect) {
            // Once connected, remove login window and start text transaction flow
            //loginWindow.dispose();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Create an instance of the demo.
                    Gui gui = new Gui(instance,instance);
                    // Make the demo visible on the screen.
                    gui.setVisible(true);
                }
            });
        } else {
            loginWindow.handleLoginFailed();
            if (loginWindow.hasReachedMaxLoginAttempts()) {
                loginWindow.dispose();
                System.out.println("You have exceeded your number of allowed attempts");
                System.exit(-1);
            }
        }
    }

    @Override
    public ArrayList<Vehicle> search(FilterSearch filterSearch) {
        AvailableVehicles availableVehicles = new AvailableVehicles(dbHandler.getConnection(), filterSearch);
        ArrayList<Vehicle> vehicles =  availableVehicles.getNumAvailable();
        return vehicles;
    }

    @Override
    public float costForReservation(FilterSearch filterSearch) {
        reservationRent = new ReservationRent(dbHandler.getConnection(), filterSearch);
        return reservationRent.getCost();
    }

    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        SuperRent superRent = new SuperRent();
        superRent.start();
    }

    @Override
    public Reservations reserve(Customers customer) {
        Pair<Reservations,Vehicle> reservationsVehiclePair =  reservationRent.makeReservation(customer);
        return reservationsVehiclePair.getKey();
    }
}
