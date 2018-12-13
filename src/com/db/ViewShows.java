package com.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

// accessible from staff functionality page
public class ViewShows extends JFrame {

    private JPanel viewShowsPanel;
    private JButton goBackButton;

    private JTable showsTable;
    private JScrollPane tableContainer;

    private String[] col_name = {"Name", "Time", "Exhibit"};

    private String[][] populateTable(){
        //String[][] tableData = {{"Show_Name","Show_Time","Exhibit_Name"}};
        String [][] tableData;
        String[][] noShowData = {{"None","None","None"}};

        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    "SELECT Shows.Name, Shows.DateAndTime, Shows.Exhibit FROM Shows " +
                            " WHERE Shows.Host = \'" + Globals.activeUser + "\'");
            ResultSet rs = stmt.executeQuery();

            stmt = Globals.con.prepareStatement(
                    "SELECT Shows.Name, Shows.DateAndTime, Shows.Exhibit   " + " FROM Shows " +
                            " WHERE Shows.Host = \'" + Globals.activeUser + "\'");

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
                        tableData[i][j-1] = rs.getString(j);
                    } // end columns loop
                    rs.next();
                } // end rows loop

                return tableData;

            }


        } catch(Exception ex) {System.err.println(ex);}


        //tableData = {{"None","None","None"}};
        //return tableData;
        return noShowData;
    } // end populateTable()


    public ViewShows() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //viewShowsPanel.setBackground(Color.lightGray);

        String[][] data = populateTable();//{{"Show_Name","Show_Time","Exhibit_Name"}}; //this is the row

        DefaultTableModel df = new DefaultTableModel(data, col_name);
        showsTable.setModel(df);
        showsTable.setCellSelectionEnabled(true);
        pack();

        add(viewShowsPanel);
        setTitle("Atlanta Zoo: Staff View Shows");
        setSize(500,500);



        // go back button
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                StaffFunctionality sf = new StaffFunctionality();
                sf.setVisible(true);

                setVisible(false);  //hide and close current window

                dispose();
            }
        }); // end go back button action

    } // end ViewShows constructor




} // end ViewShows class
