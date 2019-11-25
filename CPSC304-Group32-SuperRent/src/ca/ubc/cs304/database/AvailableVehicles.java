package ca.ubc.cs304.database;

import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.utils.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AvailableVehicles {
    private Connection connection;
    private FilterSearch filterSearch;
    private ArrayList<Vehicle> vehicles;
    private static final String EXCEPTION_TAG = "[EXCEPTION]";
    private static final String WARNING_TAG = "[WARNING]";


    public AvailableVehicles(Connection connection, FilterSearch filterSearch) {
        this.connection = connection;
        this.filterSearch = filterSearch;
        vehicles = new ArrayList<>();

    }

    public ArrayList<Vehicle> getNumAvailable(){

        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(getAvailableVehiclesQuery(filterSearch));
            while(rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVid(rs.getInt("vid"));
                vehicle.setVlicense(rs.getInt("vlicense"));
                vehicle.setMake(rs.getTimestamp("make"));
                vehicle.setModel(rs.getString("model"));
                vehicle.setYear(rs.getInt("year"));
                vehicle.setColor(rs.getString("color"));
                vehicle.setOdometer(rs.getFloat("odometer"));
                vehicle.setStatus(rs.getString("status"));
                vehicle.setVtname(rs.getString("vtname"));
                vehicle.setLocation(rs.getString("location"));
                vehicle.setCity(rs.getString("city"));
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return (ArrayList<Vehicle>) vehicles.clone();
    }

    private static String getAvailableVehiclesQuery(FilterSearch filterSearch) {
        StringBuffer allVehicles = new StringBuffer("select * from Vehicles V1");
        StringBuffer unavailableVehicles = new StringBuffer("select V1.* from Vehicles V1, Reservations R1 where V1.vid = R1.vid");

        int numFilters = 0;
        int prevDate = 0;

        numFilters = setQueries(filterSearch.getVtname(), "vtname",allVehicles, unavailableVehicles, numFilters);
        numFilters = setQueries(filterSearch.getLocation(), "location", allVehicles, unavailableVehicles, numFilters);
        numFilters = setQueries(filterSearch.getCity(), "city",allVehicles, unavailableVehicles, numFilters);

        if(filterSearch.getToDate() != null) {
            if(numFilters == 0){
                numFilters++;
            }
            unavailableVehicles.append(" and not ((R1.fromDateTime > TO_DATE('" + StringUtils.formatTimeStamp(filterSearch.getToDate()) + "', 'YYYY-MM-DD HH24:MI:SS'))");
            prevDate++;
        }

        if(filterSearch.getFromDate() != null) {
            if(numFilters == 0){
                numFilters++;
            }

            if(prevDate == 0){
                unavailableVehicles.append(" and not (R1.toDateTime < TO_DATE('" + StringUtils.formatTimeStamp(filterSearch.getFromDate()) + "', 'YYYY-MM-DD HH24:MI:SS'))");
            }
            else {
                unavailableVehicles.append(" or (R1.toDateTime < TO_DATE('" + StringUtils.formatTimeStamp(filterSearch.getFromDate()) + "', 'YYYY-MM-DD HH24:MI:SS')))");
            }

        }
        else {
            if(prevDate != 0){
                unavailableVehicles.append(")");
            }
        }

        allVehicles.append(" minus ");
        allVehicles.append(unavailableVehicles.toString());

        return allVehicles.toString();
    }


    private static int setQueries(String field, String fieldName, StringBuffer allVehicles, StringBuffer unavailableVehicles, int numFilters){
        if(field != null){
            if(numFilters == 0){
                allVehicles.append(" where V1." + fieldName + " = " + makeStringSql(field));
                numFilters++;
            }
            else {
                allVehicles.append(" and V1." + fieldName + " = " + makeStringSql(field));
            }
            unavailableVehicles.append(" and V1." + fieldName + " = " + makeStringSql(field));
        }
        return numFilters;
    }


    public static void main(String[] args) {
        FilterSearch filterSearch = new FilterSearch("fuck",null,null,null,null);
        System.out.println(getAvailableVehiclesQuery(filterSearch));
    }

    private static String makeStringSql(String string){
        return "'" + string + "'";
    }

    private void rollbackConnection() {
        try  {
            connection.rollback();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
        }
    }
}
