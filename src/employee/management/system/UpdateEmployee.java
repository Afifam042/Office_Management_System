package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateEmployee extends JFrame implements ActionListener {

    JTextField tfeducation, tffname, tfaddress, tfphone, tfemail, tfdesignation, tfposition;
    JLabel lblempId;
    JButton update, back;
    String empId;

    UpdateEmployee(String empId) {
        this.empId = empId;
        getContentPane().setBackground(Color.LIGHT_GRAY); // Grey background
        setLayout(null);
        
        JLabel heading = new JLabel("Update Employee Detail");
        heading.setBounds(320, 30, 500, 50);
        heading.setFont(new Font("SAN_SERIF", Font.BOLD, 25));
        add(heading);
        
        JLabel labelname = new JLabel("Name");
        labelname.setBounds(50, 150, 150, 30);
        labelname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelname);
        
        JLabel lblname = new JLabel();
        lblname.setBounds(200, 150, 150, 30);
        add(lblname);
        
        JLabel labelfname = new JLabel("Father's Name");
        labelfname.setBounds(400, 150, 150, 30);
        labelfname.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelfname);
        
        tffname = new JTextField();
        tffname.setBounds(600, 150, 150, 30);
        add(tffname);
        
        JLabel labeldob = new JLabel("Date of Birth");
        labeldob.setBounds(50, 200, 150, 30);
        labeldob.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldob);
        
        JLabel lbldob = new JLabel();
        lbldob.setBounds(200, 200, 150, 30);
        add(lbldob);
        
        JLabel labeladdress = new JLabel("Address");
        labeladdress.setBounds(50, 250, 150, 30);
        labeladdress.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeladdress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(200, 250, 150, 30);
        add(tfaddress);
        
        JLabel labelphone = new JLabel("Phone");
        labelphone.setBounds(400, 250, 150, 30);
        labelphone.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(600, 250, 150, 30);
        add(tfphone);
        
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 300, 150, 30);
        labelemail.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(200, 300, 150, 30);
        add(tfemail);
        
        JLabel labeleducation = new JLabel("Highest Education");
        labeleducation.setBounds(400, 300, 150, 30);
        labeleducation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeleducation);
        
        tfeducation = new JTextField();
        tfeducation.setBounds(600, 300, 150, 30);
        add(tfeducation);
        
        JLabel labeldesignation = new JLabel("Designation");
        labeldesignation.setBounds(50, 350, 150, 30);
        labeldesignation.setFont(new Font("serif", Font.PLAIN, 20));
        add(labeldesignation);
        
        tfdesignation = new JTextField();
        tfdesignation.setBounds(200, 350, 150, 30);
        add(tfdesignation);
        
        JLabel labelposition = new JLabel("Previous Position");
        labelposition.setBounds(400, 350, 150, 30);
        labelposition.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelposition);
        
        tfposition = new JTextField();
        tfposition.setBounds(600, 350, 150, 30);
        add(tfposition);
        
        JLabel labelempId = new JLabel("Employee id");
        labelempId.setBounds(50, 400, 150, 30);
        labelempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(labelempId);
        
        this.lblempId = new JLabel();
        this.lblempId.setBounds(200, 400, 150, 30);
        this.lblempId.setFont(new Font("serif", Font.PLAIN, 20));
        add(this.lblempId);
        
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM employee WHERE empId = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                lblname.setText(rs.getString("name"));
                tffname.setText(rs.getString("fname"));
                lbldob.setText(rs.getString("dob"));
                tfaddress.setText(rs.getString("address"));
                tfphone.setText(rs.getString("phone"));
                tfemail.setText(rs.getString("email"));
                tfeducation.setText(rs.getString("education"));
                tfposition.setText(rs.getString("position"));
                this.lblempId.setText(rs.getString("empId"));
                tfdesignation.setText(rs.getString("designation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        update = new JButton("Update Details");
        update.setBounds(250, 500, 150, 40);
        update.addActionListener(this);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        add(update);

        back = new JButton("Back");
        back.setBounds(450, 500, 150, 40);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        setSize(900, 600); // Increased height to fit the components better
        setLocation(300, 50);
        setVisible(true);
    }
    
   @Override
   public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            String fname = tffname.getText();
            String address = tfaddress.getText();
            String phone = tfphone.getText();
            String email = tfemail.getText();
            String education = tfeducation.getText();
            String designation = tfdesignation.getText();
            String position = tfposition.getText();

            // Input validation
            if (!isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(this, "Phone number must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop execution if phone is not a valid integer
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop execution if email is not in correct format
            }

            try {
                Conn conn = new Conn();
                String query = "UPDATE employee SET fname = ?, address = ?, phone = ?, email = ?, education = ?, position = ?, designation = ? WHERE empId = ?";
                PreparedStatement ps = conn.c.prepareStatement(query);
                ps.setString(1, fname);
                ps.setString(2, address);
                ps.setString(3, phone);
                ps.setString(4, email);
                ps.setString(5, education);
                ps.setString(6, position);
                ps.setString(7, designation);
                ps.setString(8, empId);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Details updated successfully");
                setVisible(false);
                new Home();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    // Helper method to check if a string represents a valid integer phone number
    private boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            return false;
        }
        try {
            Long.parseLong(phone); // Use Long to accommodate larger phone numbers
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Helper method to check if a string is a valid email format
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public static void main(String[] args) {
        // This will not work correctly without an empId
        // Make sure to pass a valid empId when instantiating this class
        new UpdateEmployee("E123");
    }
}
