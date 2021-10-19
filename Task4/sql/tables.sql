
DROP TABLE IF EXISTS databasechangeloglock CASCADE;
DROP TABLE IF EXISTS databasechangelog CASCADE;
DROP TABLE IF EXISTS study_hour CASCADE;
DROP TABLE IF EXISTS speaker CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS subject CASCADE;
DROP TABLE IF EXISTS teacher CASCADE;
DROP TABLE IF EXISTS department CASCADE;
DROP TABLE IF EXISTS faculty CASCADE;
DROP TABLE IF EXISTS event_speakers CASCADE;

CREATE TABLE faculty (
    faculty_id bigserial,
    faculty_name varchar(100),
    PRIMARY KEY (faculty_id)
);

CREATE TABLE department (
    department_id bigserial,
    department_name varchar(100),
    faculty_id bigint,
    PRIMARY KEY (department_id),
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE RESTRICT
);

CREATE TABLE teacher (
    teacher_id bigserial,
    teacher_fio varchar,
    teacher_degree varchar(100),
    teacher_experience integer,
    department_id bigint,
    PRIMARY KEY (teacher_id),
    FOREIGN KEY (department_id) REFERENCES department(department_id) ON DELETE RESTRICT
);

CREATE TABLE subject (
    subject_id bigserial,
    subject_name varchar(100),
    PRIMARY KEY (subject_id)
);

CREATE TABLE room (
    room_id bigserial,
    room_type varchar(100),
    room_capacity integer,
    PRIMARY KEY (room_id)
);

CREATE TABLE speaker (
    speaker_id bigserial,
    speaker_fio varchar,
    speaker_title varchar(150),
    speaker_membership varchar(150),
    PRIMARY KEY (speaker_id)
);


CREATE TABLE study_hour (
    study_hour_id bigserial,
    study_hour_class_date TIMESTAMP not null,
    study_hour_type varchar(20),
    event_topic varchar(20),
    room_id bigint not null,
    subject_id bigint,
    teacher_id bigint,
    speaker_id bigint,
    PRIMARY KEY (study_hour_id),
    FOREIGN KEY (room_id) REFERENCES room(room_id) ON DELETE RESTRICT,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE RESTRICT,
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id) ON DELETE RESTRICT,
    FOREIGN KEY (speaker_id) REFERENCES speaker(speaker_id) ON DELETE RESTRICT
);

CREATE TABLE event_speakers (
    study_hour_id bigint,
    speaker_id bigint,
    FOREIGN KEY (study_hour_id) REFERENCES study_hour(study_hour_id) ON DELETE RESTRICT,
    FOREIGN KEY (speaker_id) REFERENCES speaker(speaker_id) ON DELETE RESTRICT,
    PRIMARY KEY (study_hour_id, speaker_id)
);







