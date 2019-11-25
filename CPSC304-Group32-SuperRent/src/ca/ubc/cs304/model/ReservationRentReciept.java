package ca.ubc.cs304.model;

import java.sql.Timestamp;

public class ReservationRentReciept {
    private int confNo;
    private String vtname;
    private int cellphone;
    private Timestamp fromDateTime;
    private Timestamp toDateTime;
    private String location;
    private String city;

    public ReservationRentReciept(int confNo, String vtname, int cellphone, Timestamp fromDateTime, Timestamp toDateTime, String location, String city) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.cellphone = cellphone;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.location = location;
        this.city = city;
    }

    public int getConfNo() {
        return confNo;
    }

    public String getVtname() {
        return vtname;
    }

    public int getCellphone() {
        return cellphone;
    }

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }
}
