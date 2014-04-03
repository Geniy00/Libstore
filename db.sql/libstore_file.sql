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
INSERT INTO `file` VALUES (16,31,'31_buble.zip','PK\0\0ÄâÉD\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\\bublesort.javaçí=n√0Ö˜\0æ√möŸMá^°cêAv[Ü\"íEÓ^“?ijAπ»z¢>>ìjª¬Ë!™»À≈È#>ª¢0ÙÂ|L°mƒÿÓåu≤˙NVêπ…ßM·ú!eq2™¬—wî≥ºŸ P’ËY÷Ç*Õπ⁄áàVÖpGåtn«õµ3Gm+\\î◊äÕ$´)≠Øµ!§#u=â≥´)‰láì2a¡∏{É’+•ÇBÎB–\\°WÌÇ‰|äf˜∆òÔ“ÑWC∂ä5^∂¢eŸÕ√+SË[Ê€{∆F–æ…∂¨«>ïµ≤’–ù©	J≤CO·!Úq°9§ì<äπdæ<ÁñœÇù…∆îuÛ=Û≤-ˇqEÚ‰=»Tü•/ﬁÕ_õµÎi∞Î ≤Ûûé¿#‹u°˛ÓÂÎäPK2¿p|7\0\0Í\0\0PK\0\0\0ÄâÉD2¿p|7\0\0Í\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\\bublesort.javaPK\0\0\0\0\0\0=\0\0\0t\0\0\0\0');
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
