-- insert queries

insert into candidate values(1,"Arun");
insert into candidate values(2,"Jibil");
insert into candidate values(3,"Deepu");

insert into batch values(1,"september","2025-07-01","2025-08-01");

insert into batch_joined values(2,1,"In Progress");
insert into batch_joined values(1,1,"Completed");

insert into courses values(1,"DBMS");
insert into courses values(2,"JAVA");

insert into course_assigned values(1,1);

insert into topics values(1,"Intro to DBMS");
insert into topics values(2,"Normalisation");

insert into covered_topics values(1,1);
insert into covered_topics values(1,2);

insert into trainers values(1,"Jini");
insert into trainers values(2,"Athira");

insert into teaching values(1,1);

insert into assignments values(1,"ER Diagram","via Github","2025-07-08");

insert into batch_assignments values(1,1);

insert into topics values(3,"Java introduction");
insert into topics values(4,"Java Advanced");

insert into covered_topics values(2,3);
insert into covered_topics values(2,4);

insert into scores values(2,"2025-07-07",91);
insert into scores values(3,"2025-07-06",97);