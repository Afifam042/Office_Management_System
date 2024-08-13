package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import java.awt.event.*;

public class EmployeeDocumentsView extends JFrame implements ActionListener {

    JTable table;
    Choice cemployeeId;
    JButton search, openResumeFolder, openContractFolder, openPerformanceFolder, back;

    EmployeeDocumentsView() {
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

        // Table to display employee documents
        table = new JTable();

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

        // Open Resume Folder button
        openResumeFolder = new JButton("Open Resume Folder");
        openResumeFolder.setBounds(120, 70, 150, 20);
        openResumeFolder.addActionListener(this);
        openResumeFolder.setBackground(Color.BLACK);
        openResumeFolder.setForeground(Color.WHITE);
        add(openResumeFolder);

        // Open Contract Folder button
        openContractFolder = new JButton("Open Contract Folder");
        openContractFolder.setBounds(280, 70, 170, 20);
        openContractFolder.addActionListener(this);
        openContractFolder.setBackground(Color.BLACK);
        openContractFolder.setForeground(Color.WHITE);
        add(openContractFolder);

        // Open Performance Folder button
        openPerformanceFolder = new JButton("Open Performance Folder");
        openPerformanceFolder.setBounds(460, 70, 200, 20);
        openPerformanceFolder.addActionListener(this);
        openPerformanceFolder.setBackground(Color.BLACK);
        openPerformanceFolder.setForeground(Color.WHITE);
        add(openPerformanceFolder);

        // Back button
        back = new JButton("Back");
        back.setBounds(670, 70, 80, 20);
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
            String empId = cemployeeId.getSelectedItem();
            loadEmployeeDocuments(empId);
        } else if (ae.getSource() == openResumeFolder) {
            String empId = cemployeeId.getSelectedItem();
            openFolder("resume_folder_path", empId);
        } else if (ae.getSource() == openContractFolder) {
            String empId = cemployeeId.getSelectedItem();
            openFolder("contract_path", empId);
        } else if (ae.getSource() == openPerformanceFolder) {
            String empId = cemployeeId.getSelectedItem();
            openFolder("performance_review_path", empId);
        } else {
            setVisible(false);
            new Home();
        }
    }

    private void loadEmployeeDocuments(String empId) {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM employee_documents WHERE empId = '" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFolder(String columnName, String empId) {
        try {
            Conn c = new Conn();
            String query = "SELECT " + columnName + " FROM employee_documents WHERE empId = '" + empId + "'";
            ResultSet rs = c.s.executeQuery(query);
            if (rs.next()) {
                String folderPath = rs.getString(columnName);
                Desktop.getDesktop().open(new File(folderPath));
            } else {
                JOptionPane.showMessageDialog(this, "Folder not found for the selected employee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EmployeeDocumentsView();
    }
}
