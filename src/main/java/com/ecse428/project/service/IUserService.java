package com.ecse428.project.service;

import java.util.Optional;
import java.util.Set;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.model.User;
import com.ecse428.project.repository.AlcoholRepository;
import com.ecse428.project.repository.ModifierRepository;
import com.ecse428.project.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IUserService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModifierRepository modifierRepository;

    @Autowired
    private AlcoholRepository alcoholRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public IUserService(UserRepository userRepository, ModifierRepository modifierRepository,
            AlcoholRepository alcoholRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modifierRepository = modifierRepository;
        this.alcoholRepository = alcoholRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public IUserService() {
	}

	@Override
    public Set<Modifier> getModifiersInInventory(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId + ".");
        }
        return user.get().getModifiersInInventory();
    }

    @Override
    public ResponseEntity<String> putModifierInInventory(long userId, String modifierName) {
        // Find user in database
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId + ".");
        }

        // Find modifier in database
        Optional<Modifier> modifier = modifierRepository.findByName(modifierName);
        if (!modifier.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Modifier not found with name " + modifierName + ".");
        }

        // Let the user know if it's already in their inventory
        if (user.get().getModifiersInInventory().contains(modifier.get())) {
            return ResponseEntity.status(HttpStatus.OK).body("Modifier already in inventory.");
        }

        // Add modifier to user's modifiersInInventory
        user.get().getModifiersInInventory().add(modifier.get());

        // Save user
        userRepository.save(user.get());

        return ResponseEntity.status(HttpStatus.OK).body("Successfully added " + modifierName + ".");
    }

    @Override
    public Set<Alcohol> getAlcoholInInventory(long userId) {
        // Find user
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId + ".");
        }
        // Return alcoholInInventory
        return user.get().getAlcoholInInventory();
    }

    @Override
    public ResponseEntity<String> putAlcoholInInventory(long userId, String alcoholName) {
        // Find user in database
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId + ".");
        }
        // Find alcohol in database
        Optional<Alcohol> alcohol = alcoholRepository.findByName(alcoholName);
        if (!alcohol.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alcohol not found with name " + alcoholName + ".");
        }

        // Let the user know if it's already in their inventory
        if (user.get().getAlcoholInInventory().contains(alcohol.get())) {
            return ResponseEntity.status(HttpStatus.OK).body("Alcohol already in inventory.");
        }

        // Else add alcohol to user's alcoholInInventory
        user.get().getAlcoholInInventory().add(alcohol.get());

        // Save user
        userRepository.save(user.get());

        return ResponseEntity.status(HttpStatus.OK).body("Successfully added " + alcoholName + ".");
    }

    @Override
    public ResponseEntity<String> postSignup(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken.");

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Successful signup.");
    }

    @Override
    public ResponseEntity<String> deleteAlcoholInInventory(long userId, String alcoholName){
        // Find user in database
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId + ".");
        }

        // Find alcohol in database
        Optional<Alcohol> alcohol = alcoholRepository.findByName(alcoholName);
        if (!alcohol.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alcohol not found with name " + alcoholName + ".");
        }
        // Delete alcohol in database
        user.get().getAlcoholInInventory().remove(alcohol.get());

        // Save the user
        userRepository.save(user.get());

        return ResponseEntity.status(HttpStatus.OK).body("Successfully removed " + alcoholName + ".");
    }

}
