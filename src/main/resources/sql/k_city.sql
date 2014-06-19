CREATE TABLE if not exists  k_city (
  id int(11) NOT NULL AUTO_INCREMENT,
  city_id varchar(16) NOT NULL DEFAULT '',
  city_name varchar(16) NOT NULL DEFAULT '',
  reference varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8;