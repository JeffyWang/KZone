CREATE TABLE if not exists  k_ktv (
  id int(11) NOT NULL AUTO_INCREMENT,
  address varchar(256) DEFAULT '',
  average_price int(11) DEFAULT '0',
  create_time datetime DEFAULT NULL,
  district_id varchar(128) DEFAULT '0',
  geographic_information varchar(32) DEFAULT '',
  introduction varchar(10240) DEFAULT '',
  name varchar(128) NOT NULL DEFAULT '',
  phone_number varchar(16) DEFAULT '',
  pictures varchar(4096) DEFAULT '',
  score float DEFAULT '0',
  update_time datetime DEFAULT NULL,
  price varchar(32) DEFAULT '0.0',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8;