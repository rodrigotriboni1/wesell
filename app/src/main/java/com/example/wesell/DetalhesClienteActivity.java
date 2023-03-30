package com.example.wesell;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetalhesClienteActivity extends AppCompatActivity {
    private Cliente mCliente;

    private List<Venda> mVendas;

    private VendaAdapter mAdapter;

    private TextView mNomeTextView;
    private TextView mEmailTextView;
    private TextView mTelefoneTextView;
    private RecyclerView mVendasRecyclerView;
    private Button mAdicionarVendaButton;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        mCliente = (Cliente) getIntent().getSerializableExtra("cliente");

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mNomeTextView = findViewById(R.id.nomeClienteTextView);
        mEmailTextView = findViewById(R.id.emailClienteTextView);
        mTelefoneTextView = findViewById(R.id.telefoneClienteTextView);
        mVendasRecyclerView = findViewById(R.id.vendasRecyclerView);
        mAdicionarVendaButton = findViewById(R.id.adicionarVendaButton);

        mNomeTextView.setText(mCliente.getNome());
        mEmailTextView.setText(mCliente.getEmail());
        mTelefoneTextView.setText(mCliente.getTelefone());

        mVendas = new ArrayList<>();
        mAdapter = new VendaAdapter(mVendas);



        mVendasRecyclerView.setAdapter(mAdapter);
        mVendasRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdicionarVendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarVenda();
            }
        });

        buscarVendas();
    }

    private void adicionarVenda() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Venda");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_adicionar_venda, null);

        final EditText descricaoEditText = dialogView.findViewById(R.id.nomeProdutoEditText);
        final EditText valorEditText = dialogView.findViewById(R.id.valorEditText);

        builder.setView(dialogView);

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String valor = valorEditText.getText().toString();
                String descricao = descricaoEditText.getText().toString();



                Venda venda = new Venda(valor, descricao);

                mDatabase = FirebaseDatabase.getInstance().getReference("clientes/vendas");
                mDatabase.setValue(venda);

            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void buscarVendas() {
        mDatabase.child("Clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mVendas.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Venda venda = dataSnapshot.getValue(Venda.class);
                    mVendas.add(venda);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetalhesClienteActivity.this, "Não foi possível buscar as vendas.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

