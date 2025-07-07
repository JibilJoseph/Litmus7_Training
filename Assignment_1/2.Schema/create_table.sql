-- create table queries

create database as1;

use as1;

create table candidate(candidate_id int primary key,candidate_name varchar(40));

create table batch(batch_id int primary key,batch_name varchar(40),start_date date,end_date date);

create table batch_joined(candidate_id int,batch_id int,state varchar(40),
	foreign key(candidate_id) references candidate(candidate_id),
	foreign key(batch_id) references batch(batch_id)
);

create table courses(course_id int primary key,course_name varchar(40));

create table course_assigned(batch_id int ,course_id int ,
	foreign key(batch_id) references batch(batch_id),
	foreign key(course_id) references courses(course_id)
);

create table topics(topic_id int primary key,topic_name varchar(40));

create table covered_topics(course_id int,topic_id int,
	foreign key (course_id) references courses(course_id),
	foreign key (topic_id)  references topics(topic_id)
);

create table trainers(trainer_id int primary key,trainer_name varchar(40));

create table teaching(trainer_id int,batch_id int,
	foreign key (trainer_id) references trainers(trainer_id),
	foreign key (batch_id) references batch(batch_id)
);

create table assignments(a_id int primary key,a_name varchar(40),a_desc varchar(40),due_date date);

create table batch_assignments(batch_id int,a_id int,
	foreign key (batch_id) references batch(batch_id),
	foreign key (a_id) references assignments(a_id)
);

create table scores(candidate_id int,sub_date date,score int,
	foreign key(candidate_id) references candidate(candidate_id)
);