-- Needed to add H2GIS support for spatial data types
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();
--------------------------------------------------------
-- Table `point`
--------------------------------------------------------
CREATE TABLE IF NOT EXISTS point (
  `name` VARCHAR(45) NOT NULL,
  `point` GEOMETRY NOT NULL,
  `counter` INT NULL DEFAULT 0,
  PRIMARY KEY (`name`));
  
CREATE UNIQUE INDEX `name_UNIQUE` ON point(`name` ASC);
CREATE SPATIAL INDEX `spatial_INDEX` ON point(`point`);


INSERT INTO point(name, point, counter)
 VALUES 
('Athens','POINT(37.9794 23.7161)',0),
('Piraeus','POINT(37.9500 23.7000)',0),
('Thessaloniki','POINT(40.6333 22.9500)',0),
('Patra','POINT(38.2500 21.7333)',0),
('Larisa','POINT(39.6385 22.4131)',0),
('Irakleio','POINT(35.3333 25.1333)',0),
('Peristeri','POINT(38.0167 23.6833)',0),
('Kallithea','POINT(37.9500 23.7000)',0),
('Nikaia','POINT(37.9667 23.6333)',0),
('Glyfada','POINT(37.8800 23.7533)',0),
('Volos','POINT(39.3611 22.9425)',0),
('Chalandri','POINT(38.0167 23.8000)',0),
('Kalamata','POINT(37.0389 22.1142)',0),
('Ioannina','POINT(39.6647 20.8519)',0),
('Tríkala','POINT(39.5500 21.7667)',0),
('Chalkida','POINT(38.4625 23.5950)',0),
('Serres','POINT(41.0833 23.5500)',0),
('Alexandroupoli','POINT(40.8500 25.8667)',0),
('Rodos','POINT(36.4412 28.2225)',0);