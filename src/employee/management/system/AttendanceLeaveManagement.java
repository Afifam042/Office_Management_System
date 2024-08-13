package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class AttendanceLeaveManagement extends JFrame implements ActionListener {

    JTable table;
    Choice cemployeeId;
    JButton search, print, back, approve, disapprove;
    private static final int MAX_LEAVES = 21;

    AttendanceLeaveManagement() {
        // Set background color to light grey
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        // Search label
        JLabel searchlbl = new JLabel("Search by Employee Id");
        searchlbl.setBounds(20, 20, 150, 20);
        searchlbl.setFont(new Font("serif", Font.PLAIN, 16));
        add(searchlbl);

        // Choice for employee IDs
        cemployeeId = new Choice();
        cemployeeId.setBounds(180, 20, 150, 20);
        add(cemployeeId);

        // Load employee IDs
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT empid FROM employee");
            while (rs.next()) {
                cemployeeId.add(rs.getString("empid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table to display attendance data
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 900, 600);
        add(jsp);

        // Search button
        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        search.setBackground(Color.BLACK);
        search.setForeground(Color.WHITE);
        add(search);

        // Print button
        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        print.setBackground(Color.BLACK);
        print.setForeground(Color.WHITE);
        add(print);

        // Back button
        back = new JButton("Back");
        back.setBounds(220, 70, 80, 20);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        // Approve button
        approve = new JButton("Approve");
        approve.setBounds(320, 70, 80, 20);
        approve.addActionListener(this);
        approve.setBackground(Color.GREEN);
        approve.setForeground(Color.WHITE);
        add(approve);

        // Disapprove button
        disapprove = new JButton("Disapprove");
        disapprove.setBounds(420, 70, 100, 20);
        disapprove.addActionListener(this);
        disapprove.setBackground(Color.RED);
        disapprove.setForeground(Color.WHITE);
        add(disapprove);

        // Set window size and location
        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String empId = cemployeeId.getSelectedItem();
            // Display attendance and leave information
            String query = "SELECT e.empid, e.name, a.attendance_date, l.leave_id, l.from_date, l.to_date, l.leave_type, l.status " +
                           "FROM employee e " +
                           "LEFT JOIN employee_attendance a ON e.empid = a.empid " +
                           "LEFT JOIN leaves l ON e.empid = l.empid " +
                           "WHERE e.empid = ?";
            try {
                Conn c = new Conn();
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, empId);
                ResultSet rs = ps.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == print) {
            try {
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        } else if (ae.getSource() == approve) {
            updateLeaveStatus("Approved");
        } else if (ae.getSource() == disapprove) {
            updateLeaveStatus("Disapproved");
        }
    }

    private void updateLeaveStatus(String newStatus) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String leaveId = table.getValueAt(selectedRow, 3).toString(); // Assuming leave_id is in column index 3
            String currentStatus = table.getValueAt(selectedRow, 7).toString(); // Assuming status is in column index 7

            // Debugging output
            System.out.println("Leave ID: " + leaveId);
            System.out.println("Current Status: '" + currentStatus + "'");
            System.out.println("Current Status Length: " + currentStatus.length());

            // Check if the current status is "Pending"
            if ("Pending".equalsIgnoreCase(currentStatus.trim())) {
                try {
                    Conn c = new Conn();
                    String updateQuery = "UPDATE leaves SET status = ? WHERE leave_id = ?";
                    PreparedStatement ps = c.c.prepareStatement(updateQuery);
                    ps.setString(1, newStatus); // Set the new status
                    ps.setString(2, leaveId);   // Set the leave ID as a string
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Leave request status updated to '" + newStatus + "'.");
                        // Refresh the table data
                        String empId = cemployeeId.getSelectedItem();
                        String query = "SELECT e.empid, e.name, a.attendance_date, l.leave_id, l.from_date, l.to_date, l.leave_type, l.status " +
                                       "FROM employee e " +
                                       "LEFT JOIN employee_attendance a ON e.empid = a.empid " +
                                       "LEFT JOIN leaves l ON e.empid = l.empid " +
                                       "WHERE e.empid = ?";
                        PreparedStatement refreshPs = c.c.prepareStatement(query);
                        refreshPs.setString(1, empId);
                        ResultSet rs = refreshPs.executeQuery();
                        table.setModel(DbUtils.resultSetToTableModel(rs));
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to update leave request status.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selected leave request is not in 'Pending' status.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No leave request selected.");
        }
    }

    public static void main(String[] args) {
        new AttendanceLeaveManagement();
    }
}
