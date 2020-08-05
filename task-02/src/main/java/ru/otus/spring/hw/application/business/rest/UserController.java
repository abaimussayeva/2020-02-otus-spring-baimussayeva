package ru.otus.spring.hw.application.business.rest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.hw.application.business.security.UserPrincipal;

import java.security.Principal;

@RestController
public class UserController {

    @GetMapping("/api/user")
    public UserPrincipal getCurrentUser(Principal currentUser) {
        return (UserPrincipal) ((UsernamePasswordAuthenticationToken) currentUser).getPrincipal();
    }
}
