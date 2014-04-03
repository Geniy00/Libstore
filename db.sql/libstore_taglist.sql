CREATE DATABASE  IF NOT EXISTS `libstore` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `libstore`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: libstore
-- ------------------------------------------------------
-- Server version	5.5.25a

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `taglist`
--

DROP TABLE IF EXISTS `taglist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taglist` (
  `idtaglist` int(11) NOT NULL AUTO_INCREMENT,
  `library_fk` int(11) NOT NULL,
  `tag_fk` int(11) NOT NULL,
  PRIMARY KEY (`idtaglist`),
  UNIQUE KEY `idtaglist_UNIQUE` (`idtaglist`),
  UNIQUE KEY `libraryfk_tagfk_unique` (`library_fk`,`tag_fk`),
  KEY `tag_foreign_key` (`tag_fk`),
  KEY `library_foreign_key` (`library_fk`),
  CONSTRAINT `library_foreign_key` FOREIGN KEY (`library_fk`) REFERENCES `library` (`idlibrary`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tag_foreign_key` FOREIGN KEY (`tag_fk`) REFERENCES `tag` (`idtag`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taglist`
--

LOCK TABLES `taglist` WRITE;
/*!40000 ALTER TABLE `taglist` DISABLE KEYS */;
INSERT INTO `taglist` VALUES (53,31,32),(52,31,36);
/*!40000 ALTER TABLE `taglist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-03 17:16:17
