### Version Anglais

# Suppression des tables dans l'ordre
/*
DROP TABLE Grade;
DROP TABLE LessonClass;
DROP TABLE Lesson;
DROP TABLE Course;
DROP TABLE Teacher;
DROP TABLE Student;
DROP TABLE Classes;
DROP TABLE Subjects;
DROP TABLE Administrator;
DROP TABLE Users;
DROP TABLE Major;
DROP TABLE PossibleRole;
*/

# Création

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
    userBirthdate DATE,
    roleId INT,
    FOREIGN KEY (roleId) REFERENCES PossibleRole(roleId) ON DELETE CASCADE
);

CREATE TABLE Administrator (
    administratorId INT PRIMARY KEY,
    FOREIGN KEY (administratorId) REFERENCES Users(userId) ON DELETE CASCADE
);

CREATE TABLE Teacher (
    teacherId INT PRIMARY KEY,
    FOREIGN KEY (teacherId) REFERENCES Users(userId) ON DELETE CASCADE
);

CREATE TABLE Classes (
    classesId INT AUTO_INCREMENT PRIMARY KEY,
    classesName VARCHAR(50)
);

CREATE TABLE Student (
    studentId INT PRIMARY KEY,
    classesId INT,
    majorId INT,
    FOREIGN KEY (studentId) REFERENCES Users(userId) ON DELETE CASCADE,
    FOREIGN KEY (classesId) REFERENCES Classes(classesId) ON DELETE SET NULL,
    FOREIGN KEY (majorId) REFERENCES Major(majorId) ON DELETE SET NULL
);

CREATE TABLE Subjects (
    subjectId INT AUTO_INCREMENT PRIMARY KEY,
    subjectName VARCHAR(50)
);

CREATE TABLE Course (
    courseId INT AUTO_INCREMENT PRIMARY KEY,
    courseName VARCHAR(50),
    subjectId INT,
    FOREIGN KEY (subjectId) REFERENCES Subjects(subjectId) ON DELETE SET NULL
);

CREATE TABLE Grade (
    gradeId INT PRIMARY KEY,
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
    teacherId INT,
    FOREIGN KEY (courseId) REFERENCES Course(courseId) ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES Teacher(teacherId) ON DELETE SET NULL
);

CREATE TABLE LessonClass (
    lessonClassId INT AUTO_INCREMENT PRIMARY KEY,
    lessonId INT,
    classesId INT,
    FOREIGN KEY (lessonId) REFERENCES Lesson(lessonId) ON DELETE CASCADE,
    FOREIGN KEY (classesId) REFERENCES Classes(classesId) ON DELETE SET NULL
);

# Insertion

INSERT INTO PossibleRole (roleId, roleName) VALUES (1, 'Student'), (2, 'Teacher');

INSERT INTO Users (userId, userPassword, userLastName, userName, userEmail, userBirthdate, roleId)
VALUES (8, 'password123', 'Doe', 'John', 'johndoe@example.com', '1980-01-01', 2);

INSERT INTO Teacher (teacherId) VALUES (8);

INSERT INTO Classes (classesId, classesName)
VALUES (1, 'ING1 GSI'), (2, 'ING2 GSII');

INSERT INTO Subjects (subjectId, subjectName)
VALUES (1, 'Mathematics'), (2, 'Physics');

INSERT INTO Course (courseId, courseName, subjectId)
VALUES (1, 'Advanced Mathematics', 1), (2, 'Applied Physics', 2);

INSERT INTO Lesson (lessonId, lessonStartDate, lessonEndDate, courseId, teacherId)
VALUES 
(1, '2024-11-15 08:00:00', '2024-11-15 10:00:00', 1, 8), -- Leçon de maths
(2, '2024-11-16 10:00:00', '2024-11-16 12:00:00', 2, 8); -- Leçon de physique

INSERT INTO LessonClass (lessonClassId, lessonId, classesId)
VALUES 
(1, 1, 1), -- Leçon 1 associée à la classe ING1 GSI
(2, 1, 2), -- Leçon 1 associée à la classe ING2 GSII
(3, 2, 1), -- Leçon 2 associée à la classe ING1 GSI
(4, 2, 2); -- Leçon 2 associée à la classe ING2 GSII

INSERT INTO Users (userId, userPassword, userLastName, userName, userEmail, userBirthdate, roleId)
VALUES 
(1, 'password123', 'Mac', 'Martin', 'martin@example.com', '2004-01-01', 1),
(2, 'password123', 'Mouc', 'Eleonore', 'ele@example.com', '2003-02-01', 1),
(3, 'password123', 'Meuc', 'Fanch', 'fanch@example.com', '1998-03-01', 1),
(4, 'password123', 'Doriento', 'Theodore', 'theodore@example.com', '2004-04-01', 1),
(5, 'password123', 'Domingo', 'Elise', 'elise@example.com', '2004-05-01', 1);

INSERT INTO Users (userId, userPassword, userLastName, userName, userEmail, userBirthdate, roleId)
VALUES 
(6, 'password123', 'Azaloule', 'François', 'françois@example.com', '2003-06-01', 1),
(7, 'password123', 'Gnar', 'Gigot', 'gigot@example.com', '2002-07-01', 1),
(9, 'password123', 'Darius', 'Lola', 'lola@example.com', '2003-09-01', 1),
(10, 'password123', 'Potter', 'Ael', 'ael@example.com', '2004-10-01', 1),
(11, 'password123', 'King', 'Kingsley', 'king@example.com', '2003-11-01', 1);

-- Association des étudiants à la classe ING1 GSI (classeId = 1)
INSERT INTO Student (studentId, classesId) VALUES
(1, 1), -- Martin
(2, 1), -- Eleonore
(3, 1), -- Fanch
(4, 1), -- Theodore
(5, 1), -- Elise
(6, 2), -- François
(7, 2), -- Gigot
(9, 2), -- Lola
(10, 2), -- Ael
(11, 2); -- Kingsley


### TEST

	SELECT DISTINCT Users.userId, Users.userName, Users.userLastName, Users.userEmail
	FROM Users
	JOIN Student ON Users.userId = Student.studentId
	JOIN Classes ON Student.classesId = Classes.classesId
	JOIN LessonClass ON Classes.classesId = LessonClass.classesId
	JOIN Lesson ON LessonClass.lessonId = Lesson.lessonId
	JOIN Course ON Lesson.courseId = Course.courseId
	WHERE Course.courseName = 'Advanced Mathematics'
	AND Classes.classesName = 'ING1 GSI'
	AND Users.roleId = 1;  


UPDATE Student 
SET classesId = (SELECT classesId FROM Classes WHERE classesName = 'ING1 GSI')
WHERE studentId = 2;

-- Récupérer l'ID de la matière "Advanced Mathematics"
SELECT lessonId FROM Lesson 
WHERE courseId = (SELECT courseId FROM Course WHERE courseName = 'Advanced Mathematics');

-- Associer la matière à la classe "ING1 GSI"
INSERT INTO LessonClass (classesId, lessonId)
VALUES ((SELECT classesId FROM Classes WHERE classesName = 'ING1 GSI'),
        (SELECT lessonId FROM Lesson WHERE courseId = (SELECT courseId FROM Course WHERE courseName = 'Advanced Mathematics')));

