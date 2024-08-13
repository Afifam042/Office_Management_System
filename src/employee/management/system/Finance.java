package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Finance extends JFrame {

    private JButton btnViewSalaries, btnReimbursements, btnReports, btnProjectFinances, btnLogout;

    public Finance() {
        setTitle("Finance Department");
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10)); // Updated grid layout to accommodate 5 buttons
        mainPanel.setBackground(Color.LIGHT_GRAY);

        // Buttons
        btnViewSalaries = new JButton("View Salaries");
        btnReimbursements = new JButton("Reimbursements");
        btnReports = new JButton("Financial Reports");
        btnProjectFinances = new JButton("Manage Project Finances"); // New button
        btnLogout = new JButton("Logout");

        btnViewSalaries.setBackground(Color.BLACK);
        btnViewSalaries.setForeground(Color.WHITE);
        btnReimbursements.setBackground(Color.BLACK);
        btnReimbursements.setForeground(Color.WHITE);
        btnReports.setBackground(Color.BLACK);
        btnReports.setForeground(Color.WHITE);
        btnProjectFinances.setBackground(Color.BLACK); // New button styling
        btnProjectFinances.setForeground(Color.WHITE);
        btnLogout.setBackground(Color.BLACK);
        btnLogout.setForeground(Color.WHITE);

        mainPanel.add(btnViewSalaries);
        mainPanel.add(btnReimbursements);
        mainPanel.add(btnReports);
        mainPanel.add(btnProjectFinances); // Adding new button to panel
        mainPanel.add(btnLogout);

        add(mainPanel, BorderLayout.CENTER);

        // Button Actions
        btnViewSalaries.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewSalaries();
            }
        });

        btnReimbursements.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Reimbursements();
            }
        });

        btnReports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FinancialReports();
            }
        });

        btnProjectFinances.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProjectFinances(); // Open the Project Finances page
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logout and return to Splash screen
                dispose(); // Close the current window
                new Splash(); // Open the Splash screen
            }
        });

        setSize(400, 350); // Adjusted size for the added button
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Finance();
    }
}
