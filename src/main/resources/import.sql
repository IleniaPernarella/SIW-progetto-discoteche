INSERT INTO discoteca (nome, descrizione, indirizzo, immagine, id_gestore) VALUES('Piper', 'Discoteca moderna con luci LED e DJ internazionali.', 'Via delle Stelle 12, Roma', 'img/piper.jpg', NULL);
INSERT INTO discoteca (nome, descrizione, indirizzo, immagine, id_gestore) VALUES('Qube', 'Atmosfera elegante con musica live e drink esclusivi.', 'Corso Vittorio 33, Milano', 'img/qube.jpg', NULL);
INSERT INTO discoteca (nome, descrizione, indirizzo, immagine, id_gestore) VALUES('Room 26', 'Spazio underground per musica techno e house.', 'Via Industriale 7, Bologna', 'img/room26.jpg', NULL);
INSERT INTO discoteca (nome, descrizione, indirizzo, immagine, id_gestore) VALUES('Spazio 900', 'Ampia pista da ballo e terrazza esterna estiva.', 'Viale del Mare 100, Napoli', 'img/spazio900.jpg', NULL);
INSERT INTO discoteca (nome, descrizione, indirizzo, immagine, id_gestore) VALUES('Jolie', 'Discoteca storica con serate a tema e karaoke.', 'Piazza Centrale 4, Firenze', 'img/jolie.jpg', NULL);




INSERT INTO evento (nome, descrizione, data_ora, prezzo, ID_discoteca) VALUES ('Neon Party', 'Una serata fluorescente con DJ internazionale.', '2025-06-15 22:00:00', 25.00, 1);
INSERT INTO evento (nome, descrizione, data_ora, prezzo, ID_discoteca) VALUES ('Retro Night', 'Musica anni 80 e cocktail vintage.', '2025-06-22 21:00:00', 20.00, 1);

-- Eventi per Qube (id = 2)

INSERT INTO evento (nome, descrizione, data_ora, prezzo, ID_discoteca) VALUES ('Jazz & Wine', 'Concerto jazz dal vivo con degustazione vini.', '2025-06-12 20:30:00', 30.00, 2);

-- Eventi per Room 26 (id = 3)

INSERT INTO evento (nome, descrizione, data_ora, prezzo, ID_discoteca) VALUES ('Techno Marathon', '12 ore di musica techno underground.', '2025-06-29 23:00:00', 35.00, 3);

-- Nessun evento per Spazio 900 (id = 4) → così puoi testare il messaggio "nessun evento"

-- Eventi per Jolie (id = 5)

INSERT INTO evento (nome, descrizione, data_ora, prezzo, ID_discoteca) VALUES ('Karaoke Night', 'Canta i tuoi successi preferiti dal vivo!', '2025-06-08 21:00:00', 10.00, 5);
INSERT INTO evento (nome, descrizione, data_ora, prezzo, ID_discoteca) VALUES ('Serata Latina', 'Bachata, salsa e reggaeton tutta la notte.', '2025-06-14 22:00:00', 18.00, 5);