CREATE TABLE GenericDaoTable (
  ID       int(10) NOT NULL AUTO_INCREMENT, 
  aString  varchar(100) NOT NULL UNIQUE, 
  aDate    date NOT NULL,
  aInt 	   int(10) NOT NULL,
  PRIMARY KEY (ID));
  
  
insert into GenericDaoTable(aString, aDate, aInt) values ('Registo 1', '2012-10-10', 1);
insert into GenericDaoTable(aString, aDate, aInt) values ('Registo 2', '2012-10-10', 2);
insert into GenericDaoTable(aString, aDate, aInt) values ('Registo 3', '2012-10-11', 3);
insert into GenericDaoTable(aString, aDate, aInt) values ('Registo 4', '2012-10-11', 4);
insert into GenericDaoTable(aString, aDate, aInt) values ('Registo 5', '2012-10-12', 5);