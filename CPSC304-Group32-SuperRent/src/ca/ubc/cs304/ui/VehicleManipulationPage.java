package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.QueryDelegate;
import ca.ubc.cs304.model.Vehicle;
import ca.ubc.cs304.model.VehicleTypes;
import ca.ubc.cs304.utils.StringUtils;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;

public class VehicleManipulationPage extends JPanel implements PageUI{

        private QueryDelegate<Vehicle> queryDelegate;

        private JFrame child;
        private JTable jTable;
        private JScrollPane jScrollPane;

        private JLabel vidView;
        private JLabel vlicenseView;
        private JLabel makeView;
        private JLabel modelView;
        private JLabel yearView;
        private JLabel colorView;
        private JLabel odometerView;
        private JLabel statusView;
        private JLabel vtnameView;
        private JLabel locationView;
        private JLabel cityView;

        private JTextField vidText;
        private JTextField vlicenseText;
        private com.github.lgooddatepicker.components.DatePicker makeText;
        private JTextField modelText;
        private JTextField yearText;
        private JTextField colorText;
        private JTextField odometerText;
        private JTextField statusText;
        private JTextField vtnameText;
        private JTextField locationText;
        private JTextField cityText;

        private JButton delete;
        private JButton insert;
        private JButton update;
        private JButton viewAll;

        public VehicleManipulationPage() {
            setup();
        }

        private void setup(){

            vidView = new JLabel("vid");
            vlicenseView = new JLabel("vid");
            makeView = new JLabel("vid");
            modelView = new JLabel("vid");
            yearView = new JLabel("vid");
            colorView = new JLabel("vid");
            odometerView = new JLabel("vid");
            statusView = new JLabel("vid");
            vtnameView = new JLabel("vid");
            locationView = new JLabel("vid");
            cityView = new JLabel("vid");

            vidText = new JIntegerTextField();
            vlicenseText = new JTextField();
            makeText = new DatePicker();
            modelText = new JTextField();
            yearText = new JIntegerTextField();
            colorText = new JTextField();
            odometerText = new JTextField();
            statusText = new JTextField();
            vtnameText = new JTextField();
            locationText = new JTextField();
            cityText = new JTextField();

            delete = new JButton("Delete");
            insert = new JButton("Insert");
            update = new JButton("Update");
            viewAll = new JButton("View All");
            delete.addActionListener(deleteAL);
            insert.addActionListener(insertAL);
            update.addActionListener(updateAL);
            viewAll.addActionListener(viewAllAL);

            add(vidView);
            add(vidText);
            add(vlicenseView);
            add(vlicenseText);
            add(makeView);
            add(makeText);
            add(modelView);
            add(modelText);
            add(yearView);
            add(yearText);
            add(colorView);
            add(colorText);
            add(odometerView);
            add(odometerText);
            add(statusView);
            add(statusText);
            add(vtnameView);
            add(vtnameText);
            add(locationView);
            add(locationText);
            add(cityView);
            add(cityText);

        }

        public void showResult(ArrayList<Vehicle> vehicles){
            child = new JFrame();
            child.setSize(600,400);
            child.setLocation(200,200);
            String [] cols = {"vid", "vlicense", "make", "model", "year","color", "odometer", "status", "vtname", "location", "city"};

            String [][] data = StringUtils.getVehiclesInArray(vehicles);
            jTable = new JTable(data, cols);
            jTable.setFocusable(false);
            jScrollPane = new JScrollPane(jTable);

            child.add(jScrollPane);
            child.setVisible(true);
        }

        private Vehicle getVehicleTypes(){
            if(notValid()){
                ErrorHandling errorHandling = new ErrorHandling();
                errorHandling.showError("Please complete all the fields");
                return null;
            }
            Vehicle vehicle = new Vehicle();
            vehicle.setVid(Integer.parseInt(vidText.getText()));
            vehicle.setVlicense(Integer.parseInt(vlicenseText.getText()));
            vehicle.setMake(StringUtils.formatString(makeText.toString()+"T00:00"));
            vehicle.setModel(modelText.getText());
            vehicle.setYear(Integer.parseInt(yearText.getText()));
            vehicle.setColor(colorText.getText());
            vehicle.setOdometer(Float.parseFloat(odometerText.getText()));
            vehicle.setStatus(statusText.getText());
            vehicle.setVtname(vtnameText.getText());
            vehicle.setLocation(locationText.getText());
            vehicle.setCity(cityText.getText());

            return vehicle;
        }

        private boolean notValid(){
            return vidText.getText().equals("") || vlicenseText.getText().equals("") || makeText.getText().equals("") || modelText.getText().equals("") || yearText.getText().equals("") || colorText.getText().equals("") || odometerText.getText().equals("") || statusText.getText().equals("") || vtnameText.getText().equals("") || locationText.getText().equals("") || cityText.getText().equals("");
        }


        private ActionListener deleteAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Vehicle vehicle = getVehicleTypes();
                if(vehicle != null) {
                    queryDelegate.delete("Vehicle",vehicle);
                }
            }
        };

        private ActionListener insertAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Vehicle vehicle = getVehicleTypes();
                if(vehicle != null) {
                    queryDelegate.insert("Vehicle",vehicle);
                }
            }
        };

        private ActionListener updateAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Vehicle vehicle = getVehicleTypes();
                if(vehicle != null) {
                    queryDelegate.update("Vehicle",vehicle);
                }
            }
        };

        private ActionListener viewAllAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Vehicle vehicle = getVehicleTypes();
                if(vehicle != null) {
                    queryDelegate.viewAll("Vehicle");
                }
            }
        };


        @Override
        public void start() {

        }

        @Override
        public void end() {

        }
}

