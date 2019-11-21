package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.Rentals;
import ca.ubc.cs304.model.Reservations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationsManipulation {
    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public ReservationsManipulation(Connection connection) {
        this.connection = connection;
    }

    public void insertReservation(Reservations reservation){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (RESERVATION_COUNTER.nextval,?,?,?,?,?)");

            if(reservation.getCellphone() == -1) {
                ps.setNull(3,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(3, reservation.getCellphone());
            }

            if(reservation.getVid() == -1) {
                ps.setNull(2,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(2, reservation.getVid());
            }

            ps.setString(1,reservation.getVtname());
            ps.setTimestamp(4, reservation.getFromDateTime());
            ps.setTimestamp(5,reservation.getToDateTime());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteReservation(int confNo){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM RESERVATIONS WHERE CONFNO = ?");
            ps.setInt(1,confNo);
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateReservation(int confNo, Reservations newReservations) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE RESERVATIONS SET CONFNO = ?, VTNAME = ?, VID = ?, CELLPHONE = ?, FROMDATE = ?, TODATE = ? WHERE  CONFNO = ?");
            if(newReservations.getCellphone() == -1) {
                ps.setNull(4,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(4, newReservations.getCellphone());
            }

            if(newReservations.getVid() == -1) {
                ps.setNull(3,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(3, newReservations.getVid());
            }

            if(newReservations.getConfNo() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, newReservations.getConfNo());
            }

            ps.setString(2,newReservations.getVtname());
            ps.setTimestamp(5, newReservations.getFromDateTime());
            ps.setTimestamp(6,newReservations.getToDateTime());


            ps.setInt(7, confNo);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public List<Reservations> viewReservation(){
        List<Reservations> reservations = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATIONS");

            while(rs.next()) {
                Reservations reservation = new Reservations();
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

                reservations.add(reservation);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return reservations;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }

}
