package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginE extends JFrame implements ActionListener {

    JTextField tfusername;
    JPasswordField pfpassword;
    JButton logoutButton; // Add a logout button

    LoginE() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(40, 20, 100, 30);
        add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(150, 20, 150, 30);
        add(tfusername);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(40, 70, 100, 30);
        add(lblpassword);

        pfpassword = new JPasswordField();
        pfpassword.setBounds(150, 70, 150, 30);
        add(pfpassword);

        JButton login = new JButton("LOGIN");
        login.setBounds(150, 140, 150, 30);
        login.setBackground(Color.BLACK);
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(150, 180, 150, 30);
        logoutButton.setBackground(Color.BLACK);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.addActionListener(this);
        add(logoutButton);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/second.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(350, 0, 200, 200);
        add(image);

        setSize(600, 300);
        setLocation(450, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == logoutButton) {
            setVisible(false);
            new Splash(); // Redirect to Splash screen
        } else {
            try {
                String username = tfusername.getText();
                char[] passwordChars = pfpassword.getPassword();
                String password = new String(passwordChars);

                Conn c = new Conn();
                String query = "SELECT empid FROM employee WHERE username = ? AND password = ?";
                PreparedStatement ps = c.c.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new EmployeeDashboard(username); // Pass username instead of empId
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }

                pfpassword.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new LoginE();
    }
}
