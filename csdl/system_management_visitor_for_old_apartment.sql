create database system_management_visitor_for_old_apartment;
create table visitor (
id int,
name varchar(255),
age int,
cmnd int,
timeVisit varchar(255),
staffName varchar(255),
owner_dept_id int/*foreign key*/,
primary key(id),
foreign key (owner_dept_id) references owner_department(id)
);
create table owner_department(
id int,
name varchar(255),
age int,
phoneOrZalo  int,
block varchar(255),/*a-z*/
numRoom varchar(255),
primary key(id)
);
create table security_guard(
id int,
name varchar(255),
check_visitor_info boolean,/*true false*/
primary key(id)
);
create table deparment(
id int,
owner_id int,/*foreign key*/
block varchar(255),/*a-z*/
numRoom varchar(255),
numOfVist int,/*number of times when visitors visit this apartment*/
primary key(id),
foreign key(owner_id) references owner_department(id)
);
