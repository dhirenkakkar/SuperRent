package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Reservations {
    private int confNo; // -1 is null
    private String vtname;
    private int vid; // -1 is null
    private int cellphone; // -1 is null
    private Timestamp fromDateTime;
    private Timestamp toDateTime;

    public Reservations(){}

    public Reservations(int confNo, String vtname, int vid, int cellphone, Timestamp fromDateTime, Timestamp toDateTime) {
        this.confNo = confNo;
        this.vtname = vtname;
        this.vid = vid;
        this.cellphone = cellphone;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public int getConfNo() {
        return confNo;
    }

    public void setConfNo(int confNo) {
        this.confNo = confNo;
    }

    public String getVtname() {
        return vtname;
    }

    public void setVtname(String vtname) {
        this.vtname = vtname;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }

    public Timestamp getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Timestamp fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public Timestamp getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(Timestamp toDateTime) {
        this.toDateTime = toDateTime;
    }
}
