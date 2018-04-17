SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS Eleve, Professeur, Resultat, Obtient, Mot, Champ_lexical;

SET FOREIGN_KEY_CHECKS=1;



CREATE TABLE IF NOT EXISTS Eleve (
       id_eleve INT NOT NULL AUTO_INCREMENT,
       nom_eleve VARCHAR(30) NOT NULL,
       prenom_eleve VARCHAR(30) NOT NULL,
       PRIMARY KEY (id_eleve)
);

CREATE TABLE IF NOT EXISTS Professeur(
       id_professeur INT NOT NULL AUTO_INCREMENT,
       mot_passe VARCHAR(60) NOT NULL,
       PRIMARY KEY (id_professeur)
);

CREATE TABLE IF NOT EXISTS Resultat (
       id_resultat INT NOT NULL AUTO_INCREMENT,
       niveau INT NOT NULL,
       resultat BOOLEAN NOT NULL,
       PRIMARY KEY (id_resultat)
);

CREATE TABLE IF NOT EXISTS Obtient (
       id_eleve INT NOT NULL,
       id_resultat INT NOT NULL,
       date INT NOT NULL,
       num_essai INT NOT NULL,
       FOREIGN KEY (id_eleve) REFERENCES Eleve(id_eleve),
       FOREIGN KEY (id_resultat) REFERENCES Resultat(id_resultat)
);

CREATE TABLE IF NOT EXISTS Mot (
       id_mot INT NOT NULL AUTO_INCREMENT,
       mot VARCHAR(60),
       classe INT NOT NULL,
       PRIMARY KEY (id_mot)
);

CREATE TABLE IF NOT EXISTS Champ_lexical(
       id_champ INT NOT NULL AUTO_INCREMENT,
       nom_champ VARCHAR(60),
       classe INT NOT NULL,
       PRIMARY KEY (id_champ)
);
       
