-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: tsw
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assistenza`
--

DROP TABLE IF EXISTS `assistenza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assistenza` (
  `idassistenza` int(11) NOT NULL AUTO_INCREMENT,
  `problema` text NOT NULL,
  `email` varchar(45) NOT NULL,
  `risolto` tinyint(4) NOT NULL,
  PRIMARY KEY (`idassistenza`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assistenza`
--

LOCK TABLES `assistenza` WRITE;
/*!40000 ALTER TABLE `assistenza` DISABLE KEYS */;
INSERT INTO `assistenza` VALUES (10,'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','admin@a',0);
/*!40000 ALTER TABLE `assistenza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrello`
--

DROP TABLE IF EXISTS `carrello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `carrello` (
  `idcarrello` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `prezzotot` float DEFAULT NULL,
  PRIMARY KEY (`idcarrello`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrello`
--

LOCK TABLES `carrello` WRITE;
/*!40000 ALTER TABLE `carrello` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitarra`
--

DROP TABLE IF EXISTS `chitarra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chitarra` (
  `idchitarra` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `corpo` varchar(45) NOT NULL,
  `tastiera` varchar(45) NOT NULL,
  `top` varchar(45) NOT NULL,
  `pickup` varchar(45) NOT NULL,
  `prezzo` float NOT NULL,
  `qnt` int(11) NOT NULL,
  `comprata` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idchitarra`),
  KEY `email_idx` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitarra`
--

LOCK TABLES `chitarra` WRITE;
/*!40000 ALTER TABLE `chitarra` DISABLE KEYS */;
INSERT INTO `chitarra` VALUES (22,'mario@balbi','mario1','bekki','tast','bobbat','bellifa',523,4,0),(23,'mario@balbi','mario2','alabastro','tast','comprata','beeellsi',205.9,6,1),(24,'mariobalbi@3.it','mario3','befds','tast','ega','belfefli',5775.8,5,0),(25,'mariobalbi@1.it','mario4','bcasc','tast','bogaet','bellcci',5535.5,1,0),(26,'mario@balbi','mario5','vittorio','testa','di','cazzo',102.3,2,0);
/*!40000 ALTER TABLE `chitarra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chitarradmin`
--

DROP TABLE IF EXISTS `chitarradmin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `chitarradmin` (
  `idchitarradmin` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(45) DEFAULT NULL,
  `nome` varchar(45) NOT NULL,
  `prezzo` float NOT NULL,
  `desc` text,
  PRIMARY KEY (`idchitarradmin`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chitarradmin`
--

LOCK TABLES `chitarradmin` WRITE;
/*!40000 ALTER TABLE `chitarradmin` DISABLE KEYS */;
INSERT INTO `chitarradmin` VALUES (2,'ribellecat.jpg','ribelle',690,NULL),(3,'apollonia.png','giannola',1390,NULL),(4,'sline.png','laS',1190,NULL);
/*!40000 ALTER TABLE `chitarradmin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utente` (
  `email` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `indirizzo` varchar(45) NOT NULL,
  `citta` varchar(45) NOT NULL,
  `zip` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `esperto` tinyint(4) NOT NULL,
  `admin` tinyint(4) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('admin@a','Cribus','cribus','Via Roma 21','Hollyweed','90210','admin',1,1),('mario@balbi','Mario','Giordano','Via Hollywood 21','Roma','05652','mario',0,0),('vitt@a.a','vittorio','manisera','via della capocchia','petina','0000','pass',0,0);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-11 15:09:30
