package ca.ubc.cs304.ui;

import ca.ubc.cs304.model.*;
import ca.ubc.cs304.utils.StringUtils;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentPage extends JPanel implements PageUI{
    RentPage instace = null;

    private Reservations reservation = null;

    private JFrame parent = null;
    private JFrame child2;

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


    private JLabel fromDateView;
    private JLabel toDateView;
    private DateTimePicker fromDateTime;
    private DateTimePicker toDateTime;

    private JPanel child;
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
    private JButton rent;
    private JButton cancel;

    private JLabel message;

    private JLabel cncp;
    private JTextField cncpText;
    private JComboBox<String> cncpCb;
    private JButton rentWithoutRes;
    private String cellphone = null;
    private JButton proceedRent;

    private JLabel cardNameView;
    private JLabel cardNoView;
    private JLabel expDateView;

    private JTextField cardNameText;
    private JTextField cardNoText;
    private DatePicker expDateDate;


    ArrayList<Vehicle> vehicles;

    public RentPage(JFrame parent){
        vehicles = new ArrayList<>();
        instace = this;
        this.parent = parent;
        setupRentPage();
    }

    private void setupRentPage() {

        fromDateView = new JLabel("from date: ");
        toDateView = new JLabel("to date: ");

        cncp = new JLabel("Confirmation # or Cellphone #");
        cncpText = new JIntegerTextField();
        String [] cncpCbChoices = {"confNo", "cellphone"};
        cncpCb = new JComboBox<>(cncpCbChoices);

        add(cncp);
        add(cncpText);
        add(cncpCb);

        vtname = new JLabel("Vehicle Type");
        vtname.setVisible(true);


        String[] vehicleTypeChoices = { "","Economy","Compact", "Midsize","Standard","Fullsize","SUV", "Truck"};
        vtnameCB = new JComboBox<String>(vehicleTypeChoices);
        vtnameCB.setVisible(true);



        city = new JLabel("City");
        city.setVisible(true);


        String[] cityChoices = { "","California","Vancouver", "Toronto"};
        cityCB = new JComboBox<String>(cityChoices);
        cityCB.setVisible(true);


        location = new JLabel("Location");
        location.setVisible(true);


        String[] locationChoices = { "","Downtown", "Uptown"};
        locationCB = new JComboBox<String>(locationChoices);
        locationCB.setVisible(true);


        search = new JButton("Search");


        home = new JButton("Home");
        add(home);

        // Create a DateTimePicker. (But don't add it to the form).
        fromDateTime = new DateTimePicker();


        toDateTime = new DateTimePicker();

        proceedReserve = new JButton("Proceed to Reservation");
        submit = new JButton("Submit");
        cancel = new JButton("Cancel");
        rentWithoutRes = new JButton("Rent without reservation");
        proceedRent = new JButton("Proceed to Rent");

        cardNameView = new JLabel("Cardholder Name");
        cardNoView = new JLabel("Card #");
        expDateView = new JLabel("Exp Date");

        cardNameText = new JTextField(10);
        cardNoText = new JIntegerTextField();
        expDateDate = new DatePicker();
        rent = new JButton("Rent");


        add(submit);
        add(rentWithoutRes);



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

    private void showReservationReciept(ReservationRentReciept reservationRentReciept){

        String [] cols = {"confNo", "vtname", "cellphone","from date and time", "to date and time", "location", "city"};
        ArrayList<ReservationRentReciept> reservationRentReciepts = new ArrayList<>();
        reservationRentReciepts.add(reservationRentReciept);
        String [][] data = StringUtils.getReservationRentReceiptInArray(reservationRentReciepts);
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
        if(child != null){
            remove(child);
        }

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
        cost.add(proceedRent);
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
        addressText = new JTextField(20);
        dlicenseText = new JTextField(20);

        if(reservation == null){
            customerInfoP.add(cellphoneView);
            customerInfoP.add(cellphoneText);
            customerInfoP.add(nameView);
            customerInfoP.add(nameText);
            customerInfoP.add(addressView);
            customerInfoP.add(addressText);
            customerInfoP.add(dlicenseView);
            customerInfoP.add(dlicenseText);
        }

        customerInfoP.add(cardNameView);
        customerInfoP.add(cardNameText);
        customerInfoP.add(cardNoView);
        customerInfoP.add(cardNoText);
        customerInfoP.add(expDateView);
        customerInfoP.add(expDateDate);
        customerInfoP.add(rent);

        customerInfoP.setLayout(new GridLayout(10,2));
        customerInfoP.setVisible(true);

        customerInfo.add(customerInfoP);

        customerInfo.setVisible(true);


    }

    public Pair<Customers, CustomerRentInfo> getCustomerInfo(){

        Customers customers =  new Customers(Integer.parseInt(cellphoneText.getText()),nameText.getText(), addressText.getText(),dlicenseText.getText());
        System.out.println(expDateDate.toString());
        CustomerRentInfo customerRentInfo = new CustomerRentInfo(cardNameText.getText(), Integer.parseInt(cardNoText.getText()),StringUtils.formatString(expDateDate.toString()+"T00:00"));
        return new Pair<>(customers,customerRentInfo);
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

    public void showRent(ReservationRentReciept reservation){
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

    public void startup(){
        if(cncp != null){
            cncp.setVisible(true);
        }
        if(cncpText != null){
            cncpText.setVisible(true);
        }
        if(cncpCb != null){
            cncpCb.setVisible(true);
        }
        setVisible(true);
        parent.setVisible(true);
    }

    public JButton getRentWithoutRes() {
        return rentWithoutRes;
    }

    public void showFilter() {
        cncp.setVisible(false);
        cncpText.setVisible(false);
        cncpCb.setVisible(false);
        submit.setVisible(false);
        rentWithoutRes.setVisible(false);
        cleanUp();

        child = new JPanel();
        child.add(vtname);
        child.add(vtnameCB);
        child.add(city);
        child.add(cityCB);
        child.add(location);
        child.add(locationCB);
        child.add(fromDateView);
        child.add(fromDateTime);
        child.add(toDateView);
        child.add(toDateTime);
        child.add(search);
        child.add(home);

        child.setLayout(new GridLayout(7,4));

        add(child);
        setVisible(true);
        parent.setVisible(true);
    }

    @Override
    public void start() {
        if(cncp != null){
            cncp.setVisible(true);
        }
        if(cncpText != null){
            cncpText.setVisible(true);
        }
        if(cncpCb != null){
            cncpCb.setVisible(true);
        }
        if(rentWithoutRes != null){
            rentWithoutRes.setVisible(true);
        }
        if(submit != null){
            submit.setVisible(true);
        }
        if(home != null){
            home.setVisible(true);
            add(home);
        }
    }

    @Override
    public void end() {
        cleanUp();
    }

    public JTextField getCncpText() {
        return cncpText;
    }

    public JComboBox<String> getCncpCb() {
        return cncpCb;
    }

    public void showFilter(String text) {
        cellphone = text;
        showFilter();
    }

    public JButton getProceedRent() {
        return proceedRent;
    }

    public String getCellphone() {
        return cellphone;
    }

    public Reservations getReservation() {
        return reservation;
    }

    public void setReservation(Reservations reservation) {
        this.reservation = reservation;
    }

    public JButton getRent() {
        return rent;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}
