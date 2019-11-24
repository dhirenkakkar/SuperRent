package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.QueryDelegate;
import ca.ubc.cs304.model.Rentals;
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

public class RentalsManipulationPage extends JPanel implements PageUI{

    private QueryDelegate<Rentals> queryDelegate;

    private JFrame child;
    private JTable jTable;
    private JScrollPane jScrollPane;

    private JLabel ridView;
    private JLabel vidView;
    private JLabel cellphoneView;
    private JLabel fromDateTimeView;
    private JLabel toDateTimeView;
    private JLabel odometerView;
    private JLabel cardNameView;
    private JLabel cardNoView;
    private JLabel expDateView;
    private JLabel confNoView;

    private JTextField ridText;
    private JTextField vidText;
    private JTextField cellphoneText;
    private DateTimePicker fromDateTimeText;
    private DateTimePicker toDateTimeText;
    private JTextField odometerText;
    private JTextField cardNameText;
    private JTextField cardNoText;
    private DatePicker expDateText;
    private JTextField confNoText;

    private JButton delete;
    private JButton insert;
    private JButton update;
    private JButton viewAll;

    public RentalsManipulationPage() {
        setup();
    }

    private void setup(){

        ridView = new JLabel("rid");
        vidView = new JLabel("rid");
        cellphoneView = new JLabel("rid");
        fromDateTimeView = new JLabel("rid");
        toDateTimeView = new JLabel("rid");
        odometerView = new JLabel("rid");
        cardNameView = new JLabel("rid");
        cardNoView = new JLabel("rid");
        expDateView = new JLabel("rid");
        confNoView = new JLabel("rid");

        ridText = new JIntegerTextField();
        vidText = new JIntegerTextField();
        cellphoneText = new JIntegerTextField();
        fromDateTimeText = new DateTimePicker();
        toDateTimeText = new DateTimePicker();
        odometerText = new JTextField(10);
        cardNameText = new JTextField(10);
        cardNoText = new JIntegerTextField();
        expDateText = new DatePicker();
        confNoText = new JIntegerTextField();

        delete = new JButton("Delete");
        insert = new JButton("Insert");
        update = new JButton("Update");
        viewAll = new JButton("View All");
        delete.addActionListener(deleteAL);
        insert.addActionListener(insertAL);
        update.addActionListener(updateAL);
        viewAll.addActionListener(viewAllAL);

        add(ridView);
        add(ridText);
        add(vidView);
        add(vidText);
        add(cellphoneView);
        add(cellphoneText);
        add(fromDateTimeView);
        add(fromDateTimeText);
        add(toDateTimeView);
        add(toDateTimeText);
        add(odometerView);
        add(odometerText);
        add(cardNameView);
        add(cardNameText);
        add(cardNoView);
        add(cardNoText);
        add(expDateView);
        add(expDateText);
        add(confNoView);
        add(confNoText);



    }

    public void showResult(ArrayList<Rentals> rentals){
        child = new JFrame();
        child.setSize(600,400);
        child.setLocation(200,200);
        String [] cols = {"rid", "vid", "cellphone", "fromDateTime", "toDateTime","odometer", "cardName", "cardNo", "expDate","confNo"};
        String [][] data = StringUtils.getRentalsInArray(rentals);
        jTable = new JTable(data, cols);
        jTable.setFocusable(false);
        jScrollPane = new JScrollPane(jTable);

        child.add(jScrollPane);
        child.setVisible(true);
    }

    private Rentals getVehicleTypes(){
        if(notValid()){
            ErrorHandling errorHandling = new ErrorHandling();
            errorHandling.showError("Please complete all the fields");
            return null;
        }
        Rentals rentals = new Rentals();
        rentals.setRid(Integer.parseInt(ridText.getText()));
        rentals.setVid(Integer.parseInt(vidText.getText()));
        rentals.setCellphone(Integer.parseInt(cellphoneText.getText()));
        rentals.setFromDateTime(StringUtils.formatString(fromDateTimeText.toString()));
        rentals.setToDateTime(StringUtils.formatString(toDateTimeText.toString()));
        rentals.setOdometer(Float.parseFloat(odometerText.getText()));
        rentals.setCardName(cardNameText.getText());
        rentals.setCardNo(Integer.parseInt(cardNoText.getText()));
        rentals.setExpDate(StringUtils.formatString(expDateText.toString()+"T00:00"));
        rentals.setConfNo(Integer.parseInt(confNoText.getText()));
        return rentals;
    }

    private boolean notValid(){
        return ridText.getText().equals("") || vidText.getText().equals("") || cellphoneText.getText().equals("") || fromDateTimeText.toString().equals("") || toDateTimeText.toString().equals("") || cardNameText.getText().equals("") || odometerText.getText().equals("") || cardNoText.getText().equals("") || expDateText.getText().equals("") || confNoText.getText().equals("");
    }


    private ActionListener deleteAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Rentals rentals = getVehicleTypes();
            if(rentals != null) {
                queryDelegate.delete("Rentals",rentals);
            }
        }
    };

    private ActionListener insertAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Rentals rentals = getVehicleTypes();
            if(rentals != null) {
                queryDelegate.insert("Rentals",rentals);
            }
        }
    };

    private ActionListener updateAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Rentals rentals = getVehicleTypes();
            if(rentals != null) {
                queryDelegate.update("Rentals",rentals);
            }
        }
    };

    private ActionListener viewAllAL = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            queryDelegate.viewAll("Rentals");

        }
    };


    @Override
    public void start() {

    }

    @Override
    public void end() {

    }
}
