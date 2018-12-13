package com.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorFunctionality extends JFrame {

    private JPanel adminPanel;

    private JButton addShowButton;
    private JButton viewStaffButton;
    private JButton viewVisitorsButton;
    private JButton viewShowsButton;
    private JButton viewAnimalsButton;
    private JButton logoutButton;

    public AdministratorFunctionality() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(adminPanel);
        setTitle("Atlanta Zoo: Administrator Functionality");
        setSize(500,500);


        // addShow button
        addShowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AddShow as = new AddShow();
                as.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end button action


        // viewStaffButton
        viewStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ViewStaff vs = new ViewStaff();
                vs.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end button action



        // viewVisitorsButton
        viewVisitorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ViewStaff vs = new ViewStaff();
                //vs.setVisible(true);

                ViewVisitors vv = new ViewVisitors();
                vv.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end button action




        // viewShowsButton
        viewShowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ViewShowsAdmin vs = new ViewShowsAdmin();
                vs.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end button action




        // viewAnimalsButton
        viewAnimalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //ViewShowsAdmin vs = new ViewShowsAdmin();

                ViewAnimals va = new ViewAnimals();

                va.setVisible(true);

                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end button action





        // logoutButton
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage lp = new LoginPage();
                lp.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end logout button action








    } // end AdministratorFunctionality constructor



} // end AdministratorFunctionality class
