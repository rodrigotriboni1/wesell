package com.example.wesell;

public class Venda {

    private String clienteId;
    private String descricao;
    private String valor;

    public Venda(String valor, String descricao) {}

    public Venda(String clienteId, String descricao, String valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}
