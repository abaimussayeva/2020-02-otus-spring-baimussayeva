package ru.otus.spring.hw.application;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import ru.otus.spring.hw.domain.application.TestStartService;

import java.io.IOException;

@PropertySource("classpath:app.properties")
@ComponentScan
public class SpringBootApp {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("/i18n/bundle");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBootApp.class);
        try (context) {
            TestStartService service = context.getBean(TestStartService.class);
            service.startTest();
        } catch (IOException e) {
            System.err.println("Error was occurred while reading questions");
            return;
        }
        System.out.println("Thank you for attending");
    }
}
