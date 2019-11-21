package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame{

    static GraphicsConfiguration gc;
    private static JPanel homePage;
    private static JPanel clerkPage;
    private static JPanel customerPage;
    private static JPanel viewAvailableVehiclesPage;
    private static JPanel makeReservationPage;
    private static JPanel rentPage;
    private static JPanel returnVehiclePage;
    private static JPanel generateReportPage;

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

    private void setup(){
        setTitle("Super Rent");
        setSize(600,400);
        setLocation(200,200);

        setupClerkPage();
        setupCustomerPage();
        setupgenerateReportPage();
        setupHomePage();
        setupmakeReservationPage();
        setupRentPage();
        setupreturnVehiclePage();
        setupviewAvailableVehiclesPage();

        setContentPane(homePage);
        setVisible(true);
    }


    private void setupHomePage() {
        JButton customer, clerk;
        customer = new JButton("Customer");
        clerk = new JButton("Clerk");

        homePage = new JPanel();
        homePage.add(customer);
        homePage.add(clerk);
        customer.addActionListener(customerActionListener);
        clerk.addActionListener(clerkActionListener);
    }
    private void setupClerkPage() {
        JButton rent,returnVehicle, generateReport, home;

        rent = new JButton("Rent");
        returnVehicle = new JButton("Return Vehicle");
        generateReport = new JButton("Generate Report");
        home = new JButton("Home");

        clerkPage = new JPanel();
        clerkPage.add(rent);
        clerkPage.add(generateReport);
        clerkPage.add(returnVehicle);
        clerkPage.add(home);

        rent.addActionListener(rentActionListener);
        generateReport.addActionListener(generateReportActionListener);
        returnVehicle.addActionListener(returnVehicleActionListener);
        home.addActionListener(homeActionListener);
    }
    private void setupCustomerPage() {
        JButton viewAvailableVehicles,makeReservation,home;
        viewAvailableVehicles = new JButton("View Available Vehicles");
        makeReservation = new JButton("Make Reservation");
        home = new JButton("Home");

        customerPage = new JPanel();
        customerPage.add(makeReservation);
        customerPage.add(viewAvailableVehicles);
        customerPage.add(home);
        makeReservation.addActionListener(makeReservationActionListener);
        viewAvailableVehicles.addActionListener(viewAvailableVehiclesActionListener);
        home.addActionListener(homeActionListener);
    }
    private void setupviewAvailableVehiclesPage() {
        viewAvailableVehiclesPage = new JPanel();
        JButton search = new JButton("search");

        String[] vehicleTypeChoices = { "Economy","Compact", "Mid-size","Standard","Fullsize","SUV", "Truck"};
        setupComboBox("Vehicle Type", vehicleTypeChoices, viewAvailableVehiclesPage);

        String[] cityChoices = { "Economy","Compact", "Mid-size","Standard","Fullsize","SUV", "Truck"};
        setupComboBox("City", cityChoices, viewAvailableVehiclesPage);

        String[] locationChoices = { "Economy","Compact", "Mid-size","Standard","Fullsize","SUV", "Truck"};
        setupComboBox("Location", locationChoices, viewAvailableVehiclesPage);

        viewAvailableVehiclesPage.add(search);
        search.addActionListener(searchActionListener);

    }

    private void setupComboBox(String label, String[] choices, JPanel page){
        JLabel jLabel = new JLabel(label);
        jLabel.setVisible(true);
        page.add(jLabel);
        JComboBox<String> cb = new JComboBox<String>(choices);
        cb.setVisible(true);
        page.add(cb);
    }

    private void setupmakeReservationPage() {
        makeReservationPage = new JPanel();
    }
    private void setupRentPage() {
        rentPage = new JPanel();
    }

    private void setupreturnVehiclePage() {
        returnVehiclePage = new JPanel();
    }

    private void setupgenerateReportPage() {
        generateReportPage = new JPanel();
    }


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
            // to do
        }
    };



    private void switchContentPane(JPanel newContentPane){
        setContentPane(newContentPane);
        setVisible(true);
    }

}
