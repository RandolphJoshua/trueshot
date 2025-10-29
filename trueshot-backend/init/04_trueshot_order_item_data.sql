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
-- Table structure for table `order_item_data`
--

DROP TABLE IF EXISTS `order_item_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `productName` varchar(255) NOT NULL,
  `productBrand` varchar(255) DEFAULT NULL,
  `productCondition` varchar(255) DEFAULT NULL,
  `productImageUrl` varchar(255) DEFAULT NULL,
  `unitPrice` decimal(10,2) NOT NULL,
  `quantity` int NOT NULL,
  `lineTotal` decimal(10,2) NOT NULL,
  `status` varchar(255) NOT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_item_order` (`order_id`),
  KEY `FK_order_item_product` (`product_id`),
  CONSTRAINT `FK_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `order_data` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `product_data` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item_data`
--

LOCK TABLES `order_item_data` WRITE;
/*!40000 ALTER TABLE `order_item_data` DISABLE KEYS */;
INSERT INTO `order_item_data` (`id`, `order_id`, `product_id`, `productName`, `productBrand`, `productCondition`, `productImageUrl`, `unitPrice`, `quantity`, `lineTotal`, `status`, `lastUpdated`, `createdAt`) VALUES
  (1,1,1,'EOS R6 Mark II','Canon','EXCELLENT','https://cdn.trueshot.test/images/products/eos-r6-mk2.jpg',1299.99,1,1299.99,'ORDERED','2025-02-02 14:30:00','2025-02-01 16:45:00'),
  (2,1,2,'Alpha a7 IV','Sony','MINT','https://cdn.trueshot.test/images/products/sony-a7iv.jpg',1699.00,1,1699.00,'ORDERED','2025-02-02 14:30:00','2025-02-01 16:45:00'),
  (3,2,3,'X-T5','Fujifilm','GOOD','https://cdn.trueshot.test/images/products/fuji-xt5.jpg',899.50,2,1799.00,'RESERVED','2025-02-03 10:05:00','2025-02-02 19:20:00'),
  (4,3,4,'Z6 II','Nikon','FAIR','https://cdn.trueshot.test/images/products/nikon-z6ii.jpg',1149.00,1,1149.00,'CANCELLED','2025-02-04 09:05:00','2025-02-03 08:00:00');
/*!40000 ALTER TABLE `order_item_data` ENABLE KEYS */;
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
