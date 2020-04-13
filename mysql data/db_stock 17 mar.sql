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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (6,'17/03/2020 17:01:35','nome','cf','indirizzo','tel','email','note'),(7,'17/03/2020 17:04:25','nome','cf','indirizzo','tel','email','note'),(8,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(9,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(10,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(11,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(12,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(13,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(14,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(15,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(16,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(17,'17/03/2020 17:07:21','nome','cf','indirizzo','tel','email','note'),(18,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(19,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(20,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(21,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(22,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(23,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(24,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(25,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(26,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note'),(27,'17/03/2020 17:07:22','nome','cf','indirizzo','tel','email','note');
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
  `id` int(11) NOT NULL,
  PRIMARY KEY (`idfornitore`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fornitore`
--

LOCK TABLES `fornitore` WRITE;
/*!40000 ALTER TABLE `fornitore` DISABLE KEYS */;
INSERT INTO `fornitore` VALUES ('FR-1','17/03/2020 00:17:54','alixpresas','piva','via','333','@1','note',1),('FR-10','17/03/2020 00:17:54','presas','piva','via','333','@1','note',10),('FR-11','17/03/2020 00:17:54','alixpresas','piva','via','333','@1','note',11),('FR-12','17/03/2020 00:17:54','amanoz','piva','via','333','@1','note',12),('FR-13','17/03/2020 00:17:54','miao','pina','via','333','@1','note',13),('FR-14','17/03/2020 00:17:54','alixpress','piva','via','333','@1','note',14),('FR-15','17/03/2020 00:17:54','alixpreas','piva','via','333','@1','note',15),('FR-16','17/03/2020 00:17:54','alixprsas','piva','via','333','@1','note',16),('FR-17','17/03/2020 00:17:54','alixresas','piva','via','333','@1','note',17),('FR-18','17/03/2020 00:17:54','alxpresas','piva','via','333','@1','note',18),('FR-19','17/03/2020 00:17:54','lixpresas','piva','via','333','@1','note',19),('FR-2','17/03/2020 00:17:54','amanoz','piva','via','333','@1','note',2),('FR-20','17/03/2020 00:17:54','presas','piva','via','333','@1','note',20),('FR-21','17/03/2020 17:07:21','alixpresas','piva','via','333','@1','note',21),('FR-22','17/03/2020 17:07:21','amanoz','piva','via','333','@1','note',22),('FR-23','17/03/2020 17:07:21','miao','pina','via','333','@1','note',23),('FR-24','17/03/2020 17:07:21','alixpress','piva','via','333','@1','note',24),('FR-25','17/03/2020 17:07:21','alixpreas','piva','via','333','@1','note',25),('FR-26','17/03/2020 17:07:21','alixprsas','piva','via','333','@1','note',26),('FR-27','17/03/2020 17:07:21','alixresas','piva','via','333','@1','note',27),('FR-28','17/03/2020 17:07:21','alxpresas','piva','via','333','@1','note',28),('FR-29','17/03/2020 17:07:21','lixpresas','piva','via','333','@1','note',29),('FR-3','17/03/2020 00:17:54','miao','pina','via','333','@1','note',3),('FR-30','17/03/2020 17:07:21','presas','piva','via','333','@1','note',30),('FR-31','17/03/2020 17:07:21','alixpresas','piva','via','333','@1','note',31),('FR-32','17/03/2020 17:07:21','amanoz','piva','via','333','@1','note',32),('FR-33','17/03/2020 17:07:21','miao','pina','via','333','@1','note',33),('FR-34','17/03/2020 17:07:21','alixpress','piva','via','333','@1','note',34),('FR-35','17/03/2020 17:07:21','alixpreas','piva','via','333','@1','note',35),('FR-36','17/03/2020 17:07:21','alixprsas','piva','via','333','@1','note',36),('FR-37','17/03/2020 17:07:21','alixresas','piva','via','333','@1','note',37),('FR-38','17/03/2020 17:07:21','alxpresas','piva','via','333','@1','note',38),('FR-39','17/03/2020 17:07:21','lixpresas','piva','via','333','@1','note',39),('FR-4','17/03/2020 00:17:54','alixpress','piva','via','333','@1','note',4),('FR-40','17/03/2020 17:07:21','presas','piva','via','333','@1','note',40),('FR-5','17/03/2020 00:17:54','alixpreas','piva','via','333','@1','note',5),('FR-6','17/03/2020 00:17:54','alixprsas','piva','via','333','@1','note',6),('FR-7','17/03/2020 00:17:54','alixresas','piva','via','333','@1','note',7),('FR-8','17/03/2020 00:17:54','alxpresas','piva','via','333','@1','note',8),('FR-9','17/03/2020 00:17:54','lixpresas','piva','via','333','@1','note',9);
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
  `id` int(11) NOT NULL,
  PRIMARY KEY (`idordine`,`fk_utente`,`prodotto_sku`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` VALUES (64,'ORD-1','17/03/2020 00:17:54',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(65,'ORD-1','17/03/2020 00:17:54',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(66,'ORD-1','17/03/2020 00:17:54',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(67,'ORD-1','17/03/2020 00:17:54',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(68,'ORD-1','17/03/2020 00:17:54',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(69,'ORD-1','17/03/2020 00:17:54',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(70,'ORD-1','17/03/2020 00:17:54',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(71,'ORD-1','17/03/2020 00:17:54',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(72,'ORD-1','17/03/2020 00:17:55',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(73,'ORD-1','17/03/2020 00:17:55',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(74,'ORD-1','17/03/2020 00:17:55',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(75,'ORD-1','17/03/2020 00:17:55',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(76,'ORD-1','17/03/2020 00:17:55',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(77,'ORD-1','17/03/2020 00:17:55',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(78,'ORD-1','17/03/2020 00:17:55',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(79,'ORD-1','17/03/2020 00:17:55',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(80,'ORD-1','17/03/2020 01:22:50',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(81,'ORD-1','17/03/2020 01:22:50',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(82,'ORD-1','17/03/2020 01:22:50',0,0,'admin','re4-16/03/2020 16:07:16',0,'FR-1',1),(83,'ORD-1','17/03/2020 01:24:54',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(84,'ORD-1','17/03/2020 01:24:54',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(85,'ORD-1','17/03/2020 01:24:54',0,0,'admin','re4-16/03/2020 16:07:16',0,'FR-1',1),(86,'ORD-1','17/03/2020 01:47:18',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(87,'ORD-1','17/03/2020 01:47:18',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(88,'ORD-1','17/03/2020 01:47:18',0,0,'admin','re4-16/03/2020 16:07:16',0,'FR-1',1),(89,'ORD-1','17/03/2020 16:04:52',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(90,'ORD-1','17/03/2020 16:04:52',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(91,'ORD-1','17/03/2020 16:04:52',0,0,'admin','re4-16/03/2020 16:07:16',0,'FR-1',1),(92,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(93,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(94,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(95,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(96,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(97,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(98,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(99,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(100,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(101,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(102,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(103,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(104,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(105,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1),(106,'ORD-1','17/03/2020 17:07:21',0,0,'admin','di18-16/03/2020 16:07:16',0,'FR-1',1),(107,'ORD-1','17/03/2020 17:07:21',0,0,'admin','op15-16/03/2020 16:07:16',0,'FR-1',1);
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
  `foto` blob,
  `negozio` tinyint(1) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sku`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES ('a','1','1',1,'1',1,1,1,'1',_binary 'C:\\Users\\LittleJoke\\Pictures\\ph.jpg',1,99999),('co16-17/03/2020 00:17:55','17/03/2020 00:17:55','n5088',10,'condens pol',1,10.5,2,'note',_binary 'foto',1,16),('co26-17/03/2020 17:07:21','17/03/2020 17:07:21','n5088',10,'condens pol',1,10.5,2,'note',_binary 'foto',1,26),('co36-17/03/2020 17:07:21','17/03/2020 17:07:21','n5088',10,'condens pol',1,10.5,2,'note',_binary 'foto',1,36),('co6-17/03/2020 00:17:54','17/03/2020 00:17:54','n5088',10,'condens pol',1,10.5,2,'note',_binary 'foto',1,6),('di1-17/03/2020 00:17:54','17/03/2020 00:17:54','1n5088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,1),('di10-17/03/2020 00:17:54','17/03/2020 00:17:54','1n58',10,'diodi',1,10.5,2,'note',_binary 'foto',1,10),('di11-17/03/2020 00:17:54','17/03/2020 00:17:54','1n5088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,11),('di12-17/03/2020 00:17:54','17/03/2020 00:17:54','1n5098',10,'diodi',1,10.5,2,'note',_binary 'foto',1,12),('di17-17/03/2020 00:17:55','17/03/2020 00:17:55','1',10,'diodi',1,10.5,2,'note',_binary 'foto',1,17),('di18-17/03/2020 00:17:55','17/03/2020 00:17:55','188',10,'diodi',1,10.5,2,'note',_binary 'foto',1,18),('di19-17/03/2020 00:17:55','17/03/2020 00:17:55','1088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,19),('di2-17/03/2020 00:17:54','17/03/2020 00:17:54','1n5098',10,'diodi',1,10.5,2,'note',_binary 'foto',1,2),('di20-17/03/2020 00:17:55','17/03/2020 00:17:55','1n58',10,'diodi',1,10.5,2,'note',_binary 'foto',1,20),('di21-17/03/2020 17:07:21','17/03/2020 17:07:21','1n5088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,21),('di22-17/03/2020 17:07:21','17/03/2020 17:07:21','1n5098',10,'diodi',1,10.5,2,'note',_binary 'foto',1,22),('di27-17/03/2020 17:07:21','17/03/2020 17:07:21','1',10,'diodi',1,10.5,2,'note',_binary 'foto',1,27),('di28-17/03/2020 17:07:21','17/03/2020 17:07:21','188',10,'diodi',1,10.5,2,'note',_binary 'foto',1,28),('di29-17/03/2020 17:07:21','17/03/2020 17:07:21','1088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,29),('di30-17/03/2020 17:07:21','17/03/2020 17:07:21','1n58',10,'diodi',1,10.5,2,'note',_binary 'foto',1,30),('di31-17/03/2020 17:07:21','17/03/2020 17:07:21','1n5088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,31),('di32-17/03/2020 17:07:21','17/03/2020 17:07:21','1n5098',10,'diodi',1,10.5,2,'note',_binary 'foto',1,32),('di37-17/03/2020 17:07:21','17/03/2020 17:07:21','1',10,'diodi',1,10.5,2,'note',_binary 'foto',1,37),('di38-17/03/2020 17:07:21','17/03/2020 17:07:21','188',10,'diodi',1,10.5,2,'note',_binary 'foto',1,38),('di39-17/03/2020 17:07:21','17/03/2020 17:07:21','1088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,39),('di40-17/03/2020 17:07:21','17/03/2020 17:07:21','1n58',10,'diodi',1,10.5,2,'note',_binary 'foto',1,40),('di7-17/03/2020 00:17:54','17/03/2020 00:17:54','1',10,'diodi',1,10.5,2,'note',_binary 'foto',1,7),('di8-17/03/2020 00:17:54','17/03/2020 00:17:54','188',10,'diodi',1,10.5,2,'note',_binary 'foto',1,8),('di9-17/03/2020 00:17:54','17/03/2020 00:17:54','1088',10,'diodi',1,10.5,2,'note',_binary 'foto',1,9),('op15-17/03/2020 00:17:55','17/03/2020 00:17:55','15088',10,'optocpo',1,10.5,2,'note',_binary 'foto',1,15),('op25-17/03/2020 17:07:21','17/03/2020 17:07:21','15088',10,'optocpo',1,10.5,2,'note',_binary 'foto',1,25),('op35-17/03/2020 17:07:21','17/03/2020 17:07:21','15088',10,'optocpo',1,10.5,2,'note',_binary 'foto',1,35),('op5-17/03/2020 00:17:54','17/03/2020 00:17:54','15088',10,'optocpo',1,10.5,2,'note',_binary 'foto',1,5),('re14-17/03/2020 00:17:55','17/03/2020 00:17:55','1n88',10,'reisitenzasii',1,10.5,2,'note',_binary 'foto',1,14),('re24-17/03/2020 17:07:21','17/03/2020 17:07:21','1n88',10,'reisitenzasii',1,10.5,2,'note',_binary 'foto',1,24),('re34-17/03/2020 17:07:21','17/03/2020 17:07:21','1n88',10,'reisitenzasii',1,10.5,2,'note',_binary 'foto',1,34),('re4-17/03/2020 00:17:54','17/03/2020 00:17:54','1n88',10,'reisitenzasii',1,10.5,2,'note',_binary 'foto',1,4),('tr13-17/03/2020 00:17:55','17/03/2020 00:17:55','1n508',10,'transistor',1,10.5,2,'note',_binary 'foto',1,13),('tr23-17/03/2020 17:07:21','17/03/2020 17:07:21','1n508',10,'transistor',1,10.5,2,'note',_binary 'foto',1,23),('tr3-17/03/2020 00:17:54','17/03/2020 00:17:54','1n508',10,'transistor',1,10.5,2,'note',_binary 'foto',1,3),('tr33-17/03/2020 17:07:21','17/03/2020 17:07:21','1n508',10,'transistor',1,10.5,2,'note',_binary 'foto',1,33);
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
  `id` int(11) NOT NULL,
  PRIMARY KEY (`idutente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utente`
--

LOCK TABLES `utente` WRITE;
/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` VALUES ('admin','NULL','NULL','NULL','NULL','NULL','NULL','21232f297a57a5a743894a0e4a801fc3',0,'null',0),('b.nato1','17/03/2020 00:17:54','babbeo nato','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('b.nato2','17/03/2020 00:17:54','babbeo nato','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('b.nato3','17/03/2020 17:07:20','babbeo nato','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('b.nato4','17/03/2020 17:07:21','babbeo nato','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('d.traifuochi1','17/03/2020 00:17:53','dario traifuochi','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('d.traifuochi2','17/03/2020 00:17:54','dario traifuochi','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('d.traifuochi3','17/03/2020 17:07:20','dario traifuochi','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('d.traifuochi4','17/03/2020 17:07:21','dario traifuochi','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('f.caino1','17/03/2020 00:17:54','fabbio caino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('f.caino2','17/03/2020 00:17:54','fabbio caino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('f.caino3','17/03/2020 17:07:20','fabbio caino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('f.caino4','17/03/2020 17:07:21','fabbio caino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('l.faino1','17/03/2020 00:17:54','labio faino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('l.faino2','17/03/2020 00:17:54','labio faino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('l.faino3','17/03/2020 17:07:20','labio faino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('l.faino4','17/03/2020 17:07:21','labio faino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('m.cancro1','17/03/2020 00:17:54','meshon cancro','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('m.cancro2','17/03/2020 00:17:54','meshon cancro','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('m.cancro3','17/03/2020 17:07:20','meshon cancro','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('m.cancro4','17/03/2020 17:07:21','meshon cancro','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('p.liguori1','17/03/2020 00:17:53','pielurigi liguori','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('p.liguori2','17/03/2020 00:17:54','pielurigi liguori','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('p.liguori3','17/03/2020 17:07:20','pielurigi liguori','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('p.liguori4','17/03/2020 17:07:21','pielurigi liguori','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('v.manisera1','17/03/2020 00:17:53','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('v.manisera10','17/03/2020 17:07:21','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',10),('v.manisera11','17/03/2020 17:07:21','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',11),('v.manisera12','17/03/2020 17:07:21','vincenzo manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',12),('v.manisera2','17/03/2020 00:17:53','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('v.manisera3','17/03/2020 00:17:54','vincenzo manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('v.manisera4','17/03/2020 00:17:54','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('v.manisera5','17/03/2020 00:17:54','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',5),('v.manisera6','17/03/2020 00:17:54','vincenzo manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',6),('v.manisera7','17/03/2020 17:07:19','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',7),('v.manisera8','17/03/2020 17:07:20','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',8),('v.manisera9','17/03/2020 17:07:20','vincenzo manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',9),('z.puttana1','17/03/2020 00:17:54','zoccola puttana','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('z.puttana2','17/03/2020 00:17:54','zoccola puttana','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('z.puttana3','17/03/2020 17:07:20','zoccola puttana','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('z.puttana4','17/03/2020 17:07:21','zoccola puttana','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4);
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

-- Dump completed on 2020-03-17 19:54:08
