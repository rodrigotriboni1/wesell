package com.example.wesell;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.ViewHolder> {
    private List<Venda> mVendas;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomeProdutoTextView;
        public TextView valorTextView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            nomeProdutoTextView = itemView.findViewById(R.id.nomeProdutoTextView);
            valorTextView = itemView.findViewById(R.id.valorTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public VendaAdapter(List<Venda> vendas) {
        mVendas = vendas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venda, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Venda venda = mVendas.get(position);

        holder.nomeProdutoTextView.setText(venda.getDescricao());
        holder.valorTextView.setText(String.format(Locale.getDefault(), "R$ %.2f", venda.getValor()));
    }

    @Override
    public int getItemCount() {
        return mVendas.size();
    }

}
