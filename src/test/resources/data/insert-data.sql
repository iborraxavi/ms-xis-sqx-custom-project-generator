INSERT INTO INSTRUMENTS (instrument)
VALUES ('Instrument 01'),
       ('Instrument 02'),
       ('Instrument 03'),
       ('Instrument 04'),
       ('Instrument 05');

INSERT INTO DATA (id, symbol, instrument, usymbol, datefrom, dateto)
VALUES (3, 'Symbol 03', 'Instrument 03', 'Usymbol 03', null, null),
       (4, 'Symbol 04', 'Instrument 04', null, null, null),
       (1, 'Symbol 01', 'Instrument 01', 'Usymbol 01', null, null),
       (5, 'Symbol 05', 'Instrument 05', null, null, null),
       (2, 'Symbol 02', 'Instrument 02', 'Usymbol 02', 1141948800000, 1758671940000);