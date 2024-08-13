package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewSalaries extends JFrame {

    private JTextArea taSalaries;
    private JButton btnBack;
    private JPanel panel;

    public ViewSalaries() {
        setTitle("View Salaries");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Main Panel
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);

        // Title Label
        JLabel lblTitle = new JLabel("Employee Salaries", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        panel.add(lblTitle, BorderLayout.NORTH);

        // Salary Display Area
        taSalaries = new JTextArea(20, 50);
        taSalaries.setEditable(false);
        taSalaries.setFont(new Font("Arial", Font.PLAIN, 14));
        taSalaries.setMargin(new Insets(10, 10, 10, 10));
        taSalaries.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane spSalaries = new JScrollPane(taSalaries);
        panel.add(spSalaries, BorderLayout.CENTER);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setFocusPainted(false);
        btnBack.setBorder(BorderFactory.createEtchedBorder());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnBack);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add panel to frame
        add(panel, BorderLayout.CENTER);

        // Load Salaries
        loadSalaries();

        // Button Actions
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                new Finance(); // Return to the Finance main page
            }
        });

        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadSalaries() {
        try {
            Conn c = new Conn();
            String query = "SELECT e.name, e.designation, s.salary, " +
                           "CASE WHEN s.paid THEN 'Paid' ELSE 'Not Paid' END AS payment_status " +
                           "FROM salaries s JOIN employee e ON s.empid = e.empid";
            PreparedStatement ps = c.c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Name: ").append(rs.getString("name"))
                  .append("\nDesignation: ").append(rs.getString("designation"))
                  .append("\nSalary: $").append(rs.getDouble("salary"))
                  .append("\nStatus: ").append(rs.getString("payment_status"))
                  .append("\n\n");
            }

            taSalaries.setText(sb.toString());

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewSalaries();
    }
}
