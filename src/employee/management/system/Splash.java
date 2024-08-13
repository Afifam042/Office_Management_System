package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Splash extends JFrame implements ActionListener {

    JButton employeeButton;
    JButton adminButton;
    JButton financeDeptButton;

    Splash() {
        // Setting up the frame
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Adding heading
        JLabel heading = new JLabel("OFFICE MANAGEMENT SYSTEM");
        heading.setBounds(80, 30, 1200, 60);
        heading.setFont(new Font("serif", Font.PLAIN, 60));
        heading.setForeground(Color.BLACK);
        add(heading);

        // Adding background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/front.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1200, 700, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1200, 700);
        add(image);

        // Adding Employee button
employeeButton = new JButton("Employee");
employeeButton.setBounds(185, 400, 200, 70); // Adjusted x-coordinate
employeeButton.setBackground(Color.BLACK);
employeeButton.setForeground(Color.WHITE);
employeeButton.addActionListener(this);
image.add(employeeButton);

// Adding Admin button
adminButton = new JButton("Admin");
adminButton.setBounds(395, 400, 200, 70); // Adjusted x-coordinate
adminButton.setBackground(Color.BLACK);
adminButton.setForeground(Color.WHITE);
adminButton.addActionListener(this);
image.add(adminButton);

// Adding Finance Dept button
financeDeptButton = new JButton("Finance Dept");
financeDeptButton.setBounds(605, 400, 200, 70); // Adjusted x-coordinate
financeDeptButton.setBackground(Color.BLACK);
financeDeptButton.setForeground(Color.WHITE);
financeDeptButton.addActionListener(this);
image.add(financeDeptButton);


        // Setting frame properties
        setSize(1100, 650);
        setLocation(200, 50);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminButton) {
            // Open Login page when Admin button is clicked
            setVisible(false);
            new Login();
        } else if (ae.getSource() == employeeButton) {
            // Open Login page for Employee when Employee button is clicked
            setVisible(false);
            new LoginE(); // Ensure Login.java is properly implemented
        } else if (ae.getSource() == financeDeptButton) {
            // Open LoginF page when Finance Dept button is clicked
            setVisible(false);
            new LoginF(); // Ensure LoginF.java is properly implemented
        }
    }

    public static void main(String args[]) {
        new Splash();
    }
}
