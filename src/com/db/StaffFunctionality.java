package com.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffFunctionality extends JFrame {

    private JPanel staffPanel;

    private JButton searchAnimals;
    private JButton viewShows;
    private JButton logOut;


    public StaffFunctionality() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //staffPanel.setBackground(Color.lightGray);
        add(staffPanel);
        setTitle("Atlanta Zoo: Staff Functionality");
        setSize(500,500);

        // searchAnimals button
        searchAnimals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Registration reg = new Registration();
                //reg.setVisible(true);
                SearchAnimals sa = new SearchAnimals();
                sa.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end search animals button action

        // view shows button
        viewShows.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewShows vs = new ViewShows();
                vs.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end viewShows button action

        //logout button
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage lp = new LoginPage();
                lp.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end logout button action



    } // end constructor

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
} // end staff functionality class
