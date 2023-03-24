package com.example.wesell;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class FiltroClientes extends Filter {

    private final AdaptadorClientes adaptadorClientes;
    private final List<Cliente> clientes;
    private final List<Cliente> clientesFiltrados;

    public FiltroClientes(AdaptadorClientes adaptadorClientes, List<Cliente> clientes) {
        this.adaptadorClientes = adaptadorClientes;
        this.clientes = clientes;
        clientesFiltrados = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        clientesFiltrados.clear();
        final FilterResults resultados = new FilterResults();

        if (constraint.length() == 0) {
            clientesFiltrados.addAll(clientes);
        } else {
            final String filtroPadrao = constraint.toString().toLowerCase().trim();
            for (final Cliente cliente : clientes) {
                if (cliente.getNome().toLowerCase().contains(filtroPadrao)) {
                    clientesFiltrados.add(cliente);
                }
            }
        }

        resultados.values = clientesFiltrados;
        resultados.count = clientesFiltrados.size();

        return resultados;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adaptadorClientes.atualizarLista((List<Cliente>) results.values);
    }
}

