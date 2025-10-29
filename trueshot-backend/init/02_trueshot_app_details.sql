-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: trueshot
-- ------------------------------------------------------
-- Server version    8.0.37

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
-- Table structure for table `app_details`
--

DROP TABLE IF EXISTS `app_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `storeName` varchar(255) NOT NULL,
  `heroMessage` varchar(255) DEFAULT NULL,
  `supportEmail` varchar(255) DEFAULT NULL,
  `supportNumber` varchar(255) DEFAULT NULL,
  `faqContent` varchar(2000) DEFAULT NULL,
  `aboutContent` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_details`
--

LOCK TABLES `app_details` WRITE;
/*!40000 ALTER TABLE `app_details` DISABLE KEYS */;
INSERT INTO `app_details` (`id`, `storeName`, `heroMessage`, `supportEmail`, `supportNumber`, `faqContent`, `aboutContent`) VALUES
  (1,'TrueShot Camera Exchange','Capture every decisive moment.','hello@trueshot.test','(+63) 917-555-2025','Q: Do you ship internationally?\nA: Yes, we deliver to major APAC hubs.\n\nQ: Can I trade in my gear?\nA: Trade-ins are available after inspection by our technicians.','TrueShot is a curated marketplace for mirrorless and DSLR systems. We authenticate every listing and provide concierge support for collectors and working professionals.');
/*!40000 ALTER TABLE `app_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-02  8:30:00
