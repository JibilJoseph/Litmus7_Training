INSERT INTO Courses (course_id, course_name) VALUES
(1, 'Full Stack Development'),
(2, 'Data Science'),
(3, 'Machine Learning'),
(4, 'Web Development'),
(5, 'Mobile App Development');


INSERT INTO Topics (topic_id, topic_name, course_id) VALUES
(1, 'HTML & CSS', 1),
(2, 'JavaScript', 1),
(3, 'React.js', 1),
(4, 'Node.js', 1),
(5, 'Python Basics', 2),
(6, 'Data Analysis', 2),
(7, 'Machine Learning Algorithms', 3),
(8, 'Deep Learning', 3),
(9, 'Frontend Development', 4),
(10, 'Backend Development', 4);


INSERT INTO Batches (batch_name, start_date, end_date, course_id, max_strength, batch_code) VALUES
('FSW-2024-01', '2024-01-15', '2024-06-15', 1, 30, 'FSW01'),
('DS-2024-01', '2024-02-01', '2024-07-01', 2, 25, 'DS01'),
('ML-2024-01', '2024-03-01', '2024-08-01', 3, 20, 'ML01'),
('WEB-2024-01', '2024-01-10', '2024-05-10', 4, 35, 'WEB01'),
('MOB-2024-01', '2024-04-01', '2024-09-01', 5, 25, 'MOB01');


INSERT INTO Users (user_id, user_name, password, name, phone, created_at, status) VALUES
(1, 'john_doe', 'password123', 'John Doe', '9876543210', '2024-01-01', 'active'),
(2, 'jane_smith', 'password456', 'Jane Smith', '9876543211', '2024-01-02', 'active'),
(3, 'mike_johnson', 'password789', 'Mike Johnson', '9876543212', '2024-01-03', 'active'),
(4, 'sarah_wilson', 'password101', 'Sarah Wilson', '9876543213', '2024-01-04', 'active'),
(5, 'david_brown', 'password202', 'David Brown', '9876543214', '2024-01-05', 'active'),
(6, 'instructor_one', 'instrpass1', 'Robert Lee', '9876543215', '2024-01-01', 'active'),
(7, 'instructor_two', 'instrpass2', 'Lisa Anderson', '9876543216', '2024-01-01', 'active');


INSERT INTO Assignments (assignment_id, title, description, max_score, cut_off, topic_id, created_by) VALUES
(1, 'HTML Portfolio', 'Create a personal portfolio using HTML', 100, 60, 1, 6),
(2, 'JavaScript Calculator', 'Build a calculator using JavaScript', 100, 70, 2, 6),
(3, 'React Todo App', 'Create a todo application using React', 100, 75, 3, 6),
(4, 'Python Data Analysis', 'Analyze dataset using Python', 100, 65, 5, 7),
(5, 'ML Model Training', 'Train a basic ML model', 100, 80, 7, 7);


INSERT INTO users_assignments (user_assignement_id, user_id, assignment_id, score, total_score, review, attempt_no, max_attempts) VALUES
(1, 1, 1, 85, 100, 'Good work on HTML structure', 1, 3),
(2, 1, 2, 45, 100, 'Needs improvement in logic', 1, 3),
(3, 1, 2, 75, 100, 'Much better, good improvement', 2, 3),
(4, 2, 1, 92, 100, 'Excellent portfolio design', 1, 3),
(5, 2, 2, 88, 100, 'Great JavaScript implementation', 1, 3),
(6, 3, 1, 78, 100, 'Good effort, minor styling issues', 1, 3),
(7, 3, 3, 82, 100, 'Well structured React app', 1, 2),
(8, 4, 4, 90, 100, 'Excellent data analysis', 1, 3),
(9, 5, 5, 95, 100, 'Outstanding ML model', 1, 2);


INSERT INTO users_Batches (user_id, batch_id, status, enrolled_date) VALUES
(1, 1, 'enrolled', '2024-01-15'),
(2, 1, 'enrolled', '2024-01-15'),
(3, 1, 'enrolled', '2024-01-16'),
(4, 2, 'enrolled', '2024-02-01'),
(5, 3, 'enrolled', '2024-03-01'),
(1, 4, 'completed', '2024-01-10'),
(2, 4, 'enrolled', '2024-01-10');


INSERT INTO Batches_Assignments (assignment_id, batch_id, due_date, full_submission, created_at) VALUES
(1, 1, '2024-01-30', true, '2024-01-15'),
(2, 1, '2024-02-10', true, '2024-01-20'),
(3, 1, '2024-02-20', false, '2024-01-25'),
(4, 2, '2024-02-15', true, '2024-02-01'),
(5, 3, '2024-03-15', true, '2024-03-01');


INSERT INTO users_Topics (user_id, topics_id, created_at) VALUES
(1, 1, '2024-01-15'),
(1, 2, '2024-01-20'),
(1, 3, '2024-01-25'),
(2, 1, '2024-01-15'),
(2, 2, '2024-01-20'),
(3, 1, '2024-01-16'),
(4, 5, '2024-02-01'),
(4, 6, '2024-02-05'),
(5, 7, '2024-03-01'),
(5, 8, '2024-03-05');


INSERT INTO Sessions (session_id, batch_id, trainer_id, topic_id, session_date, start_time, end_time, mode, session_recording) VALUES
(1, 1, 6, 1, '2024-01-16', '10:00:00', '12:00:00', 'online', 'https://recordings.com/session1'),
(2, 1, 6, 2, '2024-01-18', '14:00:00', '16:00:00', 'offline', NULL),
(3, 1, 6, 3, '2024-01-20', '10:00:00', '12:00:00', 'hybrid', 'https://recordings.com/session3'),
(4, 2, 7, 5, '2024-02-02', '09:00:00', '11:00:00', 'online', 'https://recordings.com/session4'),
(5, 3, 7, 7, '2024-03-02', '15:00:00', '17:00:00', 'offline', NULL);


INSERT INTO Attendance (session_id, candidate_id, status) VALUES
(1, 1, 'present'),
(1, 2, 'present'),
(1, 3, 'late'),
(2, 1, 'present'),
(2, 2, 'absent'),
(2, 3, 'present'),
(3, 1, 'present'),
(3, 2, 'present'),
(4, 4, 'present'),
(5, 5, 'present');


INSERT INTO Roles (role_id, role_name, description) VALUES
(1, 'Student', 'Regular student enrolled in courses'),
(2, 'Instructor', 'Course instructor and trainer'),
(3, 'Admin', 'System administrator'),
(4, 'Teaching Assistant', 'Assistant to instructors'),
(5, 'Mentor', 'Student mentor and guide');


INSERT INTO User_roles (user_role_id, user_id, role_id, assigned_date, is_active) VALUES
(1, 1, 1, '2024-01-01', true),
(2, 2, 1, '2024-01-02', true),
(3, 3, 1, '2024-01-03', true),
(4, 4, 1, '2024-01-04', true),
(5, 5, 1, '2024-01-05', true),
(6, 6, 2, '2024-01-01', true),
(7, 7, 2, '2024-01-01', true),
(8, 1, 4, '2024-02-01', true);


INSERT INTO Resources (resource_id, topic_id, resource_name, resource_type, resource_path) VALUES
(1, 1, 'HTML Basics Guide', 'document', '/resources/html_basics.pdf'),
(2, 1, 'CSS Tutorial Video', 'video', '/resources/css_tutorial.mp4'),
(3, 2, 'JavaScript Reference', 'link', 'https://developer.mozilla.org/en-US/docs/Web/JavaScript'),
(4, 3, 'React Documentation', 'link', 'https://reactjs.org/docs'),
(5, 5, 'Python Cheat Sheet', 'document', '/resources/python_cheat_sheet.pdf'),
(6, 7, 'ML Algorithm Examples', 'document', '/resources/ml_examples.pdf'),
(7, 2, 'JS Practice Image', 'image', '/resources/js_practice.png');


INSERT INTO exit_users (user_id, exit_date, exit_reason) VALUES
(3, '2024-02-15', 'Personal reasons'),
(4, '2024-03-01', 'Job opportunity');