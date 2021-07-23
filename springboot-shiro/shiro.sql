/*
SQLyog Ultimate v9.62 
MySQL - 5.7.34 : Database - zmgab
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zmgab` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zmgab`;

/*Table structure for table `shiro_perms` */

DROP TABLE IF EXISTS `shiro_perms`;

CREATE TABLE `shiro_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `shiro_perms` */

insert  into `shiro_perms`(`id`,`name`,`url`) values (1,'user:*',NULL),(2,'product:*:01',NULL),(3,'order:*:01',NULL);

/*Table structure for table `shiro_role` */

DROP TABLE IF EXISTS `shiro_role`;

CREATE TABLE `shiro_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `shiro_role` */

insert  into `shiro_role`(`id`,`name`) values (1,'admin'),(2,'user'),(3,'product');

/*Table structure for table `shiro_role_perms` */

DROP TABLE IF EXISTS `shiro_role_perms`;

CREATE TABLE `shiro_role_perms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleid` bigint(20) DEFAULT NULL,
  `permsid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `shiro_role_perms` */

insert  into `shiro_role_perms`(`id`,`roleid`,`permsid`) values (1,1,1),(2,1,2),(3,2,1),(4,3,2),(5,1,3);

/*Table structure for table `shiro_user_role` */

DROP TABLE IF EXISTS `shiro_user_role`;

CREATE TABLE `shiro_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) DEFAULT NULL,
  `roleid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `shiro_user_role` */

insert  into `shiro_user_role`(`id`,`userid`,`roleid`) values (1,1,1),(2,2,2),(3,2,3);

/*Table structure for table `shrio_user` */

DROP TABLE IF EXISTS `shrio_user`;

CREATE TABLE `shrio_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `password` varchar(40) DEFAULT NULL,
  `username` varchar(40) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `shrio_user` */

insert  into `shrio_user`(`id`,`password`,`username`,`salt`) values (1,'23a31505c7efd1409a23945f692f5563','zmg','962YZcxb'),(2,'13fbe091e1cdcb9407e93af9ff131c31','by','&FkV&BXW');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
