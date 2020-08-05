insert into languages(lang_id, name) values(1, 'Русский');
insert into languages(lang_id, name) values(2, 'Английский');
insert into languages(lang_id, name) values(3, 'Казахский');

INSERT INTO roles(id, name) VALUES(1, 'user');
INSERT INTO roles(id, name) VALUES(2, 'admin');

INSERT INTO users(id, name, login, email, password) VALUES(1, 'Aigul Baimussayeva', 'aigul', 'abaimussayeva@gmail.com', '$2a$10$1PWmmPa7DYvD39RAfKI0beCMMhWEB0nQ9iC1oCtcbZw0f0QzRge72');
INSERT INTO users(id, name, login, email, password) VALUES(2, 'Николай Иванов', 'nick', 'nick@gmail.com', '$2a$10$1PWmmPa7DYvD39RAfKI0beCMMhWEB0nQ9iC1oCtcbZw0f0QzRge72');
INSERT INTO users(id, name, login, email, password) VALUES(3, 'Петр Терентьев', 'petr', 'petr@gmail.com', '$2a$10$1PWmmPa7DYvD39RAfKI0beCMMhWEB0nQ9iC1oCtcbZw0f0QzRge72');
-- пароль 123456
INSERT INTO user_roles(user_id, role_id) VALUES(1, 1);
INSERT INTO user_roles(user_id, role_id) VALUES(1, 2);
INSERT INTO user_roles(user_id, role_id) VALUES(2, 1);
INSERT INTO user_roles(user_id, role_id) VALUES(3, 1);

insert into authors (author_id, name, description) values (1, 'Шарлотта Бронте', 'Шарлотта Бронте (Charlotte Bronte, псевдоним — Каррер Белл) — английская поэтесса и романистка.');
insert into authors (author_id, name, description) values (2, 'Чарльз Диккенс ', '');
insert into authors (author_id, name, description) values (3, 'Лев Толстой', '');
insert into authors (author_id, name, description) values (4, 'Иса Калашников', '');
insert into authors (author_id, name, description) values (5, 'Ричард Фейнман', '');
insert into authors (author_id, name, description) values (6, 'Стивен Хокинг', '');
insert into authors (author_id, name, description) values (7, 'Raoul-Gabriel Urma', '');
insert into authors (author_id, name, description) values (8, 'Mario Fusco', '');
insert into authors (author_id, name, description) values (9, 'Alan Mycroft', '');
insert into authors (author_id, name, description) values (10, 'Erich Gamma', '');
insert into authors (author_id, name, description) values (11, 'Richard Helm', '');
insert into authors (author_id, name, description) values (12, 'Ralph Johnson', '');
insert into authors (author_id, name, description) values (13, 'John Vlissides', '');

insert into genres(genre_id, name, parent_id) values(1, 'Художественная литература', null);
insert into genres(genre_id, name, parent_id) values(2, 'Проза', 1);
insert into genres(genre_id, name, parent_id) values(3, 'Поэзия', 1);
insert into genres(genre_id, name, parent_id) values(4, 'Повести, рассказы', 1);
insert into genres(genre_id, name, parent_id) values(5, 'Биография. Мемурары', 1);
insert into genres(genre_id, name, parent_id) values(10, 'Фантастика', 1);
insert into genres(genre_id, name, parent_id) values(11, 'Исторический роман', 1);

insert into genres(genre_id, name, parent_id) values(6, 'Наука', null);
insert into genres(genre_id, name, parent_id) values(7, 'Естественные науки', 6);
insert into genres(genre_id, name, parent_id) values(8, 'Физика', 7);
insert into genres(genre_id, name, parent_id) values(9, 'Химия', 7);

insert into genres(genre_id, name, parent_id) values(12, 'IT', null);
insert into genres(genre_id, name, parent_id) values(13, 'Языки программирования', 12);
insert into genres(genre_id, name, parent_id) values(14, 'Java', 13);
insert into genres(genre_id, name, parent_id) values(15, 'Дизайн программного обеспечения', 13);

insert into books(book_id, name, genre_id, lang_id) values(1, 'Джейн Эйр', 2, 1);
insert into book_authors(book_id, author_id) values(1, 1);
insert into book_comments(comment_id, book_id, comment) values(1, 1, 'Интересная книга');
insert into book_comments(comment_id, book_id, comment) values(2, 1, 'Рекомендую к прочтению');

insert into books(book_id, name, genre_id, lang_id) values(2, 'Jane Eyre', 2, 2);
insert into book_authors(book_id, author_id) values(2, 1);
insert into book_comments(comment_id, book_id, comment) values(3, 2, 'Interesting book');

insert into books(book_id, name, genre_id, lang_id) values(3, 'Крошка Доррит', 2, 1);
insert into book_authors(book_id, author_id) values(3, 2);

insert into books(book_id, name, genre_id, lang_id) values(4, 'Воскресение', 2, 1);
insert into book_authors(book_id, author_id) values(4, 3);

insert into books(book_id, name, genre_id, lang_id) values(5, 'Anna Karenina', 2, 2);
insert into book_authors(book_id, author_id) values(5, 3);

insert into books(book_id, name, genre_id, lang_id) values(6, 'Жестокий век', 11, 1);
insert into book_authors(book_id, author_id) values(6, 4);

insert into books(book_id, name, genre_id, lang_id) values(7, 'Вы, конечно, шутите, мистер Фейнман', 5, 1);
insert into book_authors(book_id, author_id) values(7, 5);

insert into books(book_id, name, genre_id, lang_id) values(8, 'Краткая история времени', 8, 1);
insert into book_authors(book_id, author_id) values(8, 6);

insert into books(book_id, name, genre_id, lang_id) values(9, 'Modern Java in Action: Lambdas, Streams, Functional and Reactive Programming', 14, 2);
insert into book_authors(book_id, author_id) values(9, 7);
insert into book_authors(book_id, author_id) values(9, 8);
insert into book_authors(book_id, author_id) values(9, 9);

insert into books(book_id, name, genre_id, lang_id) values(10, 'Design Patterns: Elements of Reusable Object-Oriented Software 1st Edition', 15, 2);
insert into book_authors(book_id, author_id) values(10, 10);
insert into book_authors(book_id, author_id) values(10, 11);
insert into book_authors(book_id, author_id) values(10, 12);
insert into book_authors(book_id, author_id) values(10, 13);