package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.VehicleTypes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleTypesManipulation {
    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public VehicleTypesManipulation(Connection connection) {
        this.connection = connection;
    }

    public void insertVehicleTypes(VehicleTypes vehicleTypes){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO VEHICLETYPES VALUES (?,?,?,?,?,?,?,?,?)");

            ps.setString(1,vehicleTypes.getVtname());
            ps.setString(2,vehicleTypes.getFeatures());

            if(vehicleTypes.getWrate() == -1) {
                ps.setNull(3, Types.FLOAT);
            }
            else {
                ps.setFloat(3, vehicleTypes.getWrate());
            }

            if(vehicleTypes.getDrate() == -1) {
                ps.setNull(4, Types.FLOAT);
            }
            else {
                ps.setFloat(4, vehicleTypes.getDrate());
            }

            if(vehicleTypes.getHrate() == -1) {
                ps.setNull(5, Types.FLOAT);
            }
            else {
                ps.setFloat(5, vehicleTypes.getWrate());
            }

            if(vehicleTypes.getWirate() == -1) {
                ps.setNull(6, Types.FLOAT);
            }
            else {
                ps.setFloat(6, vehicleTypes.getWirate());
            }

            if(vehicleTypes.getDirate() == -1) {
                ps.setNull(7, Types.FLOAT);
            }
            else {
                ps.setFloat(7, vehicleTypes.getDirate());
            }

            if(vehicleTypes.getHirate() == -1) {
                ps.setNull(8, Types.FLOAT);
            }
            else {
                ps.setFloat(8, vehicleTypes.getHirate());
            }

            if(vehicleTypes.getKrate() == -1) {
                ps.setNull(9, Types.FLOAT);
            }
            else {
                ps.setFloat(9, vehicleTypes.getKrate());
            }

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteVehicleTypes(String vtname){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM VEHICLETYPES WHERE VTNAME = ?");
            ps.setString(1, vtname);
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateVehicleTypes(VehicleTypes vehicleTypes) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE VEHICLETYPES SET VTNAME = ?, FEATURES = ?, WRATE = ?, DRATE = ?, HRATE = ?, WIRATE = ?, DIRATE = ?, HIRATE = ?, KRATE = ? WHERE  VTNAME = ?");

            ps.setString(1,vehicleTypes.getVtname());
            ps.setString(2,vehicleTypes.getFeatures());

            if(vehicleTypes.getWrate() == -1) {
                ps.setNull(3, Types.FLOAT);
            }
            else {
                ps.setFloat(3, vehicleTypes.getWrate());
            }

            if(vehicleTypes.getDrate() == -1) {
                ps.setNull(4, Types.FLOAT);
            }
            else {
                ps.setFloat(4, vehicleTypes.getDrate());
            }

            if(vehicleTypes.getHrate() == -1) {
                ps.setNull(5, Types.FLOAT);
            }
            else {
                ps.setFloat(5, vehicleTypes.getWrate());
            }

            if(vehicleTypes.getWirate() == -1) {
                ps.setNull(6, Types.FLOAT);
            }
            else {
                ps.setFloat(6, vehicleTypes.getWirate());
            }

            if(vehicleTypes.getDirate() == -1) {
                ps.setNull(7, Types.FLOAT);
            }
            else {
                ps.setFloat(7, vehicleTypes.getDirate());
            }

            if(vehicleTypes.getHirate() == -1) {
                ps.setNull(8, Types.FLOAT);
            }
            else {
                ps.setFloat(8, vehicleTypes.getHirate());
            }

            if(vehicleTypes.getKrate() == -1) {
                ps.setNull(9, Types.FLOAT);
            }
            else {
                ps.setFloat(9, vehicleTypes.getKrate());
            }

            ps.setString(10, vehicleTypes.getVtname());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public List<VehicleTypes> viewVehicleTypes(){
        List<VehicleTypes> vehicleTypes = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RESERVATIONS");

            while(rs.next()) {
                VehicleTypes vehicleType = new VehicleTypes();

                vehicleType.setWrate(rs.getInt("wrate"));
                if(rs.wasNull()) {
                    vehicleType.setWrate(-1);
                }
                vehicleType.setDrate(rs.getInt("drate"));
                if(rs.wasNull()) {
                    vehicleType.setDrate(-1);
                }
                vehicleType.setHrate(rs.getInt("hrate"));
                if(rs.wasNull()) {
                    vehicleType.setHrate(-1);
                }
                vehicleType.setWirate(rs.getInt("wirate"));
                if(rs.wasNull()) {
                    vehicleType.setWirate(-1);
                }
                vehicleType.setDirate(rs.getInt("dirate"));
                if(rs.wasNull()) {
                    vehicleType.setDirate(-1);
                }
                vehicleType.setHirate(rs.getInt("hirate"));
                if(rs.wasNull()) {
                    vehicleType.setHirate(-1);
                }
                vehicleType.setKrate(rs.getInt("krate"));
                if(rs.wasNull()) {
                    vehicleType.setKrate(-1);
                }
                vehicleType.setVtname(rs.getString("vtname"));
                vehicleType.setFeatures(rs.getString("features"));

                vehicleTypes.add(vehicleType);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return vehicleTypes;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
