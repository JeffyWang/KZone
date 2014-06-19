CREATE TABLE if not exists  `k_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province_id` varchar(16) NOT NULL DEFAULT '',
  `province_name` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;