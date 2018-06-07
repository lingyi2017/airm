CREATE DATABASE if not exists airm DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use mysql;
insert into mysql.user (Host,User,Password,ssl_cipher,x509_issuer,x509_subject) values("%","airm",password("airm"),'','','');
flush privileges;

grant all privileges on *.* to 'root'@'%' identified by '';
grant all privileges on *.* to 'root'@'localhost' identified by 'wxmp68';
grant all privileges on *.* to 'root'@'127.0.0.1' identified by 'wxmp68';

grant all privileges on airm.* to 'airm'@'%' identified by 'airm';
grant all privileges on airm.* to 'airm'@'localhost' identified by 'airm';
grant all privileges on airm.* to 'airm'@'127.0.0.1' identified by 'airm';


flush privileges;
