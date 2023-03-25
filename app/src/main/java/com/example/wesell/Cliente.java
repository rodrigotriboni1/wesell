package com.example.wesell;

public class Cliente {
    public String nome;
    public String email;
    public String telefone;

    public Cliente() {
        // Construtor vazio é necessário para o Firebase.
    }

    public Cliente(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
}
