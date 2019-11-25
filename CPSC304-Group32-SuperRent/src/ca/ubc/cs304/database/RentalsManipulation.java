package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Rentals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalsManipulation {
    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public RentalsManipulation(Connection connection) {
        this.connection = connection;
    }

    public void insertRentals(Rentals rental){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RENTALS VALUES (RENT_COUNTER.nextval,?,?,?,?,?,?,?,?,?)");

            if(rental.getCellphone() == -1) {
                ps.setNull(2,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(2, rental.getCellphone());
            }

            if(rental.getVid() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, rental.getVid());
            }

            if(rental.getConfNo() == -1) {
                ps.setNull(9,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(9, rental.getConfNo());
            }

            if(rental.getCardNo() == -1) {
                ps.setNull(7,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(7, rental.getCardNo());
            }

            if(rental.getOdometer() == -1) {
                ps.setNull(5, Types.FLOAT);
            }
            else {
                ps.setFloat(5, rental.getOdometer());
            }

            ps.setString(6, rental.getCardName());
            ps.setTimestamp(3, rental.getFromDateTime());
            ps.setTimestamp(4,rental.getToDateTime());
            ps.setTimestamp(8,rental.getExpDate());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteRentals(int rid){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM RENTALS WHERE RID = ?");
            ps.setInt(1,rid);
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateRentals(Rentals newRental) {
        try {
                PreparedStatement ps = connection.prepareStatement("UPDATE RENTALS SET RID = ?, VID = ?, CELLPHONE = ?, FROMDATE = ?, TODATE = ?, ODOMETER = ?, CARDNAME = ?, CARDNO = ?, EXPDATE = ?, CONFNO = ? WHERE  RID = ?");
            if(newRental.getCellphone() == -1) {
                ps.setNull(3,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(3, newRental.getCellphone());
            }

            if(newRental.getRid() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, newRental.getRid());
            }

            if(newRental.getVid() == -1) {
                ps.setNull(2,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(2, newRental.getVid());
            }

            if(newRental.getConfNo() == -1) {
                ps.setNull(10,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(10, newRental.getConfNo());
            }

            if(newRental.getCardNo() == -1) {
                ps.setNull(8,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(8, newRental.getCardNo());
            }

            if(newRental.getOdometer() == -1) {
                ps.setNull(6, Types.FLOAT);
            }
            else {
                ps.setFloat(6, newRental.getOdometer());
            }

            ps.setString(7, newRental.getCardName());
            ps.setTimestamp(4, newRental.getFromDateTime());
            ps.setTimestamp(5,newRental.getToDateTime());
            ps.setTimestamp(9,newRental.getExpDate());

            ps.setInt(11, newRental.getRid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public List<Rentals> viewRentals(){
        List<Rentals> rentals = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMERS");

            while(rs.next()) {
                Rentals rental = new Rentals();
                rental.setCardName(rs.getString("cardName"));
                rental.setCardNo(rs.getInt("cardNo"));
                if(rs.wasNull()) {
                    rental.setCardNo(-1);
                }
                rental.setCellphone(rs.getInt("cellphone"));
                if(rs.wasNull()) {
                    rental.setCellphone(-1);
                }
                rental.setConfNo(rs.getInt("confNo"));
                if(rs.wasNull()) {
                    rental.setConfNo(-1);
                }
                rental.setExpDate(rs.getTimestamp("ExpDate"));
                rental.setFromDateTime(rs.getTimestamp("fromDateTime"));
                rental.setToDateTime(rs.getTimestamp("toDateTime"));
                rental.setRid(rs.getInt("rid"));
                if(rs.wasNull()) {
                    rental.setRid(-1);
                }
                rental.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    rental.setVid(-1);
                }
                rental.setOdometer(rs.getFloat("odometer"));
                if(rs.wasNull()) {
                    rental.setOdometer(-1);
                }

                rentals.add(rental);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return rentals;
    }

    public Rentals viewRentals(int confNo){
        Rentals rental = new Rentals();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM RENTALS WHERE CONFNO = ?");
            ps.setInt(1,confNo);
            ResultSet rs = ps.executeQuery();
            connection.commit();

            while(rs.next()) {
                rental.setCardName(rs.getString("cardName"));
                rental.setCardNo(rs.getInt("cardNo"));
                if(rs.wasNull()) {
                    rental.setCardNo(-1);
                }
                rental.setCellphone(rs.getInt("cellphone"));
                if(rs.wasNull()) {
                    rental.setCellphone(-1);
                }
                rental.setConfNo(rs.getInt("confNo"));
                if(rs.wasNull()) {
                    rental.setConfNo(-1);
                }
                rental.setExpDate(rs.getTimestamp("ExpDate"));
                rental.setFromDateTime(rs.getTimestamp("fromDateTime"));
                rental.setToDateTime(rs.getTimestamp("toDateTime"));
                rental.setRid(rs.getInt("rid"));
                if(rs.wasNull()) {
                    rental.setRid(-1);
                }
                rental.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    rental.setVid(-1);
                }
                rental.setOdometer(rs.getFloat("odometer"));
                if(rs.wasNull()) {
                    rental.setOdometer(-1);
                }

            }

            if(rental.getRid()==0){
                return null;
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            rental = null;
        }

        return rental;
    }

    public Rentals viewRentals(int cellphone, FilterSearch filterSearch){
        Rentals rentals = new Rentals();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM RENTALS WHERE CELLPHONE = ? AND FROMDATETIME = ? AND TODATETIME = ?");

            ps.setInt(1,cellphone);
            ps.setTimestamp(2,filterSearch.getFromDate());
            ps.setTimestamp(3,filterSearch.getToDate());

            ResultSet rs = ps.executeQuery();
            connection.commit();

            while(rs.next()) {
                Rentals rental = new Rentals();
                rental.setCardName(rs.getString("cardName"));
                rental.setCardNo(rs.getInt("cardNo"));
                if(rs.wasNull()) {
                    rental.setCardNo(-1);
                }
                rental.setCellphone(rs.getInt("cellphone"));
                if(rs.wasNull()) {
                    rental.setCellphone(-1);
                }
                rental.setConfNo(rs.getInt("confNo"));
                if(rs.wasNull()) {
                    rental.setConfNo(-1);
                }
                rental.setExpDate(rs.getTimestamp("ExpDate"));
                rental.setFromDateTime(rs.getTimestamp("fromDateTime"));
                rental.setToDateTime(rs.getTimestamp("toDateTime"));
                rental.setRid(rs.getInt("rid"));
                if(rs.wasNull()) {
                    rental.setRid(-1);
                }
                rental.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    rental.setVid(-1);
                }
                rental.setOdometer(rs.getFloat("odometer"));
                if(rs.wasNull()) {
                    rental.setOdometer(-1);
                }

            }

            if(rentals.getRid()==0){
                return null;
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            rentals = null;
        }

        return rentals;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
