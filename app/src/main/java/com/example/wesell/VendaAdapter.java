package com.example.wesell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.ViewHolder> {

    private List<Venda> vendas;

    public VendaAdapter(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Venda venda = vendas.get(position);
        holder.textViewNomeCliente.setText(venda.getNomeCliente());
        holder.textViewValorVenda.setText(venda.getValorVenda());

        holder.buttonExcluirVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluirVenda(venda);
            }
        });
    }
    private void excluirVenda(Venda venda) {
        DatabaseReference vendaRef = FirebaseDatabase.getInstance().getReference("vendas").child(venda.getVendaId());
        vendaRef.removeValue();
    }

    @Override
    public int getItemCount() {
        return vendas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomeCliente;
        public TextView textViewValorVenda;

        public Button buttonExcluirVenda;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNomeCliente = itemView.findViewById(R.id.textViewProduto);
            textViewValorVenda = itemView.findViewById(R.id.textViewValor);
            buttonExcluirVenda = itemView.findViewById(R.id.buttonExcluirVenda);

        }
    }
}
