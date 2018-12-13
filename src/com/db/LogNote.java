package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class LogNote extends JFrame{

    private JPanel logNotePanel;
    private JTextArea noteTextArea;
    private JButton logNoteButton;
    private JTable resultTable;
    private JButton goBackButton;

    private JLabel name;
    private JLabel species;
    private JLabel age;
    private JLabel exhibit;
    private JLabel type;



    private String[] col_name = {"Staff Member", "Note", "Time"};


    private String[][] getNotes() {
        String[][] noNotes = {{"None","None","None"}}; // row data

        String[][] tableData;


        try {

            // StaffMember, Text, DateAndTime


            // Animal, Species

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT AnimalCare.StaffMember, AnimalCare.Text, AnimalCare.DateAndTime FROM AnimalCare WHERE " +
            "AnimalCare.Animal = \'"+Globals.staffAnimalName+ "\' AND AnimalCare.Species = \'" +Globals.staffAnimalSpecies+ "\' ");

            ResultSet rs = stmt.executeQuery();


            stmt = Globals.con.prepareStatement(
                    " SELECT AnimalCare.StaffMember, AnimalCare.Text, AnimalCare.DateAndTime FROM AnimalCare WHERE " +
                            "AnimalCare.Animal = \'"+Globals.staffAnimalName+ "\' AND AnimalCare.Species = \'" +Globals.staffAnimalSpecies+ "\' ");

            ResultSet rowNum = stmt.executeQuery();
            int rows = 0;
            while(rowNum.next()) {
                //System.out.println(rowNum.getString(1));
                rows++;
            } // end while
            //System.out.println(i);

            if (!rs.next()) {
                //System.out.println("NO SHOWS");

                return noNotes;

            } else {
                tableData = new String[rows][col_name.length];

                // cycle rows
                int i;
                for (i = 0; i < rows; i++){

                    // cycle columns
                    for (int j = 1; j <= col_name.length; j++) {

                        try{tableData[i][j-1] = rs.getString(j);} catch(Exception ex) { // just added
                            tableData[i][j-1] = "Unassigned";
                        }

                        //tableData[i][j-1] = rs.getString(j);

                    } // end columns loop
                    rs.next();
                } // end rows loop

                return tableData;

            }



        } catch(Exception ex) {
            //System.err.println(ex);
        }



        return noNotes;
    }

    public LogNote() {


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        name.setText(Globals.staffAnimalName);
        species.setText(Globals.staffAnimalSpecies);
        age.setText(Globals.staffAnimalAge);
        exhibit.setText(Globals.staffAnimalExhibit);
        type.setText(Globals.staffAnimalType);

        setTitle("Atlanta Zoo: Staff Log Note");
        setSize(500,500);

        String[][] data = getNotes();//{{"name","note","time"}}; // row data

        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        //resultTable.setCellSelectionEnabled(true);
        resultTable.setColumnSelectionAllowed(false);
        resultTable.setRowSelectionAllowed(true);

        add(logNotePanel);
        pack();

        // go back button
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                SearchAnimals sf = new SearchAnimals();
                sf.setVisible(true);

                setVisible(false);  //hide and close current window

                dispose();
            }
        }); // end go back button action


        // log note button
        logNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //SearchAnimals sf = new SearchAnimals();
                //sf.setVisible(true);

                try {

                    // store note

                    // Animal, Species, StaffMember, DateAndTime

                    String noteText = noteTextArea.getText();



                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    //System.out.println(dtf.format(now));

                    String currTimeStamp = dtf.format(now);//"0000-00-00 00:00:00";
                    System.out.println(currTimeStamp);





                    PreparedStatement stmt = Globals.con.prepareStatement(
                            " INSERT INTO AnimalCare VALUES(\'"+Globals.staffAnimalName +"\' , \'" + Globals.staffAnimalSpecies
                                    + "\' , \'" +Globals.activeUser + "\' , \'" + currTimeStamp + "\' , \'" +noteText+ "\') "  );
                    int exResult = stmt.executeUpdate();


                    System.out.println(exResult);




                } catch(Exception ex) {System.err.println(ex);}

                LogNote lfNew = new LogNote();
                lfNew.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end go back button action




    } // end constructor

} // end class
