package ca.ubc.cs304.database;

import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Rentals;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClerkTransactions {
    private Connection connection;
    private FilterSearch filterSearch;
    private ArrayList<Vehicle> vehicles;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public ClerkTransactions(Connection connection, FilterSearch filterSearch) {
        this.connection = connection;
        this.filterSearch = filterSearch;
        vehicles = new ArrayList<>();
    }


    public Reservations getReservationByCellphone(int cellphone){
        Reservations reservation = new Reservations();
        RentalsManipulation rentalsManipulation = new RentalsManipulation(connection);
        Rentals rentals = rentalsManipulation.viewRentals(cellphone, filterSearch);
        if(rentals != null){
            return null;
        }

        try {
            PreparedStatement ps = connection.prepareStatement(" SELECT * FROM RESERVATIONS WHERE FROMDATETIME = ? AND  TODATETIME = ? AND VTNAME = ? AND CELLPHONE = ?");
            ps.setTimestamp(1,filterSearch.getFromDate());
            ps.setTimestamp(2,filterSearch.getToDate());
            ps.setString(3, filterSearch.getVtname());
            ps.setInt(4,cellphone);
            ResultSet rs = ps.executeQuery();
            connection.commit();

            while(rs.next()) {
                reservation = new Reservations();
                reservation.setCellphone(rs.getInt("cellphone"));
                if(rs.wasNull()) {
                    reservation.setCellphone(-1);
                }
                reservation.setConfNo(rs.getInt("confNo"));
                if(rs.wasNull()) {
                    reservation.setConfNo(-1);
                }
                reservation.setFromDateTime(rs.getTimestamp("fromDateTime"));
                reservation.setToDateTime(rs.getTimestamp("toDateTime"));

                reservation.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    reservation.setVid(-1);
                }
            }

            if(reservation.getConfNo() == 0){
                return null;
            }

            ps.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            reservation = null;
        }

        return reservation;
    }

    public Reservations getReservationByConfNo(int confNo){
        Reservations reservation = new Reservations();
        RentalsManipulation rentalsManipulation = new RentalsManipulation(connection);
        Rentals rentals = rentalsManipulation.viewRentals(confNo);
        if(rentals != null){
            return null;
        }
        try {
            PreparedStatement ps = connection.prepareStatement(" SELECT * FROM RESERVATIONS R1 where R1.CONFNO = ?");
            ps.setInt(1,confNo);
            ResultSet rs = ps.executeQuery();
            connection.commit();

            while(rs.next()) {
                reservation.setCellphone(rs.getInt("cellphone"));
                if(rs.wasNull()) {
                    reservation.setCellphone(-1);
                }
                reservation.setConfNo(rs.getInt("confNo"));
                if(rs.wasNull()) {
                    reservation.setConfNo(-1);
                }
                reservation.setFromDateTime(rs.getTimestamp("fromDateTime"));
                reservation.setToDateTime(rs.getTimestamp("toDateTime"));

                reservation.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    reservation.setVid(-1);
                }
            }

            if(reservation.getConfNo()==0){
                return null;
            }

            ps.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            reservation = null;
        }

        return reservation;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

}
