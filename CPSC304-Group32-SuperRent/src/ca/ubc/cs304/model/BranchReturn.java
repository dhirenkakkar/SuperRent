package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class BranchReturn extends TotalReturn {
    Timestamp Date;
    String Location;
    Integer newReturnsAtBranch;
    String VehicleType;
    Integer newReturns;
    Integer totalRevenue;
    Integer totalBranchRevenue;

    public void branchReturn(Timestamp Date, String Location, Integer newRentalsAtBranch, String VehicleType, Integer newRentals, Integer totalRevenue,
                             Integer totalBranchRevenue){
        this.Date = Date;
        this.Location = Location;
        this.newReturnsAtBranch = newRentalsAtBranch;
        this.VehicleType = VehicleType;
        this.newReturns = newReturns;
        this.totalRevenue = totalRevenue;
        this.totalBranchRevenue = totalBranchRevenue;
    }
}
