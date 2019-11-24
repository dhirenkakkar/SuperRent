package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiclesManipulation {
    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public VehiclesManipulation(Connection connection) {
        this.connection = connection;
    }

    public void insertVehicle(Vehicle vehicle){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RESERVATIONS VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            if(vehicle.getVid() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, vehicle.getVid());
            }

            if(vehicle.getVlicense() == -1) {
                ps.setNull(2,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(2, vehicle.getVlicense());
            }

            if(vehicle.getYear() == -1) {
                ps.setNull(5,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(5, vehicle.getYear());
            }

            if(vehicle.getOdometer() == -1) {
                ps.setNull(7, Types.FLOAT);
            }
            else {
                ps.setFloat(7, vehicle.getOdometer());
            }

            ps.setTimestamp(3, vehicle.getMake());
            ps.setString(4, vehicle.getModel());
            ps.setString(6,vehicle.getColor());
            ps.setString(8,vehicle.getStatus());
            ps.setString(9,vehicle.getVtname());
            ps.setString(10, vehicle.getLocation());
            ps.setString(11,vehicle.getCity());


            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteVehicle(int vid){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM VEHICLES WHERE VID = ?");
            ps.setInt(1, vid);
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateVehicle(Vehicle newVehicle) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE VEHICLES SET VID = ?, VLICENSE = ?, MAKE = ?, MODEL = ?, YEAR = ?, COLOR = ?, ODOMETER = ?, STATUS = ?, VTNAME = ?, LOCATION = ?, CITY = ? WHERE  VID = ?");
            if(newVehicle.getVid() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, newVehicle.getVid());
            }

            if(newVehicle.getVlicense() == -1) {
                ps.setNull(2,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(2, newVehicle.getVlicense());
            }

            if(newVehicle.getYear() == -1) {
                ps.setNull(5,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(5, newVehicle.getYear());
            }

            if(newVehicle.getOdometer() == -1) {
                ps.setNull(7, Types.FLOAT);
            }
            else {
                ps.setFloat(7, newVehicle.getOdometer());
            }

            ps.setTimestamp(3, newVehicle.getMake());
            ps.setString(4, newVehicle.getModel());
            ps.setString(6,newVehicle.getColor());
            ps.setString(8,newVehicle.getStatus());
            ps.setString(9,newVehicle.getVtname());
            ps.setString(10, newVehicle.getLocation());
            ps.setString(11,newVehicle.getCity());

            ps.setInt(12, newVehicle.getVid());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

    }

    public List<Vehicle> viewVehicle(){
        List<Vehicle> vehicles = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM VEHICLES");

            while(rs.next()) {
                Vehicle vehicle = new Vehicle();

                vehicle.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    vehicle.setVid(-1);
                }
                vehicle.setVlicense(rs.getInt("vlicense"));
                if(rs.wasNull()) {
                    vehicle.setVlicense(-1);
                }

                vehicle.setYear(rs.getInt("year"));
                if(rs.wasNull()) {
                    vehicle.setYear(-1);
                }

                vehicle.setOdometer(rs.getInt("odometer"));
                if(rs.wasNull()) {
                    vehicle.setOdometer(-1);
                }

                vehicle.setMake(rs.getTimestamp("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setStatus(rs.getString("status"));
                vehicle.setVtname(rs.getString("vtname"));
                vehicle.setLocation(rs.getString("location"));
                vehicle.setCity(rs.getString("city"));

                vehicles.add(vehicle);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return vehicles;
    }

    public Vehicle viewVehicle(int vid){
        Vehicle vehicle = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM VEHICLES WHERE VID = ?");
            ps.setInt(1, vid);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                vehicle = new Vehicle();

                vehicle.setVid(rs.getInt("vid"));
                if(rs.wasNull()) {
                    vehicle.setVid(-1);
                }
                vehicle.setVlicense(rs.getInt("vlicense"));
                if(rs.wasNull()) {
                    vehicle.setVlicense(-1);
                }

                vehicle.setYear(rs.getInt("year"));
                if(rs.wasNull()) {
                    vehicle.setYear(-1);
                }

                vehicle.setOdometer(rs.getInt("odometer"));
                if(rs.wasNull()) {
                    vehicle.setOdometer(-1);
                }

                vehicle.setMake(rs.getTimestamp("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setStatus(rs.getString("status"));
                vehicle.setVtname(rs.getString("vtname"));
                vehicle.setLocation(rs.getString("location"));
                vehicle.setCity(rs.getString("city"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return vehicle;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
