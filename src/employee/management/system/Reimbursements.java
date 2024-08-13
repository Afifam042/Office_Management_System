package employee.management.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Reimbursements extends JFrame {

    private JTable tableReimbursements;
    private JButton btnBack;
    private JPanel panel;

    public Reimbursements() {
        setTitle("Reimbursements");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.GRAY); // Set background color

        // Main Panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        // Reimbursements Table
        String[] columnNames = {"Name", "Amount", "Reason", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        tableReimbursements = new JTable(tableModel);
        tableReimbursements.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        tableReimbursements.setBackground(Color.WHITE);
        tableReimbursements.setForeground(Color.BLACK);
        tableReimbursements.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane spReimbursements = new JScrollPane(tableReimbursements);
        panel.add(spReimbursements, BorderLayout.CENTER);

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

        // Load Reimbursements
        loadReimbursements();

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

    private void loadReimbursements() {
        try {
            Conn c = new Conn();
            String query = "SELECT e.name, r.amount, r.reason, r.status " +
                           "FROM reimbursements r JOIN employee e ON r.empid = e.empid";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) tableReimbursements.getModel();
            model.setRowCount(0); // Clear existing data

            while (rs.next()) {
                Object[] row = {
                    rs.getString("name"),
                    rs.getDouble("amount"),
                    rs.getString("reason"),
                    rs.getString("status")
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
        new Reimbursements();
    }
}
