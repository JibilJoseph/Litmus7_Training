-- Assignment Questions and answers


-- 1. Get all batches a candidate is enrolled in, with their status. 

select candidate.candidate_id,candidate_name,state from candidate join batch_joined on candidate.candidate_id=batch_joined.candidate_id;

-- 2. Get all trainers assigned to a batch. 

select t.trainer_id,t.trainer_name,t2.batch_id from trainers t join teaching t2 on t.trainer_id=t2.trainer_id;

-- 3.Get all topics under a course. 

select t.topic_id,t.topic_name,c.course_id from topics t join covered_topics c where t.topic_id=c.topic_id order by course_id;

-- 4.List assignment scores for a candidate in a batch. 

select * from scores where candidate_id in (select c.candidate_id from candidate c join batch_joined b on c.candidate_id=b.candidate_id where b.batch_id=1);

select t1.candidate_id,b.batch_id,t1.candidate_name,t1.score from
(select candidate.candidate_id,candidate.candidate_name,scores.score from scores join candidate on scores.candidate_id=candidate.candidate_id) as t1
join batch_joined b on t1.candidate_id=b.candidate_id;


-- List candidates with status "Completed" in a given batch. 

select * from candidate where candidate_id in (
select candidate_id from batch_joined where state="completed"
);