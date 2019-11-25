package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class TotalReturn {
    Timestamp Date;
    String Location;
    Integer newRentursAtBranch;
    String VehicleType;
    Integer newReturns;
    Integer totalRevenue;
    Integer totalBranchRevenue;

    public void totalReturn(Timestamp Date, String Location, Integer newRentalsAtBranch, String VehicleType, Integer newRentals, Integer totalRevenue,
    Integer totalBranchRevenue){
        this.Date = Date;
        this.Location = Location;
        this.newRentursAtBranch = newRentalsAtBranch;
        this.VehicleType = VehicleType;
        this.newReturns = newRentals;
        this.totalRevenue = totalRevenue;
        this.totalBranchRevenue = totalBranchRevenue;
    }
}
