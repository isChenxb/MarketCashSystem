# Host: localhost  (Version: 5.6.27)
# Date: 2015-12-23 20:44:20
# Generator: MySQL-Front 5.3  (Build 4.214)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "tproduct"
#

DROP TABLE IF EXISTS `tproduct`;
CREATE TABLE `tproduct` (
  `product_no` varchar(6) NOT NULL DEFAULT '',
  `product_name` varchar(255) NOT NULL DEFAULT '',
  `product_price` float(8,2) unsigned NOT NULL DEFAULT '0.00',
  `product_from` varchar(255) DEFAULT '',
  PRIMARY KEY (`product_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

#
# Data for table "tproduct"
#

INSERT INTO `tproduct` VALUES ('000001','笔记本',2.00,''),('000002','苹果',3.00,''),('000003','卫生纸',10.00,'维达'),('000004','方便面',3.00,'统一'),('000005','台灯',30.00,'灯具厂'),('000006','牛奶',5.00,'蒙牛'),('000007','草稿纸',1.00,'武汉纺织大学'),('000008','电风扇',35.00,'批发商'),('000009','牙膏',5.00,'佳洁士'),('000010','速溶咖啡',11.00,'雀巢'),('000011','手机',2000.00,'华为'),('000012','平板电脑',4000.00,'微软'),('000013','可乐',3.00,'可口可乐'),('000014','耳机',112.00,'索尼'),('000015','瓜子',5.00,'恰恰'),('000016','剃须刀',15.00,'吉列'),('000017','巧克力',15.00,'德芙'),('444555','笔记本电脑',4500.00,'电脑城');

#
# Structure for table "tsale_detail"
#

DROP TABLE IF EXISTS `tsale_detail`;
CREATE TABLE `tsale_detail` (
  `sale_no` varchar(12) NOT NULL DEFAULT '',
  `producet_no` varchar(255) NOT NULL DEFAULT '',
  `product_name` varchar(255) NOT NULL DEFAULT '',
  `product_price` float(8,2) unsigned NOT NULL DEFAULT '0.00',
  `sale_sum` int(11) NOT NULL DEFAULT '0',
  `sale_men` varchar(255) NOT NULL DEFAULT '',
  `sale_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`sale_no`),
  KEY `produce_no` (`producet_no`),
  CONSTRAINT `tsale_detail_ibfk_1` FOREIGN KEY (`producet_no`) REFERENCES `tproduct` (`product_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售明细表';

#
# Data for table "tsale_detail"
#

INSERT INTO `tsale_detail` VALUES ('201511280002','444555','笔记本电脑',4500.00,5,'陈序彪','2015-11-28 17:14:30'),('201511280003','000001','笔记本',2.00,2,'陈序彪','2015-11-28 17:27:21'),('201511280004','000002','苹果',3.00,3,'陈序彪','2015-11-28 17:27:29'),('201511280005','000001','笔记本',2.00,2,'陈序彪','2015-11-28 17:41:37'),('201511280006','000001','笔记本',2.00,5,'陈序彪','2015-11-28 18:27:25'),('201511300001','000001','笔记本',2.00,2,'陈序彪','2015-11-30 15:04:58'),('201511300002','000002','苹果',3.00,3,'陈序彪','2015-11-30 21:11:31'),('201512020001','000006','牛奶',5.00,3,'陈序彪','2015-12-02 14:33:59'),('201512020002','000006','牛奶',5.00,2,'陈序彪','2015-12-02 15:02:30'),('201512020003','000001','笔记本',2.00,3,'陈序彪','2015-12-02 15:02:42'),('201512030001','000003','卫生纸',10.00,5,'陈序彪','2015-12-03 15:22:46'),('201512110001','000009','牙膏',5.00,5,'陈序彪','2015-12-11 16:12:37'),('201512110002','000006','牛奶',5.00,0,'陈序彪','2015-12-11 16:35:06'),('201512160001','000006','牛奶',5.00,2,'陈序彪','2015-12-16 16:09:00'),('201512180001','000006','牛奶',5.00,3,'陈序彪','2015-12-18 13:43:45'),('201512230001','444555','笔记本电脑',4500.00,3,'陈序彪','2015-12-23 20:34:58');

#
# Structure for table "tuser"
#

DROP TABLE IF EXISTS `tuser`;
CREATE TABLE `tuser` (
  `user_account` varchar(20) NOT NULL DEFAULT '',
  `user_password` varchar(255) NOT NULL DEFAULT '',
  `user_chrname` varchar(20) NOT NULL DEFAULT '',
  `user_role` varchar(255) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Data for table "tuser"
#

INSERT INTO `tuser` VALUES ('admin','21232f297a57a5a743894a0e4a801fc3','陈序彪','管理员'),('user1','24c9e15e52afc47c225b757e7bee1f9d','小王','普通用户');
