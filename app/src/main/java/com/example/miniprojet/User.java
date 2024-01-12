package com.example.miniprojet;

public class User {
    String id;
    String firstName;
    String lastname;
    String email;
    String password;
    String phone;


    User() {

    }

    User(String firstName, String lastname, String email, String phone) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

     User(String id,String firstName, String lastname, String email, String password, String phone) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.id=id;
    }

    public String getId() {
        return (id);
    }

    void setId(String id)
    {
        this.id=id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }





}
