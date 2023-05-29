package com.example.wesell.produtos;

public class Produto {
    private String produtoId;
    private String nome;
    private double preco;

    public Produto() {
    }

    public Produto(String produtoId, String nome, double preco) {
        this.produtoId = produtoId;
        this.nome = nome;
        this.preco = preco;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
