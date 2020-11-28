create database accounting_app_store;
use accounting_app_store;

create table Users(
	id int not null auto_increment primary key,
    username varchar(100) unique not null,
    name varchar(100),
    surname varchar(100),
    password varchar(100) not null,
    isSystemAdmin bit default 0
);

create table Categories(
	id int not null auto_increment primary key,
    name varchar(100),
    parentCategory int
);

create table CategoryAdmins(
	categoryId int not null,
    userId int not null,
    foreign key (userId) references Users(id),
    foreign key (categoryId) references Categories(id)
);

create table Records(
	id int not null auto_increment primary key,
	name varchar(100) not null,
    amount double not null,
    creationDate date not null,
    userCreatorId int not null,
    categoryId int not null,
    foreign key (userCreatorId) references Users(id),
    foreign key (categoryId) references Categories(id)
);



