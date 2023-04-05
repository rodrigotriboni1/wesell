package com.example.wesell;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddVendaActivity extends AppCompatActivity {

    private EditText editTextValorVenda;
    private EditText editTextNomeVenda;
    private Button buttonSalvarVenda;
    private RecyclerView recyclerViewVendas;

    private String clienteId;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private VendaAdapter vendaAdapter;
    private List<Venda> listaVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venda);

        // Recebe o ID do cliente selecionado na MainActivity
        clienteId = getIntent().getStringExtra("clienteId");

        // Inicializa o Firebase Auth e o Firebase Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Configura as views da Activity
        editTextValorVenda = findViewById(R.id.editTextValorVenda);
        editTextNomeVenda = findViewById(R.id.editTextNomeVenda);
        buttonSalvarVenda = findViewById(R.id.buttonSalvarVenda);
        recyclerViewVendas = findViewById(R.id.recyclerViewVendas);

        // Configura o botão para salvar a venda
        buttonSalvarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarVenda();
            }
        });

        // Configura a RecyclerView para exibir as vendas do cliente
        listaVendas = new ArrayList<>();
        vendaAdapter = new VendaAdapter(listaVendas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewVendas.setLayoutManager(layoutManager);
        recyclerViewVendas.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVendas.setAdapter(vendaAdapter);

        // Carrega as vendas do cliente do Firebase Realtime Database
        mDatabase.child("vendas").orderByChild("clienteId").equalTo(clienteId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaVendas.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Venda venda = dataSnapshot.getValue(Venda.class);
                    listaVendas.add(venda);
                }
                vendaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddVendaActivity.this, "Erro ao carregar as vendas do cliente", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para salvar a nova venda no Firebase Realtime Database
    private void salvarVenda() {
        String valorVenda = editTextValorVenda.getText().toString().trim();
        String nomeCliente = editTextNomeVenda.getText().toString().trim();

        if (TextUtils.isEmpty(valorVenda)) {
            editTextValorVenda.setError("Informe o valor da venda");
            return;
        }
        if (TextUtils.isEmpty(nomeCliente)) {
            editTextNomeVenda.setError("Informe o nome do produto");
            return;
        }

        // Cria um novo objeto Venda
        String vendaId = mDatabase.child("vendas").push().getKey();
        Venda venda = new Venda(vendaId, clienteId, valorVenda,nomeCliente);

        // Salva a nova venda no Firebase Realtime Database
        mDatabase.child("vendas").child(vendaId).setValue(venda)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddVendaActivity.this, "Venda salva com sucesso", Toast.LENGTH_SHORT).show();
                        // Limpa os campos de texto após a venda ser salva
                        editTextValorVenda.setText("");
                        editTextNomeVenda.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddVendaActivity.this, "Erro ao salvar venda: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                    // Recupera a lista de vendas do cliente e atualiza a RecyclerView
                            mDatabase.child("clientes").child(clienteId).child("vendas").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ArrayList<Venda> vendas = new ArrayList<>();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Venda venda = snapshot.getValue(Venda.class);
                                        vendas.add(venda);
                                    }
                                    // Configura a RecyclerView com a lista de vendas
                                    RecyclerView recyclerView = findViewById(R.id.recyclerViewVendas);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(AddVendaActivity.this));
                                    recyclerView.setAdapter(new VendaAdapter(vendas));
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(AddVendaActivity.this, "Erro ao recuperar vendas do cliente: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
        });
    }
}