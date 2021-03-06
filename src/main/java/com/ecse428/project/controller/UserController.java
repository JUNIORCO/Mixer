package com.ecse428.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.service.UserService;


@RestController
@RequestMapping(path = "api/user/{userId}")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("modifier")
    public Set<Modifier> getModifiersInInventory(@PathVariable int userId) {
        return userService.getModifiersInInventory(userId);
    }

    @PutMapping("modifier/{modifierName}")
    public ResponseEntity<String> putModifierInInventory(@PathVariable int userId, @PathVariable String modifierName) {
        return userService.putModifierInInventory(userId, modifierName);
    }

    @GetMapping("alcohol")
    public Set<Alcohol> getAlcoholInInventory(@PathVariable int userId) {
        return userService.getAlcoholInInventory(userId);
    }

    @PutMapping("alcohol/{alcoholName}")
    public ResponseEntity<String> putAlcoholInInventory(@PathVariable int userId, @PathVariable String alcoholName) {
        return userService.putAlcoholInInventory(userId, alcoholName);
    }

    @DeleteMapping("alcohol/{alcoholName}")
    public ResponseEntity<String> deleteAlcoholInInventory(@PathVariable int userId, @PathVariable String alcoholName){
        return userService.deleteAlcoholInInventory(userId, alcoholName);
    }
    
}
