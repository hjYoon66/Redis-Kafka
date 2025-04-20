package org.example.springbootredis.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.springbootredis.model.User;
import org.example.springbootredis.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {
    private final CacheService cacheService;

    @Autowired
    public UserController(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return cacheService.getCachedData("user:" + id, User.class)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        cacheService.cacheData("user:" + user.getId(), user, 3600);
        return ResponseEntity.ok(user);
    }
}
