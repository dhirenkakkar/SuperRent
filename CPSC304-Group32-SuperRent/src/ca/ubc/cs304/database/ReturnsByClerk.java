package ca.ubc.cs304.database;

import ca.ubc.cs304.model.Rentals;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Returns;
import ca.ubc.cs304.ui.ErrorHandling;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ReturnsByClerk {
    private Connection connection;

    public ReturnsByClerk(Connection connection){
        this.connection = connection;
    }

    public void ReturnsByClerk(int confNo) throws SQLException {
        float wrate = 0;
        float drate = 0;
        float hrate = 0;
        float wirate = 0;
        float dirate = 0;
        float hirate = 0;
        final float[] cost = {0};

        ReservationsManipulation reservationsManipulation = new ReservationsManipulation(connection);
        Reservations reservations = reservationsManipulation.viewReservation(confNo);
        if(reservations == null){
            return;
        }

        RentalsManipulation rentalsManipulation = new RentalsManipulation(connection);
        Rentals rentals = rentalsManipulation.viewRentals(confNo);
        if(rentals == null){
            JOptionPane.showMessageDialog(null, "No Such Rented Vehicle Exist");
            return;
        }

        String vtname = reservations.getVtname();


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

        Timestamp fromDate = reservations.getFromDateTime();

        PreparedStatement psss = connection.prepareStatement("SELECT rid FROM Rentals WHERE CONFNO = ?");
        psss.setInt(1,confNo);
        ResultSet rsss = psss.executeQuery();
        connection.commit();
        int rid = 0;
        while (rsss.next()){
            rid = rsss.getInt("rid");
        }

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
        int finalRid = rid;

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
                    VehiclesManipulation vehiclesManipulation = new VehiclesManipulation(connection);
                    y = connection.prepareStatement("SELECT confNo FROM Rentals WHERE rid = ?");
                    Returns returns = new Returns(finalRid,timestamp,cost[0],odometer,full);
                    rm.insertReturns(returns);
                    rmm.deleteRentals(finalRid);
                    vehiclesManipulation.updateVehicle("available", reservations.getVid());

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    y.setInt(1, finalRid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
//                ResultSet t = null;
//                try {
//                    t = y.executeQuery();
//                    while (t.next()){
//                        confNo = t.getInt("confNo");
//                    }
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
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
