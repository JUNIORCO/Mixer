package com.ecse428.project.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_alcohols", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "alcohol_name"))
    private Set<Alcohol> alcoholInInventory;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_modifiers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "modifier_name"))
    private Set<Modifier> modifiersInInventory;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.alcoholInInventory = new HashSet<Alcohol>();
        this.modifiersInInventory = new HashSet<Modifier>();
    }

    public User(Long id, String email, String password, Set<Alcohol> alcoholInInventory,
            Set<Modifier> modifiersInInventory) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.alcoholInInventory = alcoholInInventory;
        this.modifiersInInventory = modifiersInInventory;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Alcohol> getAlcoholInInventory() {
        return this.alcoholInInventory;
    }

    public void setAlcoholInInventory(Set<Alcohol> alcoholInInventory) {
        this.alcoholInInventory = alcoholInInventory;
    }

    public void addAlcoholToInventory(Alcohol chosenAlcohol) {
        this.alcoholInInventory.add(chosenAlcohol);
    }

    public Set<Modifier> getModifiersInInventory() {
        return this.modifiersInInventory;
    }

    public void setModifiersInInventory(Set<Modifier> modifiersInInventory) {
        this.modifiersInInventory = modifiersInInventory;
    }

    public void addModifierToInventory(Modifier chosenModifier) {
        this.modifiersInInventory.add(chosenModifier);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email)
                && Objects.equals(alcoholInInventory, user.alcoholInInventory)
                && Objects.equals(modifiersInInventory, user.modifiersInInventory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, alcoholInInventory, modifiersInInventory);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", email='" + getEmail() + "'" + ", password='" + getPassword() + "'"
                + ", alcoholInInventory='" + getAlcoholInInventory() + "'" + ", modifiersInInventory='"
                + getModifiersInInventory() + "'" + "}";
    }

}
