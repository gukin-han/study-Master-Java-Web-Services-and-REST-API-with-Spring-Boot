package com.gukin.rest.webservices.restwebservices.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {
    // JPA/Hibernate > DB
    // UserDaoService > Static List

    // 일단은 정적 리스트를 이용해서 리파지토리를 구현한
    private static List<User> users = new ArrayList<>();
    private static Integer nextSequence = 0;

    static {
        users.add(new User(nextSequence++, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(nextSequence++, "Eve", LocalDate.now().minusYears(25)));
        users.add(new User(nextSequence++, "Jim", LocalDate.now().minusYears(20)));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public User save(User user) {
        user.setId(nextSequence++);
        users.add(user);
        return user;
    }

    public void deleteUserById(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        users.removeIf(predicate);
    }
}
