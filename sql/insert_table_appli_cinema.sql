-- 1. Insertion des Utilisateurs (Gérants potentiels)
INSERT INTO
	utilisateur (nom, prenom, login, mdp)
VALUES
	(
		'Dupont',
		'Jean',
		'jean.dupont@email.com',
		'$2a$10$c0v8VO5cjGChXqrcF.Wfaeiwktykdlg2m8Ei0RTyELo8PzDWCKL66'
	),
	(
		'Martin',
		'Alice',
		'alice.martin@email.com',
		'$2a$10$rFTQbM9lgdjmtZ1qmSNN/.eGv8Ftl0tSzl.2p28t8DHvJHHmS0mAG'
	),
	(
		'Bernard',
		'Lucas',
		'lucas.bernard@email.com',
		'$2a$10$I584liCPodTxnyZhteDKEOQwzMptBVOMn18tTJbEaIj95K9p0cd5W'
	);

-- 2. Insertion des Franchises
-- On lie ici les franchises aux utilisateurs créés précédemment
INSERT INTO franchise 
	(nom_franchise, siege_social, id_gerant) 
VALUES
	('CinéMax', '12 rue de la Paix, Paris', 1),
	('Écran Total', '45 avenue des Arts, Lyon', 2),
	('MegaFilms', '22 boulevard Haussmann, Paris', 3),
	('Galaxy Ciné', '10 rue Nationale, Lille', 1);

-- 3. Insertion des Cinémas
-- Chaque cinéma est rattaché à une franchise via son ID
INSERT INTO cinema (denomination, adresse, ville, id_franchise) VALUES
-- CinéMax
('CinéMax Étoile', '5 Place de l''Étoile', 'Paris', 1),
('CinéMax Rivoli', '100 rue de Rivoli', 'Paris', 1),
-- Écran Total
('Le Grand Écran', '8 rue de la République', 'Lyon', 2),
('Écran Confluence', '112 cours Charlemagne', 'Lyon', 2),
-- MegaFilms
('MegaFilms Opéra', '3 rue Auber', 'Paris', 3),
('MegaFilms Bastille', '15 rue de la Roquette', 'Paris', 3),
-- Galaxy Ciné
('Galaxy Lille Centre', '20 rue Faidherbe', 'Lille', 4),
('Galaxy Euratech', '2 avenue de Bretagne', 'Lille', 4);


-- 4. Insertion des Salles
-- On crée plusieurs salles pour chaque cinéma
INSERT INTO salle (numero, description, nb_places, id_cinema) VALUES
-- CinéMax Étoile
(1, 'Salle Prestige', 150, 1),
(2, 'Salle Standard', 80, 1),
(3, 'Salle Standard', 80, 1),
-- CinéMax Rivoli
(1, 'Grande Salle', 300, 2),
(2, 'Salle Confort', 120, 2),
-- Le Grand Écran
(1, 'Salle IMAX', 450, 3),
(2, 'Salle Classique', 120, 3),
-- Écran Confluence
(1, 'Salle Premium', 200, 4),
(2, 'Salle Standard', 100, 4),
(3, 'Salle Standard', 100, 4),
-- MegaFilms Opéra
(1, 'Salle Luxe', 180, 5),
(2, 'Salle Standard', 90, 5),
-- MegaFilms Bastille
(1, 'Salle Action', 220, 6),
(2, 'Salle Classique', 110, 6),
-- Galaxy Lille Centre
(1, 'Salle Grand Format', 350, 7),
(2, 'Salle Standard', 120, 7),
-- Galaxy Euratech
(1, 'Salle Futuriste', 250, 8),
(2, 'Salle VR', 60, 8);