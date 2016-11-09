
   /*
SQLyog Enterprise - MySQL GUI v6.03
Host - 5.0.27-community-nt : Database - videomgr
*********************************************************************
Server version : 5.0.27-community-nt
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `videomgr`;

USE `videomgr`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `sm_region` */

DROP TABLE IF EXISTS `sm_region`;

CREATE TABLE `sm_region` (
  `ID` int(8) NOT NULL auto_increment,
  `REGION_NO` varchar(20) NOT NULL,
  `REGION_NAME` varchar(20) NOT NULL,
  `PARENT_ID` int(8) NOT NULL,
  `REGION_GRADE` int(8) NOT NULL,
  `CREATE_TIME` datetime default NULL,
  `REGION_DESC` varchar(255) default NULL,
  `total` int(8) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `REGION_NO` (`REGION_NO`),
  KEY `FK8235947977FC3717` (`PARENT_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `sm_region` */

insert  into `sm_region`(`ID`,`REGION_NO`,`REGION_NAME`,`PARENT_ID`,`REGION_GRADE`,`CREATE_TIME`,`REGION_DESC`,`total`) values (1,'100','全部',0,0,'2014-03-26 00:00:00',NULL,NULL);

/*Table structure for table `sm_resource` */

DROP TABLE IF EXISTS `sm_resource`;

CREATE TABLE `sm_resource` (
  `ID` int(8) NOT NULL auto_increment,
  `RESOURCE_NO` varchar(20) NOT NULL,
  `RESOURCE_NAME` varchar(20) NOT NULL,
  `RESOURCE_TYPE` char(1) NOT NULL,
  `URL` varchar(100) default NULL,
  `PARENT_ID` int(8) NOT NULL,
  `OPERATION_TYPE` int(1) default NULL,
  `ORDER_NO` int(2) default NULL,
  `CREATE_TIME` datetime default NULL,
  `REMARK` varchar(20) default NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `RESOURCE_NO` (`RESOURCE_NO`),
  KEY `FKDFF3E053BD854B71` (`PARENT_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

/*Data for the table `sm_resource` */

insert  into `sm_resource`(`ID`,`RESOURCE_NO`,`RESOURCE_NAME`,`RESOURCE_TYPE`,`URL`,`PARENT_ID`,`OPERATION_TYPE`,`ORDER_NO`,`CREATE_TIME`,`REMARK`) values (1,'100','root','1','',0,0,NULL,NULL,NULL),(2,'1000','安全管理','1','',1,0,1,NULL,NULL),(3,'1000100','用户管理','1','userList.action',2,0,1,NULL,NULL),(4,'1000200','角色管理','1','roleList.action',2,0,2,NULL,NULL),(5,'1000300','区域管理','1','',2,0,3,NULL,NULL),(6,'1000400','资源管理','1','',2,0,4,NULL,NULL);

/*Table structure for table `sm_role` */

DROP TABLE IF EXISTS `sm_role`;

CREATE TABLE `sm_role` (
  `ID` int(8) NOT NULL auto_increment,
  `ROLE_NO` varchar(20) default NULL,
  `ROLE_NAME` varchar(20) NOT NULL,
  `CREATE_TIME` datetime default NULL,
  `REOLE_DESC` varchar(20) NOT NULL,
  PRIMARY KEY  (`ID`),
  UNIQUE KEY `ROLE_NO` (`ROLE_NO`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sm_role` */

insert  into `sm_role`(`ID`,`ROLE_NO`,`ROLE_NAME`,`CREATE_TIME`,`REOLE_DESC`) values (1,'100','admin','2014-01-24 00:00:00','admin');

/*Table structure for table `sm_role_resource` */

DROP TABLE IF EXISTS `sm_role_resource`;

CREATE TABLE `sm_role_resource` (
  `ID` int(8) NOT NULL auto_increment,
  `ROLE_ID` int(8) default NULL,
  `RESOURCE_ID` int(8) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FKEBC94892E6FA2E0D` (`ROLE_ID`),
  KEY `FKEBC948928BB4258D` (`RESOURCE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `sm_role_resource` */

/*Table structure for table `sm_user` */

DROP TABLE IF EXISTS `sm_user`;

CREATE TABLE `sm_user` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CREATE_TIME` datetime default NULL,
  `LOGIN_NAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `USER_NAME` varchar(50) NOT NULL,
  `USER_NO` varchar(50) default NULL,
  `status` int(2) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sm_user` */

insert  into `sm_user`(`ID`,`CREATE_TIME`,`LOGIN_NAME`,`PASSWORD`,`USER_NAME`,`USER_NO`,`status`) values (1,'2014-03-19 00:00:00','admin','32417939784a494e352f536f454f714362677934784e3d3d','张军',NULL,1);

/*Table structure for table `sm_user_region` */

DROP TABLE IF EXISTS `sm_user_region`;

CREATE TABLE `sm_user_region` (
  `ID` int(8) NOT NULL auto_increment,
  `REGION_ID` int(8) default NULL,
  `USER_ID` int(8) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `FKE4DA1B838C24F1ED` (`USER_ID`),
  KEY `FKE4DA1B83103FC8D` (`REGION_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `sm_user_region` */

insert  into `sm_user_region`(`ID`,`REGION_ID`,`USER_ID`) values (1,1,1);

/*Table structure for table `sm_user_role` */

DROP TABLE IF EXISTS `sm_user_role`;

CREATE TABLE `sm_user_role` (
  `ID` int(8) NOT NULL auto_increment,
  `USER_ID` int(8) NOT NULL,
  `ROLE_ID` int(8) NOT NULL,
  PRIMARY KEY  (`ID`),
  KEY `FKAABA50C5E6FA2E0D` (`ROLE_ID`),
  KEY `FKAABA50C58C24F1ED` (`USER_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `sm_user_role` */

insert  into `sm_user_role`(`ID`,`USER_ID`,`ROLE_ID`) values (1,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
 
   
   
   