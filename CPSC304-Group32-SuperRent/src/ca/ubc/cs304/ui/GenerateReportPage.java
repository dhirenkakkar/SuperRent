package ca.ubc.cs304.ui;

import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Rentals;
import ca.ubc.cs304.model.Returns;
import ca.ubc.cs304.utils.StringUtils;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;

public class GenerateReportPage extends JPanel implements PageUI{
    private ArrayList<Rentals> rentals;
    private ArrayList<Returns> returns;

    private JFrame child;
    private JTable jTable;
    private JScrollPane avTable;

    private JButton home;

    private JButton dren;
    private JButton drenb;
    private JButton dret;
    private JButton dretb;

    private JLabel cityView;
    private JLabel locaitonView;
    private JLabel dateView;

    private DatePicker dateText;
    private JTextField cityText;
    private JTextField locationText;

    public GenerateReportPage(){
        returns = new ArrayList<>();
        rentals = new ArrayList<>();
        setupGenerateReportPage();
    }

    private void setupGenerateReportPage(){
        home = new JButton("Home");

        cityView = new JLabel("city");
        locaitonView = new JLabel("location");
        dateView = new JLabel("date");

        dateText = new DatePicker();
        cityText = new JTextField(10);
        locationText = new JTextField(10);

        dren = new JButton("Daily Rentals");
        drenb = new JButton("Daily Rentals Per Branch");
        dret = new JButton("Daily Returns");
        dretb = new JButton("Daily Returns Per Branch");

        add(cityView);
        add(cityText);
        add(locaitonView);
        add(locationText);
        add(dateView);
        add(dateText);

        add(dren);
        add(drenb);
        add(dret);
        add(dretb);

        add(home);
        setVisible(true);
    }

    public JButton getHome() {
        return home;
    }

    public FilterSearch getFilterSearch(String branch){
        FilterSearch filterSearch = new FilterSearch();
        if(dateText.toString().equals("")){
            JOptionPane.showMessageDialog(null, "No Date is chosen");
            return null;
        }
        filterSearch.setFromDate(StringUtils.formatString(dateText.toString()+"T00:00"));
        filterSearch.setToDate(StringUtils.formatString(dateText.toString()+"T23:59"));
        if(branch.equals("branch")){
            if(cityText.getText().equals("") || locationText.getText().equals("")){
                JOptionPane.showMessageDialog(null, "No Branch is selected");
                return null;
            }
        }
        filterSearch.setCity(cityText.getText());
        filterSearch.setLocation(locationText.getText());
        filterSearch.setVtname(null);
        return filterSearch;
    }



    public void showRentals(ArrayList<Rentals> rentals){
        child = new JFrame();
        child.setSize(600,400);
        child.setLocation(200,200);
        String [] cols = {"rid", "vid", "cellphone", "fromDateTime", "toDateTime","odometer", "cardName", "cardNo", "expDate", "confNo"};
        String [][] data = StringUtils.getRentalsInArray(rentals);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        avTable = new JScrollPane(jTable);

        child.add(avTable);
        child.setVisible(true);
    }

    public void showReturns(ArrayList<Returns> returns){
        child = new JFrame();
        child.setSize(600,400);
        child.setLocation(200,200);
        String [] cols = {"rid", "rdate", "valu", "odometer", "fulltank"};
        String [][] data = StringUtils.getReturnsInArray(returns);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        avTable = new JScrollPane(jTable);

        child.add(avTable);
        child.setVisible(true);
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

    public JButton getDren() {
        return dren;
    }

    public JButton getDrenb() {
        return drenb;
    }

    public JButton getDret() {
        return dret;
    }

    public JButton getDretb() {
        return dretb;
    }
}
