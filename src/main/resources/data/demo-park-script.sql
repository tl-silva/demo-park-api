CREATE DATABASE  IF NOT EXISTS `demo_park` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `demo_park`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: demo_park
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7it9dgecuhaofss241235vdcn` (`cpf`),
  KEY `FKtiuqdledq2lybrds2k3rfqrv4` (`user_id`),
  CONSTRAINT `FKtiuqdledq2lybrds2k3rfqrv4` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'45633380010','ebechara@email.com','2024-12-27 19:23:43.065933','ebechara@email.com','2024-12-27 19:23:43.065933','Evanildo Bechara',2),(2,'12858961069','sbueno@email.com','2024-12-27 19:24:55.155267','sbueno@email.com','2024-12-27 19:24:55.155267','Silveira Bueno',3),(3,'74612235002','tspina@email.com','2024-12-27 19:26:11.674461','tspina@email.com','2024-12-27 19:26:11.674461','Telma Spina',4),(4,'19556923004','erufino@email.com','2024-12-27 19:35:08.189792','erufino@email.com','2024-12-27 19:35:08.189792','Erika Rufino',5),(5,'14739865084','mbrasil@email.com','2024-12-27 19:36:14.054935','mbrasil@email.com','2024-12-27 19:36:14.054935','Marcelo Brasil',6),(6,'37845239003','acarnevale@email.com','2024-12-27 19:37:46.857694','acarnevale@email.com','2024-12-27 19:37:46.857694','Alexandre Carnevale',7),(11,'94378515067','frigos@email.com','2025-01-28 12:48:45.912543','frigos@email.com','2025-01-28 12:48:45.912543','Fábio Rigos',25);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients_have_spots`
--

DROP TABLE IF EXISTS `clients_have_spots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients_have_spots` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `brand` varchar(45) NOT NULL,
  `color` varchar(45) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `discount` decimal(7,2) DEFAULT NULL,
  `entry_date` datetime(6) NOT NULL,
  `exit_date` datetime(6) DEFAULT NULL,
  `fee` decimal(7,2) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `license_plate` varchar(8) NOT NULL,
  `model` varchar(45) NOT NULL,
  `receipt_number` varchar(15) NOT NULL,
  `client_id` bigint NOT NULL,
  `spot_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2ckj5x6x00kwnqw2ur0vtaiy4` (`receipt_number`),
  KEY `FKiegmigfxyygsac4gfy1sirnqt` (`client_id`),
  KEY `FK1omf6qnf7d3a6yl1wsvr1ja26` (`spot_id`),
  CONSTRAINT `FK1omf6qnf7d3a6yl1wsvr1ja26` FOREIGN KEY (`spot_id`) REFERENCES `spots` (`id`),
  CONSTRAINT `FKiegmigfxyygsac4gfy1sirnqt` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_have_spots`
--

LOCK TABLES `clients_have_spots` WRITE;
/*!40000 ALTER TABLE `clients_have_spots` DISABLE KEYS */;
INSERT INTO `clients_have_spots` VALUES (1,'FIAT','Gray','admin@email.com','2024-12-28 16:47:41.776389',0.00,'2024-12-28 16:47:41.747493','2024-12-28 19:01:23.088187',18.00,'admin@email.com','2025-01-13 16:01:23.097525','EVA-4563','Siena 1.6','20241228-164741',1,1),(2,'CHEVROLET','Black','admin@email.com','2024-12-28 17:22:39.897271',0.00,'2024-12-28 17:22:39.862044','2024-12-28 18:07:13.799051',8.50,'admin@email.com','2025-01-13 16:07:13.806799','SIL-1285','Prisma 1.4','20241228-172239',2,2),(3,'CHEVROLET','Black','admin@email.com','2025-01-13 16:05:21.854281',0.00,'2025-01-13 16:05:21.851193','2025-01-13 16:07:26.736586',5.00,'admin@email.com','2025-01-13 16:07:26.742634','SIL-1285','Prisma 1.4','20250113-160521',2,1),(4,'CHEVROLET','Black','admin@email.com','2025-01-13 16:05:38.406997',0.00,'2025-01-13 16:05:38.406024','2025-01-13 16:07:41.949811',5.00,'admin@email.com','2025-01-13 16:07:41.955023','SIL-1285','Prisma 1.4','20250113-160538',2,3),(5,'CHEVROLET','Black','admin@email.com','2025-01-13 16:05:40.665415',0.00,'2025-01-13 16:05:40.665415','2025-01-13 16:07:56.538897',5.00,'admin@email.com','2025-01-13 16:07:56.543021','SIL-1285','Prisma 1.4','20250113-160540',2,4),(6,'CHEVROLET','Black','admin@email.com','2025-01-13 16:08:04.519317',0.00,'2025-01-13 16:08:04.518281','2025-01-13 16:12:20.644868',5.00,'admin@email.com','2025-01-13 16:12:20.652240','SIL-1285','Prisma 1.4','20250113-160804',2,1),(7,'CHEVROLET','Black','admin@email.com','2025-01-13 16:08:07.311444',0.00,'2025-01-13 16:08:07.311444','2025-01-13 16:12:35.091724',5.00,'admin@email.com','2025-01-13 16:12:35.101884','SIL-1285','Prisma 1.4','20250113-160807',2,2),(8,'CHEVROLET','Black','admin@email.com','2025-01-13 16:08:21.696487',0.00,'2025-01-13 16:08:21.696487','2025-01-13 16:12:38.645913',5.00,'admin@email.com','2025-01-13 16:12:38.651229','SIL-1285','Prisma 1.4','20250113-160821',2,3),(9,'CHEVROLET','Black','admin@email.com','2025-01-13 16:08:23.169528',0.00,'2025-01-13 16:08:23.169528','2025-01-13 16:12:42.021348',5.00,'admin@email.com','2025-01-13 16:12:42.025468','SIL-1285','Prisma 1.4','20250113-160823',2,4),(10,'CHEVROLET','Black','admin@email.com','2025-01-13 16:12:46.251761',0.00,'2025-01-13 16:12:46.251761','2025-01-13 16:12:57.313787',5.00,'admin@email.com','2025-01-13 16:12:57.323344','SIL-1285','Prisma 1.4','20250113-161246',2,1),(11,'CHEVROLET','Black','admin@email.com','2025-01-13 16:13:57.615096',0.00,'2025-01-13 16:13:57.615096','2025-01-13 16:14:45.597697',5.00,'admin@email.com','2025-01-13 16:14:45.601452','SIL-1285','Prisma 1.4','20250113-161357',2,1),(12,'CHEVROLET','Black','admin@email.com','2025-01-13 16:15:28.288424',1.50,'2025-01-13 16:15:28.288424','2025-01-13 16:15:38.829410',5.00,'admin@email.com','2025-01-13 16:15:38.833824','SIL-1285','Prisma 1.4','20250113-161528',2,1),(13,'CHEVROLET','Black','admin@email.com','2025-01-13 16:16:11.073956',0.00,'2025-01-13 16:16:11.073956','2025-01-13 16:16:20.630335',5.00,'admin@email.com','2025-01-13 16:16:20.634701','SIL-1285','Prisma 1.4','20250113-161611',2,1),(15,'VOLKSWAGEN','Gray','admin@email.com','2025-01-28 14:34:46.628373',0.00,'2025-01-28 14:34:46.627411','2025-01-28 14:55:45.466115',9.25,'admin@email.com','2025-01-28 14:55:45.476381','FAB-5067','T-Cross 1.0','20250128-143446',11,14);
/*!40000 ALTER TABLE `clients_have_spots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spots`
--

DROP TABLE IF EXISTS `spots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spots` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(4) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8n53ua9cjdxace7jluxf4a6u6` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spots`
--

LOCK TABLES `spots` WRITE;
/*!40000 ALTER TABLE `spots` DISABLE KEYS */;
INSERT INTO `spots` VALUES (1,'A-01','admin@email.com','2024-12-27 19:14:55.257424','admin@email.com','2025-01-13 16:16:20.634701','OCCUPIED'),(2,'A-02','admin@email.com','2024-12-27 19:15:03.362855','admin@email.com','2025-01-13 16:12:35.100910','OCCUPIED'),(3,'A-03','admin@email.com','2024-12-27 19:15:07.970767','admin@email.com','2025-01-13 16:12:38.651229','OCCUPIED'),(4,'A-04','admin@email.com','2024-12-27 19:15:12.552174','admin@email.com','2025-01-13 16:12:42.025468','OCCUPIED'),(14,'A-05','admin@email.com','2025-01-28 14:28:32.810486','admin@email.com','2025-01-28 14:55:45.476381','FREE');
/*!40000 ALTER TABLE `spots` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `last_modified_date` datetime(6) DEFAULT NULL,
  `password` varchar(200) NOT NULL,
  `role` varchar(25) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'anonymousUser','2024-12-27 19:03:39.648683','admin@email.com','2025-01-28 12:31:13.835727','$2a$10$P/swTvww4vnU30l1blxD7Oir/egbNy9Le4hxQ/7fHwpmcSASbnnhS','ROLE_ADMIN','admin@email.com'),(2,'anonymousUser','2024-12-27 19:04:48.749445','anonymousUser','2024-12-27 19:04:48.749445','$2a$10$gZiig49YGkbCPU.GcV0Lge0FKuRRjxsx5RBmEf1oQOkYmoawhXcqu','ROLE_CLIENT','ebechara@email.com'),(3,'anonymousUser','2024-12-27 19:04:56.757504','anonymousUser','2024-12-27 19:04:56.757504','$2a$10$KrOzcydSjZvIAjYtSoorsuQqLgcuHcc9aekaN5DMpteNRg9yv9wSy','ROLE_CLIENT','sbueno@email.com'),(4,'anonymousUser','2024-12-27 19:05:10.473451','anonymousUser','2024-12-27 19:05:10.473451','$2a$10$Dlc7SpGmFaE/cHaMTSrLVexUtmAz1OjXoTQZpQnV9wEaHJmZkGx/y','ROLE_CLIENT','tspina@email.com'),(5,'admin@email.com','2024-12-27 19:32:57.222046','admin@email.com','2024-12-27 19:32:57.222046','$2a$10$tCGd3lXddS.wZVJgeVVLJ.YKkfrr.RbTL8vdbINMyyJ0AyiC/qq3e','ROLE_CLIENT','erufino@email.com'),(6,'admin@email.com','2024-12-27 19:33:32.307499','admin@email.com','2024-12-27 19:33:32.307499','$2a$10$/N9m9pWC51UzuDq7fObbZOMHgbj2kBG2K0C78huoHlxEbYh7z6mjG','ROLE_CLIENT','mbrasil@email.com'),(7,'admin@email.com','2024-12-27 19:33:50.335879','admin@email.com','2024-12-27 19:33:50.335879','$2a$10$dL6sZsm1UK1VAMig2E1uyuNqOd1t.IypBgQeBqvZ9vXcgzhf07k0.','ROLE_CLIENT','acarnevale@email.com'),(25,'anonymousUser','2025-01-28 12:46:37.372115','anonymousUser','2025-01-28 12:46:37.372115','$2a$10$OACDlhnM0zYB22.PgKA9penyTQwu9yRtfoRy4s41Ll1PWEbU4/sCi','ROLE_CLIENT','frigos@email.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-28 15:41:56
