package ca.ubc.cs304.utils;

import ca.ubc.cs304.model.*;
import oracle.sql.TIMESTAMP;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class StringUtils {
    public static String formatTimeStamp(Timestamp timestamp) {
        StringBuffer formatted = new StringBuffer(timestamp.toString());
        int len = formatted.length();
        formatted.delete(len-2,len);
        return formatted.toString();
    }

    public static Timestamp formatString(String dateTime){
        Timestamp timestampRet = null;
        StringBuffer stringBuffer = new StringBuffer(dateTime);
        stringBuffer.replace(10,11," ");
        stringBuffer.append(":00");
        TIMESTAMP timestamp = new TIMESTAMP(stringBuffer.toString());
        try {
            timestampRet = timestamp.timestampValue();
        } catch (SQLException e) {}

        return timestampRet;
    }

    public static String[][] getVehiclesInArray(ArrayList<Vehicle> vehicles){
        String [][] data =  new String[vehicles.size()][10];
        for(int i = 0; i < vehicles.size(); i++){
            Vehicle vehicle = vehicles.get(i);
            ArrayList<String> row = new ArrayList<>();

            row.add(check(vehicle.getVlicense()));
            row.add(check(vehicle.getMake()));
            row.add(check(vehicle.getModel()));
            row.add(check(vehicle.getYear()));
            row.add(check(vehicle.getColor()));
            row.add(check(vehicle.getOdometer()));
            row.add(check(vehicle.getStatus()));
            row.add(check(vehicle.getVtname()));
            row.add(check(vehicle.getLocation()));
            row.add(check(vehicle.getCity()));

            row.toArray(data[i]);
        }
        return data;
    }

    public static String[][] getCustomersInArray(ArrayList<Customers> customers){
        String [][] data =  new String[customers.size()][10];
        for(int i = 0; i < customers.size(); i++){
            Customers customer = customers.get(i);
            ArrayList<String> row = new ArrayList<>();

            row.add(check(customer.getCellphone()));
            row.add(check(customer.getName()));
            row.add(check(customer.getAddress()));
            row.add(check(customer.getDlicense()));

            row.toArray(data[i]);
        }
        return data;
    }

    public static String[][] getRentalsInArray(ArrayList<Rentals> rentals){
        String [][] data =  new String[rentals.size()][10];
        for(int i = 0; i < rentals.size(); i++){
            Rentals rental = rentals.get(i);
            ArrayList<String> row = new ArrayList<>();

            row.add(check(rental.getRid()));
            row.add(check(rental.getVid()));
            row.add(check(rental.getCellphone()));
            row.add(check(rental.getFromDateTime()));
            row.add(check(rental.getToDateTime()));
            row.add(check(rental.getOdometer()));
            row.add(check(rental.getCardName()));
            row.add(check(rental.getCardNo()));
            row.add(check(rental.getExpDate()));
            row.add(check(rental.getConfNo()));
            row.toArray(data[i]);
        }
        return data;
    }

    public static String[][] getReservationsInArray(ArrayList<Reservations> reservations){
        String [][] data =  new String[reservations.size()][10];
        for(int i = 0; i < reservations.size(); i++){
            Reservations reservation = reservations.get(i);
            ArrayList<String> row = new ArrayList<>();

            row.add(check(reservation.getConfNo()));
            row.add(check(reservation.getVtname()));
            row.add(check(reservation.getVid()));
            row.add(check(reservation.getCellphone()));
            row.add(check(reservation.getFromDateTime()));
            row.add(check(reservation.getToDateTime()));
            row.toArray(data[i]);
        }
        return data;
    }

    public static String[][] getVehicleTypesInArray(ArrayList<VehicleTypes> vehicleTypes){
        String [][] data =  new String[vehicleTypes.size()][10];
        for(int i = 0; i < vehicleTypes.size(); i++){
            VehicleTypes vehicleType = vehicleTypes.get(i);
            ArrayList<String> row = new ArrayList<>();

            row.add(check(vehicleType.getVtname()));
            row.add(check(vehicleType.getFeatures()));
            row.add(check(vehicleType.getWrate()));
            row.add(check(vehicleType.getDrate()));
            row.add(check(vehicleType.getHrate()));
            row.add(check(vehicleType.getWirate()));
            row.add(check(vehicleType.getDirate()));
            row.add(check(vehicleType.getHirate()));
            row.add(check(vehicleType.getKrate()));

            row.toArray(data[i]);
        }
        return data;
    }

    public static String[][] getReturnsInArray(ArrayList<Returns> returns){
        String [][] data =  new String[returns.size()][10];
        for(int i = 0; i < returns.size(); i++){
            Returns returnsRow = returns.get(i);
            ArrayList<String> row = new ArrayList<>();

            row.add(check(returnsRow.getRid()));
            row.add(check(returnsRow.getRdate()));
            row.add(check(returnsRow.getOdometer()));
            row.add(check(returnsRow.getFulltank()));

            row.toArray(data[i]);
        }
        return data;
    }

    private static String check(int i){
        if(i == -1){
            return "NA";
        }
        else return Integer.toString(i);
    }

    private static String check(float f){
        if(f == -1){
            return "NA";
        }
        else return Float.toString(f);
    }

    private static String check(String s){
        if(s == null){
            return "NA";
        }
        else return s;
    }

    private static String check(Timestamp t){
        if(t == null){
            return "NA";
        }
        else return t.toString();
    }

}
