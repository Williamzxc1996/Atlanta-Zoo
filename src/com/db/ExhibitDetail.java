package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class ExhibitDetail extends JFrame {
    private JTextField ExhibitName;
    private JTextField Size;
    private JTextField NumAnimal;
    private JTextField WaterFeat;
    private JButton Log;
    private JButton Detail;
    private JTable resultTable;
    private JPanel ExhibitDetail;
    private JButton Order_name;
    private String[] col_name = {"Name", "Species"};
    private int click_count_name = 0;


    public ExhibitDetail(String exhibit) {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Atlanta Zoo: Visitor Exhibit Detail");
        setSize(500,500);
        add(ExhibitDetail);
        pack();
        String sql = "SELECT Exhibit.Name as Name, Exhibit.Size as Size, Exhibit.WaterFeature as WF, number_Animal.Number as Number From Exhibit Join number_Animal ON Exhibit.Name = number_Animal.Exhibit WHERE Exhibit.Name = \'" + exhibit + "\'";
        try {
            System.out.println(sql);
            PreparedStatement stmt = Globals.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            //create new DefaultTable Module using ResultSet, and reset JTable
            rs.next();
            //Object[] row = {rs.getString("Name"), rs.getString("WaterFeature"), rs.getInt("Size")};
            ExhibitName.setText(exhibit);
            ExhibitName.setEditable(false);
            Size.setText(String.valueOf(rs.getInt("Size")));
            Size.setEditable(false);
            NumAnimal.setText(String.valueOf(rs.getInt("Number")));
            NumAnimal.setEditable(false);
            WaterFeat.setText(rs.getString("WF"));
            WaterFeat.setEditable(false);
        } catch(Exception e4) {
            System.err.println(e4);
        }

        sql = "SELECT Name, SPECIES FROM Animal WHERE Exhibit = \'" + exhibit + "\'";
        try{
            System.out.println(sql);
            PreparedStatement stmt = Globals.con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel(col_name,0);
            while (rs.next()){
                //construct row
                Object[] row = {rs.getString("Name"), rs.getString("SPECIES")};
                tableModel.addRow(row);
            }
            resultTable.setModel(tableModel);
            resultTable.setRowSelectionAllowed(true);
        } catch (Exception e4){
            System.err.println(e4);
        }
        Log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date now = new Date();
                String time = sdf.format(now);

                String sql = "INSERT INTO VisitExhibit VALUES (\'"+ exhibit +"\',\'"+ Globals.activeUser +"\',\'"+ time +"\');";
                try{
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(resultTable,"Log visit to exhibit successful!");
                }catch(Exception e4){
                    System.err.println(e4);
                    JOptionPane.showMessageDialog(resultTable,"Log visit to exhibit failed!");
                }
            }
        });

        Detail.addActionListener(new ActionListener() {
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
                    JOptionPane.showMessageDialog(ExhibitDetail,"Animal Detail\n"+"Animal Name:"+animal_name+"\n"
                            +"Animal Species:"+animal_species+"\n"
                            +"Animal Age:"+animal_age+"\n"
                            +"Animal Exhibit:"+animal_exhibit+"\n"
                            +"Animal Type:"+animal_type);
                }catch (Exception e4){
                    System.err.println(e4);
                }
            }
        });
        Order_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click_count_name++;
                String sql = "";
                if(click_count_name % 2 != 0) sql = "SELECT Name, SPECIES FROM Animal WHERE Exhibit = \'" + exhibit + "\' ORDER BY Name Desc";
                else sql = "SELECT Name, SPECIES FROM Animal WHERE Exhibit = \'" + exhibit + "\' ORDER BY Name Asc";

                try{
                    System.out.println(sql);
                    PreparedStatement stmt = Globals.con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    DefaultTableModel tableModel = new DefaultTableModel(col_name,0);
                    while (rs.next()){
                        //construct row
                        Object[] row = {rs.getString("Name"), rs.getString("SPECIES")};
                        tableModel.addRow(row);
                    }
                    resultTable.setModel(tableModel);
                    resultTable.setRowSelectionAllowed(true);
                } catch (Exception e4){
                    System.err.println(e4);
                }
            }
        });
    }
}
