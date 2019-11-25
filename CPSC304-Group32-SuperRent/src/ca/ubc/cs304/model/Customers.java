package ca.ubc.cs304.model;

public class Customers {
    int cellphone; // -1 means null
    String name;
    String address;
    String dlicense;

    public Customers(int cellphone, String name, String address, String dlicense) {
        this.cellphone = cellphone;
        this.name = name;
        this.address = address;
        this.dlicense = dlicense;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDlicense() {
        return dlicense;
    }

    public void setDlicense(String dlicense) {
        this.dlicense = dlicense;
    }
}
