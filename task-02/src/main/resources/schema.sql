drop table if exists authors;
drop table if exists genres;
drop table if exists books;
drop table if exists book_authors;
drop table if exists languages;


create table roles(
    id bigint not null auto_increment,
    name varchar(255),
    primary key(id)
);

create table users(
    id bigint not null auto_increment,
    name varchar(255),
    login varchar(255),
    email varchar(255),
    password varchar(255),
    created timestamp not null default current_timestamp,
    modified timestamp not null default current_timestamp,
    primary key(id)
);

create table user_roles(
    user_id bigint,
    role_id bigint,
    primary key(user_id, role_id),
    foreign key(user_id) references users(id) on delete cascade,
    foreign key(role_id) references roles(id) on delete cascade
);

create table authors(
    author_id bigint not null auto_increment,
    name varchar(255),
    description text,
    primary key(author_id)
);

create table genres(
    genre_id bigint not null auto_increment,
    name varchar(255) not null,
    parent_id bigint null,
    primary key(genre_id)
);
create index genres_parent_idx on genres(parent_id);

create table books(
    book_id bigint not null auto_increment,
    name varchar(255) not null,
    genre_id bigint not null,
    lang_id bigint not null,
    primary key(book_id)
);
create index books_name_idx on books(name);

create table book_authors(
    book_id bigint not null,
    author_id bigint not null,
    primary key(book_id, author_id),
    foreign key(book_id) references books(book_id) on delete cascade,
    foreign key(author_id) references authors(author_id) on delete cascade
);

create table book_comments(
    comment_id bigint not null auto_increment,
    book_id bigint not null,
    comment varchar(255) not null,
    created timestamp not null default current_timestamp,
    primary key(comment_id),
    foreign key(book_id) references books(book_id) on delete cascade
);

create table languages(
    lang_id bigint not null,
    name varchar(64) not null,
    primary key(lang_id)
);