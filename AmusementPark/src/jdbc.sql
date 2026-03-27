CREATE DATABASE ssfoley_Company_JDBC;

USE ssfoley_Company_JDBC;

/* Create Employee table */
CREATE TABLE Employee (
  SSN CHAR(11) PRIMARY KEY NOT NULL,-- NVARCHAR(11) NVARCHAR(9) INT
  Salary NUMERIC(8,2) NOT NULL,
  FirstName NVARCHAR(25) NOT NULL,
  MiddleName NVARCHAR(25),
  LastName NVARCHAR(25) NOT NULL
);

/* Create Project table */
CREATE TABLE Project(
  ID INTEGER AUTO_INCREMENT,
  Description NVARCHAR(255),
  PRIMARY KEY (ID) -- other style of specifying the primary key, useful for composite keys
);

/* Create Works_On */
CREATE TABLE Works_On(
  ID INTEGER NOT NULL,
  SSN CHAR(11) NOT NULL,
  Hours INTEGER NOT NULL,
  CONSTRAINT fk_project_id FOREIGN KEY (ID) REFERENCES Project(ID),
  CONSTRAINT fk_employee_ssn FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);

/* Add data */
INSERT INTO Employee(SSN, Salary, FirstName, LastName) VALUES ('123-45-6789', 123445.00, 'Jasper', 'Fforde');
INSERT INTO Employee(SSN, Salary, FirstName, MiddleName, LastName) VALUES ('123-45-6799', 13445.00, 'Robin', 'Wall', 'Kimmerer');
INSERT INTO Employee(SSN, Salary, FirstName, LastName) VALUES ('123-45-9876', 600000.00, 'Ada', 'Lovelace');
INSERT INTO Employee(SSN, Salary, FirstName, MiddleName, LastName) VALUES ('123-54-6799', 903445.00, 'Grace', 'Murray', 'Hopper');
INSERT INTO Employee(SSN, Salary, FirstName, MiddleName, LastName) VALUES ('123-54-9967', 133445.00, 'Octavia', 'E.', 'Butler');


INSERT INTO Project(Description) VALUES ('A test project.');
INSERT INTO Project(Description) VALUES ('Another test project.');
INSERT INTO Project(Description) VALUES ('Project Awesomesauce');
INSERT INTO Project(Description) VALUES ('Dumpster fire');
INSERT INTO Project(Description) VALUES ('Dodos for fun');


INSERT INTO Works_On(ID, SSN, Hours) VALUES (4, '123-45-6799', 3);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (3, '123-45-9876', 33);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (3, '123-54-6799', 13);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (1, '123-45-6799', -3);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (3, '123-45-6789', 5);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (1, '123-54-6799', 3);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (5, '123-45-6789', 37);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (4, '123-54-9967', 15);
INSERT INTO Works_On(ID, SSN, Hours) VALUES (5, '123-54-9967', 3);

SELECT * FROM Project NATURAL JOIN Works_On NATURAL JOIN Employee ORDER BY SSN;

