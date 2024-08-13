package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.Random;

public class AddEmployee extends JFrame implements ActionListener {

    Random ran = new Random();
    int number = ran.nextInt(999999);

    JTextField tfname, tffname, tfaddress, tfphone, tfemail, tfdesignation;
    JDateChooser dcdob;
    JComboBox<String> cbeducation, cbposition;
    JLabel lblempId;
    JButton add, back;

    AddEmployee() {
        // Set background color to light grey
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        // Heading
        JLabel heading = new JLabel("Add Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);

        // Name
        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);

        tfname = new JTextField();
        tfname.setBounds(200, 150, 150, 30);
        add(tfname);

        // Father's Name
        JLabel labelfname = new JLabel("Father's Name");
        labelfname.setBounds(400, 150, 150, 30);
        labelfname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelfname);

        tffname = new JTextField();
        tffname.setBounds(600, 150, 150, 30);
        add(tffname);

        // Date of Birth
        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(50, 200, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);

        dcdob = new JDateChooser();
        dcdob.setBounds(200, 200, 150, 30);
        add(dcdob);

        // Address
        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);

        // Phone
        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);

        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);

        // Email
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);

        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);

        // Highest Education
        JLabel labeleducation = new JLabel("Highest Education");
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);

        String courses[] = {"BBA", "BCV", "BSCS", "BSSE", "BCOM", "EE", "MBA", "MSC", "MA", "PHD"};
        cbeducation = new JComboBox<>(courses);
        cbeducation.setBackground(Color.WHITE);
        cbeducation.setBounds(600, 300, 150, 30);
        add(cbeducation);

        // Current Designation
        JLabel labeldesignation = new JLabel("Current Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);

        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);

        // Previous Position
        JLabel labelposition = new JLabel("Previous Position");
        labelposition.setBounds(400, 350, 150, 30);
        labelposition.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelposition);

        String position[] = {"Manager", "Senior Developer", "Junior Developer"};
        cbposition = new JComboBox<>(position);
        cbposition.setBackground(Color.WHITE);
        cbposition.setBounds(600, 350, 150, 30);
        add(cbposition);

        // Employee ID
        JLabel labelempId = new JLabel("Employee ID");
        labelempId.setBounds(50, 400, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);

        this.lblempId = new JLabel("" + number);
        this.lblempId.setBounds(200, 400, 150, 30);
        this.lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(this.lblempId);

        // Buttons
        add = new JButton("Add Details");
        add.setBounds(250, 500, 150, 40);
        add.addActionListener(this);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add(add);

        back = new JButton("Back");
        back.setBounds(450, 500, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        // Set window size and location
        setSize(900, 700);
        setLocation(300, 50);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            if (isEmptyField(tfname) || isEmptyField(tffname) || isEmptyField(tfaddress)
                    || isEmptyField(tfphone) || isEmptyField(tfemail) || isEmptyField(tfdesignation)) {
                JOptionPane.showMessageDialog(this, "Fill in complete information.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isValidEmail(tfemail.getText())) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!isInteger(tfphone.getText())) {
                JOptionPane.showMessageDialog(this, "Phone number must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // The code for adding details to the database
                String name = tfname.getText();
                String fname = tffname.getText();
                String dob = ((JTextField) dcdob.getDateEditor().getUiComponent()).getText();
                String address = tfaddress.getText();
                String phone = tfphone.getText();
                String email = tfemail.getText();
                String education = cbeducation.getSelectedItem().toString();
                String designation = tfdesignation.getText();
                String position = cbposition.getSelectedItem().toString();
                String empId = this.lblempId.getText();

                try {
                    Conn conn = new Conn();
                    String query = "INSERT INTO employee VALUES('" + name + "', '" + fname + "', '" + dob + "', '" + address + "', '" + phone + "', '" + email + "', '" + education + "', '" + designation + "', '" + position + "', '" + empId + "')";
                    conn.s.executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Details added successfully");
                    setVisible(false);
                    new Home();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    private boolean isEmptyField(JTextField textField) {
        return textField.getText().trim().isEmpty();
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void main(String[] args) {
        new AddEmployee();
    }
}
