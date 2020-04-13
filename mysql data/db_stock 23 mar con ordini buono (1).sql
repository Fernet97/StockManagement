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
) ENGINE=InnoDB AUTO_INCREMENT=248 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (228,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(229,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(230,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(231,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(232,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(233,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(234,'22/03/2020 21:26:50','nome','cf','indirizzo','tel','email','note'),(235,'22/03/2020 21:26:51','nome','cf','indirizzo','tel','email','note'),(236,'22/03/2020 21:26:51','nome','cf','indirizzo','tel','email','note'),(237,'22/03/2020 21:26:51','nome','cf','indirizzo','tel','email','note'),(238,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(239,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(240,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(241,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(242,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(243,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(244,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(245,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(246,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note'),(247,'22/03/2020 21:26:52','nome','cf','indirizzo','tel','email','note');
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
INSERT INTO `fornitore` VALUES ('FR-1','22/03/2020 21:26:49','alixpresas','piva','via','333','@1','note',1),('FR-10','22/03/2020 21:26:49','presas','piva','via','333','@1','note',10),('FR-11','22/03/2020 21:26:51','alixpresas','piva','via','333','@1','note',11),('FR-12','22/03/2020 21:26:51','amanoz','piva','via','333','@1','note',12),('FR-13','22/03/2020 21:26:51','miao','pina','via','333','@1','note',13),('FR-14','22/03/2020 21:26:51','alixpress','piva','via','333','@1','note',14),('FR-15','22/03/2020 21:26:51','alixpreas','piva','via','333','@1','note',15),('FR-16','22/03/2020 21:26:51','alixprsas','piva','via','333','@1','note',16),('FR-17','22/03/2020 21:26:51','alixresas','piva','via','333','@1','note',17),('FR-18','22/03/2020 21:26:51','alxpresas','piva','via','333','@1','note',18),('FR-19','22/03/2020 21:26:52','lixpresas','piva','via','333','@1','note',19),('FR-2','22/03/2020 21:26:49','amanoz','piva','via','333','@1','note',2),('FR-20','22/03/2020 21:26:52','presas','piva','via','333','@1','note',20),('FR-3','22/03/2020 21:26:49','miao','pina','via','333','@1','note',3),('FR-4','22/03/2020 21:26:49','alixpress','piva','via','333','@1','note',4),('FR-5','22/03/2020 21:26:49','alixpreas','piva','via','333','@1','note',5),('FR-6','22/03/2020 21:26:49','alixprsas','piva','via','333','@1','note',6),('FR-7','22/03/2020 21:26:49','alixresas','piva','via','333','@1','note',7),('FR-8','22/03/2020 21:26:49','alxpresas','piva','via','333','@1','note',8),('FR-9','22/03/2020 21:26:49','lixpresas','piva','via','333','@1','note',9);
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
) ENGINE=InnoDB AUTO_INCREMENT=882 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ordine`
--

LOCK TABLES `ordine` WRITE;
/*!40000 ALTER TABLE `ordine` DISABLE KEYS */;
INSERT INTO `ordine` VALUES (850,'ORD-1','22/03/2020 21:26:50',5,5,'admin','co16-22/03/2020 21:26:52',0,'FR-10',1),(851,'ORD-1','22/03/2020 21:26:50',5,6,'admin','co6-22/03/2020 21:26:50',0,'FR-2',1),(852,'ORD-1','22/03/2020 21:26:50',5,7,'admin','di1-22/03/2020 21:26:49',0,'FR-1',1),(853,'ORD-1','22/03/2020 21:26:50',5,8,'admin','di10-22/03/2020 21:26:50',0,'FR-14',1),(854,'ORD-1','22/03/2020 21:26:50',5,10,'admin','di11-22/03/2020 21:26:52',0,'FR-19',1),(855,'ORD-2','22/03/2020 21:26:50',5,5,'admin','di12-22/03/2020 21:26:52',0,'FR-10',2),(856,'ORD-2','22/03/2020 21:26:50',5,6,'admin','di17-22/03/2020 21:26:52',0,'FR-2',2),(858,'ORD-2','22/03/2020 21:26:50',5,8,'admin','di19-22/03/2020 21:26:52',0,'FR-14',2),(859,'ORD-2','22/03/2020 21:26:50',5,10,'admin','di2-22/03/2020 21:26:49',0,'FR-19',2),(860,'ORD-3','22/03/2020 21:26:50',5,5,'admin','di20-22/03/2020 21:26:52',0,'FR-10',3),(861,'ORD-3','22/03/2020 21:26:50',5,6,'admin','di7-22/03/2020 21:26:50',0,'FR-2',3),(862,'ORD-3','22/03/2020 21:26:50',5,7,'admin','di8-22/03/2020 21:26:50',0,'FR-1',3),(863,'ORD-3','22/03/2020 21:26:50',5,8,'admin','di9-22/03/2020 21:26:50',0,'FR-14',3),(864,'ORD-3','22/03/2020 21:26:50',5,10,'admin','op15-22/03/2020 21:26:52',0,'FR-19',3),(865,'ORD-4','22/03/2020 21:26:52',5,5,'admin','op5-22/03/2020 21:26:50',0,'FR-10',4),(866,'ORD-4','22/03/2020 21:26:52',5,6,'admin','re14-22/03/2020 21:26:52',0,'FR-2',4),(867,'ORD-4','22/03/2020 21:26:52',5,7,'admin','re4-22/03/2020 21:26:50',0,'FR-1',4),(868,'ORD-4','22/03/2020 21:26:52',5,8,'admin','tr13-22/03/2020 21:26:52',0,'FR-14',4),(869,'ORD-4','22/03/2020 21:26:52',5,10,'admin','tr3-22/03/2020 21:26:49',0,'FR-19',4),(880,'ORD-5','23/03/2020 13:30:42',10,0,'','di11-22/03/2020 21:26:52',0,'FR-9',5),(881,'ORD-5','23/03/2020 13:30:42',10,0,'','tr3-22/03/2020 21:26:49',0,'FR-9',5);
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sku`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prodotto`
--

LOCK TABLES `prodotto` WRITE;
/*!40000 ALTER TABLE `prodotto` DISABLE KEYS */;
INSERT INTO `prodotto` VALUES ('co16-22/03/2020 21:26:52','22/03/2020 21:26:52','n5088',10,'condens pol',0,10.5,2,'note','./DATA/IMG/prodotti.png',1,16),('co21-23/03/2020 13:19:37','23/03/2020 13:19:37','a',1,'condens pol',0,1,1,'','C:\\Users\\LittleJoke\\Documents\\NetBeansProjects\\StockManagement\\StockManagement\\img\\box.png',0,21),('co6-22/03/2020 21:26:50','22/03/2020 21:26:50','n5088',10,'condens pol',0,10.5,2,'note','./DATA/IMG/prodotti.png',1,6),('di1-22/03/2020 21:26:49','22/03/2020 21:26:49','1n5088',10,'diodi',1,10.5,2,'note','./DATA/IMG/prodotti.png',0,1),('di10-22/03/2020 21:26:50','22/03/2020 21:26:50','1n58',10,'diodi',0,10.5,2,'note','./DATA/IMG/prodotti.png',1,10),('di11-22/03/2020 21:26:52','22/03/2020 21:26:52','1n5088',10,'diodi',1,10.5,2,'note','./DATA/IMG/prodotti.png',0,11),('di12-22/03/2020 21:26:52','22/03/2020 21:26:52','1n5098',10,'diodi',0,10.5,2,'note','./DATA/IMG/prodotti.png',0,12),('di17-22/03/2020 21:26:52','22/03/2020 21:26:52','1',10,'diodi',1,10.5,2,'note','./DATA/IMG/prodotti.png',1,17),('di18-22/03/2020 21:26:52','22/03/2020 21:26:52','188',10,'diodi',1,10.5,2,'note','./DATA/IMG/prodotti.png',0,18),('di19-22/03/2020 21:26:52','22/03/2020 21:26:52','1088',10,'diodi',0,10.5,2,'note','./DATA/IMG/prodotti.png',0,19),('di2-22/03/2020 21:26:49','22/03/2020 21:26:49','1n5098',10,'diodi',0,10.5,2,'note','./DATA/IMG/prodotti.png',0,2),('di20-22/03/2020 21:26:52','22/03/2020 21:26:52','1n58',10,'diodi',0,10.5,2,'note','./DATA/IMG/prodotti.png',1,20),('di7-22/03/2020 21:26:50','22/03/2020 21:26:50','1',10,'diodi',1,10.5,2,'note','./DATA/IMG/prodotti.png',1,7),('di8-22/03/2020 21:26:50','22/03/2020 21:26:50','188',10,'diodi',1,10.5,2,'note','./DATA/IMG/prodotti.png',0,8),('di9-22/03/2020 21:26:50','22/03/2020 21:26:50','1088',10,'diodi',0,10.5,2,'note','./DATA/IMG/prodotti.png',0,9),('op15-22/03/2020 21:26:52','22/03/2020 21:26:52','15088',10,'optocpo',1,10.5,2,'note','./DATA/IMG/prodotti.png',0,15),('op5-22/03/2020 21:26:50','22/03/2020 21:26:50','15088',10,'optocpo',1,10.5,2,'note','./DATA/IMG/prodotti.png',0,5),('re14-22/03/2020 21:26:52','22/03/2020 21:26:52','1n88',10,'reisitenzasii',1,10.5,2,'note','./DATA/IMG/prodotti.png',1,14),('re4-22/03/2020 21:26:50','22/03/2020 21:26:50','1n88',10,'reisitenzasii',1,10.5,2,'note','./DATA/IMG/prodotti.png',1,4),('tr13-22/03/2020 21:26:52','22/03/2020 21:26:52','1n508',10,'transistor',0,10.5,2,'note','./DATA/IMG/prodotti.png',1,13),('tr3-22/03/2020 21:26:49','22/03/2020 21:26:49','1n508',10,'transistor',0,10.5,2,'note','./DATA/IMG/prodotti.png',1,3);
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
INSERT INTO `utente` VALUES ('admin','22/03/2020 21:26:46','admin','cf','via','000','@','21232f297a57a5a743894a0e4a801fc3',0,'null',0),('b.nato1','22/03/2020 21:26:49','babbeo nato','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('b.nato2','22/03/2020 21:26:51','babbeo nato','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('d.traifuochi1','22/03/2020 21:26:48','dario traifuochi','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('d.traifuochi2','22/03/2020 21:26:51','dario traifuochi','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('f.caino1','22/03/2020 21:26:48','fabbio caino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('f.caino2','22/03/2020 21:26:51','fabbio caino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('l.faino1','22/03/2020 21:26:48','labio faino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('l.faino2','22/03/2020 21:26:51','labio faino','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('m.cancro1','22/03/2020 21:26:48','meshon cancro','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('m.cancro2','22/03/2020 21:26:51','meshon cancro','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('p.liguori1','22/03/2020 21:26:48','pielurigi liguori','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('p.liguori2','22/03/2020 21:26:51','pielurigi liguori','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('v.manisera1','22/03/2020 21:26:48','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('v.manisera2','22/03/2020 21:26:48','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2),('v.manisera3','22/03/2020 21:26:48','vincenzo manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',3),('v.manisera4','22/03/2020 21:26:51','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',4),('v.manisera5','22/03/2020 21:26:51','vittorio manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',5),('v.manisera6','22/03/2020 21:26:51','vincenzo manisera','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',6),('z.puttana1','22/03/2020 21:26:48','zoccola puttana','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',1),('z.puttana2','22/03/2020 21:26:51','zoccola puttana','cf','via1','121','@1','9003d1df22eb4d3820015070385194c8',0,'note',2);
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

-- Dump completed on 2020-03-23 15:24:58
