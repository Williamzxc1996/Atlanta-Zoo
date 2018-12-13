package com.db;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
//import java.security.MessageDigest;


public class Registration extends JFrame{
    private JPanel registrationPanel;
    private JTextField Email;
    private JTextField Username;
    private JButton registerButton;
    private JPasswordField Password;
    private JPasswordField Password_confirm;
    private JButton registerStaffButton;

    public Registration(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        registrationPanel.setBackground(Color.ORANGE);
        add(registrationPanel);
        setTitle("Atlanta Zoo");
        setSize(500,500);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String em = Email.getText();
                String usrn = Username.getText();
                char[] ps = Password.getPassword();
                char[] ps_confirm = Password_confirm.getPassword();




                //first check if the password confirmation equals to password
                if (Arrays.equals(ps,ps_confirm)) {



                    try {


//                        Connection con = DriverManager.getConnection(
//                                "jdbc:myDriver:DatabaseName",
//                                "123",
//                                "456");

                        Statement stmt = Globals.con.createStatement();

                        String strPassword = String.copyValueOf(ps);

//                        // hash password
//                        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//                        messageDigest.update(strPassword.getBytes());
//                        String encryptedString = new String(messageDigest.digest());

                        //System.out.println(strPassword);

                        // username, password, email, usertype

                        //check if Email and Username haven't been used
                        //stmt.executeUpdate("USE cs4400_group18;");
                        //int m = stmt.executeUpdate("INSERT INTO User VALUES(\'"+usrn+"\',\'"+em+"',\'Visitor\',\'"+ps.toString()+"\')");
                        // ps.toString()
                        if (strPassword.length() <= 7) {
                            JOptionPane.showMessageDialog(registrationPanel,
                                    "your password is invalid, try use another one!");
                            Registration registration = new Registration();
                            registration.setVisible(true);
                            setVisible(false);
                            dispose();
                        }
                        else if (!em.contains("@")) {
                            JOptionPane.showMessageDialog(registrationPanel,
                                    "Email is invalid, try use another one!");
                            Registration registration = new Registration();
                            registration.setVisible(true);
                            setVisible(false);
                            dispose();
                        } else {
                            int m = stmt.executeUpdate("INSERT INTO User VALUES('" + usrn + "','" + strPassword + "','" + em + "','visitor')");
                            if (m == 1) {
                                //go back to Login Page
                                //Globals.con.close();
                                LoginPage loginPage = new LoginPage();
                                loginPage.setVisible(true);
                                setVisible(false);
                                dispose();
                            } else {
                                //insertion failed
                                JOptionPane.showMessageDialog(registrationPanel,
                                        "Email or Username may already existed! Or your password is invalid, try use another one!");
                            }
                        }
                    } catch (Exception e1) {
                        System.err.println(e1);
                    }
                }else{
                    //two password are not same
                    JOptionPane.showMessageDialog(registrationPanel,
                            "Two password don't match!");
                }
            }
        });
        registerStaffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String em = Email.getText();
                String usrn = Username.getText();
                char[] ps = Password.getPassword();
                char[] ps_confirm = Password_confirm.getPassword();


                //first check if the password confirmation equals to password
                if (Arrays.equals(ps,ps_confirm)) {
                    try {
//                        Connection con = DriverManager.getConnection(
//                                "jdbc:myDriver:DatabaseName",
//                                "123",
//                                "456");

                        Statement stmt = Globals.con.createStatement();

                        String strPassword = String.copyValueOf(ps);

                        //check if Email and Username haven't been used
                        int m = stmt.executeUpdate("INSERT INTO User VALUES('"+usrn+"','"+strPassword+"','" + em + "','staff')");
                        if(m == 1){
                            //go back to Login Page
                            //Globals.con.close();
                            LoginPage loginPage = new LoginPage();
                            loginPage.setVisible(true);
                            setVisible(false);
                            dispose();
                        }else{
                            //insertion failed
                            JOptionPane.showMessageDialog(registrationPanel,
                                    "Email or Username may already existed! Or your password is invalid, try use another one!");
                        }
                    } catch (Exception e2) {
                        System.err.println(e2);
                    }
                }else{
                    //two password are not same
                    JOptionPane.showMessageDialog(registrationPanel,
                            "Two password don't match!");
                }
            }
        });
    }
}
