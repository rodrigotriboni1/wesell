package com.example.wesell.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private List<Cliente> mClientes;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClienteClick(Cliente cliente);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ClienteAdapter(ClienteActivity clienteActivity, List<Cliente> clientes) {
        mClientes = clientes;
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
        holder.textViewNome.setText(cliente.getNome());

        holder.buttonDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClienteClick(cliente);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mClientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNome;
        public Button buttonDetalhes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            buttonDetalhes = itemView.findViewById(R.id.buttonDetalhes);
        }
    }
}
