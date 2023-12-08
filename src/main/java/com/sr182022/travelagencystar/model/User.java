package com.sr182022.travelagencystar.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String surname;
    private String name;
    private LocalDate birthDate;
    private String address;
    private int phone;
    private LocalDateTime registeredDate;
    private Role role;
    private LoyaltyCard loyaltyCard = null;

    public User() {}

    public User(int id, String username, String password, String email, String surname, String name, LocalDate birthDate, String address, int phone,
                 LocalDateTime registeredDate, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
        this.registeredDate = registeredDate;
        this.role = role;
    }

    public User(LoyaltyCard loyaltyCard) { this.loyaltyCard = loyaltyCard; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public LocalDateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(LocalDateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LoyaltyCard getLoyaltyCardId() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard loyaltyCardId) {
        this.loyaltyCard = loyaltyCard;
    }

}