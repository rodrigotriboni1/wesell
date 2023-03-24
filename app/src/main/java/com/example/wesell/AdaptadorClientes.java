package com.example.wesell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.ViewHolder> {

    private List<Cliente> listaClientes;
    private Context contexto;
    private OnItemClickListener listener;

    public void filtrar(String toString) {
    }

    public void atualizarLista(List<Cliente> values) {
    }

    public interface OnItemClickListener {
        void onItemClick(Cliente cliente);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AdaptadorClientes(Context contexto, List<Cliente> listaClientes) {
        this.contexto = contexto;
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.item_cliente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);

        holder.txtNome.setText(cliente.getNome());
        holder.txtEmail.setText(cliente.getEmail());
        holder.txtTelefone.setText(cliente.getTelefone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(cliente);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNome;
        public TextView txtEmail;
        public TextView txtTelefone;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNome = itemView.findViewById(R.id.etNome);
            txtEmail = itemView.findViewById(R.id.etEmail);
            txtTelefone = itemView.findViewById(R.id.etTelefone);
        }
    }
}
