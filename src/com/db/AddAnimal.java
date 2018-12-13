package com.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddAnimal extends JFrame{
    private JPanel AddAnimalPanel;
    private JTextField Name;
    private JComboBox Exhibit;
    private JComboBox Type;
    private JTextField Species;
    private JTextField Age;
    private JButton addAnimalButton;

    public AddAnimal(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(AddAnimalPanel);
        setSize(500, 500);
        setTitle("Atlanta Zoo: Add Animal");

        try{
            Connection connection = DriverManager.getConnection(
                    "https://academic-mysql.cc.gatech.edu/phpmyadmin",
                    "cs4400_group18",
                    "R7mNv3pS");
            Statement statement = connection.createStatement();
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

            rs = statement.executeQuery("SELECT DISTINCT Type FROM Animal");
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
            String[] typeList = new String[rowCount];
            int j = 0;
            while(rs.next()){
                typeList[i++] = rs.getString("Name");
            }
            Type = new JComboBox(typeList);

        } catch (Exception el){
            System.out.println(el);
        }

        addAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String animalName = Name.getText();
                String exhibit = (String) Exhibit.getSelectedItem();
                String type = (String) Type.getSelectedItem();
                String species = Species.getText();
                String age = Age.getText();
                try{
                    Connection connection = DriverManager.getConnection(
                            "jdbc:myDriver:DatabaseName",
                            "123",
                            "456");
                    Statement statement = connection.createStatement();
                    int res = statement.executeUpdate("INSERT INTO User VALUES('"+ animalName + "','"+ exhibit + "','" + type + "','" + species + "','" + age + "')");
                    if(res == 1) {
                        // Go back to Admin Functionality
                    }
                    else{
                        JOptionPane.showMessageDialog(AddAnimalPanel, "Failed to add show. Please make sure the animal's name, species and age are correct");
                    }
                }catch (Exception el){
                    System.out.println(el);
                }
            }
        });
    }
}
