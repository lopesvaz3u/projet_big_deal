CREATE TYPE EEtatMatch AS ENUM ('Prévu', 'En_cours', 'Terminé', 'Annulé');

-- Création de la table Compte
CREATE TABLE Compte (
    id_compte SERIAL PRIMARY KEY,
    identifiant VARCHAR(255) NOT NULL,
    mot_de_passe VARCHAR(255) NOT NULL,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    telephone VARCHAR(15) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    mail VARCHAR(255) NOT NULL
);

-- Création de la table Parieur
CREATE TABLE Parieur (
    id_parieur SERIAL PRIMARY KEY,
    rib VARCHAR(255) NOT NULL,
    argent FLOAT NOT NULL,
    id_compte INTEGER NOT NULL UNIQUE,
    FOREIGN KEY (id_compte) REFERENCES Compte(id_compte)
);

-- Création de la table Bookmaker
CREATE TABLE Bookmaker (
    id_bookmaker SERIAL PRIMARY KEY,
    id_compte INTEGER NOT NULL UNIQUE,
    FOREIGN KEY (id_compte) REFERENCES Compte(id_compte)
);

-- Création de la table Competition
CREATE TABLE Competition (
    id_competition SERIAL PRIMARY KEY,
    nom_competition VARCHAR(255) NOT NULL
);

-- Création de la table Equipe
CREATE TABLE Equipe (
    id_equipe SERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    pays VARCHAR(255) NOT NULL
);

-- Création de la table Match
CREATE TABLE Match (
    id_match SERIAL PRIMARY KEY,
    cote FLOAT NOT NULL,
    depart_prevu DATE NOT NULL,
    depart_reel DATE,
    etat VARCHAR(50) EEtatMatch NOT NULL,
    id_equipe_1 INTEGER NOT NULL,
    id_equipe_2 INTEGER NOT NULL,
    score_equipe_1 INTEGER NOT NULL DEFAULT 0,
    score_equipe_2 INTEGER NOT NULL DEFAULT 0,
    id_competition INTEGER ,
    FOREIGN KEY (id_equipe_1) REFERENCES Equipe(id_equipe),
    FOREIGN KEY (id_equipe_2) REFERENCES Equipe(id_equipe),
    FOREIGN KEY (id_competition) REFERENCES Competition(id_competition)
);

-- Création de la table Pari
CREATE TABLE Pari (
    id_paris SERIAL PRIMARY KEY,
    somme FLOAT NOT NULL,
    combine BOOLEAN NOT NULL,
    id_parieur INTEGER NOT NULL,
    FOREIGN KEY (id_parieur) REFERENCES Parieur(id_parieur)
);

-- Création de la table Association_Match_Paris
CREATE TABLE Association_Match_Paris (
    id_match INTEGER NOT NULL,
    id_paris INTEGER NOT NULL,
    PRIMARY KEY (id_match, id_paris),
    FOREIGN KEY (id_match) REFERENCES Match(id_match),
    FOREIGN KEY (id_paris) REFERENCES Pari(id_paris)
);