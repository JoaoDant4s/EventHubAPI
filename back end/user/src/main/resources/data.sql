INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('Admin', '999.999.999-01', '1990-02-23', 'admin', 'hashed_password_for_123456789', true, false);

INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('Promoter', '99.999.999/0001-01', '1982-10-11', 'promoter', 'hashed_password_for_123456789', false, true);

INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('Hudson', '999.999.999-03', '2001-09-12', 'user1@gmail.com', 'hashed_password_for_123456789', false, false);

INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('João', '999.999.999-04', '2000-10-20', 'joao@gmail.com', 'hashed_password_for_123', false, false);

INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('Nathalia', '999.999.999-05', '2000-08-31', 'user3@gmail.com', 'hashed_password_for_123456789', false, false);

INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('Matheus', '999.999.999-06', '1996-02-10', 'user4@gmail.com', 'hashed_password_for_123456789', false, false);

INSERT INTO user (name, document, birth_date, email, password, is_admin, is_promoter)
VALUES ('Atração', '999.999.999-07', '1989-11-02', 'attraction', 'hashed_password_for_123456789', false, false);

INSERT INTO participant DEFAULT VALUES;
INSERT INTO participant DEFAULT VALUES;
INSERT INTO participant DEFAULT VALUES;
INSERT INTO participant DEFAULT VALUES;
INSERT INTO participant DEFAULT VALUES;
INSERT INTO participant DEFAULT VALUES;
INSERT INTO participant DEFAULT VALUES;

INSERT INTO attraction (name, contact)
VALUES ('Lorem ipsum', '(84) 99898-4545');

INSERT INTO event (name, organizer, start_time, end_time, event_type, max_attendees, location)
VALUES ('Edição 10 anos', 'Spotted', '2024-08-10 20:00:00', '2024-08-11 05:00:00', 'Festival', 5000, 'Arena das Dunas');

INSERT INTO event (name, organizer, start_time, end_time, event_type, max_attendees, location)
VALUES ('go rn', 'GO-RN', '2024-06-30 12:00:00', '2024-07-02 12:00:00', 'Talk', 1000, 'IMD');

INSERT INTO sub_event (event_type, start_time, name, description, location, event_id)
VALUES ('Show', '2024-06-30 12:00:00', 'Major RD', '10 anos', 'Palco Trap', 1);

INSERT INTO sub_event (event_type, start_time, name, description, location, event_id)
VALUES ('Show', '2024-08-10 20:00:00', 'MC Loma e As Gêmeas Lacração', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 'Setor A', 1);

INSERT INTO sub_event (event_type, start_time, name, description, location, event_id)
VALUES ('Workshop', '2024-08-10 20:00:00', 'Workshop Spring', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 'Setor A', 2);

INSERT INTO sub_event (event_type, start_time, name, description, location, event_id)
VALUES ('Workshop', '2024-08-10 20:00:00', 'Workshop React', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 'Setor B', 2);

INSERT INTO sub_event (event_type, start_time, name, description, location, event_id)
VALUES ('Talk', '2024-08-10 20:00:00', 'Empreendedorismo no RN', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', 'Setor C', 2);

INSERT INTO ticket_type (name, version, event_id, price, description)
VALUES ('Meia Estudante', 1, 1, 50.00, 'Necessario comprovante de meia entrada');

INSERT INTO ticket_type (name, version, event_id, price, description)
VALUES ('Inteira', 1, 1, 100.00, 'Identidade');

INSERT INTO ticket_type (name, version, event_id, price, description)
VALUES ('Meia Estudante', 1, 2, 70.00, 'Necessario comprovante de meia entrada');
