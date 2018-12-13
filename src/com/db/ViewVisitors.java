package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// accessible from Administrator Functionality page
public class ViewVisitors extends JFrame {

    private JPanel viewVisitorsPanel;
    private JButton goBackButton;
    private JTable resultTable;
    private JButton deleteVisitorButton;

    private String[] col_name = {"Username", "Email"};

    private String[][] populateTable() {
        String[][] noVisitors = {{"None","None"}};
        String [][] tableData;

        try {
            // SELECT User.Username, User.Email " + " FROM User

            PreparedStatement stmt = Globals.con.prepareStatement(
                    "SELECT User.Username, User.Email \" + \" FROM User WHERE User.UserType = \'visitor\'");
            ResultSet rs = stmt.executeQuery();

            stmt = Globals.con.prepareStatement(
                    "SELECT User.Username, User.Email \" + \" FROM User WHERE User.UserType = \'visitor\'");

            ResultSet rowNum = stmt.executeQuery();
            int rows = 0;
            while(rowNum.next()) {
                //System.out.println(rowNum.getString(1));
                rows++;
            }
            //System.out.println(i);


            if (!rs.next()) {
                //System.out.println("NO SHOWS");

                return noVisitors;

            } else {
                // [row][col]
                tableData = new String[rows][col_name.length];

                // cycle rows
                int i;
                for (i = 0; i < rows; i++){

                    // cycle columns
                    for (int j = 1; j <= col_name.length; j++) {
                        tableData[i][j-1] = rs.getString(j);
                    } // end columns loop
                    rs.next();
                } // end rows loop

                return tableData;

            }


        } catch(Exception ex) {System.err.println(ex);}


        return noVisitors;
    }

    public ViewVisitors() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[][] data = populateTable(); // row data
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        //resultTable.setCellSelectionEnabled(true);
        resultTable.setColumnSelectionAllowed(false);
        resultTable.setRowSelectionAllowed(true);


        add(viewVisitorsPanel);
        setTitle("Atlanta Zoo: Administrator View Visitors");
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

        // deleteVisitorButton
        deleteVisitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = resultTable.getSelectedRow();

                // to delete animal, need: name and species

                String nameToRemove = resultTable.getModel().getValueAt(selectedRow, 0).toString();
                //String emailToRemove = resultTable.getModel().getValueAt(selectedRow, 1).toString();

                //System.out.println("\n\nRemove button pressed...");
                //System.out.println(nameToRemove);
                //System.out.println(emailToRemove);

                try {

                    PreparedStatement stmt = Globals.con.prepareStatement(
                            " DELETE FROM User WHERE User.Username = \'"  +nameToRemove+ "\' ");
                    int exResult = stmt.executeUpdate();


                    System.out.println(exResult);

                    String[][] newdata = populateTable();
                    df.setDataVector(newdata,col_name);
                    resultTable.revalidate();

                    // update table


                } catch(Exception ex) {System.err.println(ex);}



//                PreparedStatement stmt = Globals.con.prepareStatement(
//                        " " +nameToRemove+ "" +speciesToRemove+ "");
//
//                ResultSet rs = stmt.executeQuery();




            }
        }); // end button action


    } // end constructor


} // end class
