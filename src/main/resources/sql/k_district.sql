CREATE TABLE if not exists  k_district (
  id int(11) NOT NULL AUTO_INCREMENT,
  district varchar(32) NOT NULL DEFAULT '',
  district_code varchar(32) NOT NULL DEFAULT '',
  municipality varchar(32) NOT NULL DEFAULT '',
  municipality_code varchar(8) NOT NULL DEFAULT '',
  province varchar(16) NOT NULL DEFAULT '',
  province_code varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;