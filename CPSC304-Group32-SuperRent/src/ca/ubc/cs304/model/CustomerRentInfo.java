package ca.ubc.cs304.model;

import java.sql.Time;
import java.sql.Timestamp;

public class CustomerRentInfo {
    private String cardName;
    private int cardNo;
    private Timestamp expDate;

    public CustomerRentInfo(String cardName, int cardNo, Timestamp expDate) {
        this.cardName = cardName;
        this.cardNo = cardNo;
        this.expDate = expDate;
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

    public Timestamp getExpDate() {
        return expDate;
    }

    public void setExpDate(Timestamp expDate) {
        this.expDate = expDate;
    }
}
