package com.db;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//import javax.swing.event.ListSelectionListener;

// accessible from Administrator Functionality page
public class ViewAnimals extends JFrame {
    private JPanel viewAnimalsPanel;
    private JButton goBackButton;
    private JComboBox exhibitDropDown;
    private JButton searchButton;
    private JTextField nameTextField;
    private JTextField speciesTextField;
    private JComboBox animalTypeDropDown;
    private JComboBox ageMinDropDown;
    private JComboBox ageMaxDropDown;
    private JTable resultTable;

    private JButton removeAnimalButton;

    private String[] col_name = {"Name", "Species","Exhibit","Age","Type"};

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

    private String[] getAnimalTypes() {
        String[] nameList;
        String[] noNames = {"None"};

        // List<String> list = new ArrayList<String>();


        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT DISTINCT Animal.Type FROM Animal");

            ResultSet rs = stmt.executeQuery();


            stmt = Globals.con.prepareStatement(
                    " SELECT DISTINCT Animal.Type FROM Animal");

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
                //int j = 0;
                for (i = 0; i < rows; i++) {



                    //boolean contains = Arrays.stream(nameList).anyMatch(rs.getString(1)::equals);
                    nameList[i] = rs.getString(1);
//                    if (!contains) {
//
//                        nameList[j] = rs.getString(1);
//                        j+=1;
//                    }

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


    private String[][] getAllAnimals() {
        String[][] noAnimalData = {{"None","None","None","None","None"}};

        String[][] tableData;


        try {

            // "Name", "SPECIES","Exhibit","Age","Type"

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT Animal.Name, Animal.SPECIES, Animal.Exhibit, Animal.Age, Animal.Type FROM Animal ");

            ResultSet rs = stmt.executeQuery();


            stmt = Globals.con.prepareStatement(
                    " SELECT Animal.Name, Animal.SPECIES, Animal.Exhibit, Animal.Age, Animal.Type FROM Animal ");

            ResultSet rowNum = stmt.executeQuery();
            int rows = 0;
            while(rowNum.next()) {
                //System.out.println(rowNum.getString(1));
                rows++;
            } // end while
            //System.out.println(i);

            if (!rs.next()) {
                //System.out.println("NO SHOWS");

                return noAnimalData;

            } else {
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

        return noAnimalData;
    }

    private int getMinAge() {

        int minAge = 999999;

        int currAge;

        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT Animal.Age FROM Animal ");

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                //System.out.println(rowNum.getString(1));
                //rs.getString(1);

                currAge = Integer.parseInt(rs.getString(1));

                if (currAge < minAge) {
                    minAge = currAge;
                }

            } // end while

            return minAge;


        }  catch(Exception ex) {System.err.println(ex);}


        return minAge;


    } // end getMinAge


    private int getMaxAge() {

        int maxAge = 0;

        int currAge;

        try {

            PreparedStatement stmt = Globals.con.prepareStatement(
                    " SELECT Animal.Age FROM Animal ");

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                //System.out.println(rowNum.getString(1));
                //rs.getString(1);

                currAge = Integer.parseInt(rs.getString(1));

                if (currAge > maxAge) {
                    maxAge = currAge;
                }

            } // end while

            return maxAge;


        }  catch(Exception ex) {System.err.println(ex);}


        return maxAge;


    } // end getMaxAge


    private String[] getAgeRange(int hi, int lo) {

        int strLen = hi - lo + 1;

        String[] range = new String[strLen];

        int i;
        for(i=0;i < strLen; i++) {
            range[i] = Integer.toString(i+lo);
        }


        return range;


    } // end getAgeRange()


    public ViewAnimals() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        String[] exhibitNames = getExhibitNames();
        //System.out.println(exhibitNames);
        DefaultComboBoxModel exhibitOptions = new DefaultComboBoxModel(exhibitNames);
        exhibitDropDown.setModel(exhibitOptions);

        String[] animalTypes = getAnimalTypes();
        //System.out.println(exhibitNames);
        DefaultComboBoxModel typeOptions = new DefaultComboBoxModel(animalTypes);
        animalTypeDropDown.setModel(typeOptions);


        String[] ageRange = getAgeRange(getMaxAge(),getMinAge());
        DefaultComboBoxModel minAgeOptions = new DefaultComboBoxModel(ageRange);
        ageMinDropDown.setModel(minAgeOptions);


        DefaultComboBoxModel maxAgeOptions = new DefaultComboBoxModel(ageRange);
        ageMaxDropDown.setModel(maxAgeOptions);






        String[][] data = getAllAnimals();
        DefaultTableModel df = new DefaultTableModel(data, col_name);
        resultTable.setModel(df);
        //resultTable.setCellSelectionEnabled(true);
        resultTable.setColumnSelectionAllowed(false);
        resultTable.setRowSelectionAllowed(true);

        // javax.swing.event.ListSelectionListener



        // to delete animal, need: name and species


        add(viewAnimalsPanel);
        setTitle("Atlanta Zoo: Administrator View Animals");
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

        // search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nameText = nameTextField.getText();
                //System.out.println(nameText);


                String speciesText = speciesTextField.getText();
                //System.out.println(speciesText);



                //System.out.println(exhibitDropDown.getSelectedItem());

                if (Integer.parseInt(String.valueOf(ageMaxDropDown.getSelectedItem())) < Integer.parseInt(String.valueOf(ageMinDropDown.getSelectedItem())) )
                {JOptionPane.showMessageDialog(viewAnimalsPanel,
                        "Error: Max age must be larger than min age!");
                } else {

                // animalTypeDropDown.getSelectedItem;

                // ageMinDropDown
                // ageMaxDropDown

                // get data from databse
                try {

                    // "SELECT User.Username, User.Email \" + \" FROM User WHERE User.UserType = \'visitor\'"

                    // minAge, maxAge

                    // Integer.toString()

                    PreparedStatement stmt = Globals.con.prepareStatement(
                            " SELECT Animal.Name, Animal.SPECIES, Animal.Exhibit, Animal.Age, Animal.Type FROM Animal WHERE "
                                    + "  Animal.Name = \'"+nameText+"\' AND Animal.SPECIES = \'" +speciesText+ "\' AND Animal.Exhibit = \'"
                                    + exhibitDropDown.getSelectedItem() +"\' AND Animal.Type = \'" +animalTypeDropDown.getSelectedItem()+
                                    "\' AND Animal.Age BETWEEN \'"+ageMinDropDown.getSelectedItem()+"\' AND \'" +ageMaxDropDown.getSelectedItem()+ "\'");

                    ResultSet rs = stmt.executeQuery();




                    stmt = Globals.con.prepareStatement(
                            " SELECT Animal.Name, Animal.SPECIES, Animal.Exhibit, Animal.Age, Animal.Type FROM Animal WHERE "
                                    + "  Animal.Name = \'"+nameText+"\' AND Animal.SPECIES = \'" +speciesText+ "\' AND Animal.Exhibit = \'"
                                    + exhibitDropDown.getSelectedItem() +"\' AND Animal.Type = \'" +animalTypeDropDown.getSelectedItem()+
                                    "\' AND Animal.Age BETWEEN \'"+ageMinDropDown.getSelectedItem()+"\' AND \'" +ageMaxDropDown.getSelectedItem()+ "\'");

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

                        String[][] noAnimalData = {{"None","None","None","None","None"}};
                        df.setDataVector(noAnimalData,col_name);
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





                } catch(Exception ex) {System.err.println(ex);} } // end error box else

                // update result table

                //df.fireTableDataChanged();
                resultTable.revalidate();


//                if (true) {JOptionPane.showMessageDialog(viewAnimalsPanel,
//                        "Wrong Date Format!");}
                //AdministratorFunctionality af = new AdministratorFunctionality();
                //af.setVisible(true);
                //setVisible(false);  //hide and close current window
                //dispose();
            }
        }); // end button action



        // removeAnimalNutton
        removeAnimalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = resultTable.getSelectedRow();

                // to delete animal, need: name and species

                String nameToRemove = resultTable.getModel().getValueAt(selectedRow, 0).toString();
                String speciesToRemove = resultTable.getModel().getValueAt(selectedRow, 1).toString();

                //System.out.println("\n\nRemove button pressed...");
                //System.out.println(nameToRemove);
                //System.out.println(speciesToRemove);

//                PreparedStatement stmt = Globals.con.prepareStatement(
//                        " " +nameToRemove+ "" +speciesToRemove+ "");
//
//                ResultSet rs = stmt.executeQuery();

                try {

                    PreparedStatement stmt = Globals.con.prepareStatement(
                            " DELETE FROM Animal WHERE Animal.Name = \'"  +nameToRemove+ "\' AND Animal.SPECIES = \'"+speciesToRemove+ "\' ");
                    int exResult = stmt.executeUpdate();


                    System.out.println(exResult);

                    ViewAnimals vaNew = new ViewAnimals();

                    vaNew.setVisible(true);

                    setVisible(false);  //hide and close current window
                    dispose();

                    // update table


                } catch(Exception ex) {System.err.println(ex);}




            }
        }); // end button action





    } // end constructor


} // end class
