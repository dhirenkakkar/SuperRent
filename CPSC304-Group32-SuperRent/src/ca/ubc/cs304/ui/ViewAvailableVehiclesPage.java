package ca.ubc.cs304.ui;

import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.utils.StringUtils;
import com.github.lgooddatepicker.components.DateTimePicker;
import oracle.sql.TIMESTAMP;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ViewAvailableVehiclesPage extends JPanel implements PageUI{

    ViewAvailableVehiclesPage instace = null;

    private JFrame parent = null;

    private JLabel vtname;
    private JLabel city;
    private JLabel location;

    private JComboBox<String> cityCB;
    private JComboBox<String> locationCB;
    private JComboBox<String> vtnameCB;

    private JButton search;
    private JButton home;

    private JPanel numAv;


    private DateTimePicker fromDateTime;
    private DateTimePicker toDateTime;

    private JFrame child;
    private JScrollPane avTable;
    private JButton back;
    private JTable jTable;


    ArrayList<Vehicle> vehicles;

    public ViewAvailableVehiclesPage(JFrame parent){
        vehicles = new ArrayList<>();
        instace = this;
        this.parent = parent;
        setupviewAvailableVehiclesPage();
    }

    private void setupviewAvailableVehiclesPage() {

        vtname = new JLabel("Vehicle Type");
        vtname.setVisible(true);
        add(vtname);

        String[] vehicleTypeChoices = { "","Economy","Compact", "Midsize","Standard","Fullsize","SUV", "Truck"};
        vtnameCB = new JComboBox<String>(vehicleTypeChoices);
        vtnameCB.setVisible(true);
        add(vtnameCB);

        city = new JLabel("City");
        city.setVisible(true);
        add(city);

        String[] cityChoices = { "","California","Vancouver", "Toronto","Montreal","Los Angeles","San Fransisco", "Ottawa"};
        cityCB = new JComboBox<String>(cityChoices);
        cityCB.setVisible(true);
        add(cityCB);

        location = new JLabel("Location");
        location.setVisible(true);
        add(location);

        String[] locationChoices = { "","Downtown","Midtown", "Uptown"};
        locationCB = new JComboBox<String>(locationChoices);
        locationCB.setVisible(true);
        add(locationCB);

        search = new JButton("Search");
        add(search);

        home = new JButton("Home");
        add(home);

        // Create a DateTimePicker. (But don't add it to the form).
        fromDateTime = new DateTimePicker();
        add(fromDateTime);

        toDateTime = new DateTimePicker();
        add(toDateTime);

        this.setVisible(true);
        parent.setVisible(true);

    }

    public JButton getSearch() {
        return search;
    }

    public JComboBox<String> getCityCB() {
        return cityCB;
    }

    public JComboBox<String> getLocationCB() {
        return locationCB;
    }

    public JComboBox<String> getVtnameCB() {
        return vtnameCB;
    }

    public JButton getHome() {
        return home;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void showNumberOfAV(){
        if(numAv != null){
            remove(numAv);
        }
        numAv = new JPanel();
        JButton showDetails = new JButton("Show Details");
        JLabel numberLabel = new JLabel("Available:");
        JLabel number = new JLabel("" + vehicles.size());
        showDetails.addActionListener(showDetailActionListener);
        numAv.add(numberLabel);
        numAv.add(number);
        numAv.add(showDetails);
        add(numAv);
        numAv.setVisible(true);
        this.setVisible(true);
        parent.setVisible(true);
    }

    private void showAV(){

        child = new JFrame();
        child.setSize(600,400);
        child.setLocation(200,200);
        String [] cols = {"vlicense", "make", "model", "year", "color","odometer", "status", "vtname", "location", "city"};
        String [][] data = StringUtils.getVehiclesInArray(vehicles);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        avTable = new JScrollPane(jTable);

        child.add(avTable);
        child.setVisible(true);

    }

    private ActionListener backActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            instace.removeAll();
            setupviewAvailableVehiclesPage();
        }
    };

    private ActionListener showDetailActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            showAV();
        }
    };

    public FilterSearch getFilterSearch(){
        FilterSearch filterSearch = new FilterSearch();
        String check;
        check = vtnameCB.getSelectedItem().toString().toLowerCase();
        if(check.equals("")){
            filterSearch.setVtname(null);
        }
        else{
            filterSearch.setVtname(check);
        }

        check = locationCB.getSelectedItem().toString().toLowerCase();
        if(check.equals("")){
            filterSearch.setLocation(null);
        }
        else{
            filterSearch.setLocation(check);
        }

        check = cityCB.getSelectedItem().toString().toLowerCase();
        if(check.equals("")){
            filterSearch.setCity(null);
        }
        else{
            filterSearch.setCity(check);
        }

        check = fromDateTime.toString();
        if(check.equals("")){
            filterSearch.setFromDate(null);
        }
        else{
            filterSearch.setFromDate(StringUtils.formatString(check));
        }

        check = toDateTime.toString();
        if(check.equals("")){
            filterSearch.setFromDate(null);
        }
        else{
            filterSearch.setToDate(StringUtils.formatString(check));
        }
        return filterSearch;

    }

    public void cleanUp(){
        if(numAv != null){
            remove(numAv);
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {
        cleanUp();
    }
}
