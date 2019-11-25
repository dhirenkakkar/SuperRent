package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class TotalRental {
    Timestamp Date;
    String Location;
    Integer newRentalsAtBranch;
    String VehicleType;
    Integer newRentals;

    public TotalRental(Timestamp date, String location, Integer newRentalsAtBranch, String vehicleType, Integer newRentals) {
    }

    public void totalRental(Timestamp Date, String Location, Integer newRentalsAtBranch, String VehicleType, Integer newRentals){
        this.Date = Date;
        this.Location = Location;
        this.newRentalsAtBranch = newRentalsAtBranch;
        this.VehicleType = VehicleType;
        this.newRentals = newRentals;
    }
}
