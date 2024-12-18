CREATE DATABASE CYDataBase;

USE CYDataBase;

# Suppression des tables dans l'ordre
/*
DROP TABLE administrator;
DROP TABLE grade;
DROP TABLE student;
DROP TABLE major;
DROP TABLE lessonClass;
DROP TABLE lesson;
DROP TABLE classes;
DROP TABLE course;
DROP TABLE teacher;
DROP TABLE subjects;
DROP TABLE users;


DROP TABLE possibleRole;
CREATE TABLE PossibleRole (
	roleId INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(50) UNIQUE
);

INSERT INTO PossibleRole VALUES (1, "student");
INSERT INTO PossibleRole VALUES (2, "teacher");
INSERT INTO PossibleRole VALUES (3, "administrator");
*/



CREATE TABLE Major (
	majorId INT AUTO_INCREMENT PRIMARY KEY,
    majorName VARCHAR(50) UNIQUE
);

CREATE TABLE Users (
	userId INT AUTO_INCREMENT PRIMARY KEY,
    userPassword VARCHAR(50),
    userLastName VARCHAR(50),
    userName VARCHAR(50),
    userEmail VARCHAR(50) UNIQUE,
    userBirthdate VARCHAR(50),
    userRole ENUM("student","teacher","administrator")
);

CREATE TABLE UsersToValidate (
	userToValidateId INT AUTO_INCREMENT PRIMARY KEY,
    userToValidatePassword VARCHAR(50),
    userToValidateLastName VARCHAR(50),
    userToValidateName VARCHAR(50),
    userToValidateEmail VARCHAR(50) UNIQUE,
    userToValidateBirthdate VARCHAR(50),
    userToValidateRole ENUM("student","teacher","administrator"),
    userToValidateMajorId INT,
    
    FOREIGN KEY  (userToValidateMajorId) REFERENCES Major(majorId) ON DELETE SET NULL
);

CREATE TABLE Administrator (
    administratorId INT,
    
    FOREIGN KEY (administratorId) REFERENCES Users(userId) ON DELETE CASCADE,
	
    PRIMARY KEY (administratorId)
);

CREATE TABLE Teacher (
    teacherId INT,
    
    FOREIGN KEY (teacherId) REFERENCES Users(userId) ON DELETE CASCADE,
    
    PRIMARY KEY (teacherId)
);

CREATE TABLE Classes(
	classId INT AUTO_INCREMENT PRIMARY KEY,
    className VARCHAR(50) UNIQUE
);

CREATE TABLE Student (
    studentId INT NOT NULL,
    classId INT, 
    majorId INT,
    
	FOREIGN KEY  (studentId) REFERENCES Users(userId) ON DELETE CASCADE,
    FOREIGN KEY  (classId) REFERENCES Classes(classId) ON DELETE SET NULL,
    FOREIGN KEY  (majorId) REFERENCES Major(majorId) ON DELETE SET NULL,
    
    PRIMARY KEY (studentId)
);

CREATE TABLE Subjects(
	subjectId INT AUTO_INCREMENT PRIMARY KEY,
    subjectName VARCHAR(50) UNIQUE
);

CREATE TABLE Course(
	courseId INT AUTO_INCREMENT PRIMARY KEY,
    courseName VARCHAR(50) UNIQUE,
    subjectId INT,
    
    FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId) ON DELETE SET NULL
);

CREATE TABLE Grade (
    gradeId INT AUTO_INCREMENT PRIMARY KEY,
    gradeName VARCHAR(50),
    gradeValue DOUBLE,
    gradeCoefficient INT,
    studentId INT,
    courseId INT,
    teacherId INT,

    FOREIGN KEY (studentId) REFERENCES Student(studentId) ON DELETE CASCADE,
    FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE SET NULL,
    FOREIGN KEY (teacherId) REFERENCES Teacher(teacherId) ON DELETE SET NULL
);

CREATE TABLE Lesson (
	lessonId INT AUTO_INCREMENT PRIMARY KEY,
	lessonStartDate DATETIME,
    lessonEndDate DATETIME,
    courseId INT,
	teacherId int,
    
    FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES Teacher(teacherId) ON DELETE SET NULL
);

CREATE TABLE LessonClass (
	lessonClassId INT AUTO_INCREMENT PRIMARY KEY,
    lessonId INT,
    classId INT,
    
    FOREIGN KEY (lessonId) REFERENCES Lesson(lessonId) ON DELETE CASCADE,
    FOREIGN KEY (classId) REFERENCES Classes(classId) ON DELETE SET NULL
);

# Jeu de données pour test
INSERT INTO Classes VALUES (1, "GSI1");
INSERT INTO Classes VALUES (2, "GSI2");
INSERT INTO Classes VALUES (3, "GSI3");

INSERT INTO Major VALUES (1, "GI");
INSERT INTO Major VALUES (2, "GM");
INSERT INTO Major VALUES (3, "BTC");
INSERT INTO Major VALUES (4, "MF");

INSERT INTO subjects VALUES (1, "Mathématiques");
INSERT INTO subjects VALUES (2, "Informatique");

INSERT INTO users VALUES (1, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Evans", "Mark", "mark@gmail.com", "2000-01-02", "student");
INSERT INTO users VALUES (2, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Blaze", "Axel", "axel@gmail.com", "2002-05-06", "student");
INSERT INTO users VALUES (3, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Sharp", "Jude", "jude@gmail.com", "2002-04-03", "student");
INSERT INTO users VALUES (4, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Eau", "Ondine", "ondine@gmail.com", "1982-07-08", "teacher");
INSERT INTO users VALUES (5, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Roche", "Pierre", "pierre@gmail.com", "1978-09-10", "teacher");
INSERT INTO users VALUES (6, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Capone", "Bege", "bege@gmail.com", "1786-12-01", "administrator");
INSERT INTO users VALUES (7, "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62", "Newgate", "Edward", "edward@gmail.com", "1781-02-11", "administrator");

INSERT INTO users VALUES (8, "a", "Nom", "Prénom", "a@gmail.com", "1111-11-11", 3);

INSERT INTO student VALUES (1, 3, 1);
INSERT INTO student VALUES (2, 1, 2);
INSERT INTO student VALUES (3, 2, 3);

INSERT INTO teacher VALUES(4);
INSERT INTO teacher VALUES(5);

INSERT INTO administrator VALUES(6);
INSERT INTO administrator VALUES(7);

INSERT INTO course VALUES (1, "Statistique", 1); 
INSERT INTO course VALUES (2, "JEE", 2); 
INSERT INTO course VALUES (3, "Optimisation Linéaire", 1); 
INSERT INTO course VALUES (4, "Design Patern", 2); 

INSERT INTO grade VALUES(1, "note 1", 8, 3, 1, 1, 5);
INSERT INTO grade VALUES(2, "note 1", 11, 2, 1, 2, 4);
INSERT INTO grade VALUES(3, "note 1", 12, 3, 2, 3, 5);
INSERT INTO grade VALUES(4, "note 1", 14, 2, 2, 4, 4);
INSERT INTO grade VALUES(5, "note 1", 16, 3, 3, 1, 5);
INSERT INTO grade VALUES(6, "note 1", 19, 2, 3, 2, 4);

INSERT INTO lesson VALUES (1, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 1, 4);
INSERT INTO lesson VALUES (2, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 1, 4);

INSERT INTO lessonClass VALUES (1, 1, 1);
INSERT INTO lessonClass VALUES (2, 2, 2);

SELECT * FROM course;
SELECT * FROM classes;
SELECT * FROM lesson;
SELECT * FROM lessonClass;

SELECT * FROM subjects;
SELECT * FROM grade;

SELECT * FROM administrator;
SELECT * FROM teacher;
SELECT * FROM users;
SELECT * FROM userstovalidate;
SELECT * FROM student;
SELECT * FROM major;

# Exemple de query

-- SELECT * FROM student WHERE idClass = 2;

-- SELECT * FROM coursedateprof WHERE emailTeacher = (
-- 	SELECT identifiantUser FROM users WHERE idUser = 5
-- );

-- SELECT * FROM coursedateprof WHERE dateCourse LIKE("%-10-%");
-- SELECT * FROM coursedateprof WHERE dateCourse LIKE("%-%-% %:30:%");

-- SELECT COUNT(*) as NombreEleves, idClass FROM étudiant
-- GROUP BY idClass;

-- SELECT * FROM grade WHERE emailStudent = (
-- SELECT emailStudent FROM student where idClass = 1);
SELECT  
	l.lessonId,
    l.lessonStartDate,
    l.lessonEndDate,
    l.courseId,
    l.teacherId
    FROM Lesson l
JOIN LessonClass lc ON l.lessonId = lc.lessonId 
JOIN Student s ON lc.classId = s.classId 
WHERE s.studentId = 2;

SELECT c.classId, c.className FROM Classes c WHERE c.classId NOT IN (SELECT sc.classId FROM LessonClass sc WHERE sc.lessonId = 2)
