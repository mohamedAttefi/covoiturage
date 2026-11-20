-- Optional demo dataset for a fresh database, after Liquibase has created the schema.
-- Every main table receives at least five rows; IDs are reset to avoid sequence collisions.
INSERT INTO conducteurs (nom, prenom, email, telephone, numero_permis) VALUES
('Alami','Hamid','hamid.demo@example.com','0612345678','DEMO-PER-001'),
('Bennani','Salma','salma.driver@example.com','0612345679','DEMO-PER-002'),
('Idrissi','Youssef','youssef.driver@example.com','0612345680','DEMO-PER-003'),
('Alaoui','Nadia','nadia.driver@example.com','0612345681','DEMO-PER-004'),
('Tazi','Omar','omar.driver@example.com','0612345682','DEMO-PER-005');

INSERT INTO passagers (nom, prenom, email, telephone) VALUES
('Salma','Amrani','salma.demo@example.com','0620000002'),
('Imane','Zahra','imane.demo@example.com','0620000003'),
('Mehdi','Rami','mehdi.demo@example.com','0620000004'),
('Karim','Ali','karim.demo@example.com','0620000001'),
('Sara','Naji','sara.demo@example.com','0620000005');

INSERT INTO vehicules (marque, modele, immatriculation, couleur, capacite, conducteur_id) VALUES
('Dacia','Logan','DEMO-001-A-6','Blanc',4,1),
('Renault','Clio','DEMO-002-A-6','Gris',4,2),
('Peugeot','208','DEMO-003-A-6','Bleu',3,3),
('Toyota','Yaris','DEMO-004-A-6','Rouge',4,4),
('Hyundai','i20','DEMO-005-A-6','Noir',5,5);

INSERT INTO trajets ( conducteur_id, vehicule_id, places_proposees, statut) VALUES
(1,1,3,'PUBLIE'),(2,2,3,'PUBLIE'),
(3,3,2,'PUBLIE'),(4,4,3,'PUBLIE'),
(5,5,4,'PUBLIE');

INSERT INTO points_arret (ville, ordre, heure_passage, prix_depuis_depart, trajet_id) VALUES
('Rabat',0,'2030-06-01 17:00:00',0,1),
('Settat',3,'2030-06-01 19:15:00',70,1),
('Casablanca',0,'2030-06-02 08:00:00',0,2),
('Rabat',1,'2030-06-02 09:30:00',60,2),
('Fes',0,'2030-06-03 08:00:00',0,3),
('Meknes',1,'2030-06-03 09:00:00',35,3),
('Agadir',0,'2030-06-04 07:00:00',0,4),
('Essaouira',1,'2030-06-04 10:00:00',80,4),
('Tangier',0,'2030-06-05 10:00:00',0,5),
('Tetouan',1,'2030-06-05 11:00:00',30,5);

INSERT INTO reservations (passager_id, trajet_id, point_monte_id, point_descente_id, nombre_places, montant_total, date_reservation, statut) VALUES
(1,1,3,14,1,50,'2026-11-20 12:00:00','CONFIRMEE'),
(2,2,15,16,1,60,'2026-11-20 12:00:00','CONFIRMEE'),
(3,3,17,18,1,35,'2026-11-20 12:00:00','CONFIRMEE'),
(4,4,19,20,1,80,'2026-11-20 12:00:00','CONFIRMEE'),
(5,5,21,22,1,30,'2026-11-20 12:00:00','CONFIRMEE');

SELECT setval(pg_get_serial_sequence('conducteurs','id'), (SELECT max(id) FROM conducteurs));
SELECT setval(pg_get_serial_sequence('passagers','id'), (SELECT max(id) FROM passagers));
SELECT setval(pg_get_serial_sequence('vehicules','id'), (SELECT max(id) FROM vehicules));
SELECT setval(pg_get_serial_sequence('trajets','id'), (SELECT max(id) FROM trajets));
SELECT setval(pg_get_serial_sequence('points_arret','id'), (SELECT max(id) FROM points_arret));
SELECT setval(pg_get_serial_sequence('reservations','id'), (SELECT max(id) FROM reservations));
