package com.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;

public class LoginPage extends JFrame{
    private JTextField Email;
    private JPasswordField Password;
    private JButton loginButton;
    private JPanel loginPanel;
    private JButton registerButton;

    public LoginPage() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);       //Manually close the window will cause the program stop

        loginPanel.setBackground(Color.ORANGE);
        add(loginPanel);
        setTitle("Atlanta Zoo");
        setSize(500,500);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registration reg = new Registration();
                reg.setVisible(true);
                setVisible(false);  //hide and close current window
                dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get user input
                String em = Email.getText();
                char[] pw = Password.getPassword();

                try {
//                    Connection con = DriverManager.getConnection(
//                            "https://academic-mysql.cc.gatech.edu/phpmyadmin",
//                            "cs4400_group18",
//                            "R7mNv3pS");

                    //Statement stmt = Globals.con.createStatement();
                    //stmt.executeUpdate("USE cs4400_group18;");

                    //ResultSet rs = stmt.executeQuery("SELECT Password, UserType FROM User WHERE Email = " + em);


                    //String strPassword = String.copyValueOf(pw);

                    System.out.println();

                    PreparedStatement stmt = Globals.con.prepareStatement("SELECT Password, UserType FROM User WHERE Email = \'" + em + "\'");
                    ResultSet rs = stmt.executeQuery();



                    //no em in the database
                    if(!rs.next()) {
                        JOptionPane.showMessageDialog(loginPanel,
                                "Wrong Email!");
                    }else{

                        // get Username for current user
                        stmt = Globals.con.prepareStatement("SELECT Username FROM User WHERE Email = \'" + em + "\'");
                        ResultSet activeUsername = stmt.executeQuery();
                        activeUsername.next();
                        Globals.activeUser = activeUsername.getString(1);
                        //System.out.println(Globals.activeUser);


                        String pw_check = rs.getString("Password");
                        if(Arrays.equals(pw,pw_check.toCharArray())){
                            //password and email combination is correct, go to certain page
                            String role = rs.getString("UserType");
                            //goes to next page, use con.close() to close the connection to db before proceed to next page
                            if(role.equals("admin")){
                                //goes to Admin Page
                                AdministratorFunctionality af = new AdministratorFunctionality();
                                af.setVisible(true);
                                setVisible(false);
                                dispose();
                            }else if(role.equals("visitor")||role.equals("Visitor")){
                                //goes to Visitor Page
                                VisitorFunctionality vf = new VisitorFunctionality();
                                vf.setVisible(true);
                                setVisible(false);
                                dispose();
                            }else{
                                //goes to Staff Page

                                StaffFunctionality sf = new StaffFunctionality();
                                sf.setVisible(true);
                                setVisible(false);
                                dispose();

                            }
                        }else{
                            JOptionPane.showMessageDialog(loginPanel,
                                    "Wrong Password!");
                        }
                    }
                }
                catch(Exception ex){
                    System.err.println(ex);
                }
            }
        });
    }
}
