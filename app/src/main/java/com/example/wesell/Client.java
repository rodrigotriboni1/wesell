package com.example.wesell;

public class Client {

    private String id;
    private String name;

    private String phone;
    private String email;

    public Client() {
        // construtor sem argumentos necessário para o Firebase
    }

    public Client(String clientId, String name, String email, String phone) {
        // Construtor padrão necessário para o Firebase
    }

    public Client(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
