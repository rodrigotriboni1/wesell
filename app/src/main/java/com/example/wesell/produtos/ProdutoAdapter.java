package com.example.wesell.produtos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    private List<Produto> produtos;

    public ProdutoAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.bind(produto);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private TextView tvPreco;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tv_nome_produto);
            tvPreco = itemView.findViewById(R.id.tv_preco_produto);
        }

        public void bind(Produto produto) {
            tvNome.setText(produto.getNome());
            tvPreco.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
        }
    }
}
