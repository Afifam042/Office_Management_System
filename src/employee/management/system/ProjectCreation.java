package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class ProjectCreation extends JFrame implements ActionListener {

    JTextField tfProjectName, tfDescription, tfBudget, tfExpenses;
    JComboBox<String> cbProjectManager; // Dropdown for project manager
    JTextArea taObjectives;
    JButton submitButton, backButton;
    JDateChooser startDateChooser, endDateChooser;

    public ProjectCreation() {
        setLayout(null);
        getContentPane().setBackground(Color.GRAY);

        JLabel lblProjectName = new JLabel("Project Name:");
        lblProjectName.setBounds(50, 30, 150, 30);
        lblProjectName.setForeground(Color.WHITE);
        add(lblProjectName);

        tfProjectName = new JTextField();
        tfProjectName.setBounds(200, 30, 200, 30);
        add(tfProjectName);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(50, 80, 150, 30);
        lblDescription.setForeground(Color.WHITE);
        add(lblDescription);

        tfDescription = new JTextField();
        tfDescription.setBounds(200, 80, 200, 30);
        add(tfDescription);

        JLabel lblStartDate = new JLabel("Start Date:");
        lblStartDate.setBounds(50, 130, 150, 30);
        lblStartDate.setForeground(Color.WHITE);
        add(lblStartDate);

        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(200, 130, 200, 30);
        add(startDateChooser);

        JLabel lblEndDate = new JLabel("End Date:");
        lblEndDate.setBounds(50, 180, 150, 30);
        lblEndDate.setForeground(Color.WHITE);
        add(lblEndDate);

        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(200, 180, 200, 30);
        add(endDateChooser);

        JLabel lblProjectManager = new JLabel("Project Manager:");
        lblProjectManager.setBounds(50, 230, 150, 30);
        lblProjectManager.setForeground(Color.WHITE);
        add(lblProjectManager);

        cbProjectManager = new JComboBox<>();
        cbProjectManager.setBounds(200, 230, 200, 30);
        add(cbProjectManager);
        populateProjectManagerDropdown();

        JLabel lblObjectives = new JLabel("Objectives:");
        lblObjectives.setBounds(50, 280, 150, 30);
        lblObjectives.setForeground(Color.WHITE);
        add(lblObjectives);

        taObjectives = new JTextArea();
        taObjectives.setBounds(200, 280, 200, 100);
        taObjectives.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        taObjectives.setBackground(Color.DARK_GRAY);
        taObjectives.setForeground(Color.WHITE);
        add(taObjectives);

        JLabel lblBudget = new JLabel("Budget:");
        lblBudget.setBounds(50, 400, 150, 30);
        lblBudget.setForeground(Color.WHITE);
        add(lblBudget);

        tfBudget = new JTextField();
        tfBudget.setBounds(200, 400, 200, 30);
        add(tfBudget);

        JLabel lblExpenses = new JLabel("Expenses:");
        lblExpenses.setBounds(50, 450, 150, 30);
        lblExpenses.setForeground(Color.WHITE);
        add(lblExpenses);

        tfExpenses = new JTextField();
        tfExpenses.setBounds(200, 450, 200, 30);
        add(tfExpenses);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 500, 100, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        add(submitButton);

        backButton = new JButton("Back");
        backButton.setBounds(50, 500, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        setSize(500, 600);
        setLocation(400, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void populateProjectManagerDropdown() {
        try {
            Conn c = new Conn();
            String query = "SELECT username FROM employee"; // Adjust if needed to get the correct column
            Statement stmt = c.c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                cbProjectManager.addItem(rs.getString("username"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            String projectName = tfProjectName.getText();
            String description = tfDescription.getText();
            java.util.Date startDate = startDateChooser.getDate();
            java.util.Date endDate = endDateChooser.getDate();
            String projectManager = (String) cbProjectManager.getSelectedItem();
            String objectives = taObjectives.getText();
            double budget = Double.parseDouble(tfBudget.getText());
            double expenses = Double.parseDouble(tfExpenses.getText());

            java.sql.Date sqlStartDate = startDate != null ? new java.sql.Date(startDate.getTime()) : null;
            java.sql.Date sqlEndDate = endDate != null ? new java.sql.Date(endDate.getTime()) : null;

            try {
                Conn c = new Conn();
                String queryProject = "INSERT INTO projects (project_name, description, start_date, end_date, project_manager, objectives) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psProject = c.c.prepareStatement(queryProject, Statement.RETURN_GENERATED_KEYS);
                psProject.setString(1, projectName);
                psProject.setString(2, description);
                psProject.setDate(3, sqlStartDate);
                psProject.setDate(4, sqlEndDate);
                psProject.setString(5, projectManager);
                psProject.setString(6, objectives);

                int resultProject = psProject.executeUpdate();

                if (resultProject > 0) {
                    ResultSet generatedKeys = psProject.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int projectId = generatedKeys.getInt(1);

                        String queryFinances = "INSERT INTO project_finances (project_id, budget, expenses) VALUES (?, ?, ?)";
                        PreparedStatement psFinances = c.c.prepareStatement(queryFinances);
                        psFinances.setInt(1, projectId);
                        psFinances.setDouble(2, budget);
                        psFinances.setDouble(3, expenses);

                        psFinances.executeUpdate();
                        psFinances.close();
                    }
                    JOptionPane.showMessageDialog(null, "Project created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create project.");
                }

                tfProjectName.setText("");
                tfDescription.setText("");
                startDateChooser.setDate(null);
                endDateChooser.setDate(null);
                cbProjectManager.setSelectedIndex(-1);
                taObjectives.setText("");
                tfBudget.setText("");
                tfExpenses.setText("");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ProjectCreation();
    }
}
