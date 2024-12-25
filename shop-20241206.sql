-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: shop
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `shop_cart`
--

DROP TABLE IF EXISTS `shop_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_cart` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cart_goods_number` int NOT NULL DEFAULT '1' COMMENT '商品数量',
  `cart_goods_id` int NOT NULL COMMENT '关联的商品ID',
  `cart_user_id` int DEFAULT NULL COMMENT '关联的用户ID',
  PRIMARY KEY (`id`),
  KEY `shop_cart_cart_user_id_index` (`cart_user_id`),
  KEY `shop_cart_shop_goods_id_fk` (`cart_goods_id`),
  CONSTRAINT `shop_cart_shop_goods_id_fk` FOREIGN KEY (`cart_goods_id`) REFERENCES `shop_goods` (`id`),
  CONSTRAINT `shop_cart_shop_user_id_fk` FOREIGN KEY (`cart_user_id`) REFERENCES `shop_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='购物车';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_cart`
--

LOCK TABLES `shop_cart` WRITE;
/*!40000 ALTER TABLE `shop_cart` DISABLE KEYS */;
INSERT INTO `shop_cart` VALUES (80,1,9,1),(81,1,4,1),(82,1,6,1);
/*!40000 ALTER TABLE `shop_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_goods`
--

DROP TABLE IF EXISTS `shop_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_goods` (
  `id` int NOT NULL AUTO_INCREMENT,
  `goods_name` varchar(255) NOT NULL COMMENT '商品名称',
  `goods_brand` varchar(100) NOT NULL COMMENT '货物品牌',
  `goods_sales` int NOT NULL DEFAULT '0' COMMENT '商品销量',
  `goods_cost` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `goods_color` varchar(50) DEFAULT NULL COMMENT '商品颜色',
  `goods_image` varchar(255) DEFAULT NULL COMMENT '商品头图',
  `goods_image_detail` varchar(50) DEFAULT NULL COMMENT '商品详情图片',
  `goods_state` int NOT NULL DEFAULT '1' COMMENT '商品上架状态（0-下架，1-上架）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_goods`
--

LOCK TABLES `shop_goods` WRITE;
/*!40000 ALTER TABLE `shop_goods` DISABLE KEYS */;
INSERT INTO `shop_goods` VALUES (4,'AirPods','Apple',0,2999,'白色','file/55125412-3588-41a8-ac04-ab23a7203cf2.jpg','file/3bf5a4cc-0e97-447f-9d2d-d8de5d332829.jpg',1),(5,'BeatsX_1','Beats',2,1699,'白色','file/2ef6559c-da15-437e-98d8-cc579c41d827.jpg','file/2c07fecb-71da-4299-b9a6-ba1684c18ef1.jpg',1),(6,'BeatsX_2','Beats',0,1999,'蓝色','file/fec5a971-06b2-4e05-9213-5de5cbd33fcd.jpg','file/d588cedd-0dc4-457a-8169-f1bac099a700.jpg',1),(7,'Sonos耳机','Sonos',0,1899,'金色','file/ce6f3235-1d5f-4456-9c7f-2feb69896cb2.jpg','file/08b9a9ba-7ae4-4a0e-85fd-857dca9800e3.jpg',1),(8,'B&O蓝牙耳机','B&O',0,1499,'红色','file/272c450f-3bd3-420e-8af1-fb9174b264c6.jpg','file/136e00ed-5aac-4b5b-83e9-13374469b40f.jpg',1),(9,'B&O品牌耳机','B&O',0,1799,'蓝色','file/be6c0809-9d50-48ac-84aa-8a3a251a81f7.jpg','file/12b2ee83-57b9-47d0-8cd7-eaa883eb5d3c.jpg',1);
/*!40000 ALTER TABLE `shop_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_order`
--

DROP TABLE IF EXISTS `shop_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_cost` double NOT NULL COMMENT '商品总价',
  `order_user_id` int NOT NULL COMMENT '关联的用户ID',
  `order_create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `oder_state` int NOT NULL DEFAULT '0' COMMENT '订单状态（0-未支付，1-已支付）',
  PRIMARY KEY (`id`),
  KEY `shop_order_shop_user_id_fk` (`order_user_id`),
  CONSTRAINT `shop_order_shop_user_id_fk` FOREIGN KEY (`order_user_id`) REFERENCES `shop_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商城订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_order`
--

LOCK TABLES `shop_order` WRITE;
/*!40000 ALTER TABLE `shop_order` DISABLE KEYS */;
INSERT INTO `shop_order` VALUES (1,2999,1,'2024-07-30 23:01:14',1),(2,19991,3,'2024-07-30 23:02:42',1),(3,30685,3,'2024-07-30 23:05:28',1),(4,4698,1,'2024-07-31 00:14:33',1),(5,5397,1,'2024-07-31 00:25:01',1),(6,5697,1,'2024-07-31 01:08:34',1),(7,3398,1,'2024-07-31 01:43:49',1);
/*!40000 ALTER TABLE `shop_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_order_detail`
--

DROP TABLE IF EXISTS `shop_order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL COMMENT '关联的订单ID',
  `goods_id` int NOT NULL COMMENT '关联的商品ID',
  `goods_number` int DEFAULT NULL COMMENT '货物数量',
  PRIMARY KEY (`id`),
  KEY `shop_order_detail_shop_goods_id_fk` (`goods_id`),
  KEY `shop_order_detail_order_id_index` (`order_id`),
  CONSTRAINT `shop_order_detail_shop_goods_id_fk` FOREIGN KEY (`goods_id`) REFERENCES `shop_goods` (`id`),
  CONSTRAINT `shop_order_detail_shop_order_id_fk` FOREIGN KEY (`order_id`) REFERENCES `shop_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单详情';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_order_detail`
--

LOCK TABLES `shop_order_detail` WRITE;
/*!40000 ALTER TABLE `shop_order_detail` DISABLE KEYS */;
INSERT INTO `shop_order_detail` VALUES (1,1,4,NULL),(2,2,5,NULL),(3,2,4,NULL),(4,2,6,NULL),(5,2,7,NULL),(6,2,8,NULL),(7,3,5,NULL),(8,3,4,NULL),(9,3,6,NULL),(10,3,7,NULL),(11,3,8,NULL),(12,3,9,NULL),(13,4,4,NULL),(14,4,5,NULL),(15,5,5,NULL),(16,5,6,NULL),(17,6,6,2),(18,6,5,1),(19,7,5,2);
/*!40000 ALTER TABLE `shop_order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shop_user`
--

DROP TABLE IF EXISTS `shop_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL COMMENT '登录账号',
  `user_password` varchar(50) NOT NULL COMMENT '登录密码',
  `user_nickname` varchar(20) DEFAULT '系统用户' COMMENT '用户昵称',
  `user_state` int DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shop_user`
--

LOCK TABLES `shop_user` WRITE;
/*!40000 ALTER TABLE `shop_user` DISABLE KEYS */;
INSERT INTO `shop_user` VALUES (1,'pqr','123456','Keven',1),(3,'cjw','1234','系统用户',1);
/*!40000 ALTER TABLE `shop_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-06 14:54:07
