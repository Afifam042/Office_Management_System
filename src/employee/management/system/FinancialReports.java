package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FinancialReports extends JFrame {

    private JTable tableReports;
    private JButton btnBack;
    private JPanel panel;

    public FinancialReports() {
        setTitle("Financial Reports");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.GRAY); // Set background color

        // Main Panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Financial Reports Table
        String[] columnNames = {"Title", "Content"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        tableReports = new JTable(tableModel);
        tableReports.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        tableReports.setBackground(Color.WHITE);
        tableReports.setForeground(Color.BLACK);
        tableReports.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane spReports = new JScrollPane(tableReports);
        panel.add(spReports, BorderLayout.CENTER);

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

        // Load Reports
        loadReports();

        // Button Actions
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new Finance(); // Return to the Finance main page
            }
        });

        setSize(700, 500); // Adjusted size
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadReports() {
        try {
            Conn c = new Conn();
            String query = "SELECT report_title, report_content FROM financial_reports"; // Replace with your actual query
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tableReports.getModel();
            model.setRowCount(0); // Clear existing data

            while (rs.next()) {
                Object[] row = {
                    rs.getString("report_title"),
                    rs.getString("report_content")
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
        new FinancialReports();
    }
}
