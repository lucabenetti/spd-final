CREATE DATABASE `vacinacao` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- vacinacao.Agendas definition

CREATE TABLE `Agendas` (
  `id` int NOT NULL,
  `data` date DEFAULT NULL,
  `situacao` char(10) DEFAULT NULL,
  `data_situacao` date DEFAULT NULL,
  `observacoes` varchar(200) DEFAULT NULL,
  `usuarioid` int NOT NULL,
  `vacinaid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Agendas_FK_1` (`usuarioid`),
  KEY `Agendas_FK` (`vacinaid`),
  CONSTRAINT `Agendas_FK` FOREIGN KEY (`vacinaid`) REFERENCES `Vacinas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Agendas_FK_1` FOREIGN KEY (`usuarioid`) REFERENCES `Usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- vacinacao.Alergias definition

CREATE TABLE `Alergias` (
  `id` int NOT NULL,
  `nome` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- vacinacao.Usuarios definition

CREATE TABLE `Usuarios` (
  `id` int NOT NULL,
  `nome` varchar(60) DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `sexo` char(1) DEFAULT NULL,
  `logradouro` varchar(60) DEFAULT NULL,
  `numero` int DEFAULT NULL,
  `setor` varchar(40) DEFAULT NULL,
  `cidade` varchar(40) DEFAULT NULL,
  `uf` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- vacinacao.UsuariosAlergias definition

CREATE TABLE `UsuariosAlergias` (
  `usuarioid` int NOT NULL,
  `alergiaid` int DEFAULT NULL,
  KEY `UsuariosAlergias_FK` (`usuarioid`),
  KEY `UsuariosAlergias_FK_1` (`alergiaid`),
  CONSTRAINT `UsuariosAlergias_FK` FOREIGN KEY (`usuarioid`) REFERENCES `Usuarios` (`id`),
  CONSTRAINT `UsuariosAlergias_FK_1` FOREIGN KEY (`alergiaid`) REFERENCES `Alergias` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- vacinacao.Vacinas definition

CREATE TABLE `Vacinas` (
  `id` int NOT NULL,
  `titulo` varchar(60) DEFAULT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `doses` int DEFAULT NULL,
  `periodicidade` int DEFAULT NULL,
  `intervalo` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;