-- Create the database and use it
CREATE DATABASE employeemanagementsystem;
USE employeemanagementsystem;

-- Table: announcements
CREATE TABLE announcements (
    announcement_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Table: employee
CREATE TABLE employee (
    empid INT NOT NULL PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    name VARCHAR(20),
    fname VARCHAR(20),
    dob VARCHAR(30),
    address VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(40),
    education VARCHAR(20),
    designation VARCHAR(20),
    position VARCHAR(20)
);

-- Table: appraisals
CREATE TABLE appraisals (
    empid INT NULL,
    feedback TEXT NOT NULL,
    goals TEXT NOT NULL,
    rating VARCHAR(10) NOT NULL,
    KEY (empid),
    FOREIGN KEY (empid) REFERENCES employee(empid) ON DELETE CASCADE
);
select *from appraisals
-- Table: employee_attendance
CREATE TABLE employee_attendance (
    empid INT NULL,
    attendances INT,
    workinghours INT,
    monthlyovertime INT,
    KEY (empid),
    FOREIGN KEY (empid) REFERENCES employee(empid) ON DELETE CASCADE
);

-- Table: employee_documents
CREATE TABLE employee_documents (
    empid INT NULL,
    resume_folder_path VARCHAR(255),
    contract_path VARCHAR(255),
    performance_review_path VARCHAR(255),
    KEY (empid),
    FOREIGN KEY (empid) REFERENCES employee(empid) ON DELETE CASCADE
);

-- Table: login
CREATE TABLE login (
    username VARCHAR(20),
    password VARCHAR(20)
);

-- Table: logine
CREATE TABLE logine (
    username VARCHAR(20),
    password VARCHAR(20)
);

-- Table: loginf
CREATE TABLE loginf (
    username VARCHAR(20),
    password VARCHAR(20)
);

-- Table: newsletters
CREATE TABLE newsletters (
    newsletter_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: notifications
CREATE TABLE notifications (
    notification_text TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: surveys
CREATE TABLE surveys (
    survey_title VARCHAR(255) NOT NULL,
    question1 VARCHAR(500) NOT NULL,
    question2 VARCHAR(500) NOT NULL,
    question3 VARCHAR(500) NOT NULL
);

-- Projects Table
CREATE TABLE projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    project_manager VARCHAR(255) NOT NULL,
    objectives TEXT NOT NULL
);

-- Tasks Table (corrected schema)
CREATE TABLE tasks (
    task_id INT AUTO_INCREMENT PRIMARY KEY,
    task_name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    due_date DATE NOT NULL,
    priority VARCHAR(50) NOT NULL,
    project_id INT,  -- Use project_id to reference projects
    assignee VARCHAR(255) NOT NULL,
    time_spent INT,  -- Time spent in hours
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
);

-- Table: salaries
CREATE TABLE salaries (
    empid INT PRIMARY KEY,
    salary DECIMAL(10, 2) NOT NULL,
    paid BOOLEAN NOT NULL,
    FOREIGN KEY (empid) REFERENCES employee(empid) ON DELETE CASCADE
);

-- Table: reimbursements
CREATE TABLE reimbursements (
    reimbursement_id INT AUTO_INCREMENT PRIMARY KEY,
    empid INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    reason TEXT NOT NULL,
    date_requested DATE NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (empid) REFERENCES employee(empid) ON DELETE CASCADE
);

-- Table: financial_reports
CREATE TABLE financial_reports (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    report_title VARCHAR(255) NOT NULL,
    report_content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: project_finances
CREATE TABLE project_finances (
    project_id INT PRIMARY KEY,
    budget DECIMAL(10, 2) NOT NULL,
    expenses DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(project_id) ON DELETE CASCADE
);

-- Table: leaves
CREATE TABLE leaves (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    empid INT NOT NULL,
    leave_type VARCHAR(50) NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    FOREIGN KEY (empid) REFERENCES employee(empid) ON DELETE CASCADE
);

-- Insert data into announcements
INSERT INTO announcements (announcement_text) VALUES 
('Office will be closed on 14th August for Independence Day.'),
('New policy updates will be effective from 1st September.'),
('Please attend the team-building workshop on 10th August.');

-- Insert data into employee
INSERT INTO employee (empid, username, password, name, fname, dob, address, phone, email, education, designation, position) VALUES
(1, 'ahmed123', 'password1', 'Ahmed Khan', 'Rashid Khan', '1985-01-10', '123 Lahore Road, Lahore', '03001234567', 'ahmed.khan@example.com', 'BSc Computer Science', 'Software Engineer', 'Senior Developer'),
(2, 'fatima45', 'password2', 'Fatima Ali', 'Mohammad Ali', '1990-07-25', '456 Karachi Street, Karachi', '03109876543', 'fatima.ali@example.com', 'MBA', 'HR Manager', 'HR Specialist'),
(3, 'aslam78', 'password3', 'Aslam Shah', 'Ghulam Shah', '1988-03-15', '789 Islamabad Avenue, Islamabad', '03211223344', 'aslam.shah@example.com', 'BCom', 'Accountant', 'Finance Manager');

-- Insert data into appraisals
INSERT INTO appraisals (empid, feedback, goals, rating) VALUES
(1, 'Excellent performance throughout the year.', 'Complete the new project by the end of Q4.', 'A'),
(2, 'Needs improvement in time management.', 'Focus on meeting deadlines consistently.', 'B'),
(3, 'Great teamwork and leadership skills.', 'Lead the upcoming team project.', 'A');

-- Insert data into employee_attendance
INSERT INTO employee_attendance (empid, attendances, workinghours, monthlyovertime) VALUES
(1, 22, 176, 10),
(2, 20, 160, 5),
(3, 23, 184, 8);

-- Insert data into employee_documents
INSERT INTO employee_documents (empid, resume_folder_path, contract_path, performance_review_path) VALUES
(1, '/resumes/ahmed_khan_resume.pdf', '/contracts/ahmed_khan_contract.pdf', '/reviews/ahmed_khan_review.pdf'),
(2, '/resumes/fatima_ali_resume.pdf', '/contracts/fatima_ali_contract.pdf', '/reviews/fatima_ali_review.pdf'),
(3, '/resumes/aslam_shah_resume.pdf', '/contracts/aslam_shah_contract.pdf', '/reviews/aslam_shah_review.pdf');

-- Insert data into login
INSERT INTO login (username, password) VALUES
('ahmed123', 'password1'),
('fatima45', 'password2'),
('aslam78', 'password3');

-- Insert data into logine
INSERT INTO logine (username, password) VALUES
('ahmed123', 'password1'),
('fatima45', 'password2'),
('aslam78', 'password3');

-- Insert data into loginf
INSERT INTO loginf (username, password) VALUES
('ahmed123', 'password1'),
('fatima45', 'password2'),
('aslam78', 'password3');

-- Insert data into newsletters
INSERT INTO newsletters (newsletter_text) VALUES
('Welcome to the August newsletter! Check out our upcoming events and news.'),
('Our new product line has launched. Explore the latest features now.'),
('Don’t miss our annual company picnic on 25th August.');

-- Insert data into notifications
INSERT INTO notifications (notification_text) VALUES
('Reminder: Submit your timesheets by end of day.'),
('Meeting scheduled with the finance team at 3 PM today.'),
('System maintenance planned for this weekend. Please save your work.');

-- Insert data into surveys
INSERT INTO surveys (survey_title, question1, question2, question3) VALUES
('Employee Satisfaction Survey', 'How satisfied are you with your current role?', 'How would you rate the work environment?', 'What improvements would you like to see?'),
('Customer Feedback Survey', 'How satisfied are you with our product?', 'How likely are you to recommend us to others?', 'What features do you want in future updates?'),
('Annual Performance Review Survey', 'How well do you think the company has performed this year?', 'How effective is the communication within the company?', 'What additional support do you need to achieve your goals?');

-- Insert data into projects
INSERT INTO projects (project_name, description, start_date, end_date, project_manager, objectives) VALUES
('Project A', 'Description of Project A', '2024-01-01', '2024-06-30', 'Ahmed Khan', 'Objectives of Project A'),
('Project B', 'Description of Project B', '2024-02-01', '2024-08-31', 'Fatima Ali', 'Objectives of Project B');

-- Insert data into tasks
INSERT INTO tasks (task_name, description, due_date, priority, project_id, assignee) VALUES
('Task 1', 'Description of Task 1', '2024-03-01', 'High', 1, 'ahmed123'),
('Task 2', 'Description of Task 2', '2024-04-01', 'Medium', 1, 'ahmed123'),
('Task 3', 'Description of Task 3', '2024-05-01', 'Low', 2, 'fatima45');

-- Insert data into salaries
INSERT INTO salaries (empid, salary, paid) VALUES
(1, 5000.00, TRUE),
(2, 4500.00, FALSE),
(3, 4000.00, TRUE);

-- Insert data into reimbursements
INSERT INTO reimbursements (empid, amount, reason, date_requested, status) VALUES
(1, 150.00, 'Business lunch with client', '2024-07-15', 'Approved'),
(2, 75.00, 'Office supplies', '2024-07-20', 'Pending'),
(3, 200.00, 'Conference registration', '2024-07-25', 'Rejected');

-- Insert data into financial_reports
INSERT INTO financial_reports (report_title, report_content) VALUES
('Quarterly Financial Overview', 'This report provides an overview of the financial performance for the quarter, including revenue, expenses, and profit margins.'),
('Annual Budget Analysis', 'An analysis of the annual budget compared to actual expenses, highlighting variances and budget adherence.'),
('Yearly Revenue Report', 'Detailed report on the yearly revenue, including sources, trends, and growth compared to the previous year.');

-- Insert data into project_finances
INSERT INTO project_finances (project_id, budget, expenses) VALUES
(1, 10000.00, 4500.00),
(2, 15000.00, 7000.00);

-- Insert data into leaves
INSERT INTO leaves (empid, leave_type, from_date, to_date, status) VALUES
(1, 'Sick Leave', '2024-08-01', '2024-08-05', 'Approved'),
(2, 'Annual Leave', '2024-08-10', '2024-08-15', 'Pending');
ALTER TABLE employee_attendance
ADD COLUMN attendance_date DATE;
