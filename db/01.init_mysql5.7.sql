CREATE DATABASE if not exists `airm` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

use mysql;

CREATE USER 'airm'@'localhost' IDENTIFIED BY 'airm' PASSWORD EXPIRE NEVER;

UPDATE user SET authentication_string= password ('wxmp68') WHERE user='root';

flush privileges;

grant all privileges on *.* to 'root'@'%' identified by '';
grant all privileges on *.* to 'root'@'localhost' identified by 'wxmp68';
grant all privileges on *.* to 'root'@'127.0.0.1' identified by 'wxmp68';

grant all privileges on airm.* to 'airm'@'%' identified by 'airm';
grant all privileges on airm.* to 'airm'@'localhost' identified by 'airm';
grant all privileges on airm.* to 'airm'@'127.0.0.1' identified by 'airm';

flush privileges;