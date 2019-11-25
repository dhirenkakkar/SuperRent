package ca.ubc.cs304.ui;

import ca.ubc.cs304.database.ReturnsByClerk;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ReturnVehiclePage extends JPanel implements PageUI{
    JButton home;
    Connection connection;

    public ReturnVehiclePage(Connection connection){
        this.connection = connection;
        setupReturnVehiclePage();
    }

    private void setupReturnVehiclePage(){
        home = new JButton("Home");
        home.setBounds(350,200,100,100);
        add(home);


        JTextField tf = new JTextField();
        JLabel vid = new JLabel("confNo :");
        vid.setBounds(50,40,50,50);
        tf.setBounds(100,40,150,50);
        JButton ret = new JButton("Return");
        ret.setBounds(100,100,100,100);
        add(vid);
        add(tf);
        add(ret);
        setLayout(null);
        setSize(300,300);
        setVisible(true);
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int vid = Integer.parseInt(tf.getText());
                ReturnsByClerk r = new ReturnsByClerk(connection);
                try {
                    r.ReturnsByClerk(vid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
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
