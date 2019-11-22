package ca.ubc.cs304.ui;

import javax.swing.*;

public class GenerateReportPage extends JPanel {
    JButton home;

    public GenerateReportPage(){
        setupGenerateReportPage();
    }

    private void setupGenerateReportPage(){
        home = new JButton("Home");
        add(home);
    }

    public JButton getHome() {
        return home;
    }


}
