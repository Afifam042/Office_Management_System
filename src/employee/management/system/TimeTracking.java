package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TimeTracking extends JFrame implements ActionListener {

    JComboBox<String> cbTask;
    JComboBox<String> cbHoursSpent;
    JButton submitButton, backButton;

    TimeTracking() {
        setLayout(null);
        getContentPane().setBackground(Color.GRAY);  // Set background color to grey

        // Label for task selection
        JLabel lblSelectTask = new JLabel("Select Task:");
        lblSelectTask.setBounds(50, 30, 150, 30);
        lblSelectTask.setForeground(Color.WHITE); // Set label color to white
        add(lblSelectTask);

        // Combo box for task selection
        cbTask = new JComboBox<>();
        cbTask.setBounds(200, 30, 200, 30);
        add(cbTask);

        // Label for time spent
        JLabel lblTimeSpent = new JLabel("Time Spent (hours):");
        lblTimeSpent.setBounds(50, 80, 150, 30);
        lblTimeSpent.setForeground(Color.WHITE); // Set label color to white
        add(lblTimeSpent);

        // Combo box for hours spent
        cbHoursSpent = new JComboBox<>();
        populateHoursDropdown();
        cbHoursSpent.setBounds(200, 80, 100, 30);
        add(cbHoursSpent);

        // Submit button
        submitButton = new JButton("Submit");
        submitButton.setBounds(200, 130, 100, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(this);
        add(submitButton);

        // Back button
        backButton = new JButton("Back");
        backButton.setBounds(50, 130, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        loadTasks();

        // Set window size and location
        setSize(500, 250);
        setLocation(400, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void populateHoursDropdown() {
        for (int i = 1; i <= 100; i++) {
            cbHoursSpent.addItem(String.valueOf(i));
        }
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
            String selectedTask = (String) cbTask.getSelectedItem();
            String hoursSpent = (String) cbHoursSpent.getSelectedItem();

            try {
                Conn c = new Conn();
                String query = "UPDATE tasks SET time_spent = time_spent + ? WHERE task_name = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(hoursSpent));
                ps.setString(2, selectedTask);
                
                int result = ps.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "Time logged successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to log time.");
                }

                // Clear fields after submission
                cbHoursSpent.setSelectedIndex(0);
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == backButton) {
            setVisible(false);
            new Home(); // Adjust this to match the actual Home class
        }
    }

    public static void main(String[] args) {
        new TimeTracking();
    }
}
