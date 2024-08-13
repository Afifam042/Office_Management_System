package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeDashboard extends JFrame {

    private JButton btnBack, btnApplyLeave;
    private JTable tableProfile, tableProjects, tableTasks, tableLeaves;
    private String username; // Store the username for use in ApplyForLeave

    public EmployeeDashboard(String username) {
        this.username = username; // Save the username
        setTitle("Employee Dashboard");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Create Panels
        JPanel panelTop = new JPanel(new GridLayout(1, 2));
        JPanel panelCenter = new JPanel(new GridLayout(4, 1));
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));

        // Title
        JLabel lblTitle = new JLabel("Employee Dashboard", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        panelTop.add(lblTitle);

        // Buttons
        btnBack = new JButton("Back");
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnApplyLeave = new JButton("Apply for Leave");
        btnApplyLeave.setBackground(Color.BLACK);
        btnApplyLeave.setForeground(Color.WHITE);
        btnApplyLeave.setFont(new Font("Arial", Font.BOLD, 14));
        panelBottom.add(btnBack);
        panelBottom.add(btnApplyLeave);

        // Panels for Tables
        JPanel profilePanel = createTablePanel("Profile Information:", new String[]{"Field", "Value"}, new DefaultTableModel(new String[]{"Field", "Value"}, 0));
        JPanel projectsPanel = createTablePanel("Current Projects:", new String[]{"Project Name"}, new DefaultTableModel(new String[]{"Project Name"}, 0));
        JPanel tasksPanel = createTablePanel("Tasks:", new String[]{"Task Name", "Due Date", "Priority"}, new DefaultTableModel(new String[]{"Task Name", "Due Date", "Priority"}, 0));
        JPanel leavesPanel = createTablePanel("Leave Details:", new String[]{"Type", "From Date", "To Date"}, new DefaultTableModel(new String[]{"Type", "From Date", "To Date"}, 0));

        panelCenter.add(profilePanel);
        panelCenter.add(projectsPanel);
        panelCenter.add(tasksPanel);
        panelCenter.add(leavesPanel);

        // Add panels to the frame
        add(panelTop, BorderLayout.NORTH);
        add(panelCenter, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // Ensure visibility

        // Load Employee Data
        loadEmployeeData(username);

        // Add Action Listener for Apply for Leave Button
        btnApplyLeave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ApplyForLeave(username); // Pass the username to ApplyForLeave
            }
        });

        // Add Action Listener for Back Button
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                // Optionally: new LoginE().setVisible(true);
            }
        });
    }

    private JPanel createTablePanel(String title, String[] columnNames, DefaultTableModel model) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void loadEmployeeData(String username) {
    try {
        Conn c = new Conn();

        // Fetch Employee Profile
        String query = "SELECT * FROM employee WHERE username = ?";
        PreparedStatement ps = c.c.prepareStatement(query);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            DefaultTableModel modelProfile = (DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0)).getComponent(0)).getViewport().getView()).getModel();
            modelProfile.setRowCount(0); // Clear existing rows
            modelProfile.addRow(new Object[]{"ID", rs.getString("empid")});
            modelProfile.addRow(new Object[]{"Name", rs.getString("name")});
            modelProfile.addRow(new Object[]{"Email", rs.getString("email")});
            modelProfile.addRow(new Object[]{"Position", rs.getString("position")});
        }

        // Fetch Current Projects (where the user is either assignee or project manager)
        query = "SELECT DISTINCT p.project_name " +
                "FROM projects p " +
                "LEFT JOIN tasks t ON p.project_id = t.project_id " +
                "WHERE t.assignee = ? OR p.project_manager = ?";
        ps = c.c.prepareStatement(query);
        ps.setString(1, username);
        ps.setString(2, username);
        rs = ps.executeQuery();
        DefaultTableModel modelProjects = (DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(1)).getComponent(0)).getViewport().getView()).getModel();
        modelProjects.setRowCount(0); // Clear existing rows
        while (rs.next()) {
            modelProjects.addRow(new Object[]{rs.getString("project_name")});
        }

        // Fetch Leave Details
        query = "SELECT leave_type, from_date, to_date " +
                "FROM leaves " +
                "WHERE empid = (SELECT empid FROM employee WHERE username = ?)";
        ps = c.c.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        DefaultTableModel modelLeaves = (DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(3)).getComponent(0)).getViewport().getView()).getModel();
        modelLeaves.setRowCount(0); // Clear existing rows
        while (rs.next()) {
            modelLeaves.addRow(new Object[]{
                rs.getString("leave_type"),
                rs.getDate("from_date"),
                rs.getDate("to_date")
            });
        }

        // Fetch Tasks
        query = "SELECT task_name, due_date, priority FROM tasks WHERE assignee = ?";
        ps = c.c.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        DefaultTableModel modelTasks = (DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(2)).getComponent(0)).getViewport().getView()).getModel();
        modelTasks.setRowCount(0); // Clear existing rows
        while (rs.next()) {
            modelTasks.addRow(new Object[]{
                rs.getString("task_name"),
                rs.getDate("due_date"),
                rs.getString("priority")
            });
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeDashboard("ahmed123")); // Replace with actual username for testing
    }
}
