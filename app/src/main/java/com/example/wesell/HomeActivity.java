package com.example.wesell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdaptadorClientes adaptador;
    private List<Cliente> listaClientes;
    private List<Cliente> listaClientesFiltrada;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseRef = FirebaseDatabase.getInstance().getReference("clientes");

        FloatingActionButton fab = findViewById(R.id.fabAdicionar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, AdicionarClienteActivity.class));
            }
        });

        recyclerView = findViewById(R.id.listaClientes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaClientes = new ArrayList<>();
        listaClientesFiltrada = new ArrayList<>();
        adaptador = new AdaptadorClientes(this, listaClientesFiltrada);

        recyclerView.setAdapter(adaptador);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Cliente cliente = listaClientesFiltrada.get(position);
                Intent intent = new Intent(HomeActivity.this, DetalhesActivity.class);
                intent.putExtra("cliente", (CharSequence) cliente);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // não utilizado
            }
        }));

        // adiciona um TextWatcher para ouvir as alterações de texto no EditText de pesquisa
        EditText edtPesquisa = findViewById(R.id.edtPesquisa);
        edtPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // não utilizado
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptador.filtrar(s.toString()); // filtra a lista com base no texto digitado
            }

            @Override
            public void afterTextChanged(Editable s) {
                // não utilizado
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaClientes.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Cliente cliente = dataSnapshot.getValue(Cliente.class);
                    listaClientes.add(cliente);
                }
                listaClientesFiltrada.addAll(listaClientes);
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Erro ao carregar os clientes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

