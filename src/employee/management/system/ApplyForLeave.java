package employee.management.system;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ApplyForLeave extends JFrame {

    private JLabel lblName, lblStartDate, lblEndDate, lblReason;
    private JTextField tfName;
    private JDateChooser dcStartDate, dcEndDate;
    private JComboBox<String> cbReason;
    private JTextArea taOtherReason;
    private JButton btnSubmit, btnBack;
    private String username;

    // Predefined reasons
    private final String[] predefinedReasons = {
        "Medical",
        "Personal",
        "Vacation",
        "Family Emergency"
    };

    ApplyForLeave(String username) {
        this.username = username;
        setLayout(new BorderLayout());
        setTitle("Apply for Leave");
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.LIGHT_GRAY);

        lblName = new JLabel("Name: ");
        lblStartDate = new JLabel("Start Date: ");
        lblEndDate = new JLabel("End Date: ");
        lblReason = new JLabel("Reason: ");

        tfName = new JTextField();
        
        // Date Choosers
        dcStartDate = new JDateChooser();
        dcEndDate = new JDateChooser();
        dcStartDate.setDateFormatString("yyyy-MM-dd");
        dcEndDate.setDateFormatString("yyyy-MM-dd");

        // Dropdown for predefined reasons
        cbReason = new JComboBox<>(predefinedReasons);
        cbReason.setBackground(Color.WHITE);

        // Text area for other reasons
        taOtherReason = new JTextArea(4, 30);
        taOtherReason.setLineWrap(true);
        taOtherReason.setWrapStyleWord(true);
        taOtherReason.setVisible(false); // Initially hidden

        formPanel.add(lblName);
        formPanel.add(tfName);
        formPanel.add(lblStartDate);
        formPanel.add(dcStartDate);
        formPanel.add(lblEndDate);
        formPanel.add(dcEndDate);
        formPanel.add(lblReason);
        formPanel.add(cbReason);

        // Checkbox for "Other Reasons"
        JCheckBox cbOtherReason = new JCheckBox("Other Reason");
        cbOtherReason.setBackground(Color.LIGHT_GRAY);
        formPanel.add(cbOtherReason);
        formPanel.add(new JScrollPane(taOtherReason));

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBackground(Color.LIGHT_GRAY);

        btnSubmit = new JButton("Submit");
        btnBack = new JButton("Back");

        btnSubmit.setBackground(Color.BLACK);
        btnSubmit.setForeground(Color.WHITE);
        btnBack.setBackground(Color.BLACK);
        btnBack.setForeground(Color.WHITE);

        buttonsPanel.add(btnSubmit);
        buttonsPanel.add(btnBack);

        add(buttonsPanel, BorderLayout.SOUTH);

        // Button Actions
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyForLeave();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                // new EmployeeDashboard(username).setVisible(true); // Uncomment this and replace with your dashboard class if needed
            }
        });

        // Checkbox Action
        cbOtherReason.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taOtherReason.setVisible(cbOtherReason.isSelected());
            }
        });

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void applyForLeave() {
        String name = tfName.getText();
        java.util.Date startDate = dcStartDate.getDate();
        java.util.Date endDate = dcEndDate.getDate();
        String reason = (String) cbReason.getSelectedItem();
        String otherReason = taOtherReason.getText();

        if (name.isEmpty() || startDate == null || endDate == null || (reason == null && otherReason.isEmpty())) {
            JOptionPane.showMessageDialog(this, "Please fill out all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
        java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

        try {
            Conn c = new Conn();
            // Check the number of leave days already taken in the current year
            String query = "SELECT SUM(DATEDIFF(to_date, from_date) + 1) as totalTaken FROM leaves WHERE empid = ? AND YEAR(from_date) = YEAR(CURDATE())";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setInt(1, getEmployeeId()); // Method to get employee ID from username
            ResultSet rs = ps.executeQuery();
            int daysTaken = 0;
            if (rs.next()) {
                daysTaken = rs.getInt("totalTaken");
            }

            int maxLeaves = 21;
            int leaveDays = (int) ((sqlEndDate.getTime() - sqlStartDate.getTime()) / (1000 * 60 * 60 * 24) + 1);
            if (daysTaken + leaveDays > maxLeaves) {
                JOptionPane.showMessageDialog(this, "You cannot apply for more than " + maxLeaves + " days of leave in a year.", "Leave Limit Exceeded", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Use predefined reason or other reason
            String finalReason = (reason != null && !reason.equals("Other Reason")) ? reason : otherReason;

            // Insert leave details
            String insertQuery = "INSERT INTO leaves (empid, from_date, to_date, leave_type) VALUES (?, ?, ?, ?)";
            PreparedStatement insertPs = c.c.prepareStatement(insertQuery);
            insertPs.setInt(1, getEmployeeId()); // Method to get employee ID from username
            insertPs.setDate(2, sqlStartDate);
            insertPs.setDate(3, sqlEndDate);
            insertPs.setString(4, finalReason);
            insertPs.executeUpdate();

            JOptionPane.showMessageDialog(this, "Leave application submitted successfully.");
            dispose(); // Close the current window
            // new EmployeeDashboard(username).setVisible(true); // Uncomment this and replace with your dashboard class if needed
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while applying for leave.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int getEmployeeId() {
        int empid = -1;
        try {
            Conn c = new Conn();
            String query = "SELECT empid FROM employee WHERE username = ?";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                empid = rs.getInt("empid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empid;
    }

    public static void main(String[] args) {
        // Sample username for testing
        new ApplyForLeave("employee");
    }
}
