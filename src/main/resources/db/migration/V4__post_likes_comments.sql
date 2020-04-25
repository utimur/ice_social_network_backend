CREATE TABLE post_likes (
    id bigint not null auto_increment,
    post_id bigint not null,
    user_id bigint not null,
    primary key (id)
) engine=MyISAM;

CREATE TABLE post_comments (
    id bigint not null auto_increment,
    post_id bigint not null,
    user_id bigint not null,
    text varchar(255),
    creation varchar(255),
    primary key (id)
) engine=MyISAM;

CREATE TABLE post_reposts (
    id bigint not null auto_increment,
    post_id bigint not null,
    user_id bigint not null,
    primary key (id)
) engine=MyISAM;


alter table post_likes
  add constraint user_fk
  foreign key (user_id) references usr (id);
alter table post_likes
  add constraint post_fk
  foreign key (post_id) references post (id);



alter table post_comments
  add constraint user_fk
  foreign key (user_id) references usr (id);
alter table post_comments
  add constraint post_fk
  foreign key (post_id) references post (id);



alter table post_reposts
  add constraint user_fk
  foreign key (user_id) references usr (id);
alter table post_reposts
  add constraint post_fk
  foreign key (post_id) references post (id);
