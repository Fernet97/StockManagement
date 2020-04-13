-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: db_stock
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
-- Table structure for table `fornitori`
--

DROP TABLE IF EXISTS `fornitori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fornitori` (
  `idfornitori` varchar(20) NOT NULL,
  `datareg` varchar(20) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `p_iva` varchar(30) NOT NULL,
  `indirizzo` varchar(254) NOT NULL,
  `tel` varchar(15) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`idfornitori`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitori`
--

LOCK TABLES `fornitori` WRITE;
/*!40000 ALTER TABLE `fornitori` DISABLE KEYS */;
INSERT INTO `fornitori` VALUES ('FR-1','04/02/2020 13:01:04','1','1','1','1','1','');
/*!40000 ALTER TABLE `fornitori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotti`
--

DROP TABLE IF EXISTS `prodotti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prodotti` (
  `sku` varchar(45) NOT NULL,
  `datareg` varchar(20) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `categoria` varchar(45) NOT NULL,
  `qty` int(11) NOT NULL,
  `instock` tinyint(4) NOT NULL,
  `giorni_alla_consegna` int(11) DEFAULT NULL,
  `costo` float NOT NULL,
  `descrizione` text,
  `qty_inarrivo` int(11) DEFAULT NULL,
  `qty_min` int(11) NOT NULL,
  `foto` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotti`
--

LOCK TABLES `prodotti` WRITE;
/*!40000 ALTER TABLE `prodotti` DISABLE KEYS */;
INSERT INTO `prodotti` VALUES ('111-04/02/2020 13:01:39','04/02/2020 13:01:39','1','11',1,1,1,1,'',1,1,'foto.png'),('112-04/02/2020 17:25:29','04/02/2020 17:25:29','a','11',0,0,60,0,'',200,10,'foto.png');
/*!40000 ALTER TABLE `prodotti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotti_has_fornitori`
--

DROP TABLE IF EXISTS `prodotti_has_fornitori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prodotti_has_fornitori` (
  `prodotti_sku` varchar(45) NOT NULL,
  `fornitori_idfornitori` varchar(20) NOT NULL,
  PRIMARY KEY (`prodotti_sku`,`fornitori_idfornitori`),
  KEY `fk_prodotti_has_fornitori_fornitori1_idx` (`fornitori_idfornitori`),
  KEY `fk_prodotti_has_fornitori_prodotti_idx` (`prodotti_sku`),
  CONSTRAINT `fk_prodotti_has_fornitori_fornitori1` FOREIGN KEY (`fornitori_idfornitori`) REFERENCES `fornitori` (`idfornitori`),
  CONSTRAINT `fk_prodotti_has_fornitori_prodotti` FOREIGN KEY (`prodotti_sku`) REFERENCES `prodotti` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotti_has_fornitori`
--

LOCK TABLES `prodotti_has_fornitori` WRITE;
/*!40000 ALTER TABLE `prodotti_has_fornitori` DISABLE KEYS */;
INSERT INTO `prodotti_has_fornitori` VALUES ('111-04/02/2020 13:01:39','FR-1'),('112-04/02/2020 17:25:29','FR-1');
/*!40000 ALTER TABLE `prodotti_has_fornitori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utenti` (
  `id` varchar(45) NOT NULL,
  `datareg` varchar(20) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `CF` varchar(45) NOT NULL,
  `indirizzo` varchar(254) NOT NULL,
  `tel` varchar(15) NOT NULL,
  `email` varchar(254) NOT NULL,
  `pwd` varchar(45) NOT NULL,
  `permessi` int(11) NOT NULL,
  `note` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-04 18:15:01
