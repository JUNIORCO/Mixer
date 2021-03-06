package com.ecse428.project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecse428.project.model.Alcohol;
import com.ecse428.project.model.Cocktail;
import com.ecse428.project.model.Modifier;
import com.ecse428.project.repository.AlcoholRepository;
import com.ecse428.project.repository.CocktailRepository;

import com.ecse428.project.repository.ModifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotNull;

@Service
public class ICocktailService implements CocktailService{

    @Autowired
    private final CocktailRepository cocktailRepository;

    @Autowired
    private final AlcoholRepository alcoholRepository;

    @Autowired
    private final ModifierRepository modifierRepository;

    @Autowired
    public ICocktailService(CocktailRepository cocktailRepository, AlcoholRepository alcoholRepository, ModifierRepository modifierRepository) {
        this.cocktailRepository = cocktailRepository;
        this.alcoholRepository = alcoholRepository;
        this.modifierRepository = modifierRepository;
    }

    @Override
    public List<Cocktail> getCocktail(){
        return cocktailRepository.findAll();
    }

    @Override
    public List<Cocktail> getCocktailByNameContains(String cocktailName) {
        List<Cocktail> cocktails = cocktailRepository.findByNameContaining(cocktailName);

        if (cocktails.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail(s) not found containing key name " + cocktailName + ".");
        }

        return cocktails;
    }

    @Override
    public List<Cocktail> getCocktailByParams(String cName, List<String> alcohols, List<String> modifiers, List<String> tasteTypes,
                                              String strengthType, String servingSize) {

        final List<Alcohol> a_list = new ArrayList<>();
        final List<Modifier> m_list = new ArrayList<>();
        final List<Cocktail.TasteType> t_list = new ArrayList<>();
        final Alcohol a_res;
        final Modifier m_res;
        final Cocktail.TasteType t_res;
        final Cocktail.StrengthType st_found;
        final Cocktail.ServingSize ss_found;

        for (String a : alcohols) {
            Optional<Alcohol> a_found = alcoholRepository.findByName(getCase(a));

            if (a_found.isPresent()) {
                a_list.add(a_found.get());
            } else {
                throw new IllegalArgumentException(a + " is not a valid alcohol.");
            }
        }

        for (String m : modifiers) {
            Optional<Modifier> m_found = modifierRepository.findByName(getCase(m));

            if( m_found.isPresent()) {
                m_list.add(m_found.get());
            } else {
                throw new IllegalArgumentException(m + " is not a valid modifier.");
            }
        }

        for (String t : tasteTypes) {
            Cocktail.TasteType t_found = Cocktail.TasteType.valueOf(t.toUpperCase());

            if (t_found != null) {
                t_list.add(t_found);
            } else {
                throw new IllegalArgumentException(t + " is not a valid taste type.");
            }
        }

        a_res = a_list.isEmpty() ? null : a_list.get(0);
        m_res = m_list.isEmpty() ? null : m_list.get(0);
        t_res = t_list.isEmpty() ? null : t_list.get(0);
        st_found = (strengthType.isEmpty() && strengthType != null) ? null : Cocktail.StrengthType.valueOf(strengthType);
        ss_found = (servingSize.isEmpty() && servingSize != null) ? null : Cocktail.ServingSize.valueOf(servingSize);
        
        List<Cocktail> cocktails = cocktailRepository.findByParams(cName, a_res, m_res, t_res, st_found, ss_found);

        if (cocktails.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cocktail(s) not found with taste preference");
        }

        return cocktails;
    }

    @NotNull
    private String getCase (@NotNull String anyCap) {
        return anyCap.substring(0, 1).toUpperCase() + anyCap.substring(1);
    }
}
