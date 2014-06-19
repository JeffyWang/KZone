CREATE TABLE if not exists  `k_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article` longtext,
  `create_time` datetime DEFAULT NULL,
  `introduction` text,
  `link` varchar(256) DEFAULT '',
  `title` varchar(128) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;