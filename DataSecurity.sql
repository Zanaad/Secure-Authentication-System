create database DataSecurity;
use DataSecurity;
create table users(
    userID integer primary key auto_increment,
    Username nvarchar(40) not null unique,
    HashedPassword nvarchar(64) not null,
    Salt nvarchar(32) not null
);

select * from users;
