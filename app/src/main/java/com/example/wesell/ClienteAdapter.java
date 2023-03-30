package com.example.wesell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private final OnClienteClickListener listener = null;
    private List<Cliente> mClientes;
    private OnClienteClickListener mListener;

    public interface OnClienteClickListener {
        void onClienteClick(Cliente cliente);
    }

    public ClienteAdapter(List<Cliente> clientes) {
        mClientes = clientes;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cliente cliente = mClientes.get(position);
        holder.nomeTextView.setText(cliente.getNome());
        holder.emailTextView.setText(cliente.getEmail());
        holder.telefoneTextView.setText(cliente.getTelefone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClienteClick(cliente);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeTextView;
        public TextView emailTextView;
        public TextView telefoneTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            telefoneTextView = itemView.findViewById(R.id.telefoneTextView);
        }
    }
}

