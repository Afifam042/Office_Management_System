package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class ViewEmployee extends JFrame implements ActionListener {

    JTable table;
    Choice cemployeeId;
    JButton search, print, update, back;

    ViewEmployee() {
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
            ResultSet rs = c.s.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                cemployeeId.add(rs.getString("empId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Table to display employee data
        table = new JTable();

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM employee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Scroll pane for table
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

        // Update button
        update = new JButton("Update");
        update.setBounds(220, 70, 80, 20);
        update.addActionListener(this);
        update.setBackground(Color.BLACK);
        update.setForeground(Color.WHITE);
        add(update);

        // Back button
        back = new JButton("Back");
        back.setBounds(320, 70, 80, 20);
        back.addActionListener(this);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        // Set window size and location
        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String query = "SELECT * FROM employee WHERE empId = '" + cemployeeId.getSelectedItem() + "'";
            try {
                Conn c = new Conn();
                ResultSet rs = c.s.executeQuery(query);
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
        } else if (ae.getSource() == update) {
            setVisible(false);
            new UpdateEmployee(cemployeeId.getSelectedItem());
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Home();
        }
    }

    public static void main(String[] args) {
        new ViewEmployee();
    }
}
