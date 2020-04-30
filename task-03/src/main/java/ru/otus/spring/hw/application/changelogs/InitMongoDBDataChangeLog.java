package ru.otus.spring.hw.application.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.hw.application.model.*;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Lang russian;
    private Lang english;
    private Lang kazakh;
    private Author bronte, dickens, tolstoy, kalashnikov, feinman,
                   hoking, urma, fusco, mycroft, gamma, helm, johnson, vlissides;
    private Genre fiction, prose, poetry, story, biography, fantastic, history,
            science, natural, physics, chemistry, it, programming, java, design;


    @ChangeSet(order = "000", id = "dropDB", author = "abaimussayeva", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initLanguages", author = "abaimussayeva", runAlways = true)
    public void initLanguages(MongoTemplate template) {
        russian = template.save(new Lang("Русский"));
        english = template.save(new Lang("Английский"));
        kazakh = template.save(new Lang("Казахский"));
    }

    @ChangeSet(order = "002", id = "initAuthors", author = "abaimussayeva", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        bronte =  template.save(new Author("Шарлотта Бронте"));
        dickens =  template.save(new Author("Чарльз Диккенс"));
        tolstoy =  template.save(new Author("Лев Толстой"));
        kalashnikov =  template.save(new Author("Иса Калашников"));
        feinman =  template.save(new Author("Ричард Фейнман"));
        hoking =  template.save(new Author("Стивен Хокинг"));
        urma =  template.save(new Author("Raoul-Gabriel Urma"));
        fusco =  template.save(new Author("Mario Fusco"));
        mycroft =  template.save(new Author("Alan Mycroft"));
        gamma =  template.save(new Author("Erich Gamma"));
        helm =  template.save(new Author("Richard Helm"));
        johnson =  template.save(new Author("Ralph Johnson"));
        vlissides =  template.save(new Author("John Vlissides"));
    }

    @ChangeSet(order = "003", id = "initGenres", author = "abaimussayeva", runAlways = true)
    public void initGenres(MongoTemplate template) {
        fiction = template.save(new Genre().setName("Художественная литература"));
        prose = template.save(new Genre().setName("Проза").setParentId(fiction.getGenreId()));
        poetry = template.save(new Genre().setName("Поэзия").setParentId(fiction.getGenreId()));
        story = template.save(new Genre().setName("Повести, рассказы").setParentId(fiction.getGenreId()));
        biography = template.save(new Genre().setName("Биография. Мемуары").setParentId(fiction.getGenreId()));
        fantastic = template.save(new Genre().setName("Фантастика").setParentId(fiction.getGenreId()));
        history = template.save(new Genre().setName("Исторический роман").setParentId(fiction.getGenreId()));
        science = template.save(new Genre().setName("Наука"));
        natural = template.save(new Genre().setName("Естественные науки").setParentId(science.getGenreId()));
        physics = template.save(new Genre().setName("Физика").setParentId(natural.getGenreId()));
        chemistry = template.save(new Genre().setName("Химия").setParentId(natural.getGenreId()));
        it = template.save(new Genre().setName("IT"));
        programming = template.save(new Genre().setName("Языки программирования").setParentId(it.getGenreId()));
        java = template.save(new Genre().setName("Java").setParentId(programming.getGenreId()));
        design = template.save(new Genre().setName("Дизайн программного обеспечения").setParentId(programming.getGenreId()));
    }

    @ChangeSet(order = "004", id = "initBooks", author = "abaimussayeva", runAlways = true)
    public void initBooks(MongoTemplate template) {
        Book jane = template.save(new Book("Джейн Эйр", prose, russian, bronte));
        Comment comment1 = template.save(new Comment(jane.getBookId(), "Интересная книга"));
        Comment comment2 = template.save(new Comment(jane.getBookId(), "Рекомендую к прочтению"));
        Comment comment3 = template.save(new Comment(jane.getBookId(), "Рекомендую к прочтению еще раз"));
        Comment comment4 = template.save(new Comment(jane.getBookId(), "Рекомендую к прочтению еще раз"));
        Comment comment5 = template.save(new Comment(jane.getBookId(), "Рекомендую к прочтению еще раз"));
        Comment comment6 = template.save(new Comment(jane.getBookId(), "Рекомендую к прочтению еще раз"));
        template.save(jane.setComments(comment4, comment3, comment2, comment1));

        Book janeEnglish = template.save(new Book("Jane Eyre", prose, english, bronte));
        Comment comment7 = template.save(new Comment(janeEnglish.getBookId(), "Interesting book"));
        template.save(janeEnglish.setComments(comment7));

        template.save(new Book("Крошка Доррит", prose, russian, dickens));
        template.save(new Book("Воскресение", prose, russian, tolstoy));
        template.save(new Book("Anna Karenina", prose, english, tolstoy));
        template.save(new Book("Жестокий век", history, russian, kalashnikov));
        template.save(new Book("Вы, конечно, шутите, мистер Фейнман", biography, russian, feinman));
        template.save(new Book("Краткая история времени", physics, russian, hoking));
        template.save(new Book("Modern Java in Action: Lambdas, Streams, Functional and Reactive Programming", java, english,
                urma, fusco, mycroft));
        template.save(new Book("Design Patterns: Elements of Reusable Object-Oriented Software 1st Edition", design, english,
                gamma, helm, johnson, vlissides));
    }
}
