-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: ampto.sj.ifsc.edu.br    Database: pp01camilla
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `Carrinho`
--

DROP TABLE IF EXISTS `Carrinho`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Carrinho` (
  `idCarrinho` int NOT NULL AUTO_INCREMENT,
  `data` datetime NOT NULL,
  `idCliente` int NOT NULL,
  PRIMARY KEY (`idCarrinho`,`idCliente`),
  KEY `fk_Carrinho_Usuario1_idx` (`idCliente`),
  CONSTRAINT `fk_Carrinho_Usuario1` FOREIGN KEY (`idCliente`) REFERENCES `Usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Carrinho`
--

LOCK TABLES `Carrinho` WRITE;
/*!40000 ALTER TABLE `Carrinho` DISABLE KEYS */;
INSERT INTO `Carrinho` VALUES (1,'2021-02-02 00:00:00',2),(8,'2021-03-28 15:19:19',2);
/*!40000 ALTER TABLE `Carrinho` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cat_Produto`
--

DROP TABLE IF EXISTS `Cat_Produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cat_Produto` (
  `idCat_Produto` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(45) NOT NULL,
  PRIMARY KEY (`idCat_Produto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cat_Produto`
--

LOCK TABLES `Cat_Produto` WRITE;
/*!40000 ALTER TABLE `Cat_Produto` DISABLE KEYS */;
INSERT INTO `Cat_Produto` VALUES (1,'serviço'),(2,'produto');
/*!40000 ALTER TABLE `Cat_Produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cat_Usuario`
--

DROP TABLE IF EXISTS `Cat_Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cat_Usuario` (
  `idCat_Usuario` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(45) NOT NULL,
  PRIMARY KEY (`idCat_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cat_Usuario`
--

LOCK TABLES `Cat_Usuario` WRITE;
/*!40000 ALTER TABLE `Cat_Usuario` DISABLE KEYS */;
INSERT INTO `Cat_Usuario` VALUES (1,'vendedor'),(2,'cliente');
/*!40000 ALTER TABLE `Cat_Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Compra`
--

DROP TABLE IF EXISTS `Compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Compra` (
  `idCompra` int NOT NULL AUTO_INCREMENT,
  `Produto_idProduto` int NOT NULL,
  `Produto_idVendedor` int NOT NULL,
  `Carrinho_idCarrinho` int NOT NULL,
  `Carrinho_idCliente` int NOT NULL,
  `unidades` int NOT NULL,
  `entrega` tinyint NOT NULL,
  PRIMARY KEY (`idCompra`,`Produto_idProduto`,`Produto_idVendedor`,`Carrinho_idCarrinho`,`Carrinho_idCliente`),
  KEY `fk_Compra_Produto1_idx` (`Produto_idProduto`,`Produto_idVendedor`),
  KEY `fk_Compra_Carrinho1_idx` (`Carrinho_idCarrinho`,`Carrinho_idCliente`),
  CONSTRAINT `fk_Compra_Carrinho1` FOREIGN KEY (`Carrinho_idCarrinho`, `Carrinho_idCliente`) REFERENCES `Carrinho` (`idCarrinho`, `idCliente`),
  CONSTRAINT `fk_Compra_Produto1` FOREIGN KEY (`Produto_idProduto`, `Produto_idVendedor`) REFERENCES `Produto` (`idProduto`, `idVendedor`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Compra`
--

LOCK TABLES `Compra` WRITE;
/*!40000 ALTER TABLE `Compra` DISABLE KEYS */;
INSERT INTO `Compra` VALUES (1,1,1,1,2,1,1),(2,1,1,8,2,1,0),(3,2,1,8,2,1,0),(4,1,1,8,2,1,0);
/*!40000 ALTER TABLE `Compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Produto`
--

DROP TABLE IF EXISTS `Produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Produto` (
  `idProduto` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `valor` float NOT NULL,
  `Cat_Produto_idCat_Produto` int NOT NULL,
  `idVendedor` int NOT NULL,
  PRIMARY KEY (`idProduto`,`idVendedor`),
  KEY `fk_Produto_Cat_Produto1_idx` (`Cat_Produto_idCat_Produto`),
  KEY `fk_Produto_Usuario1_idx` (`idVendedor`),
  CONSTRAINT `fk_Produto_Cat_Produto1` FOREIGN KEY (`Cat_Produto_idCat_Produto`) REFERENCES `Cat_Produto` (`idCat_Produto`),
  CONSTRAINT `fk_Produto_Usuario1` FOREIGN KEY (`idVendedor`) REFERENCES `Usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Produto`
--

LOCK TABLES `Produto` WRITE;
/*!40000 ALTER TABLE `Produto` DISABLE KEYS */;
INSERT INTO `Produto` VALUES (1,'hamburguer',12.5,2,1),(2,'pizza',20,2,1),(3,'jardinagem (metro quadrado)',5,1,3),(4,'reparos domésticos (simples)',20,1,3),(5,'açaí',10,2,1),(6,'limpeza de piscina',50,1,3);
/*!40000 ALTER TABLE `Produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `Cat_Usuario_idCat_Usuario` int NOT NULL,
  PRIMARY KEY (`idUsuario`),
  KEY `fk_Usuario_Cat_Usuario_idx` (`Cat_Usuario_idCat_Usuario`),
  CONSTRAINT `fk_Usuario_Cat_Usuario` FOREIGN KEY (`Cat_Usuario_idCat_Usuario`) REFERENCES `Cat_Usuario` (`idCat_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
INSERT INTO `Usuario` VALUES (1,'Camilla',1),(2,'Gabriela',2),(3,'Rodrigo',1),(4,'Erick',2);
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'pp01camilla'
--

--
-- Dumping routines for database 'pp01camilla'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-28 20:05:08
