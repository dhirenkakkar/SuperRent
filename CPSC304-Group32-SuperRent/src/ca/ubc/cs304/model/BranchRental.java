package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class BranchRental {
    Timestamp Date;
    String Location;
    Integer newRentalsAtBranch;
    String VehicleType;
    Integer newRentals;

    public void branchRental(Timestamp Date, String Location, Integer newRentalsAtBranch, String VehicleType, Integer newRentals){
        this.Date = Date;
        this.Location = Location;
        this.newRentalsAtBranch = newRentalsAtBranch;
        this.VehicleType = VehicleType;
        this.newRentals = newRentals;
    }
}
