CREATE TABLE if not exists  k_comment (
  id int(11) NOT NULL AUTO_INCREMENT,
  ktv_id int(11) DEFAULT '0',
  comment varchar(20480) DEFAULT '',
  create_time datetime DEFAULT NULL,
  environment_score float DEFAULT '0',
  service_score float DEFAULT '0',
  sound_effects_score float DEFAULT '0',
  user_id int(11) DEFAULT '0',
  score float DEFAULT '0',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;