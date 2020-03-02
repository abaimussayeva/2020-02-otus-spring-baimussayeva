package ru.otus.spring.hw;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.hw.service.TestStartService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestStartService service = context.getBean(TestStartService.class);
        try {
            service.startTest();
        } catch (IOException e) {
            System.err.println("Error was occurred while reading questions");
            return;
        } finally {
            context.close();
        }
        System.out.println("Thank you for attending");

    }
}
