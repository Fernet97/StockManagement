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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cliente` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `datareg` varchar(45) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `cf` varchar(45) DEFAULT NULL,
  `indirizzo` varchar(45) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'1','1','1','1','1','1','1');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fornitore`
--

DROP TABLE IF EXISTS `fornitore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `fornitore` (
  `idfornitore` varchar(45) NOT NULL,
  `datareg` varchar(20) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `p_iva` varchar(30) NOT NULL,
  `indirizzo` varchar(254) NOT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `note` text,
  PRIMARY KEY (`idfornitore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitore`
--

LOCK TABLES `fornitore` WRITE;
/*!40000 ALTER TABLE `fornitore` DISABLE KEYS */;
INSERT INTO `fornitore` VALUES ('1','1','1','1','1','1','1','1');
/*!40000 ALTER TABLE `fornitore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ordine`
--

DROP TABLE IF EXISTS `ordine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `ordine` (
  `idordine` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `n_ordine` varchar(45) NOT NULL,
  `data` varchar(45) NOT NULL,
  `qty_in_arrivo` int(11) NOT NULL,
  `giorni_alla_consegna` int(11) NOT NULL,
  `fk_utente` varchar(45) NOT NULL,
  `prodotto_sku` varchar(45) NOT NULL,
  `fk_cliente` int(11) DEFAULT NULL,
  `fk_fornitore` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idordine`,`fk_utente`,`prodotto_sku`),
  KEY `fk_ordine_utente_idx` (`fk_utente`),
  KEY `fk_ordine_prodotto1_idx` (`prodotto_sku`),
  KEY `fk_ordine_cliente1_idx` (`fk_cliente`),
  KEY `fk_ordine_fornitore1_idx` (`fk_fornitore`),
  CONSTRAINT `fk_ordine_cliente1` FOREIGN KEY (`fk_cliente`) REFERENCES `cliente` (`idcliente`),
  CONSTRAINT `fk_ordine_fornitore1` FOREIGN KEY (`fk_fornitore`) REFERENCES `fornitore` (`idfornitore`),
  CONSTRAINT `fk_ordine_prodotto1` FOREIGN KEY (`prodotto_sku`) REFERENCES `prodotto` (`sku`),
  CONSTRAINT `fk_ordine_utente` FOREIGN KEY (`fk_utente`) REFERENCES `utente` (`idutente`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
/*!40000 ALTER TABLE `ordine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto`
--

DROP TABLE IF EXISTS `prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prodotto` (
  `sku` varchar(45) NOT NULL,
  `datareg` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `qty` int(10) unsigned DEFAULT '0',
  `categoria` varchar(45) NOT NULL,
  `instock` tinyint(1) NOT NULL,
  `costo` float NOT NULL,
  `qty_min` int(10) unsigned NOT NULL,
  `note` text,
  `foto` varchar(254) DEFAULT NULL,
  `negozio` tinyint(1) NOT NULL,
  PRIMARY KEY (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES ('di1-06/03/2020 13:31:31','06/03/2020 13:31:31','2n4001',0,'diodo',0,1.2,0,'note','foto',0),('di2-06/03/2020 13:37:59','06/03/2020 13:37:59','2n4001',0,'diodo',1,1.2,0,'note','note',1),('di3-06/03/2020 16:43:21','06/03/2020 16:43:21','2n4001',0,'diodo',1,1.2,0,'note','note',1);
/*!40000 ALTER TABLE `prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prodotto_has_prodotto`
--

DROP TABLE IF EXISTS `prodotto_has_prodotto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `prodotto_has_prodotto` (
  `sku_prodotto` varchar(45) NOT NULL,
  `sku_componente` varchar(45) NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`sku_prodotto`,`sku_componente`),
  KEY `fk_prodotto_has_prodotto_prodotto2_idx` (`sku_componente`),
  KEY `fk_prodotto_has_prodotto_prodotto1_idx` (`sku_prodotto`),
  CONSTRAINT `fk_prodotto_has_prodotto_prodotto1` FOREIGN KEY (`sku_prodotto`) REFERENCES `prodotto` (`sku`),
  CONSTRAINT `fk_prodotto_has_prodotto_prodotto2` FOREIGN KEY (`sku_componente`) REFERENCES `prodotto` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto_has_prodotto`
--

LOCK TABLES `prodotto_has_prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto_has_prodotto` DISABLE KEYS */;
/*!40000 ALTER TABLE `prodotto_has_prodotto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `utente` (
  `idutente` varchar(45) NOT NULL,
  `datareg` varchar(20) NOT NULL,
  `fullname` varchar(45) NOT NULL,
  `cf` varchar(45) NOT NULL,
  `indirizzo` varchar(254) NOT NULL,
  `tel` varchar(20) NOT NULL,
  `email` varchar(254) NOT NULL,
  `pwd` varchar(254) NOT NULL,
  `permessi` int(11) unsigned NOT NULL,
  `note` text,
  PRIMARY KEY (`idutente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('f.caino2','06/03/2020 12:40:59','flabio caino ','cf','via della capocchia','miiiiaaaao','mail','0b7467f09b3eaa714ad03d7b8d324d21',1,'note'),('g.bambino2','05/03/2020 00:12:00','gesu bambino ','1','1','1','1','bef7b7ac73cb391ea92d1ce2cf9551f4',1,'10'),('v.a1','1','1','1','1','1','1','',1,'1');
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

-- Dump completed on 2020-03-06 16:52:33
