create database team_assignment1;

use team_assignment1;

CREATE TABLE Courses (
    course_id INT PRIMARY KEY,
    course_name VARCHAR(255)
);

CREATE TABLE Topics (
    topic_id INT PRIMARY KEY,
    topic_name VARCHAR(255),
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

CREATE TABLE Batches (
    batch_id INT PRIMARY KEY AUTO_INCREMENT,
    batch_name VARCHAR(255),
    start_date DATE,
    end_date DATE,
    course_id INT,
    max_strength INT,
    batch_code VARCHAR(255) UNIQUE,
    FOREIGN KEY (course_id) REFERENCES Courses(course_id)
);

CREATE TABLE Users (
    user_id INT PRIMARY KEY,
    user_name VARCHAR(255),
    password VARCHAR(255),
    name VARCHAR(255),
    phone VARCHAR(255),
    created_at DATE,
    status ENUM('active', 'inactive', 'suspended')
);

CREATE TABLE Assignments (
    assignment_id INT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    max_score INT,
    cut_off INT,
    topic_id INT,
    created_by INT,
    FOREIGN KEY (topic_id) REFERENCES Topics(topic_id),
    FOREIGN KEY (created_by) REFERENCES Users(user_id)
);


CREATE TABLE users_assignments (
    user_assignement_id INT PRIMARY KEY,
    user_id INT,
    assignment_id INT,
    score INT,
    total_score INT,
    review VARCHAR(255),
    attempt_no INT,
    max_attempts INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (assignment_id) REFERENCES Assignments(assignment_id)
);


CREATE TABLE users_Batches (
    user_id INT,
    batch_id INT,
    status ENUM('enrolled', 'completed', 'dropped'),
    enrolled_date DATE,
    PRIMARY KEY (user_id, batch_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id)
);

CREATE TABLE Batches_Assignments (
    assignment_id INT,
    batch_id INT,
    due_date DATE,
    full_submission BOOLEAN,
    created_at DATE,
    PRIMARY KEY (assignment_id, batch_id),
    FOREIGN KEY (assignment_id) REFERENCES Assignments(assignment_id),
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id)
);

CREATE TABLE users_Topics (
    user_id INT,
    topics_id INT,
    created_at DATE,
    PRIMARY KEY (user_id, topics_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (topics_id) REFERENCES Topics(topic_id)
);

CREATE TABLE Sessions (
    session_id INT PRIMARY KEY,
    batch_id INT,
    trainer_id INT,
    topic_id INT,
    session_date DATE,
    start_time TIME,
    end_time TIME,
    mode ENUM('online', 'offline', 'hybrid'),
    session_recording TEXT,
    FOREIGN KEY (batch_id) REFERENCES Batches(batch_id),
    FOREIGN KEY (trainer_id) REFERENCES Users(user_id),
    FOREIGN KEY (topic_id) REFERENCES Topics(topic_id)
);

CREATE TABLE Attendance (
    session_id INT,
    candidate_id INT,
    status ENUM('present', 'absent', 'late'),
    PRIMARY KEY (session_id, candidate_id),
    FOREIGN KEY (session_id) REFERENCES Sessions(session_id),
    FOREIGN KEY (candidate_id) REFERENCES Users(user_id)
);

CREATE TABLE Roles (
    role_id INT PRIMARY KEY,
    role_name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE User_roles (
    user_role_id INT PRIMARY KEY,
    user_id INT,
    role_id INT,
    assigned_date DATE,
    is_active BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (role_id) REFERENCES Roles(role_id)
);

CREATE TABLE Resources (
    resource_id INT PRIMARY KEY,
    topic_id INT,
    resource_name VARCHAR(50),
    resource_type ENUM('document', 'video', 'link', 'image'),
    resource_path TEXT,
    FOREIGN KEY (topic_id) REFERENCES Topics(topic_id)
);

CREATE TABLE exit_users (
    user_id INT PRIMARY KEY,
    exit_date DATE,
    exit_reason VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);