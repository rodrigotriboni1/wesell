package com.example.wesell.venda;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.ViewHolder> {

    private List<Venda> vendas;

    private DatabaseReference mDatabase;

    private ViewGroup parent;

    public VendaAdapter(List<Venda> vendas) {
        this.vendas = vendas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_venda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Venda venda = vendas.get(position);
        holder.textViewNomeCliente.setText(venda.getNomeCliente());
        holder.textViewValorVenda.setText(venda.getValorVenda());
        holder.TextViewDataVenda.setText(venda.getDataVenda());
        holder.TextViewQuantidadeVenda.setText(venda.getQuantidadeVenda());

        holder.buttonExcluirVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                excluirVenda(venda);
            }
        });
    }
    public void excluirVenda(Venda venda) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parent.getContext());
        builder.setMessage("Tem certeza que deseja excluir esta venda?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                DatabaseReference vendaRef = FirebaseDatabase.getInstance().getReference("vendas").child(userId).child(venda.getClienteId()).child(venda.getVendaId());
                vendaRef.removeValue();
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public int getItemCount() {
        return vendas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewNomeCliente;
        public TextView textViewValorVenda;

        public TextView TextViewDataVenda;

        public TextView TextViewQuantidadeVenda;

        public Button buttonExcluirVenda;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNomeCliente = itemView.findViewById(R.id.textViewProduto);
            textViewValorVenda = itemView.findViewById(R.id.textViewValor);
            TextViewDataVenda = itemView.findViewById(R.id.TextViewDataVenda);
            TextViewQuantidadeVenda = itemView.findViewById(R.id.TextViewQuantidadeVenda);
            buttonExcluirVenda = itemView.findViewById(R.id.buttonExcluirVenda);


        }
    }
}
