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
        holder.bind(cliente);
    }

    @Override
    public int getItemCount() {
        return mClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private Button buttonDetalhes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            buttonDetalhes = itemView.findViewById(R.id.buttonDetalhes);

            buttonDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        Cliente cliente = mClientes.get(position);
                        mListener.onClienteClick(cliente);
                    }
                }
            });
        }

        public void bind(Cliente cliente) {
            textViewNome.setText(cliente.getNome());
        }
    }
}
