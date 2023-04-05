package com.example.wesell;

import androidx.recyclerview.widget.RecyclerView;


public class Venda {

    private String vendaId;
    private String clienteId;
    private String valorVenda;
    private String nomeCliente;
    private RecyclerView recyclerView;


    public Venda() {
        // Construtor vazio necessário para o Firebase Realtime Database
    }


    public Venda(String vendaId, String clienteId, String valorVenda, String nomeCliente) {
        this.vendaId = vendaId;
        this.clienteId = clienteId;
        this.valorVenda = valorVenda;
        this.nomeCliente = nomeCliente;
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

    public CharSequence getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

}
