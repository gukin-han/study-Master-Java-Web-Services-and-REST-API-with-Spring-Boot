package com.gukin.rest.webservices.restwebservices.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private final UserDaoService service;

    public UserResource(UserDaoService service) {
        this.service = service;
    }

    // GET /users
    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    // GET /users/{id}
    @GetMapping("/users/{id}")
    public User retrieveUserById(@PathVariable Integer id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        }
        return user;
    }

//    // GET /users/{id}
//    @GetMapping("v2/users/{id}")
//    public EntityModel<User> retrieveUserByIdV2(@PathVariable Integer id) {
//        User user = service.findOne(id);
//        if (user == null) {
//            throw new UserNotFoundException("id: " + id);
//        }
//        return user;
//    }

    // DELETE /users/{id}
    @DeleteMapping("/users/{id}")
    public void DeleteUserById(@PathVariable Integer id) {
        service.deleteUserById(id);
    }

    // POST /users
    // Rest api를 만들때 항상 소비자 관점에서 작성해야한다 -> user를 만드려는 사람
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        // location 헤더를 이용해서 만든다 -> "/users/{id}"
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
