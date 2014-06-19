CREATE TABLE if not exists  `k_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `game` longtext,
  `introduction` text,
  `link` varchar(256) DEFAULT '',
  `name` varchar(128) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8;