package com.example.wesell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {
    private OnItemClickListener listener = null;
    private List<Cliente> mClientes;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onClienteClick(Cliente cliente);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ClienteAdapter(MainActivity mainActivity, List<Cliente> clientes) {
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
        holder.textViewNome.setText(cliente.getNome());
        holder.textViewEmail.setText(cliente.getEmail());

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

    public class ViewHolder  extends RecyclerView.ViewHolder {

        public TextView textViewNome;
        public TextView textViewEmail;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
        }
    }
}
