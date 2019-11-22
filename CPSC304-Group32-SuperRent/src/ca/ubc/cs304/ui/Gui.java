package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.ClerkWindowDelegate;
import ca.ubc.cs304.delegates.CustomerWindowDelegate;
import ca.ubc.cs304.model.Customers;
import ca.ubc.cs304.model.FilterSearch;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.Vehicle;

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
        rentPage = new RentPage();
        rentPage.getHome().addActionListener(homeActionListener);
    }

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
            Reservations reservation = customerWindowDelegate.reserve(customer);
            makeReservationPage.showReservation(reservation);

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
        }
    };

    private ActionListener clerkActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(clerkPage);
        }
    };

    private ActionListener customerActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(customerPage);
        }
    };

    private ActionListener viewAvailableVehiclesActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(viewAvailableVehiclesPage);
        }
    };

    private ActionListener makeReservationActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(makeReservationPage);
        }
    };

    private ActionListener rentActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(rentPage);
        }
    };

    private ActionListener returnVehicleActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(returnVehiclePage);
        }
    };


    private ActionListener generateReportActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(generateReportPage);
        }
    };

    private ActionListener homeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            switchContentPane(homePage);
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
        makeReservationPage.cleanUp();
        viewAvailableVehiclesPage.cleanUp();
        setContentPane(newContentPane);
        setVisible(true);
    }

}
