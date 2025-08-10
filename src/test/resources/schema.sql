CREATE TABLE students (
    id CHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100),
    kana_name VARCHAR(100),
    nickname VARCHAR(100),
    email VARCHAR(100) NOT NULL,
    city VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    remark VARCHAR(1000),
    is_deleted boolean
);

CREATE TABLE students_courses (
    id          INT           NOT NULL AUTO_INCREMENT PRIMARY KEY,
    student_id  CHAR(36)      NOT NULL,
    course_name VARCHAR(100)  NOT NULL,
    start_date  DATE,
    end_date    DATE
);