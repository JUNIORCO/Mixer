package com.ecse428.project.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cocktail")
public class Cocktail {
    public enum TasteType {
        SWEET, SOUR, BITTER, SPICY, MINTY, FRUITY
    }

    public enum StrengthType {
        WEAK, MEDIUM, STRONG
    }

    public enum ServingSize {
        INDIVIDUAL, GROUP
    }

    @ElementCollection
    @Enumerated
    private Set<TasteType> tasteTypes;

    private ServingSize servingSize;

    private StrengthType strengthType;

    @Id
    @Column(name = "cocktail_name")
    private String name;

    @ManyToMany
    private List<Modifier> modifiers;

    @ManyToMany
    private List<Alcohol> alcohols;

    public Cocktail(){ }

    public void addAlcohol(Alcohol alcohol) { alcohols.add(alcohol); }

    public void addModifier(Modifier modifier) { modifiers.add(modifier); }

    public void setAlcohols(List<Alcohol> alcohols) {
        this.alcohols = alcohols;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModifiers(List<Modifier> modifiers) { this.modifiers = modifiers; }

    public String getName() {
        return name;
    }

    public List<Alcohol> getAlcohols() {
        return alcohols;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public ServingSize getServingSize() {
        return servingSize;
    }

    public void setServingSize(ServingSize servingSize) {
        this.servingSize = servingSize;
    }

    public StrengthType getStrengthType() {
        return strengthType;
    }

    public void setStrengthType(StrengthType strengthType) {
        this.strengthType = strengthType;
    }

    public Set<TasteType> getTasteTypes() {
        return tasteTypes;
    }

    public void setTasteTypes(Set<TasteType> tasteTypes) {
        this.tasteTypes = tasteTypes;
    }

    public Cocktail(String name) {
        this.name = name;
    }

    public Cocktail(String name, List<Modifier> modifiers) {
        this.name = name;
        this.modifiers = modifiers;
    }

    public Cocktail(String name, List<Alcohol> alcohols, List<Modifier> modifiers) {
        this.name = name;
        this.modifiers = modifiers;
        this.alcohols = alcohols;
    }

    public Cocktail(String name, List<Modifier> modifiers, List<Alcohol> alcohols, Set<TasteType> tasteTypes, StrengthType strengthTypes, ServingSize servingSize) {
        this.name = name;
        this.modifiers = modifiers;
        this.alcohols = alcohols;
        this.tasteTypes = tasteTypes;
        this.strengthType = strengthTypes;
        this.servingSize = servingSize;
    }
}
