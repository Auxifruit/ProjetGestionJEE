CREATE DATABASE CYDataBase;


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
*/

CREATE TABLE PossibleRole (
	roleId INT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(50)
);

CREATE TABLE Major (
	majorId INT AUTO_INCREMENT PRIMARY KEY,
    majorName VARCHAR(50)
);

CREATE TABLE Users (
	userId INT AUTO_INCREMENT PRIMARY KEY,
    userPassword VARCHAR(50),
    userLastName VARCHAR(50),
    userName VARCHAR(50),
    userEmail VARCHAR(50),
    userBirthdate VARCHAR(50),
    roleId int,
    
    FOREIGN KEY (roleId) REFERENCES PossibleRole(roleId) ON DELETE CASCADE
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
    className VARCHAR(50)
);


CREATE TABLE Student (
    studentId INT,
    classId INT, 
    majorId INT,
    
	FOREIGN KEY  (studentId) REFERENCES Users(userId) ON DELETE CASCADE,
    FOREIGN KEY  (classId) REFERENCES Classes(classId) ON DELETE SET NULL,
    FOREIGN KEY  (majorId) REFERENCES Major(majorId) ON DELETE SET NULL,
    
    PRIMARY KEY (studentId)
);



CREATE TABLE Subjects(
	subjectId INT AUTO_INCREMENT PRIMARY KEY,
    subjectName VARCHAR(50)
);

CREATE TABLE Course(
	courseId INT AUTO_INCREMENT PRIMARY KEY,
    courseName VARCHAR(50),
    subjectId INT,
    
    FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId) ON DELETE SET NULL
);

CREATE TABLE Grade (
    gradeId INT PRIMARY KEY,
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

SELECT * FROM course;
SELECT * FROM classes;
SELECT * FROM lesson;
SELECT * FROM lessonClass;

SELECT * FROM subjects;
SELECT * FROM grade;

SELECT * FROM administrator;
SELECT * FROM teacher;
SELECT * FROM users;
SELECT * FROM student;

# Jeu de données pour test
INSERT INTO PossibleRole VALUES (1, "student");
INSERT INTO PossibleRole VALUES (2, "teacher");
INSERT INTO PossibleRole VALUES (3, "administrator");

INSERT INTO Classes VALUES (1, "GSI1");
INSERT INTO Classes VALUES (2, "GSI2");
INSERT INTO Classes VALUES (3, "GSI3");

INSERT INTO Major VALUES (1, "GI");
INSERT INTO Major VALUES (2, "GM");
INSERT INTO Major VALUES (3, "BTC");

INSERT INTO subjects VALUES (1, "Mathématiques");
INSERT INTO subjects VALUES (2, "Informatique");

INSERT INTO users VALUES (1, "password", "Evans", "Mark", "mark@gmail.com", "01/02/2000", 1);
INSERT INTO users VALUES (2, "password", "Blaze", "Axel", "axel@gmail.com", "05/06/2002", 1);
INSERT INTO users VALUES (3, "password", "Sharp", "Jude", "jude@gmail.com", "03/04/2001", 1);
INSERT INTO users VALUES (4, "password", "Eau", "Ondie", "ondine@gmail.com", "07/08/1982", 2);
INSERT INTO users VALUES (5, "password", "Roche", "Pierre", "pierre@gmail.com", "09/10/1978", 2);
INSERT INTO users VALUES (6, "password", "Capone", "Bege", "bege@gmail.com", "12/01/1786", 3);
INSERT INTO users VALUES (7, "password", "Newgate", "Edward", "edward@gmail.com", "17/02/1781", 3);

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

INSERT INTO grade VALUES(1, 8, 3, 1, 1, 5);
INSERT INTO grade VALUES(2, 11, 2, 1, 2, 4);
INSERT INTO grade VALUES(3, 12, 3, 2, 3, 5);
INSERT INTO grade VALUES(4, 14, 2, 2, 4, 4);
INSERT INTO grade VALUES(5, 16, 3, 3, 1, 5);
INSERT INTO grade VALUES(6, 19, 2, 3, 2, 4);

INSERT INTO lesson VALUES (1, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 1, 4);
INSERT INTO lesson VALUES (2, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 1, 4);

INSERT INTO lessonClass VALUES (1, 1, 1);
INSERT INTO lessonClass VALUES (2, 2, 2);

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