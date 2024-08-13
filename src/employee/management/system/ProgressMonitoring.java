package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ProgressMonitoring extends JFrame implements ActionListener {

    JComboBox<String> cbProject;
    JTable progressTable;
    JButton viewProgressButton, backButton;
    JLabel progressLabel;
    JTextArea progressTextArea;

    ProgressMonitoring() {
        setLayout(null);
        getContentPane().setBackground(Color.GRAY);  // Set background color to grey

        // Label for project selection
        JLabel lblSelectProject = new JLabel("Select Project:");
        lblSelectProject.setBounds(50, 30, 150, 30);
        lblSelectProject.setForeground(Color.WHITE); // Set label color to white
        add(lblSelectProject);

        // Combo box for project selection
        cbProject = new JComboBox<>();
        cbProject.setBounds(200, 30, 200, 30);
        add(cbProject);

        // View Progress button
        viewProgressButton = new JButton("View Progress");
        viewProgressButton.setBounds(420, 30, 150, 30);
        viewProgressButton.setBackground(Color.BLACK);
        viewProgressButton.setForeground(Color.WHITE);
        viewProgressButton.addActionListener(this);
        add(viewProgressButton);

        // Progress overview label
        progressLabel = new JLabel("Progress Overview:");
        progressLabel.setBounds(50, 80, 150, 30);
        progressLabel.setForeground(Color.WHITE); // Set label color to white
        add(progressLabel);

        // Progress text area
        progressTextArea = new JTextArea();
        progressTextArea.setBounds(50, 120, 800, 200);
        progressTextArea.setEditable(false);
        progressTextArea.setBackground(Color.DARK_GRAY); // Set background color to dark grey
        progressTextArea.setForeground(Color.WHITE); // Set text color to white
        add(progressTextArea);

        // Progress table
        progressTable = new JTable();
        JScrollPane sp = new JScrollPane(progressTable);
        sp.setBounds(50, 330, 800, 300);
        add(sp);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(50, 650, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        loadProjects();

        // Set window size and location
        setSize(900, 900);
        setLocation(300, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadProjects() {
        try {
            Conn c = new Conn();
            String query = "SELECT project_id, project_name FROM projects";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                int projectId = rs.getInt("project_id");
                String projectName = rs.getString("project_name");
                cbProject.addItem(projectName + " (" + projectId + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == viewProgressButton) {
            String selectedProject = (String) cbProject.getSelectedItem();

            // Extract project ID from selected item
            int projectId = Integer.parseInt(selectedProject.split("\\(")[1].replace(")", "").trim());

            try {
                Conn c = new Conn();
                // Fetch progress overview
                String query = "SELECT task_name, description, time_spent FROM tasks WHERE project_id = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setInt(1, projectId);
                ResultSet rs = ps.executeQuery();

                // Display project progress in text area
                StringBuilder progressText = new StringBuilder();
                int totalTasks = 0;
                int totalTimeSpent = 0;

                while (rs.next()) {
                    String taskName = rs.getString("task_name");
                    String description = rs.getString("description");
                    int timeSpent = rs.getInt("time_spent");

                    progressText.append("Task Name: ").append(taskName).append("\n");
                    progressText.append("Description: ").append(description).append("\n");
                    progressText.append("Hours Spent: ").append(timeSpent).append("\n\n");

                    totalTasks++;
                    totalTimeSpent += timeSpent;
                }

                progressTextArea.setText(progressText.toString());

                // Update the progress table
                String tableQuery = "SELECT task_name, description, time_spent FROM tasks WHERE project_id = ?";
                PreparedStatement tablePs = c.c.prepareStatement(tableQuery);
                tablePs.setInt(1, projectId);
                ResultSet tableRs = tablePs.executeQuery();
                progressTable.setModel(DbUtils.resultSetToTableModel(tableRs));

                // Show progress statistics
                progressTextArea.append("Total Tasks: " + totalTasks + "\n");
                progressTextArea.append("Total Hours Spent: " + totalTimeSpent + "\n");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home(); // Navigate back to the home page
        }
    }

    public static void main(String[] args) {
        new ProgressMonitoring();
    }
}
