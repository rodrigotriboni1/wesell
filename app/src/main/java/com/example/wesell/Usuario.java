package com.example.wesell;

public class Usuario {
    private String nome;
    private String email;
    private String uuid;

    public Usuario(String nome, String email, String uuidString) {
        this.nome = nome;
        this.email = email;
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
