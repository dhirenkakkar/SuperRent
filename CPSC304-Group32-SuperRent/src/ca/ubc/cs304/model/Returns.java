package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Returns {
    private int rid; // -1 null
    private Timestamp rdate;
    private float odometer; // -1 null
    private String fulltank;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Timestamp getRdate() {
        return rdate;
    }

    public void setRdate(Timestamp rdate) {
        this.rdate = rdate;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public String getFulltank() {
        return fulltank;
    }

    public void setFulltank(String fulltank) {
        this.fulltank = fulltank;
    }
}
