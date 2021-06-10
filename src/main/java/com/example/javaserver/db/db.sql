-- MySQL dump 10.13  Distrib 8.0.24, for Win64 (x86_64)
--
-- Host: localhost    Database: quanlynhanvien
-- ------------------------------------------------------
-- Server version	8.0.24

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `idaddress` int NOT NULL AUTO_INCREMENT,
  `iduser` int NOT NULL,
  `address` varchar(45) NOT NULL,
  PRIMARY KEY (`idaddress`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,1,'146 Cộng Hòa'),(2,1,'424 Nguyễn Văn Luông'),(3,2,'146 Cộng Hòa'),(4,3,'146 Cộng Hòa'),(5,4,'148 cộng Hòa');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `iddepartment` int unsigned NOT NULL,
  `departmentname` varchar(45) NOT NULL,
  `managerid` int NOT NULL,
  `location` varchar(45) DEFAULT NULL,
  `departmentphone` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iddepartment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'IT',1,'701','0123456789'),(2,'Nhân sự',2,'702',NULL),(3,'Kế toán',3,'703',NULL);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refreshtoken`
--

DROP TABLE IF EXISTS `refreshtoken`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refreshtoken` (
  `id` bigint NOT NULL,
  `expiry_date` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_or156wbneyk8noo4jstv55ii3` (`token`),
  KEY `FKfr75ge3iecdx26qe8afh1srf6` (`user_id`),
  CONSTRAINT `FKfr75ge3iecdx26qe8afh1srf6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refreshtoken`
--

LOCK TABLES `refreshtoken` WRITE;
/*!40000 ALTER TABLE `refreshtoken` DISABLE KEYS */;
INSERT INTO `refreshtoken` VALUES (31,'2021-06-10 17:39:23','fd553ffe-50a2-40a2-a3ff-69f75b7fe58c',1),(32,'2021-06-10 17:57:57','ed167556-07fa-4d78-a055-e0507de9ab87',1),(33,'2021-06-11 13:09:31','9a25da36-374e-4f8f-a357-4f4576b7e564',1),(34,'2021-06-11 13:28:58','6d2a60e6-8fcb-4b18-b5a1-9020c2757dc5',1),(35,'2021-06-11 13:43:40','5e339ad2-e83f-4d13-9fb9-53cca3a38b0f',1),(36,'2021-06-11 14:45:37','756a313b-c6ae-47e0-b658-2460a683ca54',1),(37,'2021-06-11 14:50:02','f58bc21d-4726-4d96-9664-14970b5b46fd',1),(38,'2021-06-11 15:34:13','da0fc8cb-8783-4f3b-a43d-fe0309928230',1);
/*!40000 ALTER TABLE `refreshtoken` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `idrole` int unsigned NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) NOT NULL,
  `roledescription` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`idrole`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'CEO','full permission'),(3,'Employee',NULL),(4,'Intern',NULL),(5,'ROLE_USER',NULL),(6,'ROLE_MOD',NULL),(7,'ROLE_ADMIN',NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary`
--

DROP TABLE IF EXISTS `salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salary` (
  `salarygrade` int NOT NULL,
  `basicpay` varchar(45) NOT NULL,
  `coefficientssalary` varchar(45) NOT NULL,
  `allowance` varchar(45) DEFAULT NULL,
  `decripstion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`salarygrade`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary`
--

LOCK TABLES `salary` WRITE;
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
INSERT INTO `salary` VALUES (1,'0','0','3000000','intern'),(2,'600000','1','0','train'),(3,'800000','1','0','fresher'),(4,'800000','1.5',NULL,'lead');
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fullname` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `sex` varchar(45) DEFAULT NULL,
  `birthday` varchar(45) DEFAULT NULL,
  `idrole` int NOT NULL DEFAULT '4',
  `iddepartment` int DEFAULT NULL,
  `idsalary` int NOT NULL DEFAULT '1',
  `avatar` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'khoa@gmail.com','$2a$10$6Ax6pW/qavtRsTXAkOJzmefoacRdbG1XbW73aXysxIUq7GKhCwvLG','khoa huynh','0374080167',NULL,NULL,1,1,4,NULL),(3,'12323@gmail.com','$2a$10$wdO3gNQ7RI5bZ8DhgV4sgO.pCER4FSDpzDoJLAwY6nabgQxJTcbYi','123','4333',NULL,NULL,2,2,2,NULL),(4,'kha@gmail.com','$2a$10$PDfsW1QKGEQ6GNPB8cTepOR4HDkjlYblwZi1hlAZglVjshLe7jSsa','kha khoa','123123444',NULL,NULL,4,3,1,NULL),(5,'1232223@gmail.com','$2a$10$drmasheYMsrM0GrrGixG3.AfDXDA7ZHNyGV3Igm8a.K1qWj1dlVqi','123 123','134343',NULL,NULL,0,1,1,NULL),(6,'3333@gmail.com','$2a$10$.uxhixDulwrKhb/VsfPIyuDnMNTlrfJ/tL7zTaQ0o/jmMbqttpEry','123123123333','3434334',NULL,NULL,4,1,3,NULL),(7,'kkk@gmail.com','123','huynh anh khoa','222',NULL,NULL,4,NULL,1,NULL),(8,'kk222k@gmail.com','123','huynh anh khoa','4333',NULL,NULL,4,NULL,1,NULL),(9,'aaaaaaaa','123','huynh anh khoa',NULL,NULL,NULL,4,NULL,1,NULL),(10,'bbbbb','123','123',NULL,NULL,NULL,4,NULL,1,NULL),(11,'123321@gmail.com','$2a$10$0Xrkta.w3NOJOKOgK9pqdOZkrr69hSzb/jFIa1Tg/bjcGSg2Io2lu','huynh anh khoa',NULL,NULL,NULL,4,NULL,1,NULL),(12,'33333@gmailcom','$2a$10$JhFtbUU2fiSXlrAx9kc7Xefjg536zXJVb18DIYPjJX3aaeYj3YaQq',NULL,NULL,NULL,NULL,4,NULL,1,NULL),(13,'989@gmailcom','22','huynh anh khoa2',' 44',NULL,NULL,0,NULL,1,NULL),(14,'123123@gmail.com','$2a$10$RTVHyrkwJixT3B7g40ui6OadP6s8rUAv357Z4U7YRSOrC6dGSn8au','huynh anh khoa','33',NULL,NULL,0,NULL,1,NULL),(49,'1111@gmail.com','$2a$10$4eW3ejHdnyMDbJyDl51TmOsmDYBEjXZOzmMhOzUgwd77.9sTlQsJC','huynh anh khoa ','1333',NULL,NULL,0,NULL,1,NULL),(50,'321@gmail.com','$2a$10$ZkpZw1Dz6ZSpaSJKOFiFluuMmzsWA/59KykczIv0Dgx6mNVeczHNq','huynh anh khoa','456',NULL,NULL,0,NULL,1,NULL),(51,'321123@gmail.com','$2a$10$AhHGIyG3CKwetej/RrE6T.fbrorZbcc95fHkMmRSP8gd6CHN1ioQ2','234234','0123456789',NULL,NULL,0,NULL,1,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL,
  `idrole` int NOT NULL,
  PRIMARY KEY (`id`,`idrole`),
  CONSTRAINT `FK2oqpqfi5ut3xylw61wqhl5ggo` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,7),(3,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-10 16:11:53
