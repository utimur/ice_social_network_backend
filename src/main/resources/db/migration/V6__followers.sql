CREATE TABLE followers (
    id bigint not null auto_increment,
    user_id bigint not null,
    follower_id bigint not null,
    primary key (id)
) engine=MyISAM;

ALTER TABLE followers add constraint user_fk
  foreign key (user_id) references usr (id);