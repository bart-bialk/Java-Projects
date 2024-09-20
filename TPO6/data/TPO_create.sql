-- tables
-- Table: Mecz
CREATE TABLE Mecz (
    IdMecz int IDENTITY(1,1) NOT NULL,
    Reprezentacja int  NOT NULL,
    Data date  NOT NULL,
    BramkiPolska int  NOT NULL,
    BramkiPrzeciwnik int  NOT NULL,
    CONSTRAINT Mecz_pk PRIMARY KEY  (IdMecz,Reprezentacja)
);

-- Table: Reprezentacja
CREATE TABLE Reprezentacja (
    IdReprezentacja int IDENTITY(1,1) NOT NULL,
    Nazwa varchar(32)  NOT NULL,
    CONSTRAINT Reprezentacja_pk PRIMARY KEY  (IdReprezentacja)
);

-- foreign keys
-- Reference: Mecz_Reprezentacja (table: Mecz)
ALTER TABLE Mecz ADD CONSTRAINT Mecz_Reprezentacja
    FOREIGN KEY (Reprezentacja)
    REFERENCES Reprezentacja (IdReprezentacja);


