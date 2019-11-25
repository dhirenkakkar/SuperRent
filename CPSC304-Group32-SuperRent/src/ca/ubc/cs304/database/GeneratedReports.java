
import ca.ubc.cs304.model.BranchRental;
import ca.ubc.cs304.model.BranchReturn;
import ca.ubc.cs304.model.TotalRental;
import ca.ubc.cs304.model.TotalReturn;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class GeneratedReport {
    private Connection connection;

    ArrayList<TotalRental> totalRentalList = new ArrayList<>();
    ArrayList<TotalRental> branchRentalList = new ArrayList<>();
    ArrayList<TotalReturn> totalReturnList = new ArrayList<>();
    ArrayList<TotalReturn> branchReturnList = new ArrayList<>();


    Timestamp Date = null;
    String Location = null;
    Integer newRentalsAtBranch = 0;
    String VehicleType = null;
    Integer newRentals = 0;

    public ArrayList<TotalRental> rentals(Timestamp fromDateTime, Timestamp toDateTime) {
        PreparedStatement Rental = null;
        try {
            Rental = connection.prepareStatement("SELECT r.fromDateTime AS 'Date', v.location AS 'Location',SUM (COUNT (v.vid)) OVER (PARTITION BY v.location) AS 'Total new rentals at branch', vt.vtname AS 'Vehicle type', COUNT(v.vid) AS 'Total new rentals' FROM Vehicle v, Rentals r, VehicleTypes vt, WHERE (r.vid = v.vid) AND (? <= r.fromDateTime <= ?) GROUP BY v.location, v.vtname");
            Rental.setTimestamp(1, fromDateTime);
            Rental.setTimestamp(2, toDateTime);
            ResultSet rs1 = Rental.executeQuery();

            while(rs1.next()){
                Date = rs1.getTimestamp("r.fromDateTime");
                Location = rs1.getString("v.location");
                newRentalsAtBranch= rs1.getInt("Total new rentals at branch");
                VehicleType = rs1.getString("Vehicle type");
                newRentals = rs1.getInt("Total new rentals");

                TotalRental tr = new TotalRental(Date, Location, newRentalsAtBranch, VehicleType, newRentals);
                totalRentalList.add(tr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return totalRentalList;
    }


    public ArrayList<BranchRental> branchRentals(String location, Timestamp fromDateTime, Timestamp toDateTime) throws SQLException {

        Timestamp Date = null;
        String Location = null;
        Integer newRentalsAtBranch = 0;
        String VehicleType = null;
        Integer newRentals = 0;

        PreparedStatement RentalBranch = null;
        try {
            RentalBranch = connection.prepareStatement("SELECT r.fromDateTime AS 'Date', v.location AS 'Location', SUM (COUNT (v.vid)) OVER (PARTITION BY v.location) AS 'Total new rentals at branch', vt.vtname AS 'Vehicle type', COUNT(v.vid) AS 'Total new rentals' FROM Vehicle v, Rentals r, VehicleTypes vt, WHERE (v.location = ?) AND (r.vid = v.vid) AND (? <= r.fromDateTime <= ?) GROUP BY v.location, vt.vtname");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            RentalBranch.setString(1, location);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            RentalBranch.setTimestamp(2, fromDateTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            RentalBranch.setTimestamp(3, fromDateTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs2 = RentalBranch.executeQuery();

        while(rs2.next()){
            Date = rs2.getTimestamp("Date");
            Location = rs2.getString("Location");
            newRentalsAtBranch = rs2.getInt("Total new rentals at branch");
            VehicleType = rs2.getString("Vehicle type");
            newRentals = rs2.getInt("Total new rentals");

            BranchRental br = new BranchRental(Date, Location, newRentalsAtBranch, VehicleType, newRentals);
            branchRentalList.add(br);
        }
        return branchRentalList;
    }


    public ArrayList<TotalReturn> totalReturns(){

        Timestamp Date = null;
        String Location = null;
        Integer newReturnsAtBranch = 0;
        String VehicleType = null;
        Integer newReturns = 0;
        Integer totalRevenue = 0;
        Integer totalRevenueAtBranch = 0;

        PreparedStatement Return = null try {
            Return = connection.prepareStatement("SELECT rt.rdate AS 'Return date', v.location AS 'Location',  SUM (COUNT (v.vid)) OVER (PARTITION BY v.location) AS 'Total new returns at branch', vt.vtname AS 'Vehicle type', COUNT (v.vid) AS 'Total new returns',  SUM (rt.value) AS 'Total revenue', SUM (SUM (rt.value)) OVER (PARTITION BY v.location) AS 'Total revenue at branch' FROM Vehicle v, Rentals r, [Returns] rt, VehicleTypes vt WHERE (rt.rid = r.rid) AND (r.vid = v.vid) AND (? <= rt.rdate <= ?) GROUP BY v.location, vt.vtname");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Return.setTimestamp(1, r.fromDateTime);
        Return.setTimestamp(2, r.fromDateTime);
        ResultSet rs3 = Return.executeQuery();

        while(rs3.next()){
            try {
                Date = rs3.getTimestamp("Return date");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Location = rs3.getString("Location");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                newReturnsAtBranch = rs3.getInt("Total new returns at branch");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                VehicleType = rs3.getString("Vehicle type");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                newReturns = rs3.getInt("Total new returns");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                totalRevenue = rs3.getInt("Total revenue");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                totalRevenueAtBranch = rs3.getInt("Total revenue at branch");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            TotalReturn tr1 = new TotalReturn(Date, Location, newReturnsAtBranch, VehicleType, newReturns, totalRevenue, totalRevenueAtBranch)
            totalReturnList.add(tr1);
        }
        return totalReturnList;
    }
    


    public ArrayList<BranchReturn> branchReturns(String location, Timestamp fromTime, Timestamp toTime){

        Timestamp Date = null;
        String Location = null;
        Integer newReturnsAtBranch = 0;
        String VehicleType = null;
        Integer newReturns = 0;
        Integer totalRevenue = 0;
        Integer totalRevenueAtBranch = 0;

        PreparedStatement ReturnBranch = connection.prepareStatement("SELECT rt.rdate AS 'Return date', v.location AS 'Location',  SUM (COUNT (v.vid)) OVER (PARTITION BY v.location) AS 'Total new returns at branch', vt.vtname AS 'Vehicle type', COUNT (v.vid) AS 'Total new returns',  SUM (rt.value) AS 'Total revenue', SUM (SUM (rt.value)) OVER (PARTITION BY v.location) AS 'Total revenue at branch'  FROM Vehicle v, Rentals r, [Returns] rt, VehicleTypes vt WHERE (v.location = ?) AND (rt.rid = r.rid) AND (r.vid = v.vid) AND (? <= rt.rdate <= ?) GROUP BY v.location, v.vtname")
    
        ReturnBranch.setString(1, location);
        ReturnBranch.setTimestamp(2, fromTime);
        ReturnBranch.setTimestamp(3, toTime);
        ResultSet rs4 = ReturnBranch.executeQuery();

        while(rs4.next()){
            Date = rs4.getTimestamp("Return date");
            Location = rs4.getString("Location");
            newReturnsAtBranch = rs4.getInt("Total new returns at branch");
            VehicleType = rs4.getString("Vehicle type");
            newReturns = rs4.getInt("Total new returns");
            totalRevenue = rs4.getInt("Total revenue");
            totalRevenueAtBranch = rs4.getString("Total revenue at branch");

            BranchReturn br1 = new BranchReturn(Date, Location, newReturnsAtBranch, VehicleType, newReturns, totalRevenue, totalRevenueAtBranch;
            totalReturnList.add(br1);
        }
        return branchReturnList;
    }
}
}







