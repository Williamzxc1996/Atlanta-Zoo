package com.db;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisitorFunctionality extends JFrame{
    private JButton searchExhibitsButton;
    private JButton viewExhibitHistoryButton;
    private JButton searchShowsButton;
    private JButton viewShowHistoryButton;
    private JButton searchForAnimalsButton;
    private JButton logOutButton;
    private JPanel visitorFunctionalityPanel;

    public VisitorFunctionality(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(visitorFunctionalityPanel);
        setTitle("Atlanta Zoo: Visitor Functionality");
        setSize(500, 500);

        //searchExhibitsButton
        searchExhibitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //SearchExhibits se = new SearchExhibits();
                //se.setVisible(true);

                SearchExhibits_Visitor sev = new SearchExhibits_Visitor();
                sev.setVisible(true);


                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end search exhibits button action

        //viewExhibitHistoryButton
        viewExhibitHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ViewExhibitHistory veh = new ViewExhibitHistory();
                veh.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end view exhibit history button action

        //searchShowsButton
        searchShowsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SearchShows ss = new SearchShows();
                ss.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end search shows button action

        //viewShowHistoryButton
        viewShowHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewShowHistory vsh = new ViewShowHistory();
                vsh.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end view show history button action

        //searchAnimalsButton
        searchForAnimalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchForAnimals sfa = new SearchForAnimals();
                sfa.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end search animals button action

        //logOutButton
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage lp = new LoginPage();
                lp.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        }); // end logout button action

    } // end VisitorFunctionality constructor


} // end VisitorFuncionality class
