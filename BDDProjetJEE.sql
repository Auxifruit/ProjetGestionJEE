CREATE DATABASE CYDataBase;

/*
# Suppression des tables dans l'ordre
DROP TABLE administrateur;
DROP TABLE note;
DROP TABLE étudiant;
DROP TABLE classe;
DROP TABLE datecours;
DROP TABLE coursdateprof;
DROP TABLE cours;
DROP TABLE enseignant;
DROP TABLE matière;
DROP TABLE utilisateur;
*/

CREATE TABLE Utilisateur (
	idUtilisateur INT AUTO_INCREMENT PRIMARY KEY,
    identifiantUtilisateur VARCHAR(50),
    motDePasseUtilisateur VARCHAR(50),
    rôle VARCHAR(50)
);

CREATE TABLE Administrateur (
    emailAdmin VARCHAR(50) PRIMARY KEY,
    nomAdministrateur VARCHAR(50),
    prénomAdministrateur VARCHAR(50),
    idUtilisateur INT,
    
    FOREIGN KEY  (idUtilisateur) REFERENCES Utilisateur(idUtilisateur)
);

CREATE TABLE Enseignant (
	emailEnseignant VARCHAR(50) PRIMARY KEY,
    nomEnseignant VARCHAR(50),
    prénomEnseignant VARCHAR(50),
    dateDENaissanceEnseignant VARCHAR(50),
    idUtilisateur INT,
    
    FOREIGN KEY  (idUtilisateur) REFERENCES Utilisateur(idUtilisateur)
);


CREATE TABLE Classe(
	idClasse INT PRIMARY KEY,
    nomClasse VARCHAR(50)
);

CREATE TABLE Matière(
	idMatière INT PRIMARY KEY,
    nomMatière VARCHAR(50)
);

CREATE TABLE Étudiant (
	emailÉtudiant VARCHAR(50) PRIMARY KEY,
    nomÉtudiant VARCHAR(50),
    prénomÉtudiant VARCHAR(50),
    dateDENaissanceÉtudiant VARCHAR(50),
    idUtilisateur INT,
    idClasse INT, 
    
	FOREIGN KEY  (idUtilisateur) REFERENCES Utilisateur(idUtilisateur),
    FOREIGN KEY  (idClasse) REFERENCES Classe(idClasse)
);

CREATE TABLE Note (
	idNote INT PRIMARY KEY,
    valeurNote DOUBLE,
    coefficientNote INT,
    emailÉtudiant VARCHAR(50),
    idMatière INT,
    emailEnseignant VARCHAR(50),
    
    FOREIGN KEY (emailÉtudiant) REFERENCES Étudiant(emailÉtudiant),
    FOREIGN KEY (idMatière) REFERENCES Matière(idMatière),
    FOREIGN KEY (emailEnseignant) REFERENCES Enseignant(emailEnseignant)
);

CREATE TABLE Cours(
	idCours INT PRIMARY KEY,
    nomCours VARCHAR(50),
    idMatière INT,
    
    FOREIGN KEY (idMatière) REFERENCES Matière(idMatière)
);

CREATE TABLE DateCours (
	dateCours DATETIME PRIMARY KEY #YYYY-MM-DD HH:MM:SS
);

CREATE TABLE CoursDateProf (
	dateCours DATETIME,
    idCours INT,
	emailEnseignant VARCHAR(50),
    
	FOREIGN KEY (dateCours) REFERENCES DateCours(dateCours),
    FOREIGN KEY (idCours) REFERENCES Cours(idCours),
    FOREIGN KEY (emailEnseignant) REFERENCES Enseignant(emailEnseignant),
    
    PRIMARY KEY(dateCours, idCours, emailEnseignant)
);

SELECT * FROM administrateur;
SELECT * FROM classe;
SELECT * FROM cours;
SELECT * FROM coursdateprof;
SELECT * FROM datecours;
SELECT * FROM enseignant;
SELECT * FROM matière;
SELECT * FROM note;
SELECT * FROM utilisateur;
SELECT * FROM étudiant;

# Jeu de données pour test
INSERT INTO classe VALUES (1, "GSI1");
INSERT INTO classe VALUES (2, "GSI2");
INSERT INTO classe VALUES (3, "GSI3");

INSERT INTO matière VALUES (1, "Mathématiques");
INSERT INTO matière VALUES (2, "Informatique");

INSERT INTO utilisateur VALUES (1, "mark@gmail.com", "password", "ETUDIANT");
INSERT INTO utilisateur VALUES (2, "axel@gmail.com", "password", "ETUDIANT");
INSERT INTO utilisateur VALUES (3, "jude@gmail.com", "password", "ETUDIANT");
INSERT INTO utilisateur VALUES (4, "ondine@gmail.com", "password", "ENSEIGNANT");
INSERT INTO utilisateur VALUES (5, "pierre@gmail.com", "password", "ENSEIGNANT");
INSERT INTO utilisateur VALUES (6, "bege@gmail.com", "password", "ADMINISTRATEUR");
INSERT INTO utilisateur VALUES (7, "newgate@gmail.com", "password", "ADMINISTRATEUR");

INSERT INTO étudiant VALUES ("mark@gmail.com", "Evans", "Mark", "01/02/2000", 1, 3);
INSERT INTO étudiant VALUES ("axel@gmail.com", "Blaze", "Axel", "03/04/2001", 2, 1);
INSERT INTO étudiant VALUES ("jude@gmail.com", "Sharp", "Jude", "05/06/2002", 3, 2);

INSERT INTO enseignant VALUES("ondine@gmail.com", "Eau", "Ondie", "07/08/1982", 4);
INSERT INTO enseignant VALUES("pierre@gmail.com", "Roche", "Pierre", "09/10/1978", 5);

INSERT INTO administrateur VALUES("bege@gmail.com", "Capone", "Bege", 6);
INSERT INTO administrateur VALUES("edward@gmail.com", "Newgate", "Edward", 7);

INSERT INTO note VALUES(1, 8, 3, "mark@gmail.com", 1, "pierre@gmail.com");
INSERT INTO note VALUES(2, 11, 2, "mark@gmail.com", 2, "ondine@gmail.com");
INSERT INTO note VALUES(3, 12, 3, "axel@gmail.com", 1, "pierre@gmail.com");
INSERT INTO note VALUES(4, 14, 2, "axel@gmail.com", 2, "ondine@gmail.com");
INSERT INTO note VALUES(5, 16, 3, "jude@gmail.com", 1, "pierre@gmail.com");
INSERT INTO note VALUES(6, 19, 2, "jude@gmail.com", 2, "ondine@gmail.com");

INSERT INTO datecours VALUES ("2024-10-10 10:15:00");
INSERT INTO datecours VALUES ("2024-10-29 13:00:00");
INSERT INTO datecours VALUES ("2024-11-02 08:30:00");
INSERT INTO datecours VALUES ("2024-11-23 16:30:00");

INSERT INTO cours VALUES (1, "Statistique", 1); 
INSERT INTO cours VALUES (2, "JEE", 2); 
INSERT INTO cours VALUES (3, "Optimisation Linéaire", 1); 
INSERT INTO cours VALUES (4, "Design Patern", 2); 

INSERT INTO coursdateprof VALUES ("2024-10-10 10:15:00", 1, "pierre@gmail.com");
INSERT INTO coursdateprof VALUES ("2024-10-29 13:00:00", 2, "ondine@gmail.com");
INSERT INTO coursdateprof VALUES ("2024-11-02 08:30:00", 3, "pierre@gmail.com");
INSERT INTO coursdateprof VALUES ("2024-11-23 16:30:00", 4, "ondine@gmail.com");

# Exemple de query

SELECT * FROM étudiant WHERE idClasse = 2;

SELECT * FROM coursdateprof WHERE emailEnseignant = (
	SELECT identifiantUtilisateur FROM utilisateur WHERE idUtilisateur = 5
);

SELECT * FROM coursdateprof WHERE dateCours LIKE("%-10-%");
SELECT * FROM coursdateprof WHERE dateCours LIKE("%-%-% %:30:%");

SELECT COUNT(*) as NombreÉlèves, idClasse FROM étudiant
GROUP BY idClasse;

SELECT * FROM note WHERE emailÉtudiant = (
SELECT emailÉtudiant FROM étudiant where idClasse = 1);