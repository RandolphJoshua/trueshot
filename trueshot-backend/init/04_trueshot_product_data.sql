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
-- Table structure for table `product_data`
--

DROP TABLE IF EXISTS `product_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sku` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `modelName` varchar(255) NOT NULL,
  `conditionGrade` varchar(255) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `imageUrl` varchar(255) DEFAULT NULL,
  `lensMount` varchar(255) DEFAULT NULL,
  `sensorType` varchar(255) DEFAULT NULL,
  `releaseYear` int DEFAULT NULL,
  `shutterCount` int DEFAULT NULL,
  `stockQuantity` int DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `featured` tinyint(1) DEFAULT NULL,
  `available` tinyint(1) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_product_sku` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_data`
--

LOCK TABLES `product_data` WRITE;
/*!40000 ALTER TABLE `product_data` DISABLE KEYS */;
INSERT INTO `product_data` (`id`, `sku`, `brand`, `modelName`, `conditionGrade`, `description`, `imageUrl`, `lensMount`, `sensorType`, `releaseYear`, `shutterCount`, `stockQuantity`, `price`, `featured`, `available`, `lastUpdated`, `createdAt`) VALUES
  (1,'TS-001','Canon','EOS R6 Mark II','EXCELLENT','Second-generation full-frame hybrid body inspected with 3-month in-house warranty. Comes with original box and two LP-E6NH batteries.','https://cdn.trueshot.test/images/products/eos-r6-mk2.jpg','Canon RF','Full Frame CMOS',2022,14500,5,1299.99,1,1,'2025-01-12 09:30:00','2025-01-05 08:15:00'),
  (2,'TS-002','Sony','Alpha a7 IV','MINT','Barely used hybrid workhorse with shutter count below 4k. Includes Peak Design strap and 128GB CFexpress Type A card.','https://cdn.trueshot.test/images/products/sony-a7iv.jpg','Sony E','Full Frame CMOS',2021,3200,3,1699.00,1,1,'2025-01-10 11:20:00','2025-01-02 10:45:00'),
  (3,'TS-003','Fujifilm','X-T5','GOOD','Well-loved APS-C body with minor cosmetic wear. Bundled with VG-XT5 vertical grip and extra NP-W235 battery.','https://cdn.trueshot.test/images/products/fuji-xt5.jpg','Fujifilm X','APS-C X-Trans CMOS',2022,23100,7,899.50,0,1,'2025-01-08 14:05:00','2024-12-28 09:10:00'),
  (4,'TS-004','Nikon','Z6 II','FAIR','Trusted backup camera with noticeable brassing but fully operational. Body only, rated for another 100k actuations.','https://cdn.trueshot.test/images/products/nikon-z6ii.jpg','Nikon Z','Full Frame BSI CMOS',2020,48700,2,1149.00,0,0,'2025-01-15 15:25:00','2024-12-20 12:00:00'),
  (5,'TS-005','Panasonic','Lumix GH6','EXCELLENT','Micro Four Thirds flagship tuned for video shooters. Includes cage, XLR module, and dual 256GB V90 SD cards.','https://cdn.trueshot.test/images/products/panasonic-gh6.jpg','Micro Four Thirds','Micro Four Thirds CMOS',2022,18900,4,1399.95,0,1,'2025-01-18 10:40:00','2024-12-30 13:25:00'),
  (6,'TS-006','Canon','RF 24-70mm f/2.8L IS USM','GOOD','Pro standard zoom with pristine optics, original hood, and pouch included.','https://cdn.trueshot.test/images/products/rf-24-70-28.jpg','Canon RF','Full Frame',2019,NULL,6,2099.00,1,1,'2025-01-14 16:12:00','2025-01-07 09:55:00'),
  (7,'TS-007','Sony','FE 70-200mm f/2.8 GM OSS II','EXCELLENT','Latest-generation telephoto zoom, optically perfect with tripod collar and case.','https://cdn.trueshot.test/images/products/sony-70-200-gm2.jpg','Sony E','Full Frame',2021,NULL,4,2598.00,1,1,'2025-01-13 18:22:00','2025-01-06 10:05:00'),
  (8,'TS-008','Fujifilm','XF 56mm f/1.2 R WR','MINT','Weather-sealed portrait prime with smooth focus ring and minimal signs of use.','https://cdn.trueshot.test/images/products/fuji-56wr.jpg','Fujifilm X','APS-C',2022,NULL,8,999.00,0,1,'2025-01-09 12:48:00','2024-12-27 14:15:00'),
  (9,'TS-009','Nikon','NIKKOR Z 24-120mm f/4 S','GOOD','Versatile travel zoom, glass freshly cleaned and firmware updated.','https://cdn.trueshot.test/images/products/nikon-24-120-z.jpg','Nikon Z','Full Frame',2021,NULL,5,1096.95,0,1,'2025-01-11 17:05:00','2024-12-29 16:30:00'),
  (10,'TS-010','Sigma','18-35mm f/1.8 DC HSM Art','GOOD','Canon EF mount classic with minor cosmetic wear and tight focus ring.','https://cdn.trueshot.test/images/products/sigma-18-35-art.jpg','Canon EF','APS-C',2013,NULL,9,649.00,0,1,'2025-01-05 13:35:00','2024-12-18 11:50:00'),
  (11,'TS-011','Tamron','28-75mm f/2.8 Di III VXD G2','EXCELLENT','Second-generation standard zoom with lens dock profile and box.','https://cdn.trueshot.test/images/products/tamron-28-75-g2.jpg','Sony E','Full Frame',2021,NULL,7,899.00,0,1,'2025-01-07 09:18:00','2024-12-22 10:10:00'),
  (12,'TS-012','Canon','EOS R5 C','MINT','Cinema-focused hybrid body with fan housing, includes 512GB CFexpress card.','https://cdn.trueshot.test/images/products/eos-r5c.jpg','Canon RF','Full Frame CMOS',2022,6100,2,3799.00,1,1,'2025-01-17 08:45:00','2025-01-04 13:40:00'),
  (13,'TS-013','Blackmagic Design','Pocket Cinema Camera 6K G2','GOOD','Super 35 cine camera with active EF mount, cage, and V-lock plate.','https://cdn.trueshot.test/images/products/bmpcc6k-g2.jpg','Canon EF','Super 35 CMOS',2022,880,3,1895.00,0,1,'2025-01-16 10:25:00','2024-12-26 15:35:00'),
  (14,'TS-014','DJI','Ronin RS 3 Pro Combo','GOOD','Carbon fiber gimbal kit with focus motor, RavenEye, and counterweight set.','https://cdn.trueshot.test/images/products/dji-rs3-pro.jpg','Universal','N/A',2022,NULL,6,869.00,0,1,'2025-01-06 19:42:00','2024-12-24 09:05:00'),
  (15,'TS-015','Godox','AD200Pro Pocket Flash','EXCELLENT','Dual-head strobe with bare bulb, fresnel head, and two batteries.','https://cdn.trueshot.test/images/products/godox-ad200pro.jpg','Universal','N/A',2020,NULL,12,349.00,0,1,'2025-01-04 16:00:00','2024-12-19 08:20:00'),
  (16,'TS-016','Profoto','B10X Plus','MINT','500Ws monolight with Profoto Connect trigger and padded case.','https://cdn.trueshot.test/images/products/profoto-b10x-plus.jpg','Universal','N/A',2021,NULL,3,2095.00,0,1,'2025-01-03 09:55:00','2024-12-17 12:45:00'),
  (17,'TS-017','Peak Design','Travel Tripod Carbon Fiber','GOOD','Full carbon kit with phone mount and tool tucked in center column.','https://cdn.trueshot.test/images/products/pd-travel-tripod.jpg','Universal','N/A',2020,NULL,10,599.95,0,1,'2025-01-02 15:10:00','2024-12-16 13:30:00'),
  (18,'TS-018','SmallRig','Modular Camera Cage Kit','MINT','Expandable cage system pre-configured for mirrorless rigs with NATO rails.','https://cdn.trueshot.test/images/products/smallrig-cage.jpg','Universal','N/A',2023,NULL,15,229.00,0,1,'2025-01-01 10:25:00','2024-12-14 09:45:00'),
  (19,'TS-019','SanDisk','Extreme Pro SDXC 512GB V90','MINT','High-speed card tested for sustained 260MB/s write for 6K capture.','https://cdn.trueshot.test/images/products/sandisk-512-v90.jpg','Universal','N/A',2023,NULL,25,299.99,0,1,'2025-01-05 08:05:00','2024-12-21 07:55:00'),
  (20,'TS-020','Angelbird','AV PRO CFexpress 1TB Type B','MINT','Firmware-updated media with 1785 MB/s sustained read and heat sink.','https://cdn.trueshot.test/images/products/angelbird-1tb.jpg','Universal','N/A',2024,NULL,18,549.99,0,1,'2025-01-06 11:11:00','2024-12-23 12:35:00'),
  (21,'TS-021','Sony','FX3 Cinema Line','EXCELLENT','Compact cinema body with top handle, XLR module, and cage-ready kit.','https://cdn.trueshot.test/images/products/sony-fx3.jpg','Sony E','Full Frame CMOS',2021,4100,2,3499.99,1,1,'2025-01-18 14:20:00','2025-01-08 09:00:00'),
  (22,'TS-022','Canon','RF 85mm f/1.2L USM DS','GOOD','Dreamy portrait lens with DS coating, minimal use, includes drop-in ND.','https://cdn.trueshot.test/images/products/rf-85-ds.jpg','Canon RF','Full Frame',2020,NULL,4,2999.00,0,1,'2025-01-12 10:55:00','2025-01-03 16:45:00'),
  (23,'TS-023','Nikon','Z8','MINT','Flagship mirrorless body with only 900 actuations and extra EN-EL15c battery.','https://cdn.trueshot.test/images/products/nikon-z8.jpg','Nikon Z','Full Frame Stacked CMOS',2023,900,2,3699.95,1,1,'2025-01-19 11:05:00','2025-01-09 14:25:00'),
  (24,'TS-024','Fujifilm','GFX100S','EXCELLENT','Medium format body with GF 32-64mm kit, shutter count under 5k.','https://cdn.trueshot.test/images/products/gfx100s.jpg','Fujifilm G','Medium Format CMOS',2021,4800,1,3999.00,1,1,'2025-01-17 16:40:00','2025-01-06 17:30:00'),
  (25,'TS-025','Leica','Q3','MINT','Full-frame fixed-lens compact with Summilux 28mm lens and original accessories.','https://cdn.trueshot.test/images/products/leica-q3.jpg','Fixed Lens','Full Frame CMOS',2023,600,1,5895.00,1,1,'2025-01-20 09:35:00','2025-01-10 12:05:00');
/*!40000 ALTER TABLE `product_data` ENABLE KEYS */;
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
