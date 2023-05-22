package com.example.wesell.produtos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Produto {

    private String nome;
    private double preco;

    public Produto(){

    }

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void salvarProdutoNoFirebase() {
        FirebaseUser user = obterUsuarioAtual();
        String userId = user.getUid();
        DatabaseReference ref = obterReferenciaProduto(userId);
        ref.push().setValue(this);
    }

    private FirebaseUser obterUsuarioAtual() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private DatabaseReference obterReferenciaProduto(String userId) {
        return FirebaseDatabase.getInstance().getReference("produtos").child(userId);
    }
}
