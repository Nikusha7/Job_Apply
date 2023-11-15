CREATE DATABASE IF NOT EXISTS jobapplywebappdb;
USE jobapplywebappdb;
--
-- Table structure for table `applicant`
--

DROP TABLE IF EXISTS `applicant`;
CREATE TABLE `applicant` (
  `applicant_id` int NOT NULL AUTO_INCREMENT,
  `job_category` varchar(150) NOT NULL,
  `applicant_name` varchar(70) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  `previous_company` varchar(100) DEFAULT NULL,
  `previous_position` varchar(80) DEFAULT NULL,
  `email` varchar(75) DEFAULT NULL,
  `phone_number` varchar(12) NOT NULL,
  `submission_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`applicant_id`)
);


--
-- Table structure for table `resume`
--

DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume` (
  `resume_id` int NOT NULL AUTO_INCREMENT,
  `resume_name` varchar(65) DEFAULT NULL,
  `resume_directory_path` varchar(200) NOT NULL,
  `resume_document_extension` varchar(25) DEFAULT NULL,
  `resume_size` double NOT NULL,
  `applicant_id` int DEFAULT NULL,
  PRIMARY KEY (`resume_id`),
  KEY `fk_applicant_id` (`applicant_id`),
  CONSTRAINT `fk_applicant_id` FOREIGN KEY (`applicant_id`) REFERENCES `applicant` (`applicant_id`) ON DELETE CASCADE
);

