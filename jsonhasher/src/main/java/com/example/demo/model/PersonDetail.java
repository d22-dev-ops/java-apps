package com.example.demo.model;

import java.util.Collections;
import java.util.List;

public class PersonDetail implements Comparable<PersonDetail> {
    private String name;
    private String email;
    private String role;
    private String loginname; // Nullable fields are represented as String and can accept null values
    private String location;
    private String url; // Nullable fields are represented as String and can accept null values

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(PersonDetail other) {
        return this.name.compareToIgnoreCase(other.name); // Sort by name; adjust as necessary
    }

}
