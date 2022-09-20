CREATE DATABASE  IF NOT EXISTS `smbdbiblioteca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `smbdbiblioteca`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: smbdbiblioteca
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `estudiante`
--

DROP TABLE IF EXISTS `estudiante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudiante` (
  `idestudiante` int unsigned NOT NULL AUTO_INCREMENT,
  `nombres` varchar(64) NOT NULL,
  `apellidopaterno` varchar(32) NOT NULL,
  `apellidomaterno` varchar(32) NOT NULL,
  `estado` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`idestudiante`),
  UNIQUE KEY `idestudiante` (`idestudiante`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudiante`
--

LOCK TABLES `estudiante` WRITE;
/*!40000 ALTER TABLE `estudiante` DISABLE KEYS */;
INSERT INTO `estudiante` VALUES (1,'Oscar Alexander','Rojas','Soplín',1),(2,'Claudia','Magallanes','Quiroz',1),(3,'Jorge Luis','Mauricio','Montes',1);
/*!40000 ALTER TABLE `estudiante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libro`
--

DROP TABLE IF EXISTS `libro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `libro` (
  `idlibro` int unsigned NOT NULL AUTO_INCREMENT,
  `isbn` varchar(32) NOT NULL,
  `titulo` varchar(256) NOT NULL,
  `autores` varchar(512) NOT NULL,
  `idmateria` int unsigned NOT NULL,
  `anio` smallint NOT NULL,
  `numejemplar` tinyint unsigned NOT NULL,
  `estado` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`idlibro`),
  KEY `fk_libro_materia` (`idmateria`),
  CONSTRAINT `fk_libro_materia` FOREIGN KEY (`idmateria`) REFERENCES `materia` (`idmateria`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libro`
--

LOCK TABLES `libro` WRITE;
/*!40000 ALTER TABLE `libro` DISABLE KEYS */;
INSERT INTO `libro` VALUES (1,'9541203587412','The Road to Learn React: Your Journey to Master Plain Yet','Robin Wieruch',11,2017,1,0),(2,'9541203587412','The Road to Learn React: Your Journey to Master Plain Yet','Robin Wieruch',11,2017,2,0),(3,'9541203587412','The Road to Learn React: Your Journey to Master Plain Yet','Robin Wieruch',11,2017,3,1),(4,'9541203587412','The Road to Learn React: Your Journey to Master Plain Yet','Robin Wieruch',11,2017,4,0),(5,'9788478290598','Patrones de Diseño','Erich Gamma',2,2002,1,0),(6,'9788478290598','Patrones de Diseño','Erich Gamma',2,2002,2,1),(7,'9788478290598','Patrones de Diseño','Erich Gamma',2,2002,3,0),(8,'9788499641249','Desarrollo de Bases de Datos: Casos Prácticos Desde el Análisis a la Implementación','Dolores Cuadra Fernández',4,2013,1,1),(9,'9788499641249','Desarrollo de Bases de Datos: Casos Prácticos Desde el Análisis a la Implementación','Dolores Cuadra Fernández',4,2013,2,1),(10,'9788499641249','Desarrollo de Bases de Datos: Casos Prácticos Desde el Análisis a la Implementación','Dolores Cuadra Fernández',4,2013,3,0),(11,'9788448179267','EBOOK Fundamentos de bases de datos','Abraham Silberschatz, Henry Korth, S. Sudarshan',4,2015,1,1),(12,'9788448179267','EBOOK Fundamentos de bases de datos','Abraham Silberschatz, Henry Korth, S. Sudarshan',4,2015,2,0),(13,'9781596931862','Handbook of Software Quality Assurance','G. Gordon Schulmeyer',15,2007,1,1),(14,'9781596931862','Handbook of Software Quality Assurance','G. Gordon Schulmeyer',15,2007,2,1),(15,'9781596931862','Customer Oriented Software Quality Assurance','Frank P. Ginac',15,1988,1,1),(16,'9781596931862','Customer Oriented Software Quality Assurance','Frank P. Ginac',15,1988,2,1),(17,'9781329224278','Scrum and XP from the Trenches','Henrik Kniberg',19,2007,1,1),(18,'9781329224278','Scrum and XP from the Trenches','Henrik Kniberg',19,2007,2,1),(19,'9781329224278','Scrum and XP from the Trenches','Henrik Kniberg',19,2007,3,1),(20,'9781484221891','Material Design Implementation with AngularJS: UI Component Framework','V. Keerti Kotaru',19,2016,1,1),(21,'9781484221891','Material Design Implementation with AngularJS: UI Component Framework','V. Keerti Kotaru',19,2016,2,1),(22,'9785210236598','Fundamentos de bases de datos','Abraham Silberschatz',4,1997,1,1),(23,'9751240315427','Microsoft SQL Server 2019 Standard incl. 1 Device CAL','Microsoft Co.',16,2019,1,1),(24,'9510320528447','Oracle 12c PL/SQL: Curso práctico de formación','Antolín Muñoz Chaparro',16,2017,1,1),(25,'9235152032659','Introducción a las bases de datos NoSQL usando MongoDB','Antonio Sarasa',20,2016,1,1),(26,'9632054852156','Professional NoSQL','Shashank Tiwari',20,2011,1,1);
/*!40000 ALTER TABLE `libro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `materia`
--

DROP TABLE IF EXISTS `materia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia` (
  `idmateria` int unsigned NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(64) NOT NULL,
  PRIMARY KEY (`idmateria`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia`
--

LOCK TABLES `materia` WRITE;
/*!40000 ALTER TABLE `materia` DISABLE KEYS */;
INSERT INTO `materia` VALUES (1,'Ciencias en la Computación'),(2,'Algorítmica'),(3,'Inteligencia Artificial'),(4,'Base de datos'),(5,'Análisis y Diseño de Sistemas'),(6,'Frameworks y API\'s'),(7,'Lenguaje Ensamblador'),(8,'Entornos de Desarrollo'),(9,'Sistemas Digitales'),(10,'Desarrollo de Aplicaciones Web'),(11,'Desarrollo Frontend'),(12,'Desarrollo Backend'),(13,'Estructura de datos'),(14,'Normativas de diseño web'),(15,'Quality Assurance'),(16,'SQL'),(17,'Servicios en AWS'),(18,'Modelos relacionales y no relacioneales'),(19,'Metodologías de software'),(20,'NoSQL'),(21,'UX');
/*!40000 ALTER TABLE `materia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prestamo`
--

DROP TABLE IF EXISTS `prestamo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prestamo` (
  `idprestamo` int unsigned NOT NULL AUTO_INCREMENT,
  `idestudiante` int unsigned NOT NULL,
  `idlibro` int unsigned NOT NULL,
  `fechaprestamo` date NOT NULL,
  `numdiasprestamo` tinyint NOT NULL,
  `estado` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`idprestamo`),
  KEY `fk_prestamo_estudiante` (`idestudiante`),
  KEY `fk_prestamo_libro` (`idlibro`),
  CONSTRAINT `fk_prestamo_estudiante` FOREIGN KEY (`idestudiante`) REFERENCES `estudiante` (`idestudiante`) ON DELETE CASCADE,
  CONSTRAINT `fk_prestamo_libro` FOREIGN KEY (`idlibro`) REFERENCES `libro` (`idlibro`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prestamo`
--

LOCK TABLES `prestamo` WRITE;
/*!40000 ALTER TABLE `prestamo` DISABLE KEYS */;
INSERT INTO `prestamo` VALUES (1,1,2,'2022-05-30',15,1),(2,1,4,'2022-06-25',10,1),(3,1,5,'2022-06-16',15,1),(4,2,10,'2022-06-24',7,1),(5,2,1,'2022-06-23',8,1),(6,3,12,'2022-06-19',5,1),(7,3,7,'2022-06-27',14,1);
/*!40000 ALTER TABLE `prestamo` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `tr_modificarEstadoLibroDePrestamo` AFTER INSERT ON `prestamo` FOR EACH ROW BEGIN
	UPDATE libro SET estado = 0 WHERE idlibro = NEW.idlibro;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `tr_modificacionEstadoPrestamo` AFTER UPDATE ON `prestamo` FOR EACH ROW BEGIN
	IF NEW.estado = 0 THEN
		UPDATE libro SET estado = 1 WHERE idlibro = NEW.idlibro;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `idusuario` tinyint unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(64) NOT NULL,
  `nombreusuario` varchar(256) NOT NULL,
  `contrasena` varchar(256) NOT NULL,
  `estaactivo` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`idusuario`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `nombreusuario` (`nombreusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'asfgasfg@fadsg.com','oalex','123',1),(2,'afsdf@ddd.com','clau','123',1),(3,'dsf44@daaa.com','jmauricio','123',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'smbdbiblioteca'
--

--
-- Dumping routines for database 'smbdbiblioteca'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-24 18:51:30
