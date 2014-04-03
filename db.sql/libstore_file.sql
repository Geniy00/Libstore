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
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `idfile` int(11) NOT NULL AUTO_INCREMENT,
  `library_fk` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET latin1 NOT NULL,
  `data` longblob NOT NULL,
  PRIMARY KEY (`idfile`),
  UNIQUE KEY `library_fk_UNIQUE` (`library_fk`),
  UNIQUE KEY `idfile_UNIQUE` (`idfile`),
  KEY `lib2_foreign_key` (`library_fk`),
  CONSTRAINT `lib2_foreign_key` FOREIGN KEY (`library_fk`) REFERENCES `library` (`idlibrary`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (16,31,'31_buble.zip','PK\0\0���D\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\\bublesort.java��=n�0��\0��m��M�^�c�Av[�\"�E�^�?ijA��z�>>�j���!�����#>��0��|L�m���u��NV��ɧM�!eq2���w���� P��Yւ*͹ڇ�V�pG�tnǛ�3Gm+\\�׊�$�)���!�#u=���)�l��2a���{��+��B�B�\\�W��|�f�Ƙ�҄WC��5^��e���+S�[��{�Fоɶ��>����Н�	J�CO�!�q�9��<��d�<��ς����u�=�-�qE��=�T��/��_���i��ʲ��#�u�����PK2�p|7\0\0�\0\0PK\0\0\0���D2�p|7\0\0�\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\\bublesort.javaPK\0\0\0\0\0\0=\0\0\0t\0\0\0\0');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-03 17:16:14
