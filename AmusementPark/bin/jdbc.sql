/*CREATE DATABASE ssfoley_Company_JDBC;

USE ssfoley_Company_JDBC;*/

CREATE TABLE Customer (
	CustomerID int NOT NULL auto_increment,
    FirstName varchar(255),
    LastName varchar(255),
    ThrillLevel int,
    Height int NOT NULL,
    Budget double,
    Age int,
    TicketType varchar(255) NOT NULL,
    PRIMARY KEY (CustomerID),
    CONSTRAINT fk_ticket_type FOREIGN KEY (TicketType) REFERENCES Ticket (TicketType)
);

CREATE TABLE Ride (
	RideID int NOT NULL auto_increment,
    RideName varchar(255) NOT NULL,
    HeightRequirement int,
    RideType varchar(255),
    RideTime int,
    ThrillLevel int,
    Capacity int,
    AvgWaitTime double,
    Rating double,
    PRIMARY KEY (RideID, RideName)
);

CREATE TABLE RIDE_ON (
	RideID int,
    RideName varchar(255),
    CustomerID int,
    CONSTRAINT fk_ride_id FOREIGN KEY (RideID) REFERENCES Ride (RideID),
    CONSTRAINT fk_ride_name FOREIGN KEY (RideName) REFERENCES Ride (RideName),
    CONSTRAINT fk_customer_id FOREIGN KEY (CustomerID) References Customer (CustomerID)
);

CREATE TABLE CarnivalGame (
	GameID int NOT NULL auto_increment,
    PrizeID int,
    Price double,
    GameName varchar(255) NOT NULL,
    WinOdds double,
    PRIMARY KEY (GameID, PrizeID)
);

CREATE TABLE FoodStall (
	StallID int NOT NULL auto_increment,
    Price double,
    StallName varchar(255),
    FoodType varchar(255),
    AmountSold int,
    PRIMARY KEY (StallID)
);