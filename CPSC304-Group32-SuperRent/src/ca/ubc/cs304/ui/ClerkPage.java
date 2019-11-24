package ca.ubc.cs304.ui;

import javax.swing.*;

public class ClerkPage extends JPanel implements PageUI{

    private  JButton rent,returnVehicle, generateReport, home;

    public ClerkPage(){
        setupClerkPage();
    }

    private void setupClerkPage() {


        rent = new JButton("Rent");
        returnVehicle = new JButton("Return Vehicle");
        generateReport = new JButton("Generate Report");
        home = new JButton("Home");

        add(rent);
        add(generateReport);
        add(returnVehicle);
        add(home);
    }

    public JButton getRent() {
        return rent;
    }

    public JButton getReturnVehicle() {
        return returnVehicle;
    }

    public JButton getGenerateReport() {
        return generateReport;
    }

    public JButton getHome() {
        return home;
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }
}
