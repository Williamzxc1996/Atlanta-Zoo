package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

// accessible from visitor functionality page
public class SearchShows extends JFrame {

    private JPanel searchShowsPanel;
    private JButton goBackButton;
    private JTextField showName_textfield;
    private JTextField dateTextField;
    private JComboBox exhibitNameDropDown;
    private JButton searchButton;
    private JButton logVisitButton;
    private JTable resultTable;

    private String[] col_name = {"Name", "Exhibit", "Date"};

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


    public SearchShows() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        String[][] data = {{"name","exhibit","date"}}; // row data
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        resultTable.setCellSelectionEnabled(true);


        add(searchShowsPanel);
        setTitle("Atlanta Zoo: Visitor Search Shows");
        setSize(500,500);

        //default data
        String sql = "SELECT Name, Exhibit, DateAndTime From Shows;";
        try {
//                        Connection con = DriverManager.getConnection(
//                                "https://academic-mysql.cc.gatech.edu/phpmyadmin",
//                                "cs4400_group18",
//                                "R7mNv3pS");
            System.out.println(sql);
            PreparedStatement stmt = Globals.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            //create new DefaultTable Module using ResultSet, and reset JTable
            DefaultTableModel tableModel = new DefaultTableModel(col_name,0);
            while (rs.next()){
                //construct row
                Object[] row = {rs.getString("Name"),rs.getString("Exhibit"), rs.getString("DateAndTime")};
                tableModel.addRow(row);
            }
            resultTable.setModel(tableModel);
            resultTable.setRowSelectionAllowed(true);
        } catch (Exception e4){
            System.err.println(e4);
        }

        String[] exhibitNames = getExhibitNames();
        DefaultComboBoxModel exhibitOptions = new DefaultComboBoxModel(exhibitNames);
        exhibitNameDropDown.setModel(exhibitOptions);

        // go back button
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisitorFunctionality vf = new VisitorFunctionality();
                vf.setVisible(true);

                setVisible(false);  //hide and close current window

                dispose();
            }
        }); // end go back button action

        // sjava.sql.SQLException: Value '0000-00-00 00:00:00' can not be represented as java.sql.Timestampearch button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Show_name = showName_textfield.getText();
                String Date_and_time = dateTextField.getText();
                System.out.println(Date_and_time);
                String Exhibit = (String)exhibitNameDropDown.getSelectedItem();     //maybe N/A
                String sql = "SELECT Name, Exhibit, DateAndTime From Shows";
                //show everything first before search
                boolean isWhere = false;
                if(!Show_name.equals("")){
                    if(!isWhere){
                        sql += " WHERE Name = \'" + Show_name + "\'";
                        isWhere = true;
                    }else{
                        sql += " AND Name = \'" + Show_name + "\'";
                    }
                }
                if(!(Date_and_time.equals(""))){
                    if(!isWhere){
                        sql += " WHERE DateAndTime = \'" + Date_and_time + "\'";
                        isWhere = false;
                    }else{
                        sql += " AND DateAndTime = \'" + Date_and_time + "\'";
                    }
                }
                if(!Exhibit.equals("N/A")){
                    if(!isWhere){
                        sql += " WHERE Exhibit = \'" + Exhibit + "\'";
                        isWhere = false;
                    }else{
                        sql += " AND Exhibit = \'" + Exhibit + "\'";
                    }
                }
                sql += ";";
                try {
//                        Connection con = DriverManager.getConnection(
//                                "https://academic-mysql.cc.gatech.edu/phpmyadmin",
//                                "cs4400_group18",
//                                "R7mNv3pS");
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    //create new DefaultTable Module using ResultSet, and reset JTable
                    DefaultTableModel tableModel = new DefaultTableModel(col_name,0);
                    while (rs.next()){
                        //construct row
                        Object[] row = {rs.getString("Name"),rs.getString("Exhibit"), rs.getString("DateAndTime")};
                        tableModel.addRow(row);
                    }
                    resultTable.setModel(tableModel);
                    resultTable.setRowSelectionAllowed(true);
                } catch (Exception e4){
                    System.err.println(e4);
                }
            }
        }); // end search button action

        //log visit button
        logVisitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = resultTable.getSelectedRow();
                String show_name = (String) resultTable.getModel().getValueAt(row,0);
                String exhibit_name = (String) resultTable.getModel().getValueAt(row,1);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date now = new Date();
                String time = sdf.format(now);

                String visitor_name = Globals.activeUser;
                String sql = "INSERT INTO VisitShow VALUES (\'"+show_name+"\',\'"+time+"\',\'"+visitor_name+"\');";

                //log a visit to the show
                try{
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(searchShowsPanel,
                            "Log visit to show successful!");
                }catch(Exception e4){
                    System.err.println(e4);
                    JOptionPane.showMessageDialog(searchShowsPanel,
                            "Log visit to show failed!");
                }

                sql = "INSERT INTO VisitExhibit VALUES (\'"+exhibit_name+"\',\'"+visitor_name+"\',\'"+time+"\');";

                //log a visit to the exhibit
                try{
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(searchShowsPanel,
                            "Log visit to exhibit successful!");
                }catch(Exception e4){
                    System.err.println(e4);
                    JOptionPane.showMessageDialog(searchShowsPanel,
                            "Log visit to exhibit failed!");
                }
            }
        }); //end log visit button

    } // end searchShows constructor

} // end SearchShows class
