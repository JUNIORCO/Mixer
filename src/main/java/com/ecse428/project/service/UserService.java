package com.ecse428.project.service;

import java.util.Set;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.User;

import org.springframework.http.ResponseEntity;

public interface UserService {
    public Set<Modifier> getModifiersInInventory(long userId);

    public ResponseEntity<String> putModifierInInventory(long userId, String modifierName);

    public Set<Alcohol> getAlcoholInInventory(long userId);

    public ResponseEntity<String> putAlcoholInInventory(long userId, String alcoholName);

    public ResponseEntity<String> postSignup(User user);

    public ResponseEntity<String> deleteAlcoholInInventory(long userId, String alcoholName);

}
