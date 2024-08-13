package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;




public class EmployeeSurveyManagement extends JFrame implements ActionListener {

    JButton createSurveyButton, distributeSurveyButton, viewFeedbackButton, backButton;

    EmployeeSurveyManagement() {
        // Set background color to grey
        getContentPane().setBackground(Color.GRAY);
        setLayout(null);

        // Create Survey button
        createSurveyButton = new JButton("Create Survey");
        createSurveyButton.setBounds(20, 20, 150, 30);
        createSurveyButton.setBackground(Color.BLACK);
        createSurveyButton.setForeground(Color.WHITE);
        createSurveyButton.addActionListener(this);
        add(createSurveyButton);

        // Distribute Survey button
        distributeSurveyButton = new JButton("Distribute Survey");
        distributeSurveyButton.setBounds(200, 20, 150, 30);
        distributeSurveyButton.setBackground(Color.BLACK);
        distributeSurveyButton.setForeground(Color.WHITE);
        distributeSurveyButton.addActionListener(this);
        add(distributeSurveyButton);

        // View Feedback button
        viewFeedbackButton = new JButton("View Feedback");
        viewFeedbackButton.setBounds(380, 20, 150, 30);
        viewFeedbackButton.setBackground(Color.BLACK);
        viewFeedbackButton.setForeground(Color.WHITE);
        viewFeedbackButton.addActionListener(this);
        add(viewFeedbackButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(560, 20, 80, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        // Set window size and location
        setSize(660, 100);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createSurveyButton) {
            new CreateSurveyForm();
        } else if (ae.getSource() == distributeSurveyButton) {
            distributeSurvey();
        } else if (ae.getSource() == viewFeedbackButton) {
            viewFeedback();
        } else {
            setVisible(false);
            new Home();
        }
    }

    private void distributeSurvey() {
        // Logic to distribute the survey to employees
        JOptionPane.showMessageDialog(this, "Survey distributed to employees!");
    }

    private void viewFeedback() {
        // Logic to view feedback received from employees
        JOptionPane.showMessageDialog(this, "Viewing feedback...");
    }

    public static void main(String[] args) {
        new EmployeeSurveyManagement();
    }

    // Inner class for creating a survey form
    class CreateSurveyForm extends JFrame implements ActionListener {

        JLabel titleLabel, question1Label, question2Label, question3Label;
        JTextField titleField, question1Field, question2Field, question3Field;
        JButton createButton, cancelButton;

        CreateSurveyForm() {
            // Set background color to grey
            getContentPane().setBackground(Color.GRAY);
            setLayout(null);

            // Survey Title label and field
            titleLabel = new JLabel("Survey Title:");
            titleLabel.setBounds(20, 20, 100, 20);
            titleLabel.setForeground(Color.WHITE);
            add(titleLabel);

            titleField = new JTextField();
            titleField.setBounds(130, 20, 500, 20);
            add(titleField);

            // Question 1 label and field
            question1Label = new JLabel("Question 1:");
            question1Label.setBounds(20, 60, 100, 20);
            question1Label.setForeground(Color.WHITE);
            add(question1Label);

            question1Field = new JTextField();
            question1Field.setBounds(130, 60, 500, 20);
            add(question1Field);

            // Question 2 label and field
            question2Label = new JLabel("Question 2:");
            question2Label.setBounds(20, 100, 100, 20);
            question2Label.setForeground(Color.WHITE);
            add(question2Label);

            question2Field = new JTextField();
            question2Field.setBounds(130, 100, 500, 20);
            add(question2Field);

            // Question 3 label and field
            question3Label = new JLabel("Question 3:");
            question3Label.setBounds(20, 140, 100, 20);
            question3Label.setForeground(Color.WHITE);
            add(question3Label);

            question3Field = new JTextField();
            question3Field.setBounds(130, 140, 500, 20);
            add(question3Field);

            // Create button
            createButton = new JButton("Create");
            createButton.setBounds(150, 180, 100, 30);
            createButton.setBackground(Color.BLACK);
            createButton.setForeground(Color.WHITE);
            createButton.addActionListener(this);
            add(createButton);

            // Cancel button
            cancelButton = new JButton("Cancel");
            cancelButton.setBounds(300, 180, 100, 30);
            cancelButton.setBackground(Color.BLACK);
            cancelButton.setForeground(Color.WHITE);
            cancelButton.addActionListener(this);
            add(cancelButton);

            // Set window size and location
            setSize(660, 250);
            setLocation(300, 200);
            setVisible(true);
        }

        public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == createButton) {
                createSurvey();
            } else {
                setVisible(false);
            }
        }

        private void createSurvey() {
            String surveyTitle = titleField.getText();
            String question1 = question1Field.getText();
            String question2 = question2Field.getText();
            String question3 = question3Field.getText();

            if (!surveyTitle.isEmpty() && !question1.isEmpty() && !question2.isEmpty() && !question3.isEmpty()) {
                try {
                    Conn c = new Conn();
                    String query = "INSERT INTO surveys (survey_title, question1, question2, question3) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = c.c.prepareStatement(query);
                    preparedStatement.setString(1, surveyTitle);
                    preparedStatement.setString(2, question1);
                    preparedStatement.setString(3, question2);
                    preparedStatement.setString(4, question3);

                    int result = preparedStatement.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(this, "Survey created successfully!");
                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to create survey. Please try again.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }
}
