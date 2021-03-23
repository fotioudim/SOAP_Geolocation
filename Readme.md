# Java WSDL-based SOAP web service with Springboot (Spring-WS, Contract first) and nearest neighbor search

### SOAP development style
Contract-first approach is used, where we start with the WSDL contract, and use Java to implement said contract . The pros of contract-first are: a) easy for both producer and consumer to see what the contract is about, what is needed and what is expected. b) reusable schema and xsd, agnostic of the programming language implemented.

The web service domain is defined in an XML schema file (XSD) that Spring-WS will automatically export as a WSDL. Using Mojo's JAXB-2 Maven plugin we generate Java “SOAP-related” classes from XSDs based on the JAXB 2.x implementation .

### Find closest point to given latitude/longitude - Nearest neighbor search
Nearest neighbor search (NNS) is the optimization problem of finding the point in a given set that is closest to a given point. Using Linear search is the simplest solution to the NNS problem but has a running time of O(dN), where d is the dimensionality. Another more efficient way is to use space-partitioning methods, such as Kd-tree average complexity is O(log N) in the case of randomly distributed points, worst case complexity is O(kN^(1-1/k)). 3d-tree implementation is a good algorithm as far as the time complexity is concerned, although for bigger accuracy at calculating the surface distance of points in the Earth “sphere” Great-circle_distance algorithms can also be used.

### Frameworks
Springboot is chosen as the application framework because it provides a lightweight  implementation of the strong Spring ecosystem with many modules/projects that are easy to “wire” together. In this project many such modules have been utilised, like a) “Spring Web services”, a very efficient product for contract-first SOAP service development, b) “Spring Cache” , an abstraction layer for implementing server-side in-memory caching, c) “Spring Test” , a support module for unit testing(with Junit5) and integration testing, d) “Spring Jdbc” , providing libraries for connecting an application with JDBC. 

### Application server
Apache Tomcat is used as an application server, as the default option of Springboot.

### Build Tool
Maven build tool is used. Two build profiles are provided, one for supporting connection to MySQL database server, the other supporting connection with the in-memory H2 database. H2 database is chosen as the default because it can be set up during application startup and the final jar can be run without any other actions. For production purposes a Mysql database could be set up running the following commands at the mysql prompt as an example.

CREATE DATABASE Geolocation;
USE Geolocation;

-- Table `geolocation`.`point`
CREATE TABLE IF NOT EXISTS `geolocation`.`point` (
  `name` VARCHAR(45) NOT NULL,
  `point` POINT NOT NULL,
  `counter` INT NULL DEFAULT DEFAULT 0,
  PRIMARY KEY (`name`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  SPATIAL INDEX `spatial_INDEX` (`point`) VISIBLE)
ENGINE = InnoDB

INSERT INTO point(name, point, counter)
 VALUES 
('Athens',ST_GeomFromText('POINT(37.9794 23.7161)'),0),
('Piraeus',ST_GeomFromText('POINT(37.9500 23.7000)'),0),
('Thessaloníki',ST_GeomFromText('POINT(40.6333 22.9500)'),0),
('Pátra',ST_GeomFromText('POINT(38.2500 21.7333)'),0),
('Lárisa',ST_GeomFromText('POINT(39.6385 22.4131)'),0),
('Irákleio',ST_GeomFromText('POINT(35.3333 25.1333)'),0),
('Peristéri',ST_GeomFromText('POINT(38.0167 23.6833)'),0),
('Kallithéa',ST_GeomFromText('POINT(37.9500 23.7000)'),0),
('Níkaia',ST_GeomFromText('POINT(37.9667 23.6333)'),0),
('Glyfáda',ST_GeomFromText('POINT(37.8800 23.7533)'),0),
('Vólos',ST_GeomFromText('POINT(39.3611 22.9425)'),0),
('Chalándri',ST_GeomFromText('POINT(38.0167 23.8000)'),0),
('Kalamáta',ST_GeomFromText('POINT(37.0389 22.1142)'),0),
('Ioánnina',ST_GeomFromText('POINT(39.6647 20.8519)'),0),
('Tríkala',ST_GeomFromText('POINT(39.5500 21.7667)'),0),
('Chalkída',ST_GeomFromText('POINT(38.4625 23.5950)'),0),
('Sérres',ST_GeomFromText('POINT(41.0833 23.5500)'),0),
('Alexandroúpoli',ST_GeomFromText('POINT(40.8500 25.8667)'),0),
('Ródos',ST_GeomFromText('POINT(36.4412 28.2225)'),0)

### Testing with SOAPUI
The service can also be tested using SOAPUI, API Testing tool, using the wsdl found in http://localhost:8080/ws/geolocation.wsdl when the service is running.
sample requests "getFrequentPoints.xml" and "getNearestPoint.xml" in src/main/resources of source code.

### Web Service start up
Run command: java -jar SOAP-h2.jar
