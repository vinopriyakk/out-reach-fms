-- MySQL dump 10.13  Distrib 5.7.30, for Linux (x86_64)
--
-- Host: localhost    Database: cts_outreach_fms
-- ------------------------------------------------------
-- Server version	5.7.30-0ubuntu0.16.04.1

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
-- Table structure for table `cts_or_feedback_choice_answer`
--

DROP TABLE IF EXISTS `cts_or_feedback_choice_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_feedback_choice_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) DEFAULT NULL,
  `question` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKld77il6m6pmb7f14wf5rnok5w` (`question`),
  CONSTRAINT `FKld77il6m6pmb7f14wf5rnok5w` FOREIGN KEY (`question`) REFERENCES `cts_or_feedback_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_feedback_choice_answer`
--

LOCK TABLES `cts_or_feedback_choice_answer` WRITE;
/*!40000 ALTER TABLE `cts_or_feedback_choice_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `cts_or_feedback_choice_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_feedback_question`
--

DROP TABLE IF EXISTS `cts_or_feedback_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_feedback_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `participant_type` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `question_type` varchar(255) DEFAULT NULL,
  `rating_question` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ujok6h17dr86mlgul5vndb9d` (`rating_question`),
  CONSTRAINT `FK8ujok6h17dr86mlgul5vndb9d` FOREIGN KEY (`rating_question`) REFERENCES `cts_or_feedback_rating_question` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_feedback_question`
--

LOCK TABLES `cts_or_feedback_question` WRITE;
/*!40000 ALTER TABLE `cts_or_feedback_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `cts_or_feedback_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_feedback_rating_question`
--

DROP TABLE IF EXISTS `cts_or_feedback_rating_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_feedback_rating_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dislike_question` varchar(255) DEFAULT NULL,
  `like_question` varchar(255) DEFAULT NULL,
  `rating_question` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_feedback_rating_question`
--

LOCK TABLES `cts_or_feedback_rating_question` WRITE;
/*!40000 ALTER TABLE `cts_or_feedback_rating_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `cts_or_feedback_rating_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_fms_event`
--

DROP TABLE IF EXISTS `cts_or_fms_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_fms_event` (
  `event_id` varchar(255) NOT NULL,
  `iiepcategory` varchar(255) DEFAULT NULL,
  `base_location` varchar(255) DEFAULT NULL,
  `beneficiary_name` varchar(255) DEFAULT NULL,
  `council_name` varchar(255) DEFAULT NULL,
  `event_date` varchar(255) DEFAULT NULL,
  `event_description` varchar(255) DEFAULT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `lives_impacted` float DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `venue_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_fms_event`
--

LOCK TABLES `cts_or_fms_event` WRITE;
/*!40000 ALTER TABLE `cts_or_fms_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `cts_or_fms_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_fms_event_user_info`
--

DROP TABLE IF EXISTS `cts_or_fms_event_user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_fms_event_user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_unit` varchar(255) DEFAULT NULL,
  `emp_id` varchar(255) DEFAULT NULL,
  `emp_name` varchar(255) DEFAULT NULL,
  `employee_email` varchar(255) DEFAULT NULL,
  `event_status` varchar(255) DEFAULT NULL,
  `event_status_code` int(11) NOT NULL,
  `is_feedback_completed` int(11) NOT NULL,
  `is_feedback_sent` int(11) NOT NULL,
  `travel_hours` double DEFAULT NULL,
  `volunteer_hours` double DEFAULT NULL,
  `event_information_event_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfcejlffeaytnty9pn9krple3s` (`event_information_event_id`),
  CONSTRAINT `FKfcejlffeaytnty9pn9krple3s` FOREIGN KEY (`event_information_event_id`) REFERENCES `cts_or_fms_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_fms_event_user_info`
--

LOCK TABLES `cts_or_fms_event_user_info` WRITE;
/*!40000 ALTER TABLE `cts_or_fms_event_user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `cts_or_fms_event_user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_fms_roles`
--

DROP TABLE IF EXISTS `cts_or_fms_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_fms_roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_fms_roles`
--

LOCK TABLES `cts_or_fms_roles` WRITE;
/*!40000 ALTER TABLE `cts_or_fms_roles` DISABLE KEYS */;
INSERT INTO `cts_or_fms_roles` VALUES (1,'ADMIN'),(2,'PMO'),(3,'POC');
/*!40000 ALTER TABLE `cts_or_fms_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_fms_user`
--

DROP TABLE IF EXISTS `cts_or_fms_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_fms_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conduct_nume` varchar(255) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5vy4co9dhj0y52hdjqi0gcced` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_fms_user`
--

LOCK TABLES `cts_or_fms_user` WRITE;
/*!40000 ALTER TABLE `cts_or_fms_user` DISABLE KEYS */;
INSERT INTO `cts_or_fms_user` VALUES (1,'7358096368','564876','Vinopriya','Kannaiyan','$2a$10$QpFEqWFFwAZLnwjShKT/qeXuJsPFFMBj9EQTSmxrFTC/Yxd9ha.yG');
/*!40000 ALTER TABLE `cts_or_fms_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_fms_users_roles`
--

DROP TABLE IF EXISTS `cts_or_fms_users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_fms_users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKgrt73fn5j373juocu0lb55ayp` (`role_id`),
  CONSTRAINT `FKgrt73fn5j373juocu0lb55ayp` FOREIGN KEY (`role_id`) REFERENCES `cts_or_fms_roles` (`id`),
  CONSTRAINT `FKn8icpl54ovq3l22pbo7tgjs41` FOREIGN KEY (`user_id`) REFERENCES `cts_or_fms_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_fms_users_roles`
--

LOCK TABLES `cts_or_fms_users_roles` WRITE;
/*!40000 ALTER TABLE `cts_or_fms_users_roles` DISABLE KEYS */;
INSERT INTO `cts_or_fms_users_roles` VALUES (1,1);
/*!40000 ALTER TABLE `cts_or_fms_users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cts_or_user_feedback_answer`
--

DROP TABLE IF EXISTS `cts_or_user_feedback_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cts_or_user_feedback_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `choice_answer` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `dislike_answer` varchar(255) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `event_id` varchar(255) DEFAULT NULL,
  `feedback_type` varchar(255) DEFAULT NULL,
  `like_answer` varchar(255) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cts_or_user_feedback_answer`
--

LOCK TABLES `cts_or_user_feedback_answer` WRITE;
/*!40000 ALTER TABLE `cts_or_user_feedback_answer` DISABLE KEYS */;
/*!40000 ALTER TABLE `cts_or_user_feedback_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-13 12:20:37
