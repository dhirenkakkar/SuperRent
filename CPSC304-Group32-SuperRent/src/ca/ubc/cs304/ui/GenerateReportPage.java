package ca.ubc.cs304.ui;

import javax.swing.*;

public class GenerateReportPage extends JPanel implements PageUI{
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


    @Override
    public void start() {

    }

    @Override
    public void end() {

    }
}
