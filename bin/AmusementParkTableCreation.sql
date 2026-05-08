CREATE TABLE Ticket (
	TicketType varchar(255),
    Price double,
    PRIMARY KEY (TicketType)
);

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
    CONSTRAINT fk_customer_ticket FOREIGN KEY (TicketType) REFERENCES Ticket (TicketType)
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
    PRIMARY KEY (RideID)
);

CREATE TABLE RIDE_ON (
    RideID int,
    CustomerID int,
    CONSTRAINT fk_ride_id FOREIGN KEY (RideID) REFERENCES Ride (RideID),
    CONSTRAINT fk_rideon_customer FOREIGN KEY (CustomerID) References Customer (CustomerID)
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

CREATE TABLE PLAY (
	CustomerID int,
    GameID int,
    CONSTRAINT fk_game_id FOREIGN KEY (GameID) REFERENCES CarnivalGame (GameID),
    CONSTRAINT fk_play_customer FOREIGN KEY (CustomerID) References Customer (CustomerID)
);

CREATE TABLE BUY_TICKET (
	TicketType varchar(255),
    CustomerID int,
    CONSTRAINT fk_ticket_type FOREIGN KEY (TicketType) REFERENCES Ticket (TicketType),
    CONSTRAINT fk_buyticket_customer FOREIGN KEY (CustomerID) References Customer (CustomerID)
);

CREATE TABLE EAT_AT (
	StallID int,
    CustomerID int,
    CONSTRAINT fk_stall_id FOREIGN KEY (StallID) REFERENCES FoodStall (StallID),
    CONSTRAINT fk_eatat_customer FOREIGN KEY (CustomerID) References Customer (CustomerID)
);

CREATE TABLE RIDE_ON (
    RideOnID int PRIMARY KEY AUTO_INCREMENT,
    CustomerID int NOT NULL,
    RideID int NOT NULL,
    RideDate datetime DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_rideon_customer FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    CONSTRAINT fk_rideon_ride FOREIGN KEY (RideID) REFERENCES Ride(RideID)
);

CREATE TABLE BUY_TICKET (
    SaleID int PRIMARY KEY AUTO_INCREMENT,
    CustomerID int NOT NULL,
    TicketType varchar(255) NOT NULL,
    PurchaseDate datetime DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ticketsale_customer FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    CONSTRAINT fk_ticketsale_ticket FOREIGN KEY (TicketType) REFERENCES Ticket(TicketType)
);

CREATE TABLE EAT_AT (
    EatID int PRIMARY KEY AUTO_INCREMENT,
    CustomerID int NOT NULL,
    StallID int NOT NULL,
    EatDate datetime DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_eatat_customer FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    CONSTRAINT fk_eatat_restaurant FOREIGN KEY (StallID) REFERENCES FoodStall(StallID)
);

CREATE TABLE PLAY (
	PlayID int PRIMARY KEY AUTO_INCREMENT,
	CustomerID int,
    GameID int,
    PlayDate datetime DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_game_id FOREIGN KEY (GameID) REFERENCES CarnivalGame (GameID),
    CONSTRAINT fk_play_customer FOREIGN KEY (CustomerID) References Customer (CustomerID)
);

#DROP TABLE Customer;
#DROP TABLE Ride;
#DROP TABLE Ticket;
#DROP TABLE CarnivalGame;
#DROP TABLE FoodStall;
#DROP TABLE RIDE_ON;
#DROP TABLE BUY_TICKET;
#DROP TABLE EAT_AT;
#DROP TABLE PLAY;