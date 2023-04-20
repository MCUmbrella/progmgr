create table programs (
	id bigint unsigned primary key auto_increment,
	actorCount integer not null,
	name varchar(255) not null,
	typeName varchar(255) not null,
	actorList varchar(255) not null,
	view varchar(255) not null
);
