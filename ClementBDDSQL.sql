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

# Données classes
INSERT INTO Classes VALUES (1, "GSI1");
INSERT INTO Classes VALUES (2, "GSI2");
INSERT INTO Classes VALUES (3, "GM1");
INSERT INTO Classes VALUES (4, "GM2");
INSERT INTO Classes VALUES (5, "BTC1");
INSERT INTO Classes VALUES (6, "BTC2");
INSERT INTO Classes VALUES (7, "MF1");
INSERT INTO Classes VALUES (8, "MF2");

# Données filières
INSERT INTO Major VALUES (1, "GSI");
INSERT INTO Major VALUES (2, "GM");
INSERT INTO Major VALUES (3, "BTC");
INSERT INTO Major VALUES (4, "MF");

# Données UE
INSERT INTO subjects VALUES (1, "Mathématiques");
INSERT INTO subjects VALUES (2, "Informatique");

# Données élèves
INSERT INTO users VALUES (1, "password", "DURÉCU", "Clément", "clement.shoddox@gmail.com", "2003-07-17", "student");
INSERT INTO users VALUES (2, "password", "DAANOUNI", "Siham", "axel@gmail.com", "2001-08-18", "student");
INSERT INTO users VALUES (3, "password", "PROVENT", "Amaury", "jude@gmail.com", "2002-09-19", "student");
INSERT INTO users VALUES (4, "password", "LEBIGH", "Hicham", "ondine@gmail.com", "2003-10-20", "student");
INSERT INTO users VALUES (5, "password", "AIT MOUSSA", "Amine", "pierre@gmail.com", "2003-11-21", "student");
INSERT INTO users VALUES (6, "password", "BARRÉ", "Guillaume", "bege@gmail.com", "2002-12-22", "student");
INSERT INTO users VALUES (7, "password", "DONATI", "Adrien", "adrien-donati@gmail.com", "2001-01-23", "student");
INSERT INTO users VALUES (8, "password", "MONTALBAN", "César", "cesar-montal@gmail.com", "2000-02-24", "student");
INSERT INTO users VALUES (9, "password", "DASSIER", "Maxime", "maxime-dassier@gmail.com", "2004-03-25", "student");
INSERT INTO users VALUES (10, "password", "EISNER", "Valentin", "valentin-eis@gmail.com", "2004-04-26", "student");
INSERT INTO users VALUES (11, "password", "BLAZE", "Axel", "axel-blaze@gmail.com", "2003-05-27", "student");
INSERT INTO users VALUES (12, "password", "PION", "Oscar", "oscar-pion@gmail.com", "2003-06-28", "student");
INSERT INTO users VALUES (13, "password", "TORRES", "Luna", "antoine-valls@gmail.com", "2003-10-29", "student");
INSERT INTO users VALUES (14, "password", "CORCEL", "Killian", "killian-corcel@gmail.com", "2002-08-10", "student");
INSERT INTO users VALUES (15, "password", "VIDAL", "Romain", "romain-vidal@gmail.com", "2003-06-15", "student");
INSERT INTO users VALUES (16, "password", "MARCI", "Thomas", "thomas-marci@gmail.com", "2004-01-12", "student");
INSERT INTO users VALUES (17, "password", "CANTOREL", "Estelle", "estelle-cantorel@gmail.com", "2002-11-11", "student");
INSERT INTO users VALUES (18, "password", "BARREL", "Nicolas", "nicolas-barrel@gmail.com", "2003-12-10", "student");
INSERT INTO users VALUES (19, "password", "RIVA", "Gabriel", "gabriel-riva@gmail.com", "2001-10-09", "student");
INSERT INTO users VALUES (20, "password", "CHAUME", "Vincent", "vincent-chaume@gmail.com", "2003-08-16", "student");
INSERT INTO users VALUES (21, "password", "POUSSIN", "Élise", "elise-poussin@gmail.com", "2003-07-01", "student");
INSERT INTO users VALUES (22, "password", "LEROUX", "Émilie", "emilie-leroux@gmail.com", "2003-03-08", "student");
INSERT INTO users VALUES (23, "password", "CASSAGNE", "Raphaël", "raphael-cassa@gmail.com", "2002-02-20", "student");
INSERT INTO users VALUES (24, "password", "BERGER", "Nicolas", "nicolas-berger@gmail.com", "2003-08-19", "student");

# Données professeurs
INSERT INTO users VALUES (25, "password", "CRANSAC", "Didier", "didier-cransac@gmail.com", "1985-02-20", "teacher");
INSERT INTO users VALUES (26, "password", "TIREL", "Vincent", "vincent-tirel@gmail.com", "1974-01-16", "teacher");
INSERT INTO users VALUES (27, "password", "MÉNIELLE", "Adrien", "adrien-menielle@gmail.com", "1990-06-15", "teacher");
INSERT INTO users VALUES (28, "password", "COSTES", "Éléonore", "eleonore-costes@gmail.com", "1965-10-17", "teacher");

# Données admin
INSERT INTO users VALUES (29, "admin", "ADMIN", "Admin", "admin@gmail.com", "1111-11-11", "administrator");

# Données étudiant-classes-filières
INSERT INTO student VALUES (1, 1, 1);
INSERT INTO student VALUES (2, 1, 1);
INSERT INTO student VALUES (3, 1, 1);
INSERT INTO student VALUES (4, 2, 1);
INSERT INTO student VALUES (5, 2, 1);
INSERT INTO student VALUES (6, 2, 1);
INSERT INTO student VALUES (7, 3, 2);
INSERT INTO student VALUES (8, 3, 2);
INSERT INTO student VALUES (9, 3, 2);
INSERT INTO student VALUES (10, 4, 2);
INSERT INTO student VALUES (11, 4, 2);
INSERT INTO student VALUES (12, 4, 2);
INSERT INTO student VALUES (13, 5, 3);
INSERT INTO student VALUES (14, 5, 3);
INSERT INTO student VALUES (15, 5, 3);
INSERT INTO student VALUES (16, 6, 3);
INSERT INTO student VALUES (17, 6, 3);
INSERT INTO student VALUES (18, 6, 3);
INSERT INTO student VALUES (19, 7, 4);
INSERT INTO student VALUES (20, 7, 4);
INSERT INTO student VALUES (21, 7, 4);
INSERT INTO student VALUES (22, 8, 4);
INSERT INTO student VALUES (23, 8, 4);
INSERT INTO student VALUES (24, 8, 4);

# Données professeurs
INSERT INTO teacher VALUES(25);
INSERT INTO teacher VALUES(26);
INSERT INTO teacher VALUES(27);
INSERT INTO teacher VALUES(28);

# Données admin
INSERT INTO administrator VALUES(33);

# Données cours-matière-UE
INSERT INTO course VALUES (1, "Statistiques", 1); 
INSERT INTO course VALUES (2, "Probabilités", 1); 
INSERT INTO course VALUES (3, "Programmation procédurale", 2); 
INSERT INTO course VALUES (4, "Cybersécurité", 2); 

# Données note-nom-valeur-coefficient-étudiant-cours-prof
INSERT INTO grade VALUES(1, "note 1", 10, 3, 1, 1, 25);
INSERT INTO grade VALUES(2, "note 2", 12, 2, 1, 2, 26);
INSERT INTO grade VALUES(3, "note 3", 14, 3, 1, 3, 27);
INSERT INTO grade VALUES(4, "note 4", 16, 2, 1, 4, 28);

INSERT INTO grade VALUES(5, "note 1", 9, 3, 2, 1, 25);
INSERT INTO grade VALUES(6, "note 2", 11, 2, 2, 2, 26);
INSERT INTO grade VALUES(7, "note 3", 13, 3, 2, 3, 27);
INSERT INTO grade VALUES(8, "note 4", 15, 2, 2, 4, 28);

INSERT INTO grade VALUES(9, "note 1", 8, 3, 3, 1, 25);
INSERT INTO grade VALUES(10, "note 2", 10, 2, 3, 2, 26);
INSERT INTO grade VALUES(11, "note 3", 12, 3, 3, 3, 27);
INSERT INTO grade VALUES(12, "note 4", 14, 2, 3, 4, 28);

INSERT INTO grade VALUES(13, "note 1", 7, 3, 4, 1, 25);
INSERT INTO grade VALUES(14, "note 2", 9, 2, 4, 2, 26);
INSERT INTO grade VALUES(15, "note 3", 11, 3, 4, 3, 27);
INSERT INTO grade VALUES(16, "note 4", 13, 2, 4, 4, 28);

INSERT INTO grade VALUES(17, "note 1", 6, 3, 5, 1, 25);
INSERT INTO grade VALUES(18, "note 2", 8, 2, 5, 2, 26);
INSERT INTO grade VALUES(19, "note 3", 10, 3, 5, 3, 27);
INSERT INTO grade VALUES(20, "note 4", 12, 2, 5, 4, 28);

INSERT INTO grade VALUES(21, "note 1", 15, 3, 6, 1, 25);
INSERT INTO grade VALUES(22, "note 2", 17, 2, 6, 2, 26);
INSERT INTO grade VALUES(23, "note 3", 19, 3, 6, 3, 27);
INSERT INTO grade VALUES(24, "note 4", 20, 2, 6, 4, 28);

INSERT INTO grade VALUES(25, "note 1", 11, 3, 7, 1, 25);
INSERT INTO grade VALUES(26, "note 2", 13, 2, 7, 2, 26);
INSERT INTO grade VALUES(27, "note 3", 15, 3, 7, 3, 27);
INSERT INTO grade VALUES(28, "note 4", 17, 2, 7, 4, 28);

INSERT INTO grade VALUES(29, "note 1", 10, 3, 8, 1, 25);
INSERT INTO grade VALUES(30, "note 2", 12, 2, 8, 2, 26);
INSERT INTO grade VALUES(31, "note 3", 14, 3, 8, 3, 27);
INSERT INTO grade VALUES(32, "note 4", 16, 2, 8, 4, 28);

INSERT INTO grade VALUES(33, "note 1", 18, 3, 9, 1, 25);
INSERT INTO grade VALUES(34, "note 2", 20, 2, 9, 2, 26);
INSERT INTO grade VALUES(35, "note 3", 22, 3, 9, 3, 27);
INSERT INTO grade VALUES(36, "note 4", 24, 2, 9, 4, 28);

INSERT INTO grade VALUES(37, "note 1", 14, 3, 11, 1, 25);
INSERT INTO grade VALUES(38, "note 2", 16, 2, 11, 2, 26);
INSERT INTO grade VALUES(39, "note 3", 18, 3, 11, 3, 27);
INSERT INTO grade VALUES(40, "note 4", 20, 2, 11, 4, 28);

INSERT INTO grade VALUES(41, "note 1", 14, 3, 12, 1, 25);
INSERT INTO grade VALUES(42, "note 2", 16, 2, 12, 2, 26);
INSERT INTO grade VALUES(43, "note 3", 18, 3, 12, 3, 27);
INSERT INTO grade VALUES(44, "note 4", 20, 2, 12, 4, 28);

INSERT INTO grade VALUES(45, "note 1", 14, 3, 13, 1, 25);
INSERT INTO grade VALUES(46, "note 2", 16, 2, 13, 2, 26);
INSERT INTO grade VALUES(47, "note 3", 18, 3, 13, 3, 27);
INSERT INTO grade VALUES(48, "note 4", 20, 2, 13, 4, 28);

INSERT INTO grade VALUES(49, "note 1", 14, 3, 14, 1, 25);
INSERT INTO grade VALUES(50, "note 2", 16, 2, 14, 2, 26);
INSERT INTO grade VALUES(51, "note 3", 18, 3, 14, 3, 27);
INSERT INTO grade VALUES(52, "note 4", 20, 2, 14, 4, 28);

INSERT INTO grade VALUES(53, "note 1", 14, 3, 15, 1, 25);
INSERT INTO grade VALUES(54, "note 2", 16, 2, 15, 2, 26);
INSERT INTO grade VALUES(55, "note 3", 18, 3, 15, 3, 27);
INSERT INTO grade VALUES(56, "note 4", 20, 2, 15, 4, 28);

INSERT INTO grade VALUES(57, "note 1", 14, 3, 16, 1, 25);
INSERT INTO grade VALUES(58, "note 2", 16, 2, 16, 2, 26);
INSERT INTO grade VALUES(59, "note 3", 18, 3, 16, 3, 27);
INSERT INTO grade VALUES(60, "note 4", 20, 2, 16, 4, 28);

INSERT INTO grade VALUES(61, "note 1", 14, 3, 17, 1, 25);
INSERT INTO grade VALUES(62, "note 2", 16, 2, 17, 2, 26);
INSERT INTO grade VALUES(63, "note 3", 18, 3, 17, 3, 27);
INSERT INTO grade VALUES(64, "note 4", 20, 2, 17, 4, 28);

INSERT INTO grade VALUES(65, "note 1", 14, 3, 18, 1, 25);
INSERT INTO grade VALUES(66, "note 2", 16, 2, 18, 2, 26);
INSERT INTO grade VALUES(67, "note 3", 18, 3, 18, 3, 27);
INSERT INTO grade VALUES(68, "note 4", 20, 2, 18, 4, 28);

INSERT INTO grade VALUES(69, "note 1", 14, 3, 19, 1, 25);
INSERT INTO grade VALUES(70, "note 2", 16, 2, 19, 2, 26);
INSERT INTO grade VALUES(71, "note 3", 18, 3, 19, 3, 27);
INSERT INTO grade VALUES(72, "note 4", 20, 2, 19, 4, 28);

INSERT INTO grade VALUES(73, "note 1", 14, 3, 20, 1, 25);
INSERT INTO grade VALUES(74, "note 2", 16, 2, 20, 2, 26);
INSERT INTO grade VALUES(75, "note 3", 18, 3, 20, 3, 27);
INSERT INTO grade VALUES(76, "note 4", 20, 2, 20, 4, 28);

INSERT INTO grade VALUES(77, "note 1", 14, 3, 21, 1, 25);
INSERT INTO grade VALUES(78, "note 2", 16, 2, 21, 2, 26);
INSERT INTO grade VALUES(79, "note 3", 18, 3, 21, 3, 27);
INSERT INTO grade VALUES(80, "note 4", 20, 2, 21, 4, 28);

INSERT INTO grade VALUES(81, "note 1", 14, 3, 22, 1, 25);
INSERT INTO grade VALUES(82, "note 2", 16, 2, 22, 2, 26);
INSERT INTO grade VALUES(83, "note 3", 18, 3, 22, 3, 27);
INSERT INTO grade VALUES(84, "note 4", 20, 2, 22, 4, 28);

INSERT INTO grade VALUES(85, "note 1", 14, 3, 23, 1, 25);
INSERT INTO grade VALUES(86, "note 2", 16, 2, 23, 2, 26);
INSERT INTO grade VALUES(87, "note 3", 18, 3, 23, 3, 27);
INSERT INTO grade VALUES(88, "note 4", 20, 2, 23, 4, 28);

INSERT INTO grade VALUES(89, "note 1", 14, 3, 24, 1, 25);
INSERT INTO grade VALUES(90, "note 2", 16, 2, 24, 2, 26);
INSERT INTO grade VALUES(91, "note 3", 18, 3, 24, 3, 27);
INSERT INTO grade VALUES(92, "note 4", 20, 2, 24, 4, 28);

INSERT INTO grade VALUES(93, "note 1", 18, 3, 10, 1, 25);
INSERT INTO grade VALUES(94, "note 2", 20, 2, 10, 2, 26);
INSERT INTO grade VALUES(95, "note 3", 22, 3, 10, 3, 27);
INSERT INTO grade VALUES(36, "note 4", 24, 2, 10, 4, 28);

# Données lesson-start-end-course-teacher
INSERT INTO lesson VALUES (1, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 1, 25);
INSERT INTO lesson VALUES (2, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 2, 26);
INSERT INTO lesson VALUES (3, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 3, 27);
INSERT INTO lesson VALUES (4, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 4, 28);
INSERT INTO lesson VALUES (5, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 1, 25);
INSERT INTO lesson VALUES (6, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 2, 26);
INSERT INTO lesson VALUES (7, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 3, 27);
INSERT INTO lesson VALUES (8, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 4, 28);
INSERT INTO lesson VALUES (9, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 1, 25);
INSERT INTO lesson VALUES (10, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 2, 26);
INSERT INTO lesson VALUES (11, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 3, 27);
INSERT INTO lesson VALUES (12, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 4, 28);
INSERT INTO lesson VALUES (13, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 1, 25);
INSERT INTO lesson VALUES (14, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 2, 26);
INSERT INTO lesson VALUES (15, "2024-11-20T08:30:00", "2024-11-20T10:00:00", 3, 27);
INSERT INTO lesson VALUES (16, "2024-12-22T14:45:00", "2024-12-22T16:15:00", 4, 28);

# Données course-nom-subject
INSERT INTO course VALUES (1, "Statistiques", 1); 
INSERT INTO course VALUES (2, "Probabilités", 1); 
INSERT INTO course VALUES (3, "Programmation procédurale", 2); 
INSERT INTO course VALUES (4, "Cybersécurité", 2); 

# Données lessonclass-lesson-class
INSERT INTO lessonClass VALUES (1, 1, 1);
INSERT INTO lessonClass VALUES (2, 2, 1);
INSERT INTO lessonClass VALUES (3, 3, 2);
INSERT INTO lessonClass VALUES (4, 4, 2);
INSERT INTO lessonClass VALUES (5, 5, 3);
INSERT INTO lessonClass VALUES (6, 6, 3);
INSERT INTO lessonClass VALUES (7, 7, 4);
INSERT INTO lessonClass VALUES (8, 8, 4);
INSERT INTO lessonClass VALUES (9, 9, 5);
INSERT INTO lessonClass VALUES (10, 10, 5);
INSERT INTO lessonClass VALUES (11, 11, 6);
INSERT INTO lessonClass VALUES (12, 12, 6);
INSERT INTO lessonClass VALUES (13, 13, 7);
INSERT INTO lessonClass VALUES (14, 14, 7);
INSERT INTO lessonClass VALUES (15, 15, 8);
INSERT INTO lessonClass VALUES (16, 16, 8);


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

SELECT c.classId, c.className FROM Classes c WHERE c.classId NOT IN (SELECT sc.classId FROM LessonClass sc WHERE sc.lessonId = 2);
