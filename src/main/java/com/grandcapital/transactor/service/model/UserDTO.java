package com.grandcapital.transactor.service.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private LocalDate dateOfBirth;

    private List<String> emails = new ArrayList<>();
    private List<String> phones = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

}
