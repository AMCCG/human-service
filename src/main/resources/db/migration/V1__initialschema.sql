CREATE TABLE `profileimage` (
  `id` int NOT NULL,
  `path` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `human` (
  `id` int NOT NULL,
  `idCard` varchar(13) DEFAULT NULL,
  `thaiTitle` varchar(100) DEFAULT NULL,
  `thaiFirstName` varchar(100) DEFAULT NULL,
  `thaiLastName` varchar(100) DEFAULT NULL,
  `englishTitle` varchar(100) DEFAULT NULL,
  `englishFirstName` varchar(100) DEFAULT NULL,
  `englishLastName` varchar(100) DEFAULT NULL,
  `dateOfBirth` varchar(100) DEFAULT NULL,
  `address` longtext,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `profileImageId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `human_idCard_IDX` (`idCard`) USING BTREE,
  KEY `human_thaiFirstName_IDX` (`thaiFirstName`) USING BTREE,
  KEY `human_thaiLastName_IDX` (`thaiLastName`) USING BTREE,
  KEY `human_englishFirstName_IDX` (`englishFirstName`) USING BTREE,
  KEY `human_englishLastName_IDX` (`englishLastName`) USING BTREE,
  KEY `human_FK` (`profileImageId`),
  CONSTRAINT `human_FK` FOREIGN KEY (`profileImageId`) REFERENCES `profileimage` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;