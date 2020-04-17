CREATE TABLE post (
    id bigint not null auto_increment,
    user_id bigint not null,
    img varchar(255),
    text varchar(255),
    creation varchar(255),
    likes bigint,
    reposts bigint,
    primary key (id)
) engine=MyISAM;

alter table post
  add constraint user_fk
  foreign key (user_id) references usr (id);