package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

// accessible from visitor functionality page
public class ViewShowHistory extends JFrame {

    private JPanel viewShowHistoryPanel;
    private JButton goBackButton;
    private JTextField nameTextField;
    private JTextField timeTextField;
    private JComboBox exhibitNameDropDown;
    private JTable resultTable;
    private JButton searchButton;

    private String[] col_name = {"Name","Time", "Exhibit"};

    public ViewShowHistory(){

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[][] data = {{"none","none","none"}}; // row data
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        resultTable.setCellSelectionEnabled(true);


        add(viewShowHistoryPanel);
        setTitle("Atlanta Zoo: Visitor View Show History");
        setSize(500,500);

        String sql = "SELECT DISTINCT VisitShow.ShowName, VisitShow.DateAndTime, Shows.Exhibit FROM Shows JOIN VisitShow ON (Shows.Name = VisitShow.ShowName) " +
                "WHERE VisitShow.Visitor = \'" + Globals.activeUser + "\';";
        try{
            PreparedStatement stmt = Globals.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel(col_name, 0);
            while(rs.next()){
                Object[] row = {rs.getString("ShowName"), rs.getString("DateandTime"), rs.getString("Exhibit")};
                tableModel.addRow(row);
            }
            resultTable.setModel(tableModel);

        } catch(Exception ex){
            System.err.println(ex);
        }

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


        // Search button actioin
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String showName = nameTextField.getText();
                String dateNTime = timeTextField.getText();

                String exhibitName = (String) exhibitNameDropDown.getSelectedItem();
                // Default SQL
                String sql = "SELECT DISTINCT VisitShow.ShowName, VisitShow.DateAndTime, Shows.Exhibit FROM Shows JOIN VisitShow ON (Shows.Name = VisitShow.ShowName) " +
                        "WHERE VisitShow.Visitor = \'" + Globals.activeUser + "\'";
                // Add exhibit name filter
                if(!showName.equals("")){
                    sql += " AND ShowName = \'" + showName + "\'";
                }
                // Add time filter
                if(!dateNTime.equals("")){
//                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//                    dateNTime = simpleDateFormat.format(dateNTime);
                    sql += " AND DateAndTime = \'" + dateNTime + "\'";
                }
                // Add exhibit filter
                if(!exhibitName.equals("")){
                    sql += " AND Exhibit = \'" + exhibitName + "\'";
                }

                sql += ";";
                try{
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel tableModel = new DefaultTableModel(col_name, 0);
                    while(rs.next()){
                        Object[] row = {rs.getString("ShowName"), rs.getString("DateandTime"), rs.getString("Exhibit")};
                        tableModel.addRow(row);
                    }
                    resultTable.setModel(tableModel);

                } catch(Exception ex){
                    System.err.println(ex);
                }

            }
        });

    } // end ViewShowHistory constructor


} // end ViewShowHistory class
