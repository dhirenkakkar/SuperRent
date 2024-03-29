package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.*;
import ca.ubc.cs304.delegates.ClerkWindowDelegate;
import ca.ubc.cs304.delegates.CustomerWindowDelegate;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.Gui;
import ca.ubc.cs304.ui.LoginWindow;
import javafx.util.Pair;

import javax.swing.*;
import java.util.ArrayList;

public class SuperRent implements LoginWindowDelegate, ClerkWindowDelegate, CustomerWindowDelegate {

    private DatabaseConnectionHandler dbHandler = null;
    private LoginWindow loginWindow = null;
    private SuperRent instance = null;
    private ReservationRent reservation;
    private ReservationRent rent;


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
                    Gui gui = new Gui(instance,instance,dbHandler.getConnection());
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
        reservation = new ReservationRent(dbHandler.getConnection(), filterSearch);
        return reservation.getCost();
    }

    /**
     * Main method called at launch time
     */
    public static void main(String args[]) {
        SuperRent superRent = new SuperRent();
        superRent.start();
    }

    @Override
    public ReservationRentReciept reserve(Customers customer) {
        Pair<Reservations,Vehicle> reservationsVehiclePair =  reservation.makeReservation(customer);
        if(reservationsVehiclePair == null){
            return null;
        }
        Reservations reservations = reservationsVehiclePair.getKey();
        Vehicle vehicle = reservationsVehiclePair.getValue();
        ReservationRentReciept reservationRentReciept = new ReservationRentReciept(reservations.getConfNo(),vehicle.getVtname(),reservations.getCellphone(),reservations.getFromDateTime(),reservations.getToDateTime(),vehicle.getLocation(),vehicle.getCity());
        return reservationRentReciept;
    }

    @Override
    public Reservations searchForReservation(int confNo) {
        ClerkTransactions clerkTransactions = new ClerkTransactions(dbHandler.getConnection(), null);
        return clerkTransactions.getReservationByConfNo(confNo);
    }

    @Override
    public Reservations searchForReservation(int cellphone, FilterSearch filterSearch) {
        ClerkTransactions clerkTransactions = new ClerkTransactions(dbHandler.getConnection(), filterSearch);
        return clerkTransactions.getReservationByCellphone(cellphone);
    }

    @Override
    public float costForRent(FilterSearch filterSearch) {
        rent = new ReservationRent(dbHandler.getConnection(), filterSearch);
        return rent.getCost();
    }

    @Override
    public ReservationRentReciept rentWithoutReservation(Customers customer, CustomerRentInfo customerRentInfo) {
        Pair<Rentals,Vehicle> rentalsVehiclePair = rent.rentWithoutReservation(customer, customerRentInfo);
        if(rentalsVehiclePair == null){
            return null;
        }
        Rentals rent = rentalsVehiclePair.getKey();
        Vehicle vehicle = rentalsVehiclePair.getValue();
        ReservationRentReciept reservationRentReciept = new ReservationRentReciept(rent.getConfNo(),vehicle.getVtname(),rent.getCellphone(),rent.getFromDateTime(),rent.getToDateTime(),vehicle.getLocation(),vehicle.getCity());
        return reservationRentReciept;
    }

    @Override
    public ReservationRentReciept rentWithReservation(CustomerRentInfo customerRentInfo, Reservations reservations) {
        rent = new ReservationRent(dbHandler.getConnection(), null);
        Pair<Rentals,Vehicle> rentalsVehiclePair = rent.rent(customerRentInfo, reservations);
        Rentals rent = rentalsVehiclePair.getKey();
        Vehicle vehicle = rentalsVehiclePair.getValue();
        ReservationRentReciept reservationRentReciept = new ReservationRentReciept(rent.getConfNo(),vehicle.getVtname(),rent.getCellphone(),rent.getFromDateTime(),rent.getToDateTime(),vehicle.getLocation(),vehicle.getCity());
        return reservationRentReciept;
    }

    @Override
    public ArrayList<Rentals> generateDailyRentals(FilterSearch filterSearch) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Rentals> generateDailyRentalsPerBranch(FilterSearch filterSearch) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Returns> generateDailyReturns(FilterSearch filterSearch) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Returns> generateDailyReturnsPerBranch(FilterSearch filterSearch) {
        return new ArrayList<>();
    }
}
