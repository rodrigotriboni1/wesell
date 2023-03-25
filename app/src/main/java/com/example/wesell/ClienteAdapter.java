package com.example.wesell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private List<Cliente> mClientes;

    public ClienteAdapter(List<Cliente> clientes) {
        mClientes = clientes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View clienteView = inflater.inflate(R.layout.item_cliente, parent, false);

        ViewHolder viewHolder = new ViewHolder(clienteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cliente cliente = mClientes.get(position);

        TextView nomeTextView = holder.nomeTextView;
        nomeTextView.setText(cliente.nome);

        TextView emailTextView = holder.emailTextView;
        emailTextView.setText(cliente.email);

        TextView telefoneTextView = holder.telefoneTextView;
        telefoneTextView.setText(cliente.telefone);
    }

    @Override
    public int getItemCount() {
        return mClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView emailTextView;
        public TextView telefoneTextView;
        public ViewHolder(View itemView) {
            super(itemView);

            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            telefoneTextView = itemView.findViewById(R.id.telefoneTextView);
        }
    }
}

