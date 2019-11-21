package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Returns;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnsManipulation {
    private Connection connection;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";

    public ReturnsManipulation(Connection connection) {
        this.connection = connection;
    }

    public void insertReturns(Returns returns){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO RETURNS VALUES (?,?,?,?)");

            if(returns.getRid() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, returns.getRid());
            }

            if(returns.getOdometer() == -1) {
                ps.setNull(3, Types.FLOAT);
            }
            else {
                ps.setFloat(3, returns.getOdometer());
            }



            ps.setString(4,returns.getFulltank());
            ps.setTimestamp(2, returns.getRdate());

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void deleteReturns(int rid){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM RETURNS WHERE RID = ?");
            ps.setInt(1,rid);
            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public void updateReturns(int rid, Returns newReturns) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE RETURNS SET RID = ?, RDATE = ?, ODOMETER = ?, FULLTANK = ? WHERE  RID = ?");
            if(newReturns.getRid() == -1) {
                ps.setNull(1,java.sql.Types.INTEGER);
            }
            else {
                ps.setInt(1, newReturns.getRid());
            }

            if(newReturns.getOdometer() == -1) {
                ps.setNull(3, Types.FLOAT);
            }
            else {
                ps.setFloat(3, newReturns.getOdometer());
            }

            ps.setString(4,newReturns.getFulltank());
            ps.setTimestamp(2, newReturns.getRdate());

            ps.setInt(5,rid);

            ps.executeUpdate();
            connection.commit();

            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public List<Returns> viewReturns(){
        List<Returns> returnsList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RETURNS");

            while(rs.next()) {
                Returns returns = new Returns();
                returns.setRid(rs.getInt("rid"));
                if(rs.wasNull()) {
                    returns.setRid(-1);
                }
                returns.setOdometer(rs.getFloat("odometer"));
                if(rs.wasNull()) {
                    returns.setOdometer(-1);
                }
                returns.setRdate(rs.getTimestamp("rdate"));
                returns.setFulltank(rs.getString("fulltank"));

                returnsList.add(returns);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return returnsList;
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
