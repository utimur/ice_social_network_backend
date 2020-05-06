create table dialog(
    id bigint not null auto_increment,
    user_id bigint not null references usr,
    friend_id bigint not null references usr,
    last_message varchar(512),
    last_message_date varchar(255),
    dialog_index bigint,
    primary key (id)
) engine=MyISAM;

-- create table party(
--     id bigint not null auto_increment,
--     chat_id bigint not null references dialog,
--     user_id bigint not null references usr,
--     primary key (id)
-- ) engine=MyISAM;

create table message(
    id bigint not null auto_increment,
    dialog_id bigint not null references dialog,
    user_id bigint not null references usr,
    text varchar(512),
    img varchar(255),
    date varchar(30),
    primary key (id)
) engine=MyISAM;