create database coopmo_db;
create user 'coopmo'@'%' identified by 'coopmopass';
grant all on coopmo_db.* to 'coopmo'@'%';
