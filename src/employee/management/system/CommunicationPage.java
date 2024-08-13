package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import net.proteanit.sql.DbUtils;

public class CommunicationPage extends JFrame implements ActionListener {

    JTable announcementsTable;
    JButton createAnnouncementButton, viewNewslettersButton, sendNotificationButton, backButton;

    CommunicationPage() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Announcements Table
        announcementsTable = new JTable();
        JScrollPane announcementsScrollPane = new JScrollPane(announcementsTable);
        announcementsScrollPane.setBounds(20, 20, 860, 300);
        add(announcementsScrollPane);

        // Buttons
        createAnnouncementButton = new JButton("Create Announcement");
        createAnnouncementButton.setBounds(20, 340, 200, 30);
        createAnnouncementButton.addActionListener(this);
        add(createAnnouncementButton);

        viewNewslettersButton = new JButton("View Newsletters");
        viewNewslettersButton.setBounds(240, 340, 200, 30);
        viewNewslettersButton.addActionListener(this);
        add(viewNewslettersButton);

        sendNotificationButton = new JButton("Send Notification");
        sendNotificationButton.setBounds(460, 340, 200, 30);
        sendNotificationButton.addActionListener(this);
        add(sendNotificationButton);

        backButton = new JButton("Back");
        backButton.setBounds(680, 340, 200, 30);
        backButton.addActionListener(this);
        add(backButton);

        // Fetch and display existing announcements
        fetchAnnouncements();

        setSize(900, 420);
        setLocation(300, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createAnnouncementButton) {
            // Open a form to create a new announcement
            new CreateAnnouncementForm(this); // Pass the reference to the Home page
        } else if (ae.getSource() == viewNewslettersButton) {
            // Open a form to view newsletters
            new ViewNewslettersForm();
        } else if (ae.getSource() == sendNotificationButton) {
            // Open a form to send notifications
            new SendNotificationForm();
        } else {
            // Go back to the home page
            setVisible(false);
            new Home();
        }
    }

    public void refreshAnnouncements() {
        // Method to refresh announcements when called from CreateAnnouncementForm
        fetchAnnouncements();
    }

    private void fetchAnnouncements() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM announcements");
            announcementsTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CommunicationPage();
    }
}

class CreateAnnouncementForm extends JFrame implements ActionListener {

    JTextField announcementField;
    JButton createButton, cancelButton;

    CommunicationPage communicationPage; // Reference to CommunicationPage

    CreateAnnouncementForm(CommunicationPage communicationPage) {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        this.communicationPage = communicationPage; // Initialize the reference

        JLabel titleLabel = new JLabel("Create Announcement");
        titleLabel.setBounds(20, 20, 200, 20);
        add(titleLabel);

        JLabel announcementLabel = new JLabel("Announcement:");
        announcementLabel.setBounds(20, 60, 100, 20);
        add(announcementLabel);

        announcementField = new JTextField();
        announcementField.setBounds(120, 60, 600, 20);
        add(announcementField);

        createButton = new JButton("Create");
        createButton.setBounds(120, 100, 100, 30);
        createButton.addActionListener(this);
        add(createButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(240, 100, 100, 30);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setSize(750, 180);
        setLocation(350, 250);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createButton) {
            createAnnouncement();
            setVisible(false);
        } else {
            setVisible(false);
        }
    }

    private void createAnnouncement() {
        String announcementText = announcementField.getText();
        if (!announcementText.isEmpty()) {
            try {
                Conn c = new Conn();
                String query = "INSERT INTO announcements (announcement_text) VALUES (?)";
                PreparedStatement preparedStatement = c.c.prepareStatement(query);
                preparedStatement.setString(1, announcementText);

                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "Announcement created successfully!");
                    // Refresh announcements on the CommunicationPage
                    communicationPage.refreshAnnouncements();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to create announcement. Please try again.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please enter announcement text.");
        }
    }
}

class ViewNewslettersForm extends JFrame {
}

class SendNotificationForm extends JFrame {
}

//srp
//each class has a distinct responsibility:

//CommunicationPage: UI handling, database interaction, and navigation.
//CreateAnnouncementForm: UI handling and database interaction for creating announcements.
//ViewNewslettersForm: UI handling for viewing newsletters.
//SendNotificationForm: UI handling for sending notifications.