package com.example.diabetesmanagement;

public class Profile {
    public String username;
    public String email;
    public String password;
    public String date;

    public Profile(){

    }

    public Profile(String username, String email, String password, String date) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
