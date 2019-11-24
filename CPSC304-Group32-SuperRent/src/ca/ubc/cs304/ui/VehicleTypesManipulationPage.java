package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.QueryDelegate;
import ca.ubc.cs304.model.VehicleTypes;
import ca.ubc.cs304.utils.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VehicleTypesManipulationPage extends JPanel implements PageUI{

    private QueryDelegate<VehicleTypes> queryDelegate;

    private JFrame child;
    private JTable jTable;
    private JScrollPane jScrollPane;

    private JLabel vtnameView;
    private JLabel featuresView;
    private JLabel wrateView;
    private JLabel drateView;
    private JLabel hrateView;
    private JLabel wirateView;
    private JLabel dirateView;
    private JLabel hirateView;
    private JLabel krateView;

    private JTextField vtnameText;
    private JTextField featuresText;
    private JTextField wrateText;
    private JTextField drateText;
    private JTextField hrateText;
    private JTextField wirateText;
    private JTextField dirateText;
    private JTextField hirateText;
    private JTextField krateText;

    private JButton delete;
    private JButton insert;
    private JButton update;
    private JButton viewAll;

    public VehicleTypesManipulationPage() {
        setup();
    }

    private void setup(){

        vtnameView = new JLabel("vtname");
        featuresView = new JLabel("vtname");
        wrateView = new JLabel("vtname");
        drateView = new JLabel("vtname");
        hrateView = new JLabel("vtname");
        wirateView = new JLabel("vtname");
        dirateView = new JLabel("vtname");
        hirateView = new JLabel("vtname");
        krateView = new JLabel("vtname");

        vtnameText = new JTextField(10);
        featuresText = new JTextField(10);
        wrateText = new JTextField(10);
        drateText = new JTextField(10);
        hrateText = new JTextField(10);
        wirateText = new JTextField(10);
        dirateText = new JTextField(10);
        hirateText = new JTextField(10);
        krateText = new JTextField(10);

        delete = new JButton("Delete");
        insert = new JButton("Insert");
        update = new JButton("Update");
        viewAll = new JButton("View All");
        delete.addActionListener(deleteAL);
        insert.addActionListener(insertAL);
        update.addActionListener(updateAL);
        viewAll.addActionListener(viewAllAL);

        add(vtnameView);
        add(vtnameText);
        add(featuresView);
        add(featuresText);
        add(wrateView);
        add(wrateText);
        add(drateView);
        add(drateText);
        add(hrateView);
        add(hrateText);
        add(wirateView);
        add(wirateText);
        add(dirateView);
        add(dirateText);
        add(hirateView);
        add(hirateText);
        add(krateView);
        add(krateText);
    }

    public void showResult(ArrayList<VehicleTypes> vehicleTypes){
        child = new JFrame();
        child.setSize(600,400);
        child.setLocation(200,200);
        String [] cols = {"vtname", "features", "wrate", "drate", "hrate","wirate", "dirate", "hirate", "krate"};
        String [][] data = StringUtils.getVehicleTypesInArray(vehicleTypes);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        jScrollPane = new JScrollPane(jTable);

        child.add(jScrollPane);
        child.setVisible(true);
    }

    private VehicleTypes getVehicleTypes(){
        if(notValid()){
            ErrorHandling errorHandling = new ErrorHandling();
            errorHandling.showError("Please complete all the fields");
            return null;
        }
        VehicleTypes vehicleTypes = new VehicleTypes();
        vehicleTypes.setVtname(vtnameText.getName());
        vehicleTypes.setFeatures(featuresText.getName());
        vehicleTypes.setKrate(Float.parseFloat(krateText.getText()));
        vehicleTypes.setWrate(Float.parseFloat(wrateText.getText()));
        vehicleTypes.setDrate(Float.parseFloat(drateText.getText()));
        vehicleTypes.setHrate(Float.parseFloat(hrateText.getText()));
        vehicleTypes.setWirate(Float.parseFloat(wirateText.getText()));
        vehicleTypes.setDirate(Float.parseFloat(dirateText.getText()));
        vehicleTypes.setHirate(Float.parseFloat(hirateText.getText()));

        return vehicleTypes;
    }

    private boolean notValid(){
        return vtnameText.getText().equals("") || featuresText.getText().equals("") || wrateText.getText().equals("") || drateText.getText().equals("") || hrateText.getText().equals("") || wirateText.getText().equals("") || dirateText.getText().equals("") || hirateText.getText().equals("");
    }


    private ActionListener deleteAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            VehicleTypes vehicleTypes = getVehicleTypes();
            if(vehicleTypes != null) {
                queryDelegate.delete("VehicleTypes",vehicleTypes);
            }
        }
    };

    private ActionListener insertAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            VehicleTypes vehicleTypes = getVehicleTypes();
            if(vehicleTypes != null) {
                queryDelegate.insert("VehicleTypes",vehicleTypes);
            }
        }
    };

    private ActionListener updateAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            VehicleTypes vehicleTypes = getVehicleTypes();
            if(vehicleTypes != null) {
                queryDelegate.update("VehicleTypes",vehicleTypes);
            }
        }
    };

    private ActionListener viewAllAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            VehicleTypes vehicleTypes = getVehicleTypes();
            if(vehicleTypes != null) {
                queryDelegate.viewAll("VehicleTypes");
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
