package com.db;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

// accessible from visitor functionality page
public class ViewExhibitHistory extends JFrame {

    private JPanel viewExhibitHistoryPanel;
    private JButton goBackButton;
    private JTextField nameTextField;
    private JTextField timeTextField;
    private JButton searchButton;
    private JSpinner minNumVisits_DropDown;
    private JSpinner maxNumVisits_DropDown;
    private JTable resultTable;

    private String[] col_name = {"Name", "Time", "Number of Visits"};

    public ViewExhibitHistory() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[][] data = {{"none","none","none"}}; // row data
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        resultTable.setCellSelectionEnabled(true);


        add(viewExhibitHistoryPanel);
        setTitle("Atlanta Zoo: Visitor View Exhibit History");
        setSize(500,500);

        // Pull our default history (without filtering
        String sql = "SELECT DISTINCT VisitExhibit.Exhibit, VisitExhibit.DateAndTime, visit_times.numVisit FROM visit_times JOIN VisitExhibit "
                + "ON visit_times.Exhibit = VisitExhibit.Exhibit WHERE VisitExhibit.Visitor = \'" + Globals.activeUser + "\';";
        try{
            PreparedStatement stmt = Globals.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel(col_name, 0);
            while(rs.next()){
                Object[] row = {rs.getString("Exhibit"), rs.getString("DateandTime"), rs.getInt("numVisit")};
                tableModel.addRow(row);
            }
            resultTable.setModel(tableModel);

        } catch(Exception ex){
            System.err.println(ex);
        }


        // Go back button
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisitorFunctionality vf = new VisitorFunctionality();
                vf.setVisible(true);

                setVisible(false);  //hide and close current window

                dispose();
            }
        }); // end go back button action

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String exhibitName = nameTextField.getText();
                String dateNTime = timeTextField.getText();
                int minVisits = (int)minNumVisits_DropDown.getValue();
                int maxVisits = (int)maxNumVisits_DropDown.getValue();
                // Default SQL
                String sql = "SELECT DISTINCT VisitExhibit.Exhibit, VisitExhibit.DateAndTime, visit_times.numVisit FROM visit_times JOIN VisitExhibit "
                        + "ON visit_times.Exhibit = VisitExhibit.Exhibit WHERE VisitExhibit.Visitor = \'" + Globals.activeUser + "\'";
                // Add exhibit name filter
                if(!exhibitName.equals("")){
                    sql += " AND VisitExhibit.Exhibit = \'" + exhibitName + "\'";
                }
                // Add time filter
                if(!dateNTime.equals("")){
                    sql += " AND VisitExhibit.DateAndTime = \'" + dateNTime + "\'";
                }
                // Add number of visits filter
                if(maxVisits != 0 ||  minVisits != 0){
                    if(maxVisits == minVisits){
                        sql += " AND visit_times.numVisit = " + maxVisits;
                    }
                    else{
                        sql += " AND visit_times.numVisit BETWEEN " + minVisits + " AND " + maxVisits;
                    }
                }
                System.out.println(sql);
                sql += ";";
                try{
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel tableModel = new DefaultTableModel(col_name, 0);
                    while(rs.next()){
                        Object[] row = {rs.getString("Exhibit"), rs.getString("DateandTime"), rs.getInt("numVisit")};
                        tableModel.addRow(row);
                    }
                    resultTable.setModel(tableModel);

                } catch(Exception ex){
                    System.err.println(ex);
                }

            }
        });

    } // end ViewExhibitHistory constructor

} // end ViewExhibitHistory class
