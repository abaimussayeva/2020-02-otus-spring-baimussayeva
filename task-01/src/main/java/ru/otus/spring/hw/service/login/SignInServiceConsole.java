package ru.otus.spring.hw.service.login;

import ru.otus.spring.hw.domain.Person;

import java.util.Scanner;

public class SignInServiceConsole implements SignInService {
    @Override
    public Person signIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to our test!");
        System.out.println("Enter your name: ");
        String name = scanner.next();
        System.out.println("Enter your last name: ");
        String lastName = scanner.next();
        return new Person(name, lastName);
    }
}
