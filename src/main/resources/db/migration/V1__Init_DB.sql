create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );


create table usr (
    id bigint not null auto_increment,
    password varchar(255) not null,
    username varchar(255) not null,
    name varchar(255) not null,
    surname varchar(255) not null,
    email varchar(255),
    avatar varchar(255),
    status varchar(512),
    online varchar(255),
    primary key (id)
) engine=MyISAM;