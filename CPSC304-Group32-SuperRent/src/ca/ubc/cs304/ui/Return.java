package ca.ubc.cs304.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Return {
    public void Return(){
        JFrame jf = new JFrame("Car Return");
        JTextField tf = new JTextField();
        JLabel vid = new JLabel("vid :");
        vid.setBounds(50,40,50,50);
        tf.setBounds(80,40,150,50);
        JButton ret = new JButton("Return");
        ret.setBounds(100,100,100,100);
        jf.add(vid);
        jf.add(tf);
        jf.add(ret);
        jf.setLayout(null);
        jf.setSize(300,300);
        jf.setVisible(true);
        ret.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.setVisible(false);
                int vid = Integer.parseInt(tf.getText());
                ReturnsByClerk r = new ReturnsByClerk();
                try {
                    r.ReturnsByClerk(vid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }

    public static void main(String[] args) {
        Return k = new Return();
        k.Return();
    }
}
