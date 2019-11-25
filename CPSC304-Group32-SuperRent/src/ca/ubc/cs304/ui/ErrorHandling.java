package ca.ubc.cs304.ui;

import javax.swing.*;

public class ErrorHandling extends JFrame {
    private JButton ok;
    private JLabel err;
    public ErrorHandling(){
        ok = new JButton("OK");
        err = new JLabel("");
    }
    public void showError(String error){
        err.setText(error);
        add(ok);
        add(err);
        setVisible(true);
    }
}
