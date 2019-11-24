package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.QueryDelegate;
import ca.ubc.cs304.model.Reservations;
import ca.ubc.cs304.model.VehicleTypes;
import ca.ubc.cs304.utils.StringUtils;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.zinternaltools.JIntegerTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ReservationsManipulationPage extends JPanel implements PageUI{

    private QueryDelegate<Reservations> queryDelegate;

    private JFrame child;
    private JTable jTable;
    private JScrollPane jScrollPane;

    private JLabel confNoView;
    private JLabel vtnameView;
    private JLabel vidView;
    private JLabel cellphoneView;
    private JLabel fromDateTimeView;
    private JLabel toDateTimeView;

    private JTextField confNoText;
    private JTextField vtnameText;
    private JTextField vidText;
    private JTextField cellphoneText;
    private DateTimePicker fromDateTimeText;
    private DateTimePicker toDateTimeText;


    private JButton delete;
    private JButton insert;
    private JButton update;
    private JButton viewAll;

    public ReservationsManipulationPage() {
        setup();
    }

    private void setup(){

        confNoView = new JLabel("confNo");
        vtnameView = new JLabel("confNo");
        vidView = new JLabel("confNo");
        cellphoneView = new JLabel("confNo");
        fromDateTimeView = new JLabel("confNo");
        toDateTimeView = new JLabel("confNo");

        confNoText = new JIntegerTextField();
        vtnameText = new JTextField(10);
        vidText = new JIntegerTextField();
        cellphoneText = new JIntegerTextField();
        fromDateTimeText = new DateTimePicker();
        toDateTimeText = new DateTimePicker();

        delete = new JButton("Delete");
        insert = new JButton("Insert");
        update = new JButton("Update");
        viewAll = new JButton("View All");
        delete.addActionListener(deleteAL);
        insert.addActionListener(insertAL);
        update.addActionListener(updateAL);
        viewAll.addActionListener(viewAllAL);

        add(confNoView);
        add(confNoText);
        add(vtnameView);
        add(vtnameText);
        add(vidView);
        add(vidText);
        add(cellphoneView);
        add(cellphoneText);
        add(fromDateTimeView);
        add(fromDateTimeText);
        add(toDateTimeView);
        add(toDateTimeText);

    }

    public void showResult(ArrayList<Reservations> reservations){
        child = new JFrame();
        child.setSize(600,400);
        child.setLocation(200,200);
        String [] cols = {"confNo", "vtname", "vid", "cellphone", "fromDateTime","toDateTime"};
        String [][] data = StringUtils.getReservationsInArray(reservations);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        jScrollPane = new JScrollPane(jTable);

        child.add(jScrollPane);
        child.setVisible(true);
    }

    private Reservations getVehicleTypes(){
        if(notValid()){
            ErrorHandling errorHandling = new ErrorHandling();
            errorHandling.showError("Please complete all the fields");
            return null;
        }
        Reservations reservations = new Reservations();
        reservations.setConfNo(Integer.parseInt(confNoText.getText()));
        reservations.setVtname(vtnameText.getText());
        reservations.setVid(Integer.parseInt(vidText.getText()));
        reservations.setCellphone(Integer.parseInt(cellphoneText.getText()));
        reservations.setFromDateTime(StringUtils.formatString(fromDateTimeText.toString()));
        reservations.setToDateTime(StringUtils.formatString(toDateTimeText.toString()));

        return reservations;
    }

    private boolean notValid(){
        return vtnameText.getText().equals("") || confNoText.getText().equals("") || vidText.getText().equals("") || cellphoneText.getText().equals("") || fromDateTimeText.toString().equals("") || toDateTimeText.toString().equals("");
    }


    private ActionListener deleteAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Reservations reservations = getVehicleTypes();
            if(reservations != null) {
                queryDelegate.delete("Reservations",reservations);
            }
        }
    };

    private ActionListener insertAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Reservations reservations = getVehicleTypes();
            if(reservations != null) {
                queryDelegate.insert("Reservations",reservations);
            }
        }
    };

    private ActionListener updateAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Reservations reservations = getVehicleTypes();
            if(reservations != null) {
                queryDelegate.update("Reservations",reservations);
            }
        }
    };

    private ActionListener viewAllAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            queryDelegate.viewAll("Reservations");
        }
    };


    @Override
    public void start() {

    }

    @Override
    public void end() {

    }

}
