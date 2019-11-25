package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Rentals {
    private int rid; // -1 is null
    private int vid; // -1 is null
    private int cellphone; // -1 is null
    private Timestamp fromDateTime;
    private Timestamp toDateTime;
    private float odometer; // -1 is null
    private String cardName;
    private int cardNo; // -1 is null
    private Timestamp expDate;
    private int confNo; // -1 is null

    public Rentals(){
    }

    public Rentals(int rid, int vid, int cellphone, Timestamp fromDateTime, Timestamp toDateTime, float odometer, String cardName, int cardNo, Timestamp expDate, int confNo) {
        this.rid = rid;
        this.vid = vid;
        this.cellphone = cellphone;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.odometer = odometer;
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
        this.confNo = confNo;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }


    public int getConfNo() {
        return confNo;
    }

    public void setConfNo(int confNo) {
        this.confNo = confNo;
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

    public Timestamp getExpDate() {
        return expDate;
    }

    public void setExpDate(Timestamp expDate) {
        this.expDate = expDate;
    }
}
