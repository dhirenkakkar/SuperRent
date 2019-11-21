package ca.ubc.cs304.database;

import ca.ubc.cs304.model.FilterSearch;
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


    private Reservations getReservationByCellphone(int cellphone, int vid){
        Reservations reservation = null;
        try {
            PreparedStatement ps = connection.prepareStatement(" SELECT * FROM RESERVATIONS WHERE FROMDATE = ?, TODATE = ?, VID = ?, CELLPHONE = ?");
            ps.setTimestamp(1,filterSearch.getFromDate());
            ps.setTimestamp(2,filterSearch.getToDate());
            ps.setInt(3, vid);
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
            ps.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return reservation;
    }

    private Reservations getReservationByConfNo(int confNo){
        Reservations reservation = null;
        try {
            PreparedStatement ps = connection.prepareStatement(" SELECT * FROM RESERVATIONS R1 where R1.CONFNO = ?");
            ps.setInt(1,confNo);
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
            ps.close();
        }catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
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
