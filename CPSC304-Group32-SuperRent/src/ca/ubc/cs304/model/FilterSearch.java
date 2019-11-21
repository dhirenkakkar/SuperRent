package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Timestamp;

public class FilterSearch {
    private String vtname;
    private String location;
    private String city;
    private Timestamp fromDate;
    private Timestamp toDate;

    public FilterSearch(String vtname, String location, String city, Timestamp fromDate, Timestamp toDate) {
        this.vtname = vtname;
        this.location = location;
        this.city = city;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getVtname() {
        return vtname;
    }

    public void setVtname(String vtname) {
        this.vtname = vtname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }
}
