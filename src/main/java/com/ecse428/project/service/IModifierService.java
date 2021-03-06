package com.ecse428.project.service;

import java.util.List;

import com.ecse428.project.model.Modifier;
import com.ecse428.project.repository.ModifierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IModifierService implements ModifierService {

    @Autowired
    private ModifierRepository modifierRepository;

    @Autowired
    public IModifierService(ModifierRepository modifierRepository) {
        this.modifierRepository = modifierRepository;
    }

    public IModifierService() {
    }

    @Override
    public List<Modifier> getModifiers() {
        return modifierRepository.findAll();
    }
}
