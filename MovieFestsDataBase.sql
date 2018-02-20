
CREATE TABLE Actor
(
	IdActor              INTEGER NOT NULL AUTO_INCREMENT,
	Name                 CHAR(50) NULL,
	IdMovie              INTEGER NULL,
	CONSTRAINT XPKActors PRIMARY KEY (IdActor)
)
 AUTO_INCREMENT = 1;

CREATE TABLE Feedback
(
	IdRes                INTEGER NOT NULL,
	Rate                 INTEGER NULL,
	Comment              VARCHAR(100) NULL,
	IdMovie              INTEGER NULL,
	CONSTRAINT XPKFeedback PRIMARY KEY (IdRes)
);

CREATE TABLE Festival
(
	IdFest               INTEGER NOT NULL AUTO_INCREMENT,
	DateFrom             DATETIME NULL,
	DateTo               DATETIME NULL,
	Name                 VARCHAR(20) NULL,
	Info                 VARCHAR(100) NULL,
	TicketNum            INTEGER NULL,
	CONSTRAINT XPKFestival PRIMARY KEY (IdFest)
)
 AUTO_INCREMENT = 1;

CREATE TABLE Galery
(
	IdG                  INTEGER NOT NULL AUTO_INCREMENT,
	Picture              VARCHAR(50) NULL,
	IdMovie              INTEGER NULL,
	CONSTRAINT XPKGalery PRIMARY KEY (IdG)
)
 AUTO_INCREMENT = 1;

CREATE TABLE Hall
(
	IdHall               INTEGER NOT NULL AUTO_INCREMENT,
	IdLok                INTEGER NULL,
	CONSTRAINT XPKHall PRIMARY KEY (IdHall)
)
 AUTO_INCREMENT = 1;

CREATE TABLE Location
(
	IdLok                INTEGER NOT NULL AUTO_INCREMENT,
	Building             VARCHAR(20) NULL,
	Adress               VARCHAR(50) NULL,
	CONSTRAINT XPKLocation PRIMARY KEY (IdLok)
)
 AUTO_INCREMENT = 1;

CREATE TABLE Movie
(
	IdMovie              INTEGER NOT NULL AUTO_INCREMENT,
	Name                 VARCHAR(20) NULL,
	Picture              VARCHAR(50) NULL,
	Year                 INTEGER NULL,
	About                VARCHAR(100) NULL,
	Director             VARCHAR(50) NULL,
	Length               INTEGER NULL,
	Country              VARCHAR(30) NULL,
	imdb                 VARCHAR(60) NULL,
	tomato               VARCHAR(60) NULL,
	youtube              VARCHAR(60) NULL,
	CONSTRAINT XPKMovie PRIMARY KEY (IdMovie)
)
 AUTO_INCREMENT = 1;

CREATE TABLE OnFest
(
	IdFest               INTEGER NOT NULL,
	IdProjection         INTEGER NOT NULL,
	CONSTRAINT XPKOnFest PRIMARY KEY (IdFest,IdProjection)
);

CREATE TABLE OnLocation
(
	IdFest               INTEGER NOT NULL,
	IdLok                INTEGER NOT NULL,
	CONSTRAINT XPKOnLocation PRIMARY KEY (IdFest,IdLok)
);

CREATE TABLE Projection
(
	IdProjection         INTEGER NOT NULL AUTO_INCREMENT,
	IdMovie              INTEGER NULL,
	IdHall               INTEGER NULL,
	Date                 DATETIME NULL,
	Status               VARCHAR(20) NULL,
	VrCount              INTEGER NULL,
	Price                INTEGER NULL,
	CONSTRAINT XPKProjection PRIMARY KEY (IdProjection)
)
 AUTO_INCREMENT = 1;

CREATE TABLE Reservation
(
	IdRes                INTEGER NOT NULL AUTO_INCREMENT,
	Code                 VARCHAR(10) NULL,
	Date                 DATETIME NULL,
	Type                 VARCHAR(20) NULL,
	TicketNum            INTEGER NULL,
	Username             VARCHAR(20) NULL,
	IdProjection         INTEGER NULL,
	Status               VARCHAR(20) NULL,
	VrCount              INTEGER NULL,
	CONSTRAINT XPKReservation PRIMARY KEY (IdRes)
)
 AUTO_INCREMENT = 1;

CREATE TABLE User
(
	Username             VARCHAR(20) NOT NULL,
	Firstname            VARCHAR(20) NULL,
	Lastname             VARCHAR(30) NULL,
	Email                VARCHAR(50) NULL,
	Phone                VARCHAR(20) NULL,
	Password             VARCHAR(12) NULL,
	Type                 VARCHAR(20) NULL,
	CONSTRAINT XPKUser PRIMARY KEY (Username)
);

ALTER TABLE Actor
ADD CONSTRAINT R_14 FOREIGN KEY (IdMovie) REFERENCES Movie (IdMovie);

ALTER TABLE Feedback
ADD CONSTRAINT R_11 FOREIGN KEY (IdRes) REFERENCES Reservation (IdRes);

ALTER TABLE Feedback
ADD CONSTRAINT R_12 FOREIGN KEY (IdMovie) REFERENCES Movie (IdMovie);

ALTER TABLE Galery
ADD CONSTRAINT R_13 FOREIGN KEY (IdMovie) REFERENCES Movie (IdMovie);

ALTER TABLE Hall
ADD CONSTRAINT R_2 FOREIGN KEY (IdLok) REFERENCES Location (IdLok);

ALTER TABLE OnFest
ADD CONSTRAINT R_7 FOREIGN KEY (IdFest) REFERENCES Festival (IdFest);

ALTER TABLE OnFest
ADD CONSTRAINT R_8 FOREIGN KEY (IdProjection) REFERENCES Projection (IdProjection);

ALTER TABLE OnLocation
ADD CONSTRAINT R_3 FOREIGN KEY (IdFest) REFERENCES Festival (IdFest);

ALTER TABLE OnLocation
ADD CONSTRAINT R_4 FOREIGN KEY (IdLok) REFERENCES Location (IdLok);

ALTER TABLE Projection
ADD CONSTRAINT R_5 FOREIGN KEY (IdMovie) REFERENCES Movie (IdMovie);

ALTER TABLE Projection
ADD CONSTRAINT R_6 FOREIGN KEY (IdHall) REFERENCES Hall (IdHall);

ALTER TABLE Reservation
ADD CONSTRAINT R_9 FOREIGN KEY (Username) REFERENCES User (Username);

ALTER TABLE Reservation
ADD CONSTRAINT R_10 FOREIGN KEY (IdProjection) REFERENCES Projection (IdProjection);
