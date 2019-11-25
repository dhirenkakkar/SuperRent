package ca.ubc.cs304.ui;

import javax.swing.*;

public class ReturnVehiclePage extends JPanel implements PageUI{
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

    @Override
    public void start() {

    }

    @Override
    public void end() {

    }
}
