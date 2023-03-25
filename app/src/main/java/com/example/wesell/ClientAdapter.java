package com.example.wesell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ClientAdapter extends ArrayAdapter<Client> {

    public ClientAdapter(Context context, List<Client> clients) {
        super(context, 0, clients);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Verifica se a view está sendo reutilizada, senão infla uma nova view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.client_item, parent, false);
        }

        // Obtém o cliente na posição atual
        Client client = getItem(position);

        // Define o nome do cliente na TextView nameTextView
        TextView nameTextView = convertView.findViewById(R.id.name_text_view);
        nameTextView.setText(client.getName());

        // Define o e-mail do cliente na TextView emailTextView
        TextView emailTextView = convertView.findViewById(R.id.email_text_view);
        emailTextView.setText(client.getEmail());

        return convertView;
    }
}
