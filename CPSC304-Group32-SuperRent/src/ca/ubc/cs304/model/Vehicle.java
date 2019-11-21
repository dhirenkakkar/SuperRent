package ca.ubc.cs304.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Vehicle {
    private int vid; // -1 is null
    private int vlicense; // -1 is null
    private Timestamp make;
    private String model;
    private int year; // -1 is null
    private String color;
    private float odometer; // -1 is null
    private String status;
    private String vtname;
    private String location;
    private String city;

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getVlicense() {
        return vlicense;
    }

    public void setVlicense(int vlicense) {
        this.vlicense = vlicense;
    }

    public Timestamp getMake() {
        return make;
    }

    public void setMake(Timestamp make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getOdometer() {
        return odometer;
    }

    public void setOdometer(float odometer) {
        this.odometer = odometer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
