package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PerformanceAppraisalPage extends JFrame implements ActionListener {

    JButton createAppraisalButton, viewAppraisalsButton, backButton;

    PerformanceAppraisalPage() {
        // Set background color to grey
        getContentPane().setBackground(Color.GRAY);
        setLayout(null);

        // Create Appraisal button
        createAppraisalButton = new JButton("Create Appraisal");
        createAppraisalButton.setBounds(100, 50, 200, 30);
        createAppraisalButton.addActionListener(this);
        add(createAppraisalButton);

        // View Appraisals button
        viewAppraisalsButton = new JButton("View Appraisals");
        viewAppraisalsButton.setBounds(100, 100, 200, 30);
        viewAppraisalsButton.addActionListener(this);
        add(viewAppraisalsButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(100, 150, 200, 30);
        backButton.addActionListener(this);
        add(backButton);

        // Set window size and location
        setSize(400, 250);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createAppraisalButton) {
            // Open a form to create a new appraisal
            new CreateAppraisalForm();
        } else if (ae.getSource() == viewAppraisalsButton) {
            // Open a form to view existing appraisals
            new ViewAppraisalsForm();
        } else {
            // Go back to the home page
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new PerformanceAppraisalPage();
    }

    // Inner class for creating an appraisal form
    class CreateAppraisalForm extends JFrame implements ActionListener {

        JTextField employeeIdField, goalsField, feedbackField;
        JComboBox<String> ratingComboBox;
        JButton createButton, cancelButton;

        CreateAppraisalForm() {
            // Set background color to grey
            getContentPane().setBackground(Color.GRAY);
            setLayout(null);

            // Title label
            JLabel titleLabel = new JLabel("Create Appraisal");
            titleLabel.setBounds(20, 20, 200, 20);
            titleLabel.setForeground(Color.WHITE);
            add(titleLabel);

            // Employee ID label and field
            JLabel employeeIdLabel = new JLabel("Employee ID:");
            employeeIdLabel.setBounds(20, 60, 100, 20);
            employeeIdLabel.setForeground(Color.WHITE);
            add(employeeIdLabel);

            employeeIdField = new JTextField();
            employeeIdField.setBounds(120, 60, 200, 20);
            add(employeeIdField);

            // Goals label and field
            JLabel goalsLabel = new JLabel("Goals:");
            goalsLabel.setBounds(20, 90, 100, 20);
            goalsLabel.setForeground(Color.WHITE);
            add(goalsLabel);

            goalsField = new JTextField();
            goalsField.setBounds(120, 90, 200, 20);
            add(goalsField);

            // Feedback label and field
            JLabel feedbackLabel = new JLabel("Feedback:");
            feedbackLabel.setBounds(20, 120, 100, 20);
            feedbackLabel.setForeground(Color.WHITE);
            add(feedbackLabel);

            feedbackField = new JTextField();
            feedbackField.setBounds(120, 120, 200, 20);
            add(feedbackField);

            // Rating label and dropdown
            JLabel ratingLabel = new JLabel("Rating:");
            ratingLabel.setBounds(20, 150, 100, 20);
            ratingLabel.setForeground(Color.WHITE);
            add(ratingLabel);

            ratingComboBox = new JComboBox<>(new String[] {"A", "B", "C"});
            ratingComboBox.setBounds(120, 150, 100, 20);
            add(ratingComboBox);

            // Create button
            createButton = new JButton("Create");
            createButton.setBounds(120, 180, 100, 30);
            createButton.addActionListener(this);
            add(createButton);

            // Cancel button
            cancelButton = new JButton("Cancel");
            cancelButton.setBounds(240, 180, 100, 30);
            cancelButton.addActionListener(this);
            add(cancelButton);

            // Set window size and location
            setSize(350, 250);
            setLocation(400, 200);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == createButton) {
                createAppraisal();
            } else {
                setVisible(false);
            }
        }

        private void createAppraisal() {
            String employeeId = employeeIdField.getText();
            String goals = goalsField.getText();
            String feedback = feedbackField.getText();
            String rating = (String) ratingComboBox.getSelectedItem();

            if (!employeeId.isEmpty() && !goals.isEmpty() && !feedback.isEmpty() && rating != null) {
                try {
                    // Validate employeeId as an integer
                    int employeeIdInt;
                    try {
                        employeeIdInt = Integer.parseInt(employeeId);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "Employee ID must be an integer.", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Stop execution if employeeId is not an integer
                    }

                    Conn c = new Conn();
                    String query = "INSERT INTO appraisals (empid, goals, feedback, rating) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = c.c.prepareStatement(query);
                    preparedStatement.setInt(1, employeeIdInt);
                    preparedStatement.setString(2, goals);
                    preparedStatement.setString(3, feedback);
                    preparedStatement.setString(4, rating);

                    int result = preparedStatement.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Appraisal created successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to create appraisal. Please try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }

    // Inner class for viewing appraisals
    class ViewAppraisalsForm extends JFrame {

        JTextArea appraisalsTextArea;
        JScrollPane scrollPane;

        ViewAppraisalsForm() {
            // Set background color to grey
            getContentPane().setBackground(Color.GRAY);
            setLayout(new BorderLayout());

            // Appraisals Text Area
            appraisalsTextArea = new JTextArea();
            appraisalsTextArea.setEditable(false);
            
            // Add Text Area to Scroll Pane
            scrollPane = new JScrollPane(appraisalsTextArea);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            add(scrollPane, BorderLayout.CENTER);

            // Fetch and display existing appraisals
            fetchAppraisals();

            // Set window size and location
            setSize(500, 300);
            setLocation(400, 200);
            setVisible(true);
        }

        private void fetchAppraisals() {
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery("SELECT * FROM appraisals");
                StringBuilder appraisalsText = new StringBuilder();
                while (rs.next()) {
                    appraisalsText.append("Employee ID: ").append(rs.getInt("empid")).append("\n");
                    appraisalsText.append("Goals: ").append(rs.getString("goals")).append("\n");
                    appraisalsText.append("Feedback: ").append(rs.getString("feedback")).append("\n");
                    appraisalsText.append("Rating: ").append(rs.getString("rating")).append("\n");
                    appraisalsText.append("------------------------\n");
                }
                appraisalsTextArea.setText(appraisalsText.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
