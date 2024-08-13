package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ResourceAllocation extends JFrame implements ActionListener {

    JTextField tfResourceName, tfAllocationHours, tfDetails;
    JComboBox<String> cbTask;
    JButton submitButton, backButton;

    ResourceAllocation() {
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY); // Grey background

        JLabel lblResourceName = new JLabel("Resource Name:");
        lblResourceName.setBounds(50, 30, 150, 30);
        add(lblResourceName);

        tfResourceName = new JTextField();
        tfResourceName.setBounds(200, 30, 200, 30);
        add(tfResourceName);

        JLabel lblAllocationHours = new JLabel("Hours Allocated:");
        lblAllocationHours.setBounds(50, 80, 150, 30);
        add(lblAllocationHours);

        tfAllocationHours = new JTextField();
        tfAllocationHours.setBounds(200, 80, 200, 30);
        add(tfAllocationHours);

        JLabel lblTask = new JLabel("Task:");
        lblTask.setBounds(50, 130, 150, 30);
        add(lblTask);

        cbTask = new JComboBox<>();
        cbTask.setBounds(200, 130, 200, 30);
        add(cbTask);

        JLabel lblDetails = new JLabel("Additional Details:");
        lblDetails.setBounds(50, 180, 150, 30);
        add(lblDetails);

        tfDetails = new JTextField();
        tfDetails.setBounds(200, 180, 200, 30);
        add(tfDetails);

        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 230, 100, 30);
        submitButton.setBackground(Color.BLACK); // Black button
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        add(submitButton);

        backButton = new JButton("Back");
        backButton.setBounds(50, 230, 100, 30);
        backButton.setBackground(Color.BLACK); // Black button
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        loadTasks();

        setSize(500, 320);
        setLocation(400, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadTasks() {
        try {
            Conn c = new Conn();
            String query = "SELECT task_name FROM tasks";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                cbTask.addItem(rs.getString("task_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            String resourceName = tfResourceName.getText();
            String allocationHours = tfAllocationHours.getText();
            String task = (String) cbTask.getSelectedItem();
            String details = tfDetails.getText();

            try {
                Conn c = new Conn();
                String query = "INSERT INTO resource_allocation (resource_name, hours_allocated, task, details) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, resourceName);
                ps.setString(2, allocationHours);
                ps.setString(3, task);
                ps.setString(4, details);

                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Resource allocated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to allocate resource.");
                }

                // Clear fields after submission
                tfResourceName.setText("");
                tfAllocationHours.setText("");
                tfDetails.setText("");
                cbTask.setSelectedIndex(0);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ResourceAllocation();
    }
}
