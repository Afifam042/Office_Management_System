package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Home extends JFrame implements ActionListener {

    JButton view, add, update, remove, attendanceLeaveManagement, viewDocuments, surveyManagement, communicationPageButton, performanceAppraisalButton;
    JButton createProject, manageTasks, trackTime, monitorProgress, logoutButton;

    JLabel announcementsLabel;
    JTextArea announcementsTextArea;

    String empID = "defaultEmpID"; // Placeholder, set appropriately

    Home() {
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/home.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 0, 1120, 730);
        add(image);

        JLabel heading = new JLabel("Administration Dashboard");
        heading.setBounds(380, 10, 400, 40);
        heading.setFont(new Font("Raleway", Font.BOLD, 25));
        heading.setForeground(Color.BLACK);
        image.add(heading);

        add = new JButton("Add Employee");
        add.setBounds(650, 80, 150, 40);
        add.addActionListener(this);
        image.add(add);

        view = new JButton("View Employees");
        view.setBounds(820, 80, 150, 40);
        view.addActionListener(this);
        image.add(view);

        update = new JButton("Update Employee");
        update.setBounds(650, 140, 150, 40);
        update.addActionListener(this);
        image.add(update);

        remove = new JButton("Remove Employee");
        remove.setBounds(820, 140, 150, 40);
        remove.addActionListener(this);
        image.add(remove);

        attendanceLeaveManagement = new JButton("Track Attendance and Leave");
        attendanceLeaveManagement.setBounds(650, 200, 320, 40);
        attendanceLeaveManagement.addActionListener(this);
        image.add(attendanceLeaveManagement);

        viewDocuments = new JButton("View Employee Documents");
        viewDocuments.setBounds(650, 260, 320, 40);
        viewDocuments.addActionListener(this);
        image.add(viewDocuments);

        surveyManagement = new JButton("Survey Management");
        surveyManagement.setBounds(650, 320, 320, 40);
        surveyManagement.addActionListener(this);
        image.add(surveyManagement);

        communicationPageButton = new JButton("Communication Page");
        communicationPageButton.setBounds(650, 380, 320, 40);
        communicationPageButton.addActionListener(this);
        image.add(communicationPageButton);

        performanceAppraisalButton = new JButton("Performance Appraisal");
        performanceAppraisalButton.setBounds(650, 440, 320, 40);
        performanceAppraisalButton.addActionListener(this);
        image.add(performanceAppraisalButton);

        // New buttons for project management features
        createProject = new JButton("Project Creation");
        createProject.setBounds(30, 80, 200, 40);
        createProject.addActionListener(this);
        image.add(createProject);

        manageTasks = new JButton("Task Management");
        manageTasks.setBounds(30, 140, 200, 40);
        manageTasks.addActionListener(this);
        image.add(manageTasks);

        trackTime = new JButton("Time Tracking");
        trackTime.setBounds(30, 200, 200, 40);
        trackTime.addActionListener(this);
        image.add(trackTime);

        monitorProgress = new JButton("Progress Monitoring");
        monitorProgress.setBounds(30, 260, 200, 40);
        monitorProgress.addActionListener(this);
        image.add(monitorProgress);

        // Add Logout Button
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(30, 320, 200, 40);
        logoutButton.addActionListener(this);
        image.add(logoutButton);

        // Announcements section
        announcementsLabel = new JLabel("Announcements:");
        announcementsLabel.setBounds(20, 500, 200, 20);
        announcementsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        announcementsLabel.setForeground(Color.WHITE);
        image.add(announcementsLabel);

        announcementsTextArea = new JTextArea();
        announcementsTextArea.setBounds(20, 530, 400, 100);
        announcementsTextArea.setEditable(false);
        image.add(announcementsTextArea);

        // Fetch and display existing announcements
        fetchAnnouncements();

        setSize(1120, 630);
        setLocation(250, 100);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            setVisible(false);
            new AddEmployee();
        } else if (ae.getSource() == view) {
            setVisible(false);
            new ViewEmployee();
        } else if (ae.getSource() == update) {
            // Update empID before using it
            empID = getSelectedEmployeeID(); // Replace with actual logic to get empID
            if (empID != null && !empID.isEmpty()) {
                setVisible(false);
                new UpdateEmployee(empID);
            } else {
                JOptionPane.showMessageDialog(this, "Employee ID is not set.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (ae.getSource() == remove) {
            setVisible(false);
            new RemoveEmployee();
        } else if (ae.getSource() == attendanceLeaveManagement) {
            setVisible(false);
            new AttendanceLeaveManagement();
        } else if (ae.getSource() == viewDocuments) {
            setVisible(false);
            new EmployeeDocumentsView();
        } else if (ae.getSource() == surveyManagement) {
            setVisible(false);
            new EmployeeSurveyManagement();
        } else if (ae.getSource() == communicationPageButton) {
            setVisible(false);
            new CommunicationPage();
            fetchAnnouncements(); // Refresh announcements when returning from CommunicationPage
        } else if (ae.getSource() == performanceAppraisalButton) {
            setVisible(false);
            new PerformanceAppraisalPage();
        } else if (ae.getSource() == createProject) {
            setVisible(false);
            new ProjectCreation(); // New class for project creation
        } else if (ae.getSource() == manageTasks) {
            setVisible(false);
            new TaskManagement(); // New class for task management
        } else if (ae.getSource() == trackTime) {
            setVisible(false);
            new TimeTracking(); // New class for time tracking
        } else if (ae.getSource() == monitorProgress) {
            setVisible(false);
            new ProgressMonitoring(); // New class for progress monitoring
        } else if (ae.getSource() == logoutButton) {
            setVisible(false);
            new Splash(); // Navigate to the splash screen
        }
    }

    private String getSelectedEmployeeID() {
        // Implement logic to get the selected employee ID
        // This is a placeholder; you should replace this with actual logic to fetch the selected empID
        return "E123"; // Example ID, replace with dynamic value
    }

    private void fetchAnnouncements() {
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM announcements");
            StringBuilder announcementsText = new StringBuilder();
            while (rs.next()) {
                announcementsText.append(rs.getString("announcement_text")).append("\n");
            }
            announcementsTextArea.setText(announcementsText.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
