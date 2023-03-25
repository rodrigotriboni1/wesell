package com.example.wesell;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class HomeActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private List<Cliente> mClientes;
    private ClienteAdapter mAdapter;

    private RecyclerView mClientesRecyclerView;
    private Button mAdicionarClienteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mClientes = new ArrayList<>();
        mAdapter = new ClienteAdapter(mClientes);

        mClientesRecyclerView = findViewById(R.id.clientesRecyclerView);
        mClientesRecyclerView.setAdapter(mAdapter);
        mClientesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdicionarClienteButton = findViewById(R.id.adicionarClienteButton);
        mAdicionarClienteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarCliente();
            }
        });

        buscarClientes();
    }

    private void adicionarCliente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Cliente");


        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_adicionar_cliente, null);

        final EditText nomeEditText = dialogView.findViewById(R.id.nomeEditText);
        final EditText emailEditText = dialogView.findViewById(R.id.emailEditText);
        final EditText telefoneEditText = dialogView.findViewById(R.id.telefoneEditText);

        builder.setView(dialogView);

        builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = nomeEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String telefone = telefoneEditText.getText().toString();

                Cliente cliente = new Cliente(nome, email, telefone);
                mDatabase.child("clientes").push().setValue(cliente);
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void buscarClientes() {
        mDatabase.child("clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mClientes.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Cliente cliente = dataSnapshot.getValue(Cliente.class);
                    mClientes.add(cliente);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Não foi possível buscar os clientes.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}