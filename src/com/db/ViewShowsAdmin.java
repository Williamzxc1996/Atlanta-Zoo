package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.util.*;

// accessible from Administrator Functionality page
public class ViewShowsAdmin extends JFrame {

    private JPanel viewShowsAdminPanel;
    private JButton goBackButton;
    private JTable resultTable;
    private JButton searchButton;
    private JTextField nameTextField;
    private JTextField dateTextField;
    private JComboBox exhibitDropDown;
    private JButton removeShowButton;

    private String[] col_name = {"Name", "Exhibit","Date"};

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

    private String[][] getAllShows() {

        String[][] noShowData = {{"None","None","None"}};

        // name, exhibit, date

        String [][] tableData;

        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    "SELECT Shows.Name, Shows.Exhibit, Shows.DateAndTime FROM Shows ");
            ResultSet rs = stmt.executeQuery();

            stmt = Globals.con.prepareStatement(
                    "SELECT Shows.Name, Shows.Exhibit, Shows.DateAndTime FROM Shows ");

            ResultSet rowNum = stmt.executeQuery();
            int rows = 0;
            while(rowNum.next()) {
                //System.out.println(rowNum.getString(1));
                rows++;
            }
            //System.out.println(i);

            if (!rs.next()) {
                //System.out.println("NO SHOWS");

                return noShowData;

            } else {
                // [row][col]
                tableData = new String[rows][col_name.length];

                // cycle rows
                int i;
                for (i = 0; i < rows; i++){

                    // cycle columns
                    for (int j = 1; j <= col_name.length; j++) {
                        try{tableData[i][j-1] = rs.getString(j);} catch(Exception ex) {
                            tableData[i][j-1] = "Unassigned";
                        }
                        //tableData[i][j-1] = rs.getString(j);
                    } // end columns loop
                    rs.next();
                } // end rows loop

                return tableData;

            }


        } catch(Exception ex) {System.err.println(ex);}



        return noShowData;
    } // end getAllShows()

    public ViewShowsAdmin() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[] exhibitNames = getExhibitNames();
        //System.out.println(exhibitNames);
        DefaultComboBoxModel exhibitOptions = new DefaultComboBoxModel(exhibitNames);
        exhibitDropDown.setModel(exhibitOptions);



        String[][] data = getAllShows();//{{"name","exhibit","date"}}; // row data
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        //resultTable.setCellSelectionEnabled(true);
        resultTable.setColumnSelectionAllowed(false);
        resultTable.setRowSelectionAllowed(true);




        add(viewShowsAdminPanel);
        setTitle("Atlanta Zoo: Administrator View Shows");
        setSize(500,500);

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

        // goBack button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameText = nameTextField.getText();
                System.out.println(nameText);


                String dateText = dateTextField.getText();
                System.out.println(dateText);

                System.out.println(exhibitDropDown.getSelectedItem());

                // get data from databse
                try {

                    // "SELECT User.Username, User.Email \" + \" FROM User WHERE User.UserType = \'visitor\'"

                    PreparedStatement stmt = Globals.con.prepareStatement(
                            " SELECT Shows.Name, Shows.Exhibit, Shows.DateAndTime FROM Shows WHERE "
                    + "  Shows.Name = \'"+nameText+"\' AND Shows.DateAndTime = \'" +dateText+ "\' AND Shows.Exhibit = \'"
                                    + exhibitDropDown.getSelectedItem() +"\'" );

                    ResultSet rs = stmt.executeQuery();




                    stmt = Globals.con.prepareStatement(
                            " SELECT Shows.Name, Shows.Exhibit, Shows.DateAndTime FROM Shows WHERE "
                                    + "  Shows.Name = \'"+nameText+"\' AND Shows.DateAndTime = \'" +dateText+ "\' AND Shows.Exhibit = \'"
                                    + exhibitDropDown.getSelectedItem() +"\'" );

                    ResultSet rowNum = stmt.executeQuery();

                    int rows = 0;
                    while(rowNum.next()) {
                        //System.out.println(rowNum.getString(1));
                        rows++;
                    }

                    System.out.println(rows);

                    // Name, Exhibit, Date

                    if (!rs.next()) {
                        //System.out.println("NO SHOWS");

                        String[][] noShowData = {{"None","None","None"}};
                        df.setDataVector(noShowData,col_name);
                        resultTable.revalidate();

                    } else {
                        // [row][col]
                        String [][] tableData = new String[rows][col_name.length];

                        // cycle rows
                        int i;
                        for (i = 0; i < rows; i++){

                            // cycle columns
                            for (int j = 1; j <= col_name.length; j++) {
                                tableData[i][j-1] = rs.getString(j);
                            } // end columns loop
                            rs.next();
                        } // end rows loop

                        //return tableData;

                        // put tableData where it belongs
                        df.setDataVector(tableData,col_name);
                        resultTable.revalidate();

                    }





                } catch(Exception ex) {System.err.println(ex);}

                // update result table

                //df.fireTableDataChanged();
                resultTable.revalidate();


                if (true) {JOptionPane.showMessageDialog(viewShowsAdminPanel,
                        "Wrong Date Format!");}
                //AdministratorFunctionality af = new AdministratorFunctionality();
                //af.setVisible(true);
                //setVisible(false);  //hide and close current window
                //dispose();
            }
        }); // end button action

        // removeAnimalNutton
        removeShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = resultTable.getSelectedRow();

                // to delete animal, need: name and species

                String nameToRemove = resultTable.getModel().getValueAt(selectedRow, 0).toString();
                String exhibitToRemove = resultTable.getModel().getValueAt(selectedRow, 1).toString();

                String dateToRemove;

                try {

                    dateToRemove = resultTable.getModel().getValueAt(selectedRow, 2).toString();

                } catch(Exception ex) {

                    dateToRemove = "0000-00-00 00:00:00";
                }
                //String dateToRemove = resultTable.getModel().getValueAt(selectedRow, 2).toString();

//                System.out.println("\n\nRemove button pressed...");
//                System.out.println(nameToRemove);
//                System.out.println(exhibitToRemove);
//                System.out.println(dateToRemove);

                if (dateToRemove.equals("Unassigned") || dateToRemove.equals("")) {
                    dateToRemove = "0000-00-00 00:00:00";
                }

//                PreparedStatement stmt = Globals.con.prepareStatement(
//                        " " +nameToRemove+ "" +speciesToRemove+ "");
//
//                ResultSet rs = stmt.executeQuery();

                try {

                    // name and time

                    PreparedStatement stmt = Globals.con.prepareStatement(
                            " DELETE FROM Shows WHERE Shows.Name = \'"  +nameToRemove+ "\' AND Shows.DateAndTime = \'"+dateToRemove+"\' ");
                    int exResult = stmt.executeUpdate();


                    System.out.println(exResult);

                    // change data
                    //String[][] newdata = getAllShows();
                    //df.setDataVector(newdata,col_name);
                    //resultTable.revalidate();

                    // or redraw table
                    ViewShowsAdmin vsNew = new ViewShowsAdmin();
                    vsNew.setVisible(true);

                    setVisible(false);  //hide and close current window
                    dispose();


                } catch(Exception ex) {System.err.println(ex);}




            }
        }); // end button action


    } // end constructor

} // end class
