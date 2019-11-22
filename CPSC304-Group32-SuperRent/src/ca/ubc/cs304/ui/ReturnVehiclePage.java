package ca.ubc.cs304.ui;

import javax.swing.*;

public class ReturnVehiclePage extends JPanel {
    JButton home;

    public ReturnVehiclePage(){
        setupReturnVehiclePage();
    }

    private void setupReturnVehiclePage(){
        home = new JButton("Home");
        add(home);
    }

    public JButton getHome() {
        return home;
    }
}
