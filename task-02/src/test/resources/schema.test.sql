drop table if exists authors;
drop table if exists genres;
drop table if exists books;
drop table if exists book_authors;
drop table if exists languages;

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
    primary key(book_id, author_id)
);

create table languages(
    lang_id bigint not null,
    name varchar(64) not null,
    primary key(lang_id)
);



