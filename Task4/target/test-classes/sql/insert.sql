
INSERT INTO faculty (faculty_id, faculty_name)
VALUES (2, 'nameFaculty');

INSERT INTO department (department_id ,department_name, faculty_id)
VALUES (2, 'nameDepartment', 2);

INSERT INTO teacher (teacher_id, teacher_fio, teacher_degree, teacher_experience)
VALUES (2, 'fio', 'degree', 5);

INSERT INTO subject (subject_id, subject_name)
VALUES (2, 'subjectName');

INSERT INTO room (room_id, room_type, room_capacity)
VALUES (2,'type', 100);

INSERT INTO speaker (speaker_id, speaker_fio, speaker_title, speaker_membership)
VALUES (2, 'fio', 'title', 'membership');

INSERT INTO study_hour (study_hour_type, study_hour_id, study_hour_class_date, room_id, teacher_id, subject_id)
VALUES ('lesson', 3, '2017-06-22 19:10:25-07', 2, 2, 2 );

INSERT INTO study_hour (study_hour_type, study_hour_id, study_hour_class_date, room_id, event_topic)
VALUES ('event', 4, '2018-06-22 19:10:25-07', 2, 'TopicEvent' );

INSERT INTO event_speakers (study_hour_id, speaker_id)
VALUES (4, 2);










