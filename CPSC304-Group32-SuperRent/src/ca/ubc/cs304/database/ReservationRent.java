package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.utils.StringUtils;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationRent {
    private Connection connection;
    private FilterSearch filterSearch;
    private ArrayList<Vehicle> vehicles;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";


    public ReservationRent(Connection connection, FilterSearch filterSearch) {
        this.connection = connection;
        this.filterSearch = filterSearch;
        vehicles = new ArrayList<>();

    }

    public float getCost(){
        float cost;
        AvailableVehicles availableVehicles = new AvailableVehicles(this.connection, this.filterSearch);
        vehicles = availableVehicles.getNumAvailable();
        if(vehicles.size() != 0){
            cost = estimateCost();
        }
        else {
            cost = -1;
        }

        return cost;
    }

    public Pair<Rentals,Vehicle> rentWithoutReservation(Customers customer, CustomerRentInfo customerRentInfo) {
        Pair<Reservations,Vehicle> reservationsVehiclePair =  makeReservation(customer);
        return rent(customerRentInfo, reservationsVehiclePair.getKey());
    }

    public boolean checkReservation(int confNo){
        return false;
    }

    public boolean checkReservation(int cellphone, FilterSearch filterSearch){
        return false;
    }

    public Pair<Rentals,Vehicle> rent(CustomerRentInfo customerRentInfo, Reservations reservations){
//        loginSignup(customer);
//        Pair<Reservations,Vehicle> reservationsCustomersPair =  reserve(customer);
        VehiclesManipulation vehiclesManipulation = new VehiclesManipulation(this.connection);
        Vehicle vehicle = vehiclesManipulation.viewVehicle(reservations.getVid());

        Rentals rentals = new Rentals();
        rentals.setVid(reservations.getVid());
        rentals.setCellphone(reservations.getCellphone());
        rentals.setFromDateTime(filterSearch.getFromDate());
        rentals.setToDateTime(filterSearch.getToDate());
        rentals.setOdometer(vehicle.getOdometer());
        rentals.setCardNo(customerRentInfo.getCardNo());
        rentals.setCardName(customerRentInfo.getCardName());
        rentals.setExpDate(customerRentInfo.getExpDate());
        rentals.setConfNo(reservations.getConfNo());
        RentalsManipulation rm = new RentalsManipulation(this.connection);
        rm.insertRentals(rentals);

        return new Pair<>(rentals,vehicle);
    }

    public Pair<Reservations,Vehicle> makeReservation(Customers customer){
        loginSignup(customer);
        return reserve(customer);
    }

    private Pair<Reservations,Vehicle> reserve(Customers customer){
        Reservations reservations = new Reservations();
        VehiclesManipulation vm = new VehiclesManipulation(this.connection);
        ReservationsManipulation rm = new ReservationsManipulation(this.connection);

        Vehicle v = vehicles.get(0);
        v.setStatus("Rent");
        vm.updateVehicle(v);
        vehicles.remove(0);

        reservations.setCellphone(customer.getCellphone());
        reservations.setVid(v.getVid());
        reservations.setVtname(v.getVtname());
        reservations.setFromDateTime(filterSearch.getFromDate());
        reservations.setToDateTime(filterSearch.getToDate());
        rm.insertReservation(reservations);

        ClerkTransactions clerkTransactions = new ClerkTransactions(connection,filterSearch);
        Reservations reservationsTemp = clerkTransactions.getReservationByCellphone(customer.getCellphone());

        return new Pair<>(reservationsTemp,v);
    }



    private void loginSignup(Customers customer){
        CustomersManipulation cm = new CustomersManipulation(this.connection);
        Customers customerExist = cm.viewCustomers(customer.getDlicense());
        if(customerExist == null){
            signup(customer);
        }
    }

    private void signup(Customers customer){
        CustomersManipulation cm = new CustomersManipulation(this.connection);
        cm.insertCustomer(customer);
    }

    private float estimateCost(){
        float wrate = 0;
        float drate = 0;
        float hrate = 0;
        float wirate = 0;
        float dirate = 0;
        float hirate = 0;
        float cost = 0;
        int wperiod;
        int dperiod;
        int hperiod;

        ArrayList<Integer> wdh = numWDH(filterSearch.getFromDate(),filterSearch.getToDate());
        wperiod = wdh.get(2);
        dperiod = wdh.get(1);
        hperiod = wdh.get(0);

        try {
            PreparedStatement ps = connection.prepareStatement(" SELECT * FROM VEHICLETYPES V1 where V1.VTNAME = ?");
            ps.setString(1,filterSearch.getVtname());
            ResultSet rs = ps.executeQuery();
            connection.commit();

            while(rs.next()) {
                wrate = rs.getInt("wrate");
                drate = rs.getInt("drate");
                hrate = rs.getInt("hrate");
                wirate = rs.getInt("wirate");
                dirate = rs.getInt("dirate");
                hirate = rs.getInt("hirate");
            }

            cost = wrate*wperiod + wirate*wperiod + drate*dperiod + dirate*dperiod + hrate*hperiod + hirate*hperiod;

            ps.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return cost;
    }

    private ArrayList<Integer> numWDH(Timestamp t1, Timestamp t2){
        int diffYear = t2.getYear() - t1.getYear();
        int diffMonth = t2.getMonth() - t1.getMonth();
        int diffDate = t2.getDate() - t1.getDate();
        int diffH = t2.getHours() - t1.getHours();
        int diffM = t2.getMinutes() - t1.getMinutes();
        int diffS = t2.getSeconds() - t1.getSeconds();

        final int hoursInYear = 8760;
        final int hoursInMonth = 720;
        final int hoursInDay = 24;
        final int hoursInWeek = 168;
        int extraHour = 0;
        if(diffM != 0 ){
            extraHour = 1;
        }
        int numberOfHoursInTotal = diffYear*hoursInYear + diffMonth*hoursInMonth + diffDate*hoursInDay + diffH + extraHour;
        int numberOfWeeks = numberOfHoursInTotal/hoursInWeek;
        int numberOfDays = (numberOfHoursInTotal - numberOfWeeks*hoursInWeek)/hoursInDay;
        int numberOfHours = numberOfHoursInTotal - numberOfWeeks*hoursInWeek - numberOfDays*hoursInDay;

        ArrayList<Integer> wdh = new ArrayList<>();
        wdh.add(numberOfHours);
        wdh.add(numberOfDays);
        wdh.add(numberOfWeeks);

        return wdh;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
