CREATE TABLE if not exists  `k_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT '0',
  `app_name` varchar(128) NOT NULL DEFAULT '',
  `app_version` varchar(128) NOT NULL DEFAULT '',
  `os_type` varchar(128) NOT NULL DEFAULT '',
  `os_version` varchar(128) NOT NULL DEFAULT '',
  `token` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;