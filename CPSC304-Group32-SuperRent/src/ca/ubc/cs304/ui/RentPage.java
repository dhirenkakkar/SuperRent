package ca.ubc.cs304.ui;

import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.utils.StringUtils;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentPage extends JPanel {
    RentPage instace = null;

    private JFrame parent = null;

    private JLabel vtname;
    private JLabel city;
    private JLabel location;
    private JLabel noAV;

    private JComboBox<String> cityCB;
    private JComboBox<String> locationCB;
    private JComboBox<String> vtnameCB;

    private JButton search;
    private JButton home;
    private JButton proceedReserve;

    private JPanel numAv;
    private JPanel cost;
    private JFrame customerInfo;
    private JPanel customerInfoP;


    private DateTimePicker fromDateTime;
    private DateTimePicker toDateTime;

    private JFrame child;
    private JScrollPane avTable;
    private JButton back;
    private JTable jTable;

    private JLabel cellphoneView;
    private JLabel nameView;
    private JLabel addressView;
    private JLabel dlicenseView;

    private JTextField cellphoneText;
    private JTextField nameText;
    private JTextField addressText;
    private JTextField dlicenseText;

    private JButton submit;
    private JButton cancel;

    private JLabel message;


    ArrayList<Vehicle> vehicles;

    public RentPage(JFrame parent){
        vehicles = new ArrayList<>();
        instace = this;
        this.parent = parent;
        setupRentPage();
    }

    private void setupRentPage() {

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

        String[] cityChoices = { "","California","Vancouver", "Toronto"};
        cityCB = new JComboBox<String>(cityChoices);
        cityCB.setVisible(true);
        add(cityCB);

        location = new JLabel("Location");
        location.setVisible(true);
        add(location);

        String[] locationChoices = { "","Downtown", "Uptown"};
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

        proceedReserve = new JButton("Proceed to Reservation");
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");


        this.setVisible(true);
        parent.setVisible(true);

    }

    public void showNoAvailableVehicle(){
        cleanUp();
        if(noAV != null){
            remove(noAV);
        }
        noAV = new JLabel("No Available Vehicle");
        add(noAV);
        setVisible(true);
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

    private void showReservationReciept(Reservations reservation){

        String [] cols = {"confNo", "vtname", "vid", "year", "cellphone","from date and time", "to date and time", "vtname", "location", "city"};
        ArrayList<Reservations> reservations = new ArrayList<>();
        reservations.add(reservation);
        String [][] data = StringUtils.getReservationsInArray(reservations);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        avTable = new JScrollPane(jTable);

        customerInfo.add(avTable);
        customerInfo.setVisible(true);
        parent.setVisible(true);

    }

    private ActionListener backActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            instace.removeAll();
            setupRentPage();
        }
    };


    public FilterSearch getFilterSearch(){
        FilterSearch filterSearch = new FilterSearch();
        String check;
        check = vtnameCB.getSelectedItem().toString().toLowerCase();
        if(check.equals("")){
            showErrorMessage("Please Complete All the Fields");
            return null;
        }
        else{
            filterSearch.setVtname(check);
        }

        check = locationCB.getSelectedItem().toString().toLowerCase();
        if(check.equals("")){
            showErrorMessage("Please Complete All the Fields");
            return null;
        }
        else{
            filterSearch.setLocation(check);
        }

        check = cityCB.getSelectedItem().toString().toLowerCase();
        if(check.equals("")){
            showErrorMessage("Please Complete All the Fields");
            return null;
        }
        else{
            filterSearch.setCity(check);
        }

        check = fromDateTime.toString();
        if(check.equals("")){
            showErrorMessage("Please Complete All the Fields");
            return null;
        }
        else{
            filterSearch.setFromDate(StringUtils.formatString(check));
        }

        check = toDateTime.toString();
        if(check.equals("")){
            showErrorMessage("Please Complete All the Fields");
            return null;
        }
        else{
            filterSearch.setToDate(StringUtils.formatString(check));
        }

        if(filterSearch.getFromDate().compareTo(filterSearch.getToDate()) > 0){
            showErrorMessage("To date must be after from date");
            return null;
        }
        return filterSearch;
    }

    public void cleanUp(){
        if(noAV != null){
            remove(noAV);
        }
        if(cost != null){
            remove(cost);
        }
        if(customerInfo != null){
            remove(customerInfo);
        }
        if(message != null){
            remove(message);
        }
    }

    public void showCost(float costValue) {
        if(cost != null){
            remove(cost);
        }

        cost = new JPanel();
        JLabel estimatedCost = new JLabel("Estimated Cost");
        JLabel ecValue = new JLabel("" + costValue);
        cost.add(estimatedCost);
        cost.add(ecValue);
        cost.add(proceedReserve);
        cost.setVisible(true);
        add(cost);
        setVisible(true);
        parent.setVisible(true);
    }

    public JButton getProceedReserve() {
        return proceedReserve;
    }

    public void customerInfoView() {
        customerInfo = new JFrame();
        customerInfoP = new JPanel();

        customerInfo.setSize(600,400);
        customerInfo.setLocation(200,200);

        cellphoneView = new JLabel("Cellphone");
        nameView = new JLabel("Name");
        addressView = new JLabel("Address");
        dlicenseView = new JLabel("Dlicense");

        cellphoneText = new JIntegerTextField();
        nameText = new JTextField(20);
//        nameText.setPreferredSize(new Dimension(10,5));
        addressText = new JTextField(20);
        dlicenseText = new JTextField(20);

        customerInfoP.add(cellphoneView);
        customerInfoP.add(cellphoneText);
        customerInfoP.add(nameView);
        customerInfoP.add(nameText);
        customerInfoP.add(addressView);
        customerInfoP.add(addressText);
        customerInfoP.add(dlicenseView);
        customerInfoP.add(dlicenseText);
        customerInfoP.add(submit);

        customerInfo.add(customerInfoP);
        customerInfo.setVisible(true);


    }

    public Customers getCustomerInfo(){
        return new Customers(Integer.parseInt(cellphoneText.getText()),nameText.getText(), addressText.getText(),dlicenseText.getText());
    }

    public JButton getSubmit() {
        return submit;
    }

    public void cancelReservation(){
        cleanUp();
        setVisible(true);
        parent.setVisible(true);
    }

    public JButton getCancel() {
        return cancel;
    }

    public void showReservation(Reservations reservation){
        customerInfoP.setVisible(false);
        showReservationReciept(reservation);
    }

    public void showErrorMessage(String err){
        if(message != null){
            remove(message);
        }
        message = new JLabel(err);
        add(message);
        setVisible(true);
        parent.setVisible(true);
    }
}
