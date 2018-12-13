package com.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddShow extends JFrame {
    private JTextField Name;
    private JComboBox Staff;
    private JTextField Date;
    private JTextField Time;
    private JButton addShowButton;
    private JComboBox Exhibit;
    private JPanel AddShowPanel;
    private JButton goBackButton;
    private JComboBox exhibitNameComboBox;

    private String[] getExhibitNames() {
        String[] nameList;
        String[] noNames = {"None"};

        // List<String> list = new ArrayList<String>();


        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT DISTINCT Exhibit.Name FROM Exhibit");

            ResultSet rs = stmt.executeQuery();


            stmt = Globals.con.prepareStatement(
                    " SELECT DISTINCT Exhibit.Name FROM Exhibit");

            ResultSet rowNum = stmt.executeQuery();
            int rows = 0;
            while(rowNum.next()) {
                //System.out.println(rowNum.getString(1));
                rows++;
            } // end while
            //System.out.println(i);

            if (!rs.next()) {
                //System.out.println("NO SHOWS");

                return noNames;

            } else {


                nameList = new String[rows];

                int i;
                for (i = 0; i < rows; i++) {

                    nameList[i] = rs.getString(1);
                    rs.next();

                } // end for loop

//                // loop to print names
//                for (i=0; i < nameList.length; i++) {
//                    System.out.println(nameList[i]);
//                }

                return nameList;
            } // end else




        } catch(Exception ex) {System.err.println(ex);}

        return noNames;
    }


    private String[] getStaffNames() {
        String[] nameList;
        String[] noNames = {"None"};

        // List<String> list = new ArrayList<String>();


        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT DISTINCT User.Username FROM User WHERE User.UserType = \'staff\' ");

            ResultSet rs = stmt.executeQuery();


            stmt = Globals.con.prepareStatement(
                    " SELECT DISTINCT User.Username FROM User WHERE User.UserType = \'staff\' ");

            ResultSet rowNum = stmt.executeQuery();
            int rows = 0;
            while(rowNum.next()) {
                //System.out.println(rowNum.getString(1));
                rows++;
            } // end while
            //System.out.println(i);

            if (!rs.next()) {
                //System.out.println("NO SHOWS");

                return noNames;

            } else {


                nameList = new String[rows];

                int i;
                for (i = 0; i < rows; i++) {

                    nameList[i] = rs.getString(1);
                    rs.next();

                } // end for loop

//                // loop to print names
//                for (i=0; i < nameList.length; i++) {
//                    System.out.println(nameList[i]);
//                }

                return nameList;
            } // end else




        } catch(Exception ex) {System.err.println(ex);}

        return noNames;
    }

    public AddShow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(AddShowPanel);
        setSize(500, 500);
        setTitle("Atlanta Zoo: Administrator Add Show");

        String[] exhibitNames = getExhibitNames();
        //System.out.println(exhibitNames);
        DefaultComboBoxModel exhibitOptions = new DefaultComboBoxModel(exhibitNames);
        exhibitNameComboBox.setModel(exhibitOptions);

        String[] staffNames = getStaffNames();
        //System.out.println(exhibitNames);
        DefaultComboBoxModel staffOptions = new DefaultComboBoxModel(staffNames);
        Staff.setModel(staffOptions);

        // goBack button
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AdministratorFunctionality af = new AdministratorFunctionality();
                af.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end button action

        try{
//            Connection connection = DriverManager.getConnection(
//                    "jdbc:myDriver:DatabaseName",
//                    "123",
//                    "456");
            Statement statement = Globals.con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT Name FROM Exhibit");
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            String[] exhibitList = new String[rowCount];
            int i = 0;
            while(rs.next()){
                exhibitList[i++] = rs.getString("Name");
            }
            Exhibit = new JComboBox(exhibitList);

            rs = statement.executeQuery("SELECT DISTINCT Name FROM Staff");
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
            String[] staffList = new String[rowCount];
            int j = 0;
            while(rs.next()){
                staffList[i++] = rs.getString("Name");
            }
            Staff = new JComboBox(staffList);

        } catch (Exception el){
            System.out.println(el);
        }

        addShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String showName = Name.getText();
                //String exhibit = (String) Exhibit.getSelectedItem();
                //String staff = (String) Staff.getSelectedItem();
                String date = Date.getText();
                String time = Time.getText();
                try{
//                    Connection connection = DriverManager.getConnection(
//                            "jdbc:myDriver:DatabaseName",
//                            "123",
//                            "456");
                    Statement statement = Globals.con.createStatement();

                    // Name, DateAndTime, Host, Exhibit
                    int res = statement.executeUpdate("INSERT INTO Shows VALUES(\'"+showName + "\', \'"+ date +" "+ time + "\',\'" + Staff.getSelectedItem()
                            + "\',\'" + exhibitNameComboBox.getSelectedItem() + "')");





                    if(res == 1) {
                        // Go back to Admin Functionality
                        AdministratorFunctionality af = new AdministratorFunctionality();
                        af.setVisible(true);
                        setVisible(false);  //hide and close current window

                        dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(AddShowPanel, "Failed to add show. Please make sure the show's name, date and time are correct");
                    }
                }catch (Exception el){
                    System.out.println(el);
                }
            }
        });
    }
}
