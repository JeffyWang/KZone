CREATE TABLE if not exists  k_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime DEFAULT NULL,
  favorite varchar(128) DEFAULT '',
  password varchar(32) NOT NULL DEFAULT '',
  update_time datetime DEFAULT NULL,
  user_name varchar(16) NOT NULL DEFAULT '',
  uuid varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (id),
  UNIQUE KEY (user_name)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;