CREATE DATABASE CYDataBase;


USE CYDataBase;

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