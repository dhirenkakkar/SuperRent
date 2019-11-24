package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkWindowDelegate;
import ca.ubc.cs304.delegates.CustomerWindowDelegate;
import ca.ubc.cs304.model.*;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Gui extends JFrame{

    static GraphicsConfiguration gc;
    private static HomePage homePage;
    private static ClerkPage clerkPage;
    private static CustomerPage customerPage;
    private static ViewAvailableVehiclesPage viewAvailableVehiclesPage;
    private static MakeReservationPage makeReservationPage;
    private static RentPage rentPage;
    private static ReturnVehiclePage returnVehiclePage;
    private static GenerateReportPage generateReportPage;

    private CustomerWindowDelegate customerWindowDelegate;
    private ClerkWindowDelegate clerkWindowDelegate;

    public static void main (String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create an instance of the demo.
                Gui gui = new Gui();
                // Make the demo visible on the screen.
                gui.setVisible(true);
            }
        });
    }

    public Gui(){
        super(gc);
        setup();
    }

    public Gui(CustomerWindowDelegate customerWindowDelegate, ClerkWindowDelegate clerkWindowDelegate){
        super(gc);
        this.customerWindowDelegate = customerWindowDelegate;
        this.clerkWindowDelegate = clerkWindowDelegate;
        setup();
    }

    private void setup(){
        setTitle("Super Rent");
        setSize(600,400);
        setLocation(200,200);

        setupHomePage();
        setupClerkPage();
        setupCustomerPage();
        setupgenerateReportPage();
        setupmakeReservationPage();
        setupRentPage();
        setupreturnVehiclePage();
        setupviewAvailableVehiclesPage();

        setContentPane(homePage);
        setVisible(true);
    }

    private void setupHomePage() {
        homePage = new HomePage();
        homePage.getClerk().addActionListener(clerkActionListener);
        homePage.getCustomer().addActionListener(customerActionListener);
    }


    private void setupClerkPage() {
        clerkPage = new ClerkPage();

        clerkPage.getRent().addActionListener(rentActionListener);
        clerkPage.getGenerateReport().addActionListener(generateReportActionListener);
        clerkPage.getReturnVehicle().addActionListener(returnVehicleActionListener);
        clerkPage.getHome().addActionListener(homeActionListener);
    }
    private void setupCustomerPage() {
        customerPage = new CustomerPage();

        customerPage.getMakeReservation().addActionListener(makeReservationActionListener);
        customerPage.getViewAvailableVehicles().addActionListener(viewAvailableVehiclesActionListener);
        customerPage.getHome().addActionListener(homeActionListener);
    }
    private void setupviewAvailableVehiclesPage() {
        viewAvailableVehiclesPage = new ViewAvailableVehiclesPage(this);
        viewAvailableVehiclesPage.getSearch().addActionListener(searchActionListener);
        viewAvailableVehiclesPage.getHome().addActionListener(homeActionListener);
    }

    private void setupmakeReservationPage() {
        makeReservationPage = new MakeReservationPage(this);
        makeReservationPage.getHome().addActionListener(homeActionListener);
        makeReservationPage.getSearch().addActionListener(searchForReservationActionListener);
        makeReservationPage.getProceedReserve().addActionListener(proceedReserveActionListener);
        makeReservationPage.getSubmit().addActionListener(submitActionListener);
        makeReservationPage.getCancel().addActionListener(cancelReserveActionListener);
    }
    private void setupRentPage() {
        rentPage = new RentPage(this);
        rentPage.getHome().addActionListener(homeActionListener);
        rentPage.getHome().addActionListener(homeActionListener);
        rentPage.getSearch().addActionListener(searchForRentActionListener);
        rentPage.getProceedReserve().addActionListener(proceedReserveActionListener);
        rentPage.getSubmit().addActionListener(submitRentActionListener);
        rentPage.getCancel().addActionListener(cancelReserveActionListener);
        rentPage.getRentWithoutRes().addActionListener(rentWithoutResActionListener);
        rentPage.getProceedRent().addActionListener(getProceedRentActionListener);
        rentPage.getRent().addActionListener(rentingActionListener);
    }

    private ActionListener rentingActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Reservations reservation = rentPage.getReservation();
            Pair<Customers, CustomerRentInfo> customerRentInfoPair = rentPage.getCustomerInfo();
            ReservationRentReciept reservationRentReciept = null;
            if(reservation != null){
                reservationRentReciept = clerkWindowDelegate.rentWithReservation(customerRentInfoPair.getValue(),rentPage.getReservation());
            }
            else{
                reservationRentReciept = clerkWindowDelegate.rentWithoutReservation(customerRentInfoPair.getKey(),customerRentInfoPair.getValue());
            }
            if(reservationRentReciept != null){
                rentPage.showRent(reservationRentReciept);
            }
        }
    };

    private ActionListener getProceedRentActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            rentPage.customerInfoView();
        }
    };

    private ActionListener rentWithoutResActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            rentPage.showFilter();
        }
    };

    private ActionListener searchForRentActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FilterSearch filterSearch = rentPage.getFilterSearch();
            if(filterSearch == null){
                return;
            }
            if(rentPage.getCellphone() != null){
                Reservations reservation = clerkWindowDelegate.searchForReservation(Integer.parseInt(rentPage.getCellphone()), filterSearch);
                if(reservation == null){
                    rentPage.showErrorMessage("No such reservation");
                }
                rentPage.setReservation(reservation);
                rentPage.customerInfoView();
            }
            else{
                float cost= clerkWindowDelegate.costForRent(filterSearch);
                if(cost == -1){
                    rentPage.showNoAvailableVehicle();
                }
                else {
                    rentPage.showCost(cost);
                }
            }
        }
    };

    private ActionListener submitRentActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(rentPage.getCncpCb().getSelectedItem().toString().equals("Cellphone")){
                rentPage.showFilter(rentPage.getCncpText().getText());
            }
            else {
                Reservations reservation = clerkWindowDelegate.searchForReservation(Integer.parseInt(rentPage.getCncpText().getText()));
                if(reservation == null){
                    rentPage.showErrorMessage("No such reservation");
                }
                rentPage.setReservation(reservation);
                rentPage.customerInfoView();
            }

        }
    };

    private void setupreturnVehiclePage() {
        returnVehiclePage = new ReturnVehiclePage();
        returnVehiclePage.getHome().addActionListener(homeActionListener);
    }

    private void setupgenerateReportPage() {
        generateReportPage = new GenerateReportPage();
        generateReportPage.getHome().addActionListener(homeActionListener);

    }



    private ActionListener searchForReservationActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FilterSearch filterSearch = makeReservationPage.getFilterSearch();
            if(filterSearch == null){
                return;
            }
            else {
                float cost= customerWindowDelegate.costForReservation(filterSearch);
                if(cost == -1){
                    makeReservationPage.showNoAvailableVehicle();
                }
                else {
                    makeReservationPage.showCost(cost);
                }
            }

        }
    };

    private ActionListener submitActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Customers customer = makeReservationPage.getCustomerInfo();
            ReservationRentReciept reservationRentReciept = customerWindowDelegate.reserve(customer);
            makeReservationPage.showReservation(reservationRentReciept);

        }
    };

    private ActionListener proceedReserveActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            makeReservationPage.customerInfoView();
        }
    };

    private ActionListener cancelReserveActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            makeReservationPage.cancelReservation();
            switchContentPane(homePage);
            switchPageUi(makeReservationPage,homePage);
        }
    };

    private ActionListener clerkActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(clerkPage);
            switchPageUi(homePage,clerkPage);
        }
    };

    private ActionListener customerActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(customerPage);
            switchPageUi(homePage,customerPage);
        }
    };

    private ActionListener viewAvailableVehiclesActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(viewAvailableVehiclesPage);
            switchPageUi(customerPage,viewAvailableVehiclesPage);
        }
    };

    private ActionListener makeReservationActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(makeReservationPage);
            switchPageUi(customerPage,makeReservationPage);
        }
    };

    private ActionListener rentActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(rentPage);
            switchPageUi(clerkPage,rentPage);
        }
    };

    private ActionListener returnVehicleActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(returnVehiclePage);
            switchPageUi(clerkPage,returnVehiclePage);
        }
    };


    private ActionListener generateReportActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(generateReportPage);
            switchPageUi(clerkPage,generateReportPage);
        }
    };

    private ActionListener homeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(homePage);
            switchPageUi(clerkPage,homePage);
            switchPageUi(customerPage,homePage);
            switchPageUi(viewAvailableVehiclesPage,homePage);
            switchPageUi(makeReservationPage,homePage);
            switchPageUi(rentPage,homePage);
            switchPageUi(returnVehiclePage,homePage);
            switchPageUi(generateReportPage,homePage);
        }
    };

    private ActionListener searchActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FilterSearch filterSearch = viewAvailableVehiclesPage.getFilterSearch();
            if(filterSearch == null){
                return;
            }
            ArrayList<Vehicle> vehicles = customerWindowDelegate.search(filterSearch);
            viewAvailableVehiclesPage.setVehicles(vehicles);
            viewAvailableVehiclesPage.showNumberOfAV();
            setVisible(true);

//            ArrayList<Vehicle> vehicles = generate();
//            viewAvailableVehiclesPage.setVehicles(vehicles);
//            viewAvailableVehiclesPage.showNumberOfAV();
//            setVisible(true);
        }
    };

    private ArrayList<Vehicle> generate(){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        Vehicle v1 = new Vehicle(0,0,null,null,0,null,0,null,null,null,null);
        Vehicle v6 = new Vehicle(0,0,null,null,0,null,0,null,null,null,null);
        Vehicle v2 = new Vehicle(0,0,null,null,0,null,0,null,null,null,null);
        Vehicle v3 = new Vehicle(0,0,null,null,0,null,0,null,null,null,null);
        Vehicle v4 = new Vehicle(0,0,null,null,0,null,0,null,null,null,null);
        Vehicle v5 = new Vehicle(0,0,null,null,0,null,0,null,null,null,null);

        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);
        vehicles.add(v4);
        vehicles.add(v5);
        vehicles.add(v6);

        return vehicles;
    }

    private void switchContentPane(JPanel newContentPane){
        setContentPane(newContentPane);
        setVisible(true);
    }

    private void switchPageUi(PageUI oldp, PageUI newp){
        oldp.end();
        newp.start();
    }

}
