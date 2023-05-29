package com.example.wesell.venda;

import androidx.recyclerview.widget.RecyclerView;


public class Venda {

    private String vendaId;
    private String clienteId;
    private String valorVenda;
    private String nomeProduto;

    private String dataVenda;

    private String quantidadeVenda;

    private RecyclerView recyclerView;


    public Venda() {
        // Construtor vazio necessário para o Firebase Realtime Database
    }


    public Venda(String vendaId, String clienteId, String valorVenda, String nomeProduto, String dataVenda, String quantidadeVenda) {
        this.vendaId = vendaId;
        this.clienteId = clienteId;
        this.valorVenda = valorVenda;
        this.nomeProduto = nomeProduto;
        this.dataVenda = dataVenda;
        this.quantidadeVenda = quantidadeVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getVendaId() {
        return vendaId;
    }

    public void setVendaId(String vendaId) {
        this.vendaId = vendaId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(String valorVenda) {
        this.valorVenda = valorVenda;
    }

    public CharSequence getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public String getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(String quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }


}
