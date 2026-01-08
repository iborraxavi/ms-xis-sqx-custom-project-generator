CREATE TABLE INSTRUMENTS
(
    instrument VARCHAR(100) PRIMARY KEY
);

CREATE TABLE DATA
(
    id         INTEGER PRIMARY KEY,
    symbol     VARCHAR(100),
    instrument VARCHAR(100),
    usymbol    VARCHAR(100),
    dateto     LONG,
    datefrom   LONG
);

ALTER TABLE DATA
    ADD CONSTRAINT fk_instruments
        FOREIGN KEY (instrument)
            REFERENCES INSTRUMENTS (instrument)
            ON DELETE CASCADE;