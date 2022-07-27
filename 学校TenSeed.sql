-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `accountID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `passWord` varchar(255) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `eMail` varchar(45) NOT NULL,
  `accountType` enum('Student','Teacher','Admin') NOT NULL,
  `accountBatch` int NOT NULL,
  `regAdminID` int NOT NULL,
  `regDate` date NOT NULL,
  PRIMARY KEY (`accountID`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accounts`
--

LOCK TABLES `accounts` WRITE;
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` VALUES (1,'alpaca','madalpaca','madz','dinar','madz25@gmail.com','Admin',1,1,'2022-07-25'),(29,'stud1','qweqwe1','stud1','ent1','student1@gmail.com','Student',2,1,'2022-07-25'),(30,'stud2','qweqwe2','stud2','ent2','student2@gmail.com','Student',2,1,'2022-07-25'),(31,'tea1','qweqwe1','tea1','cher1','teacher1@gmail.com','Teacher',2,1,'2022-07-25'),(32,'tea2','qweqwe2','tea2','cher2','teacher2@gmail.com','Teacher',2,1,'2022-07-25'),(33,'demo1','qweqwe','demo1','sample1','sample1@gmail.com','Student',3,1,'2022-07-25');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `adminID` int NOT NULL AUTO_INCREMENT,
  `accountID` int NOT NULL,
  PRIMARY KEY (`adminID`),
  KEY `accounts_admins_1` (`accountID`),
  CONSTRAINT `accounts_admins_1` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`accountID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,1);
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duo`
--

DROP TABLE IF EXISTS `duo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `duo` (
  `duoID` int NOT NULL AUTO_INCREMENT,
  `studentID` int NOT NULL,
  `teacherID` int NOT NULL,
  `language` varchar(45) NOT NULL,
  PRIMARY KEY (`duoID`),
  KEY `students_duo_1` (`studentID`),
  KEY `teachers_duo_1` (`teacherID`),
  CONSTRAINT `students_duo_1` FOREIGN KEY (`studentID`) REFERENCES `students` (`studentID`) ON DELETE CASCADE,
  CONSTRAINT `teachers_duo_1` FOREIGN KEY (`teacherID`) REFERENCES `teachers` (`teacherID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duo`
--

LOCK TABLES `duo` WRITE;
/*!40000 ALTER TABLE `duo` DISABLE KEYS */;
INSERT INTO `duo` VALUES (12,21,3,'Struts1'),(13,22,4,'Struts2'),(14,22,4,'Struts1'),(15,23,4,'HTML / CSS / REACT'),(16,21,4,'HTML / CSS / REACT');
/*!40000 ALTER TABLE `duo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluations`
--

DROP TABLE IF EXISTS `evaluations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluations` (
  `evaluationID` int NOT NULL AUTO_INCREMENT,
  `teacherID` int NOT NULL,
  `studentID` int NOT NULL,
  `evaluation` varchar(255) NOT NULL,
  `stars` int NOT NULL,
  `evalDate` date NOT NULL,
  PRIMARY KEY (`evaluationID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluations`
--

LOCK TABLES `evaluations` WRITE;
/*!40000 ALTER TABLE `evaluations` DISABLE KEYS */;
INSERT INTO `evaluations` VALUES (1,3,21,'Amazing',5,'2022-07-25'),(2,3,21,'AMAZING',5,'2022-07-25');
/*!40000 ALTER TABLE `evaluations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exams`
--

DROP TABLE IF EXISTS `exams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exams` (
  `examID` int NOT NULL AUTO_INCREMENT,
  `accountBatch` int NOT NULL,
  `term` enum('Prelims','Midterms','Finals') NOT NULL,
  `language` varchar(45) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`examID`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exams`
--

LOCK TABLES `exams` WRITE;
/*!40000 ALTER TABLE `exams` DISABLE KEYS */;
INSERT INTO `exams` VALUES (36,2,'Midterms','Struts1','Super Midterms for struts1'),(37,3,'Midterms','HTML / CSS / REACT','Super Midterms for HTML etc...');
/*!40000 ALTER TABLE `exams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gitlinks`
--

DROP TABLE IF EXISTS `gitlinks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gitlinks` (
  `gitID` varchar(45) NOT NULL,
  `gitLinkID` int NOT NULL AUTO_INCREMENT,
  `studentID` int NOT NULL,
  `gitLink` varchar(100) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`gitLinkID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gitlinks`
--

LOCK TABLES `gitlinks` WRITE;
/*!40000 ALTER TABLE `gitlinks` DISABLE KEYS */;
INSERT INTO `gitlinks` VALUES ('1',1,1,'https://github.com/madalpaca25/myProject','updates'),('1',2,2,'https://github.com/freeCodeCamp/freeCodeCamp','updates on back-end'),('1',3,3,'https://github.com/996icu/996.ICU','updates on front-end');
/*!40000 ALTER TABLE `gitlinks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gits`
--

DROP TABLE IF EXISTS `gits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gits` (
  `gitID` int NOT NULL AUTO_INCREMENT,
  `activityNumber` int NOT NULL,
  `activity` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  PRIMARY KEY (`gitID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gits`
--

LOCK TABLES `gits` WRITE;
/*!40000 ALTER TABLE `gits` DISABLE KEYS */;
INSERT INTO `gits` VALUES (1,1,'Struts2','Individual Projects');
/*!40000 ALTER TABLE `gits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `performances`
--

DROP TABLE IF EXISTS `performances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `performances` (
  `performanceID` int NOT NULL AUTO_INCREMENT,
  `teacherID` int NOT NULL,
  `studentID` int NOT NULL,
  `performance` varchar(255) NOT NULL,
  `stars` int NOT NULL,
  `perfDate` date NOT NULL,
  PRIMARY KEY (`performanceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `performances`
--

LOCK TABLES `performances` WRITE;
/*!40000 ALTER TABLE `performances` DISABLE KEYS */;
/*!40000 ALTER TABLE `performances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `examID` int NOT NULL,
  `questionID` int NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `choiceA` varchar(45) NOT NULL,
  `choiceB` varchar(45) NOT NULL,
  `choiceC` varchar(45) NOT NULL,
  `choiceD` varchar(45) NOT NULL,
  `answerKey` varchar(45) NOT NULL,
  `explanation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`questionID`),
  KEY `exams_questions_1` (`examID`),
  CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`examID`) REFERENCES `exams` (`examID`) ON DELETE CASCADE,
  CONSTRAINT `exams_questions_1` FOREIGN KEY (`examID`) REFERENCES `exams` (`examID`) ON DELETE CASCADE,
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`examID`) REFERENCES `exams` (`examID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (37,5,'What is your favorite color','black','blue','red','green','a','black looks good, looking good means no dandruff, clear means no dandruff'),(36,6,'What is the opposite of light?','dark','life','dim','blue','a','sample'),(36,8,'qweqweweeqwefxfa','131','qweesada','qweqwe','qweqwe','d','eqweqweqwee'),(36,9,'qweqweqwe1123?','qweqweqw','dasdas','fasdas','weadsad','c','e12ewewe'),(36,10,'dasdwrw','weaweaw','dasd','dasdasd','fasdasd','b','dasdasdas');
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scores`
--

DROP TABLE IF EXISTS `scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scores` (
  `examID` int NOT NULL,
  `scoreID` int NOT NULL AUTO_INCREMENT,
  `duoID` int NOT NULL,
  `examDate` date NOT NULL,
  `score` varchar(45) DEFAULT NULL,
  `examStatus` varchar(45) NOT NULL DEFAULT 'Open',
  PRIMARY KEY (`scoreID`),
  KEY `examID_idx` (`examID`) /*!80000 INVISIBLE */,
  KEY `duo_duoID_1` (`duoID`),
  CONSTRAINT `duo_duoID_1` FOREIGN KEY (`duoID`) REFERENCES `duo` (`duoID`) ON DELETE CASCADE,
  CONSTRAINT `exams_scores_1` FOREIGN KEY (`examID`) REFERENCES `exams` (`examID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scores`
--

LOCK TABLES `scores` WRITE;
/*!40000 ALTER TABLE `scores` DISABLE KEYS */;
INSERT INTO `scores` VALUES (36,78,12,'2022-07-25','75','Closed');
/*!40000 ALTER TABLE `scores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `sessionID` int NOT NULL AUTO_INCREMENT,
  `accountID` int NOT NULL,
  `userName` varchar(45) NOT NULL,
  `tokenValue` varchar(255) NOT NULL,
  PRIMARY KEY (`sessionID`),
  UNIQUE KEY `sessionID_UNIQUE` (`sessionID`),
  KEY `sessionsAccoutsUserName_idx` (`userName`),
  KEY `accounts_sessions_1` (`accountID`),
  CONSTRAINT `accounts_sessions_1` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`accountID`) ON DELETE CASCADE,
  CONSTRAINT `sessionsAccountsID` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`accountID`),
  CONSTRAINT `sessionsAccoutsUserName` FOREIGN KEY (`userName`) REFERENCES `accounts` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (23,30,'stud2','K6TARPFZ7KKZKKNZ0CGSAHVN5JGX9LLS'),(25,29,'stud1','O59N9O4S0NPL9CMKSAVHKPF7ZW1J4HJW'),(26,29,'stud1','J1N59DEL56CHLZLYLYTDRG53OYG2P114'),(27,29,'stud1','O5CO2QV9K3WTPPSLRP22SRWT1VEJJDMV'),(28,29,'stud1','4LXKJ7XYGNE9Q596LL85E2UXEHR27C4F'),(29,29,'stud1','A6SJ1IZRN0OA2QODD6NCFU7BDEKMHQ8E'),(30,29,'stud1','D831SC5KBY93IF6U3UI5HJJ6C7IJKUPL'),(31,29,'stud1','BJONPC3GSQC8KZX6SQM2L14UWZ5517IH'),(32,29,'stud1','JIO46ZMTREP7KPK4DRWNDZTUDXQHGIMZ'),(33,29,'stud1','JIO46ZMTREP7KPK4DRWNDZTUDXQHGIMZ'),(34,29,'stud1','FEU3TY46XJ37LSRXU0XI18XMQXM6FS4'),(35,29,'stud1','851ZB5DRBNJ7YJTI7JEMNJ5MGDM5GMZH'),(36,29,'stud1','F91W2AFR6I53KL26QN4GOUEYT41DKTN2'),(37,29,'stud1','OIUYZYL6E35SSFFMYVW2YM622Z6BFX9S'),(38,29,'stud1','ND761J2O3G9JGO6QVXF3R2UP3VGO9W4K'),(39,29,'stud1','PXBE768Y4EKPF9VW0HD3GORQCNMYXHFB'),(40,29,'stud1','8WE216HXYXSTISBSNZAQJYK0WNCZYNZO'),(41,29,'stud1','G6AQ2ZO01OIEVUZNQF8MHLOSVXLPR1LJ'),(42,29,'stud1','J38W8VIOUB6DR8AE54FT9U373OD50BJX'),(43,29,'stud1','5VQBDRBQB335SYAZZ2R43IH2HGLQPF22'),(44,29,'stud1','3KO95VK7ST6O5GG34GFBIXAC1BVOSBA3'),(45,29,'stud1','5NQX4AD1GOMW853NYU0BSVLPZ2925BA5'),(46,29,'stud1','EB7RWFC2H4TBGP643E0RXQGBM9MAZQCJ'),(47,29,'stud1','6M2H7FQN86WZ78V1BTGPGP9RG8M3O8Y0'),(48,29,'stud1','LSQB7A1FIQ2LOWG1XEBP3XM73TGS3NT8'),(49,29,'stud1','CKCFW4HJSQRE044QSEYLQENP4RD6NYML'),(50,29,'stud1','O2CMP46BKTFOSLNK1DIUGZ2PPIU29LPB'),(51,29,'stud1','O1FP5VSIFZU9EA3UZX0EJXDRRY53BVGO'),(52,29,'stud1','GI4GXYM7PAHE59QOFOBJA6RNTLVX1DDM'),(53,29,'stud1','1VF5M93ZRZLXKJVFWKAPKGEJLJUQ1G7M'),(54,29,'stud1','NXWRAIIB9J282JJODTQU48JXQ4H57J2A'),(55,29,'stud1','FLF3BAXS8I7CYBR6SAXL3XCMT5N1ZD0A'),(56,29,'stud1','AUUPW56MF7QB13FF2VLHDEC50MJX64JL'),(57,29,'stud1','MKGBI19ZEXDWN73Q01YZSAHIAWRO2UP0'),(60,29,'stud1','AW8DBAM2N8Q0UZQ2QKV60ICB6TGGQ3MP'),(61,29,'stud1','GZ6WKCTYCERA86PLM92VMY9LQKPS27YW'),(62,29,'stud1','DNKJBOELIW49DHSV8FDTKRSRGCRBVEU3'),(63,29,'stud1','9U39D1QV6SCXG0O5MUBZQOJOOD27OS6P'),(65,29,'stud1','JYFYNRL2H8S44CF0Z8A4RR7MC0W8H280'),(66,29,'stud1','4HYBMTBDES2AHR007FM42PWNC71GTJMQ'),(67,29,'stud1','4HYBMTBDES2AHR007FM42PWNC71GTJMQ'),(68,29,'stud1','JUYPUB8VDLSZYCQQWFL4PTDBI7E80VV7'),(69,29,'stud1','8FFZH5MTP0U8E39ZQE765JQXT6HY8ZXS'),(70,29,'stud1','EGTXFUNVT9VAUNTR0R5072VCKNCSVXOL'),(71,29,'stud1','Q34IZYI67YIM6YV7WWCN42NEGTFKSUIW'),(75,30,'stud2','OZHL7U8PGINEFIPGZAW2PE7ETJO9WB4R'),(76,30,'stud2','FTKJSLF5JIP7L9IY70S1KTAZWHQSSXYM'),(77,30,'stud2','KE3CTY0OCCN4GVOH4BUORZYM0L2QBCV9'),(78,30,'stud2','5YGC9E9JA1CMA85Y8ASPKPERXOSY9T30'),(79,30,'stud2','CA3GMUD779ANSV1X2VQB7S9HN27OX6S0'),(80,30,'stud2','29D7X2UQPQQ5AO6G4GWDBXBPTH1OXJID'),(82,30,'stud2','OTURJ4FAG7VOANH76U8RMTBAOLW7AKD0'),(83,30,'stud2','KK81MBHM6N64EQCFAEE2OJILTMN3ILKC'),(84,30,'stud2','M7PU1C99LD3RU93457DFNMXENJP9ZVC0'),(86,29,'stud1','AJRW3M475G4A5DW661XPPBRG12ELGCIB'),(87,29,'stud1','IJYDEE3437POVCY61KG6OLPFHHS4V8FX'),(88,29,'stud1','I39X4O2VON0YS7AUCGUNYFYF6OHPJJUK'),(90,30,'stud2','2PBOW3ARWA6U0O8XLTJVTRJXJLM7367Q'),(91,30,'stud2','22PLOF6QGQYFI8SM6PWEA45ZGNROR3MZ'),(93,1,'alpaca','L1MDD4V4V7965TNQEZZ9P0VH577XG0GT'),(96,1,'alpaca','BVAFCRHF9FYRXTD7BJ56NVD0OM7ZWV1C'),(97,1,'alpaca','GUWIYDBBGEEG1B5AFG8YN95I1M84Q9OE'),(98,1,'alpaca','NV37LCULHGBX8R90L0RHHME9Y8C6IOU7'),(99,1,'alpaca','PWV33D94LIQWZWBZLYJC4D1K1YTPKE9P'),(100,1,'alpaca','JD7JQPU5PB11HRKXTGDHHT6SW84SM22T'),(101,1,'alpaca','M479CZONTBTD71YPVW4Z221WH8KY0P8I'),(102,1,'alpaca','GHPOE2MXKVC3MCVZ7UT14KID4F0SX7OQ'),(103,1,'alpaca','HAW4CHWWER9ILET4UJ39E6D6ZT7LJ90L'),(104,1,'alpaca','H9XKG2CVL5HD1L5M76OT6XSMM4YQPMDA'),(105,1,'alpaca','MRUBCTB738MTNX68R1MRGBAW24SRJZKW'),(106,1,'alpaca','1JQGL5R1AW07RUUTBBSRVNNJL0RRR3E9'),(107,1,'alpaca','M3C3IJ7BI743UDU4ZQ4ZHS7D4TBBOCE7'),(108,1,'alpaca','CW784J1I1PT6KFGLMJG2NUZNLU7RE0L9'),(109,1,'alpaca','NG4V3EXWSRKJQU539ZURGNO575LYOQ8L'),(110,1,'alpaca','315OQUBCBJ9R2Y9VXEWW6BIN45WGGG7Y'),(111,1,'alpaca','A92ZAIEY8ZXKIEGLVH49L5O5Z6ZFHAT0'),(112,1,'alpaca','3C96N4KIZM7167NA515MGC3HBXD32UI4'),(113,1,'alpaca','7YU23R8T3E6I6N3OL3FDXH7VMNM0IC8W'),(114,1,'alpaca','1YHSBQ1R4GFI12JAR0KW2VW9BSAS9ADZ'),(115,1,'alpaca','B369KG5ODUSI58M35EU45ELM4CJQ8ZYJ'),(116,1,'alpaca','NNHV9JUUQ9DQPCLVZQYSPF59RR6789NI'),(117,1,'alpaca','4QX4CAHUEB74OKKZZY9YJMM8WJ9POQQ'),(118,1,'alpaca','2PBQCP0EG55ZV0FJBMX5MXZ9Y6SPBAQC'),(123,1,'alpaca','CLFSJX9T0N9E7HF2M24FXJW9YZTA6ACD'),(124,1,'alpaca','CLFSJX9T0N9E7HF2M24FXJW9YZTA6ACD'),(130,29,'stud1','2QAQ0PZSANMQCHXV78DZ8M8IT9XS2PHR'),(133,1,'alpaca','2O5LR8B1HJZ7W4BOF224KYNO1ITPYFSY'),(134,1,'alpaca','2O5LR8B1HJZ7W4BOF224KYNO1ITPYFSY');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `studentID` int NOT NULL AUTO_INCREMENT,
  `accountID` int NOT NULL,
  PRIMARY KEY (`studentID`),
  KEY `accounts_students_1` (`accountID`),
  CONSTRAINT `accounts_students_1` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`accountID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (21,29),(22,30),(23,33);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers` (
  `teacherID` int NOT NULL AUTO_INCREMENT,
  `accountID` int NOT NULL,
  PRIMARY KEY (`teacherID`),
  KEY `accounts_teachers_1` (`accountID`),
  CONSTRAINT `accounts_teachers_1` FOREIGN KEY (`accountID`) REFERENCES `accounts` (`accountID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (3,31),(4,32);
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-27 20:52:54
