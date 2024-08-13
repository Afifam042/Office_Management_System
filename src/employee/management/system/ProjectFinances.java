package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProjectFinances extends JFrame {

    private JTable tableProjectFinances;
    private JButton btnBack;
    private JPanel panel;

    public ProjectFinances() {
        setTitle("Project Finances");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.GRAY); // Set background color

        // Main Panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Project Finances Table
        String[] columnNames = {"Project ID", "Project Name", "Budget", "Expenses", "Remaining Budget"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        tableProjectFinances = new JTable(tableModel);
        tableProjectFinances.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        tableProjectFinances.setBackground(Color.WHITE);
        tableProjectFinances.setForeground(Color.BLACK);
        tableProjectFinances.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane spProjectFinances = new JScrollPane(tableProjectFinances);
        panel.add(spProjectFinances, BorderLayout.CENTER);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(btnBack);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);

        // Load Project Finances
        loadProjectFinances();

        // Button Actions
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new Finance(); // Return to the Finance main page
            }
        });

        setSize(800, 600); // Adjusted size for better view
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadProjectFinances() {
        try {
            Conn c = new Conn();
            String query = "SELECT p.project_id, p.project_name, pf.budget, pf.expenses, " +
                           "(pf.budget - pf.expenses) AS remaining_budget " +
                           "FROM projects p " +
                           "JOIN project_finances pf ON p.project_id = pf.project_id";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tableProjectFinances.getModel();
            model.setRowCount(0); // Clear existing data

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("project_id"),
                    rs.getString("project_name"),
                    rs.getDouble("budget"),
                    rs.getDouble("expenses"),
                    rs.getDouble("remaining_budget")
                };
                model.addRow(row);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ProjectFinances();
    }
}
