package com.application.dtos;

public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String email;

    public RegisterRequest() { }

    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }


}
