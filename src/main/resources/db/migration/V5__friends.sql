CREATE TABLE friends (
    id bigint not null auto_increment,
    user_id bigint not null references usr,
    friend_id bigint not null references usr,
    primary key (id)
) engine=MyISAM;

