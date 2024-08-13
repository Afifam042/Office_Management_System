package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class ViewProjects extends JFrame implements ActionListener {

    private JTable projectTable;
    private JButton backButton;

    ViewProjects() {
        setLayout(new BorderLayout());
        setTitle("View Projects");
        getContentPane().setBackground(Color.LIGHT_GRAY); // Grey background

        projectTable = new JTable();
        JScrollPane sp = new JScrollPane(projectTable);
        add(sp, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK); // Black background
        backButton.setForeground(Color.WHITE); // White text
        backButton.addActionListener(this);
        add(backButton, BorderLayout.SOUTH);

        loadProjects();

        setSize(800, 600);
        setLocation(200, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void loadProjects() {
        try {
            Conn c = new Conn();
            String query = "SELECT * FROM projects";
            ResultSet rs = c.s.executeQuery(query);
            projectTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            setVisible(false);
            new Home(); // Assumes there is a Home class to return to
        }
    }

    public static void main(String[] args) {
        new ViewProjects();
    }
}
