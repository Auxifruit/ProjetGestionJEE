CREATE DATABASE CYDataBase;


# Suppression des tables dans l'ordre
/*
DROP TABLE administrateur;
DROP TABLE note;
DROP TABLE etudiant;
DROP TABLE classe;
DROP TABLE coursdateprof;
DROP TABLE datecours;
DROP TABLE cours;
DROP TABLE enseignant;
DROP TABLE matiere;
DROP TABLE utilisateur;
DROP TABLE rolepossible;
*/

CREATE TABLE RolePossible (
	idRole INT AUTO_INCREMENT PRIMARY KEY,
    nomRole VARCHAR(50)
);

CREATE TABLE Utilisateur (
	idUtilisateur INT AUTO_INCREMENT PRIMARY KEY,
    motDePasseUtilisateur VARCHAR(50),
    nomUtilisateur VARCHAR(50),
    prenomUtilisateur VARCHAR(50),
    emailUtilisateur VARCHAR(50),
    dateDeNaissanceUtilisateur VARCHAR(50),
    idRole int,
    
    FOREIGN KEY (idRole) REFERENCES RolePossible(idRole) ON DELETE CASCADE
);

CREATE TABLE Administrateur (
    idAdministrateur INT,
    
    FOREIGN KEY (idAdministrateur) REFERENCES Utilisateur(idUtilisateur) ON DELETE CASCADE,
	
    PRIMARY KEY (idAdministrateur)
);

CREATE TABLE Enseignant (
    idEnseignant INT,
    
    FOREIGN KEY (idEnseignant) REFERENCES Utilisateur(idUtilisateur) ON DELETE CASCADE,
    
    PRIMARY KEY (idEnseignant)
);


CREATE TABLE Classe(
	idClasse INT PRIMARY KEY,
    nomClasse VARCHAR(50)
);


CREATE TABLE Etudiant (
    idEtudiant INT,
    idClasse INT, 
    
	FOREIGN KEY  (idEtudiant) REFERENCES Utilisateur(idUtilisateur) ON DELETE CASCADE,
    FOREIGN KEY  (idClasse) REFERENCES Classe(idClasse) ON DELETE SET NULL,
    
    PRIMARY KEY (idEtudiant)
);



CREATE TABLE Matiere(
	idMatiere INT PRIMARY KEY,
    nomMatiere VARCHAR(50)
);

CREATE TABLE Note (
    idNote INT PRIMARY KEY,
    valeurNote DOUBLE,
    coefficientNote INT,
    idEtudiant INT,
    idMatiere INT,
    idEnseignant INT,

    FOREIGN KEY (idEtudiant) REFERENCES Etudiant(idEtudiant) ON DELETE CASCADE,
    FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere) ON DELETE SET NULL,
    FOREIGN KEY (idEnseignant) REFERENCES Enseignant(idEnseignant) ON DELETE SET NULL
);

CREATE TABLE Cours(
	idCours INT PRIMARY KEY,
    nomCours VARCHAR(50),
    idMatiere INT,
    
    FOREIGN KEY (idMatiere) REFERENCES Matiere(idMatiere) ON DELETE SET NULL
);

CREATE TABLE DateCours (
	dateCours DATETIME PRIMARY KEY #YYYY-MM-DD HH:MM:SS
);

CREATE TABLE CoursDateProf (
	dateCours DATETIME,
    idCours INT,
	idEnseignant int,
    
	FOREIGN KEY (dateCours) REFERENCES DateCours(dateCours) ON DELETE CASCADE,
    FOREIGN KEY (idCours) REFERENCES Cours(idCours) ON DELETE CASCADE,
    FOREIGN KEY (idEnseignant) REFERENCES Enseignant(idEnseignant) ON DELETE CASCADE,
    
    PRIMARY KEY(dateCours, idCours, idEnseignant)
);


SELECT * FROM classe;
SELECT * FROM cours;
SELECT * FROM coursdateprof;
SELECT * FROM datecours;

SELECT * FROM matiere;
SELECT * FROM note;

SELECT * FROM administrateur;
SELECT * FROM enseignant;
SELECT * FROM utilisateur;
SELECT * FROM etudiant;

# Jeu de données pour test
INSERT INTO rolePossible VALUES (1, "etudiant");
INSERT INTO rolePossible VALUES (2, "enseignant");
INSERT INTO rolePossible VALUES (3, "administrateur");

INSERT INTO classe VALUES (1, "GSI1");
INSERT INTO classe VALUES (2, "GSI2");
INSERT INTO classe VALUES (3, "GSI3");

INSERT INTO matiere VALUES (1, "Mathématiques");
INSERT INTO matiere VALUES (2, "Informatique");

INSERT INTO utilisateur VALUES (1, "password", "Evans", "Mark", "mark@gmail.com", "01/02/2000", 1);
INSERT INTO utilisateur VALUES (2, "password", "Blaze", "Axel", "axel@gmail.com", "05/06/2002", 1);
INSERT INTO utilisateur VALUES (3, "password", "Sharp", "Jude", "jude@gmail.com", "03/04/2001", 1);
INSERT INTO utilisateur VALUES (4, "password", "Eau", "Ondie", "ondine@gmail.com", "07/08/1982", 2);
INSERT INTO utilisateur VALUES (5, "password", "Roche", "Pierre", "pierre@gmail.com", "09/10/1978", 2);
INSERT INTO utilisateur VALUES (6, "password", "Capone", "Bege", "bege@gmail.com", "12/01/1786", 3);
INSERT INTO utilisateur VALUES (7, "password", "Newgate", "Edward", "edward@gmail.com", "17/02/1781", 3);

INSERT INTO etudiant VALUES (1, 3);
INSERT INTO etudiant VALUES (2, 1);
INSERT INTO etudiant VALUES (3, 2);

INSERT INTO enseignant VALUES(4);
INSERT INTO enseignant VALUES(5);

INSERT INTO administrateur VALUES(6);
INSERT INTO administrateur VALUES(7);

INSERT INTO note VALUES(1, 8, 3, 1, 1, 5);
INSERT INTO note VALUES(2, 11, 2, 1, 2, 4);
INSERT INTO note VALUES(3, 12, 3, 2, 1, 5);
INSERT INTO note VALUES(4, 14, 2, 2, 2, 4);
INSERT INTO note VALUES(5, 16, 3, 3, 1, 5);
INSERT INTO note VALUES(6, 19, 2, 3, 2, 4);

INSERT INTO datecours VALUES ("2024-10-10 10:15:00");
INSERT INTO datecours VALUES ("2024-10-29 13:00:00");
INSERT INTO datecours VALUES ("2024-11-02 08:30:00");
INSERT INTO datecours VALUES ("2024-11-23 16:30:00");

INSERT INTO cours VALUES (1, "Statistique", 1); 
INSERT INTO cours VALUES (2, "JEE", 2); 
INSERT INTO cours VALUES (3, "Optimisation Linéaire", 1); 
INSERT INTO cours VALUES (4, "Design Patern", 2); 

INSERT INTO coursdateprof VALUES ("2024-10-10 10:15:00", 1, 5);
INSERT INTO coursdateprof VALUES ("2024-10-29 13:00:00", 2, 4);
INSERT INTO coursdateprof VALUES ("2024-11-02 08:30:00", 3, 5);
INSERT INTO coursdateprof VALUES ("2024-11-23 16:30:00", 4, 4);


# Exemple de query

SELECT * FROM etudiant WHERE idClasse = 2;

SELECT * FROM coursdateprof WHERE emailEnseignant = (
	SELECT identifiantUtilisateur FROM utilisateur WHERE idUtilisateur = 5
);

SELECT * FROM coursdateprof WHERE dateCours LIKE("%-10-%");
SELECT * FROM coursdateprof WHERE dateCours LIKE("%-%-% %:30:%");

SELECT COUNT(*) as NombreEleves, idClasse FROM étudiant
GROUP BY idClasse;

SELECT * FROM note WHERE emailEtudiant = (
SELECT emailEtudiant FROM etudiant where idClasse = 1);