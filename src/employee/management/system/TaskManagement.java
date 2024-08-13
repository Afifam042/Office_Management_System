package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import com.toedter.calendar.JDateChooser;

public class TaskManagement extends JFrame implements ActionListener {

    JTextField tfTaskName, tfDescription;
    JComboBox<String> cbProject, cbAssignee, cbPriority;
    JButton submitButton, backButton;
    JDateChooser dueDateChooser;

    TaskManagement() {
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel lblTaskName = new JLabel("Task Name:");
        lblTaskName.setBounds(50, 30, 150, 30);
        add(lblTaskName);

        tfTaskName = new JTextField();
        tfTaskName.setBounds(200, 30, 200, 30);
        add(tfTaskName);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setBounds(50, 80, 150, 30);
        add(lblDescription);

        tfDescription = new JTextField();
        tfDescription.setBounds(200, 80, 200, 30);
        add(tfDescription);

        JLabel lblDueDate = new JLabel("Due Date:");
        lblDueDate.setBounds(50, 130, 150, 30);
        add(lblDueDate);

        dueDateChooser = new JDateChooser();
        dueDateChooser.setBounds(200, 130, 200, 30);
        add(dueDateChooser);

        JLabel lblPriority = new JLabel("Priority:");
        lblPriority.setBounds(50, 180, 150, 30);
        add(lblPriority);

        cbPriority = new JComboBox<>(new String[] {"High", "Medium", "Low"});
        cbPriority.setBounds(200, 180, 200, 30);
        add(cbPriority);

        JLabel lblProject = new JLabel("Project:");
        lblProject.setBounds(50, 230, 150, 30);
        add(lblProject);

        cbProject = new JComboBox<>();
        cbProject.setBounds(200, 230, 200, 30);
        add(cbProject);

        JLabel lblAssignee = new JLabel("Assignee:");
        lblAssignee.setBounds(50, 280, 150, 30);
        add(lblAssignee);

        cbAssignee = new JComboBox<>();
        cbAssignee.setBounds(200, 280, 200, 30);
        add(cbAssignee);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 330, 100, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        add(submitButton);

        backButton = new JButton("Back");
        backButton.setBounds(50, 330, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        loadProjects();
        loadAssignees();

        setSize(500, 400);
        setLocation(400, 200);
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

    private void loadAssignees() {
        try {
            Conn c = new Conn();
            String query = "SELECT name FROM employee";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                cbAssignee.addItem(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            String taskName = tfTaskName.getText();
            String description = tfDescription.getText();
            java.util.Date dueDate = dueDateChooser.getDate();
            String priority = (String) cbPriority.getSelectedItem();
            String selectedProject = (String) cbProject.getSelectedItem();
            String assignee = (String) cbAssignee.getSelectedItem();

            int projectId = Integer.parseInt(selectedProject.split("\\(")[1].replace(")", "").trim());

            java.sql.Date sqlDueDate = dueDate != null ? new java.sql.Date(dueDate.getTime()) : null;

            try {
                Conn c = new Conn();
                String query = "INSERT INTO tasks (task_name, description, due_date, priority, project_id, assignee) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, taskName);
                ps.setString(2, description);
                ps.setDate(3, sqlDueDate);
                ps.setString(4, priority);
                ps.setInt(5, projectId);
                ps.setString(6, assignee);

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Task created successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to create task.");
                }

                tfTaskName.setText("");
                tfDescription.setText("");
                dueDateChooser.setDate(null);
                cbPriority.setSelectedIndex(0);
                cbProject.setSelectedIndex(0);
                cbAssignee.setSelectedIndex(0);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new TaskManagement();
    }
}
