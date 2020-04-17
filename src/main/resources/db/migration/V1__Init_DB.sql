create table hibernate_sequence (
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );

create table message (
    id bigint not null auto_increment,
    author_id bigint,
    text varchar(255) not null,
    primary key (id)
) engine=MyISAM;

create table usr (
    id bigint not null auto_increment,
    password varchar(255) not null,
    username varchar(255) not null,
    avatar varchar(255) not null,
    primary key (id)
) engine=MyISAM;

alter table message
  add constraint message_user_fk
  foreign key (author_id) references usr (id);