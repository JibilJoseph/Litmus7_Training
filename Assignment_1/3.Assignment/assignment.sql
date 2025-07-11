-- Assignment Questions and answers


-- 1. Get all batches a candidate is enrolled in, with their status. 

SELECT 
    u.user_id,
    u.name AS candidate_name,
    b.batch_id,
    b.batch_name,
    b.batch_code,
    ub.status,
    ub.enrolled_date
FROM Users u
JOIN users_Batches ub ON u.user_id = ub.user_id
JOIN Batches b ON ub.batch_id = b.batch_id; 

-- 2. Get all trainers assigned to a batch. 

SELECT DISTINCT
    b.batch_id,
    b.batch_name,
    u.user_id AS trainer_id,
    u.name AS trainer_name,
    u.user_name AS trainer_username
FROM Batches b
JOIN Sessions s ON b.batch_id = s.batch_id
JOIN Users u ON s.trainer_id = u.user_id order by b.batch_id;

-- 3.Get all topics under a course. 

SELECT 
    c.course_id,
    c.course_name,
    t.topic_id,
    t.topic_name
FROM Courses c
JOIN Topics t ON c.course_id = t.course_id
ORDER BY c.course_id;

-- 4.List assignment scores for a candidate in a batch. 

SELECT 
    u.user_id,
    u.name AS candidate_name,
    b.batch_id,
    b.batch_name,
    a.assignment_id,
    a.title AS assignment_title,
    ua.score,
    ua.total_score,
    ua.attempt_no,
    ua.max_attempts,
    ua.review,
    t.topic_name
FROM Users u
JOIN users_Batches ub ON u.user_id = ub.user_id
JOIN Batches b ON ub.batch_id = b.batch_id
JOIN Batches_Assignments ba ON b.batch_id = ba.batch_id
JOIN Assignments a ON ba.assignment_id = a.assignment_id
JOIN users_assignments ua ON u.user_id = ua.user_id AND a.assignment_id = ua.assignment_id
JOIN Topics t ON a.topic_id = t.topic_id
WHERE u.user_id = 1 AND b.batch_id = 1  
ORDER BY a.assignment_id, ua.attempt_no;


-- 5. List candidates with status "Completed" in a given batch. 

SELECT 
    u.user_id,
    u.name AS candidate_name,
    u.user_name,
    u.phone,
    ub.status,
    ub.enrolled_date,
    b.batch_id,
    b.batch_name,
    b.batch_code
FROM Users u
JOIN users_Batches ub ON u.user_id = ub.user_id
JOIN Batches b ON ub.batch_id = b.batch_id
WHERE ub.status = 'completed' 
ORDER BY b.batch_id;