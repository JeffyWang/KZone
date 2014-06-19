CREATE TABLE if not exists k_area (
  id int(11) NOT NULL AUTO_INCREMENT,
  area_id varchar(16) NOT NULL DEFAULT '',
  area_name varchar(16) NOT NULL DEFAULT '',
  reference varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;