package com.db;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// accessible from visitor functionality page
public class SearchForAnimals extends JFrame {

    private JPanel searchForAnimalsPanel;

    private JButton goBackButton;
    private JTable resultTable;
    private JButton searchButton;
    private JTextField nameTextField;
    private JTextField speciesTextField;
    private JComboBox exhibitNameDropDown;
    private JComboBox animalTypeDropDown;
    private JComboBox ageMinDropDown;
    private JComboBox ageMaxDropDown;
    private JButton detailButton;

    private String[] col_name = {"Name", "Species", "Exhibit", "Age", "Type"};

    public SearchForAnimals() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[][] data = {{"name","species","exhibit","age","type"}}; // row data
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        resultTable.setCellSelectionEnabled(true);


        add(searchForAnimalsPanel);
        setTitle("Atlanta Zoo: Visitor Search for Animals");
        setSize(500,500);



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


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String Species = speciesTextField.getText();
                String type = (String) animalTypeDropDown.getSelectedItem();
                String exhibit = (String) exhibitNameDropDown.getSelectedItem();
                String Max_age = (String) ageMaxDropDown.getSelectedItem();
                String Min_age = (String) ageMinDropDown.getSelectedItem();
                String sql = "";
                boolean changed = false;

                if (Integer.parseInt(Min_age) > Integer.parseInt(Max_age)) {
                    JOptionPane.showMessageDialog(searchForAnimalsPanel,
                            "Min cannot be greater than Max!");
                } else if ((Integer.parseInt(Min_age) <= Integer.parseInt(Max_age)) && ((Integer.parseInt(Min_age) != 0 && Integer.parseInt(Max_age) != 0))) {
                     sql += "SELECT Name, SPECIES, Exhibit, Age, Type FROM Animal ";
                    sql += " Where Age BETWEEN " + Min_age + " and " + Max_age;
                    changed = true;
                } else {
                    sql += "SELECT Name, SPECIES, Exhibit, Age, Type FROM Animal";
                }

                if (!name.equals("")) {
                    if (changed) {
                        sql += " and Name = \'" + name + "\'";
                    } else {
                        sql += " Where Name = \'" + name + "\'";
                        changed = true;
                    }
                }
                if (!exhibit.equals("")) {
                    if (changed) {
                        sql += " and Exhibit = \'" + exhibit + "\'";
                    } else {
                        sql += " Where Exhibit = \'" + exhibit + "\'";
                        changed = true;
                    }
                }
                if (!Species.equals("")) {
                    if (changed) {
                        sql += " and SPECIES = \'" + Species + "\'";
                    } else {
                        sql += " Where SPECIES = \'" + Species + "\'";
                        changed = true;
                    }
                }
                if (!type.equals("")) {
                    if (changed) {
                        sql += " and Type = \'" + type + "\'";
                    } else {
                        sql += " Where Type = \'" + type + "\'";
                    }
                }
                try {
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    DefaultTableModel tableModel = new DefaultTableModel(col_name,0);
                    while (rs.next()){
                        //construct row
                        Object[] row = {rs.getString("Name"), rs.getString("SPECIES"), rs.getString("Exhibit"), rs.getString("Age"), rs.getString("Type")};
                        tableModel.addRow(row);
                    }
                    resultTable.setModel(tableModel);
                    resultTable.setRowSelectionAllowed(true);
                } catch (Exception e4){
                    System.err.println(e4);
                }
            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = resultTable.getSelectedRow();
                String animal_name = (String) resultTable.getModel().getValueAt(row,0);
                String sql = "SELECT Name, SPECIES, Exhibit, Age, Type FROM Animal WHERE Name = \'" + animal_name + "\';";
                try{
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    String animal_species = rs.getString("SPECIES");
                    String animal_exhibit = rs.getString("Exhibit");
                    String animal_age = String.valueOf(rs.getInt("Age"));
                    String animal_type = rs.getString("Type");
                    JOptionPane.showMessageDialog(searchForAnimalsPanel,"Animal Detail\n"+"Animal Name:"+animal_name+"\n"
                            +"Animal Species:"+animal_species+"\n"
                            +"Animal Age:"+animal_age+"\n"
                            +"Animal Exhibit:"+animal_exhibit+"\n"
                            +"Animal Type:"+animal_type);
                }catch (Exception e4){
                    System.err.println(e4);
                }
            }
        });
    } // end SearchForAnimals constructor

} // end SearchForAnimals class
