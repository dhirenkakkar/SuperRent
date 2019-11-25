package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.RentalsManipulation;
import ca.ubc.cs304.database.ReturnsManipulation;
import ca.ubc.cs304.model.Returns;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ReturnsByClerk {
    private Connection connection;

    public void ReturnsByClerk(int vid) throws SQLException {
        float wrate = 0;
        float drate = 0;
        float hrate = 0;
        float wirate = 0;
        float dirate = 0;
        float hirate = 0;
        final float[] cost = {0};
        String vtname;
        PreparedStatement ps = connection.prepareStatement("SELECT vtname FROM Vehicles WHERE vid = ?");
        ps.setInt(1,vid);
        ResultSet rs = ps.executeQuery();
        vtname = rs.getString("vtname");

        PreparedStatement pss = connection.prepareStatement("SELECT wrate,drate,hrate,wirate,dirate,hirate FROM VehicleTypes WHERE vtname = ?");
        pss.setString(1,vtname);
        ResultSet rss = pss.executeQuery();
        while (rss.next()){
            wrate = rss.getInt("wrate");
            drate = rss.getInt("drate");
            hrate = rss.getInt("hrate");
            wirate = rss.getInt("wirate");
            dirate = rss.getInt("dirate");
            hirate = rss.getInt("hirate");
        }
        final int[] wperiod = {0};
        final int[] dperiod = {0};
        final int[] hperiod = {0};
        PreparedStatement psss = connection.prepareStatement("SELECT rid FROM Rentals WHERE vid = ?");
        psss.setInt(1,vid);
        ResultSet rsss = psss.executeQuery();
        int rid = rsss.getInt("rid");
        PreparedStatement pssss = connection.prepareStatement("SELECT fromDate,fromTime FROM Rentals WHERE rid=?");
        pssss.setInt(1,rid);
        ResultSet rssss = pssss.executeQuery();
        Timestamp fromDate = null;
        fromDate = rssss.getTimestamp("fromDate");
        JFrame rbc = new JFrame("Return");
        rbc.setLayout(null);
        rbc.setVisible(true);
        JTextField tf = new JTextField("Enter the date");
        tf.setBounds(20,50,200,50);
        JTextField tff = new JTextField("Enter the odometer reading");
        tff.setBounds(20,100,200,50);
        JTextField tfff = new JTextField("Is the gas tank full true/false");
        tfff.setBounds(20,150,200,50);
        JButton next = new JButton("next");
        next.setBounds(70,210,80,50);
        Timestamp finalFromDate = fromDate;
        float finalWrate = wrate;
        float finalDrate = drate;
        float finalHrate = hrate;
        float finalWirate = wirate;
        float finalDirate = dirate;
        float finalHirate = hirate;
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timestamp timestamp = Timestamp.valueOf(tf.getText());
                String full = tfff.getText();
                float odometer = Float.parseFloat(tff.getText());
                ArrayList<Integer> wdh = numWDH(finalFromDate, timestamp);
                wperiod[0] = wdh.get(2);
                dperiod[0] = wdh.get(1);
                hperiod[0] = wdh.get(0);
                rbc.setVisible(false);
                cost[0] = wperiod[0] * finalWrate + dperiod[0] * finalDrate + hperiod[0] * finalHrate + wperiod[0] * finalWirate + dperiod[0] * finalDirate + hperiod[0] * finalHirate;
                PreparedStatement y = null;
                try {
                    ReturnsManipulation rm = new ReturnsManipulation(connection);
                    RentalsManipulation rmm = new RentalsManipulation(connection);
                    y = connection.prepareStatement("SELECT confNo FROM Rentals WHERE rid = ?");
                    Returns returns = new Returns(rid,timestamp,odometer,full,cost[0]);
                    rm.insertReturns(returns);
                    rmm.deleteRentals(rid);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    y.setInt(1, rid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                ResultSet t = null;
                try {
                    t = y.executeQuery();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                int confNo = 0;
                try {
                    confNo = t.getInt("confNo");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(null, "the cost is " + cost[0] + " confNo : " + confNo + "Date of Return " + timestamp);
            }
        });

        rbc.setSize(300,300);
        rbc.add(next);
        rbc.add(tf);
        rbc.add(tff);
        rbc.add(tfff);
    }

    private ArrayList<Integer> numWDH(Timestamp t1, Timestamp t2){
        int diffYear = t2.getYear() - t1.getYear();
        int diffMonth = t2.getMonth() - t1.getMonth();
        int diffDate = t2.getNanos() - t1.getDate();
        int diffH = t2.getHours() - t1.getHours();
        int diffM = t2.getMinutes() - t1.getMinutes();
        int diffS = t2.getSeconds() - t1.getSeconds();

        final int hoursInYear = 8760;
        final int hoursInMonth = 720;
        final int hoursInDay = 24;
        final int hoursInWeek = 168;
        int extraHour = 0;
        if(diffM != 0 ){
            extraHour = 1;
        }
        int numberOfHoursInTotal = diffYear*hoursInYear + diffMonth*hoursInMonth + diffDate*hoursInDay + diffH + extraHour;
        int numberOfWeeks = numberOfHoursInTotal/hoursInWeek;
        int numberOfDays = (numberOfHoursInTotal - numberOfWeeks*hoursInWeek)/hoursInDay;
        int numberOfHours = numberOfHoursInTotal - numberOfWeeks*hoursInWeek - numberOfDays*hoursInDay;

        ArrayList<Integer> wdh = new ArrayList<>();
        wdh.add(numberOfHours);
        wdh.add(numberOfDays);
        wdh.add(numberOfWeeks);

        return wdh;
    }
}
