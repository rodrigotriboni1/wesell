package com.example.wesell.produtos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder> {
    private List<Produto> mProdutos;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onProdutoClick(Produto produto);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProdutoAdapter(List<Produto> produtos) {
        mProdutos = produtos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Produto produto = mProdutos.get(position);
        holder.bind(produto);
    }

    @Override
    public int getItemCount() {
        return mProdutos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNome;
        private TextView textViewPreco;
        private Button buttonDetalhes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.tv_nome_produto);
            textViewPreco = itemView.findViewById(R.id.tv_preco_produto);
            buttonDetalhes = itemView.findViewById(R.id.button_detalhes);

            buttonDetalhes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && mListener != null) {
                        Produto produto = mProdutos.get(position);
                        mListener.onProdutoClick(produto);
                    }
                }
            });
        }

        public void bind(Produto produto) {
            textViewNome.setText(produto.getNome());
            textViewPreco.setText(String.format("R$ %.2f", produto.getPreco()));
        }
    }
}
