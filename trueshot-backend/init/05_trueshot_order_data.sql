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
-- Table structure for table `order_data`
--

DROP TABLE IF EXISTS `order_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `buyerName` varchar(255) NOT NULL,
  `buyerEmail` varchar(255) NOT NULL,
  `buyerPhone` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `totalAmount` decimal(10,2) DEFAULT NULL,
  `specialInstructions` varchar(255) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_data`
--

LOCK TABLES `order_data` WRITE;
/*!40000 ALTER TABLE `order_data` DISABLE KEYS */;
INSERT INTO `order_data` (`id`, `buyerName`, `buyerEmail`, `buyerPhone`, `status`, `totalAmount`, `specialInstructions`, `lastUpdated`, `createdAt`) VALUES
  (1,'Lara Mitchell','lara.mitchell@example.test','(+63) 917-555-0198','PAID',2998.99,'Signature required on delivery.','2025-02-02 14:30:00','2025-02-01 16:45:00'),
  (2,'Miguel Santos','miguel.santos@example.test','(+63) 917-888-1100','PENDING_PAYMENT',1799.00,'Reserve until payment confirmation.','2025-02-03 10:05:00','2025-02-02 19:20:00'),
  (3,'Chloe Navarro','chloe.navarro@example.test','(+63) 916-777-4321','CANCELLED',1149.00,'Customer cancelled after stock verification.','2025-02-04 09:10:00','2025-02-03 08:00:00');
/*!40000 ALTER TABLE `order_data` ENABLE KEYS */;
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
