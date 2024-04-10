create database DataSecurity;
use DataSecurity;
create table users(
    userID integer primary key auto_increment,
    Username nvarchar(40) NOT NULL UNIQUE,
    HashedPassword nvarchar(64) NOT NULL,
    Salt nvarchar(32) NOT NULL
);

select * from users;

drop table users;