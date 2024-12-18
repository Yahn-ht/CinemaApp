package com.example.cinemaapp.data.api;

public class RegisterRequest {
    private String email;
    private String password;
    private String conf_password;
    private String Name;

    public RegisterRequest() {
    }
    public RegisterRequest(String Name, String email,String password, String conf_password) {
        this.email = email;
        this.password = password;
        this.Name = Name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String Name() {
        return Name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        Name = name;
    }
}
