package com.example.wesell;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddVendaActivity extends AppCompatActivity {

    private EditText editTextValorVenda;
    private EditText editTextNomeVenda;
    private EditText editTextDataVenda;
    private Button buttonSalvarVenda;
    private RecyclerView recyclerViewVendas;

    private String clienteId;

    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;

    private DatabaseReference databaseReference;
    private VendaAdapter vendaAdapter;
    private List<Venda> listaVendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venda);

        // Recebe o ID do cliente selecionado na MainActivity
        clienteId = getIntent().getStringExtra("clienteId");

        recyclerView = findViewById(R.id.recyclerViewVendas);

        // Inicializa o Firebase Auth e o Firebase Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();



        // Configura as views da Activity
        editTextValorVenda = findViewById(R.id.editTextValorVenda);
        editTextNomeVenda = findViewById(R.id.editTextNomeVenda);
        editTextDataVenda = findViewById(R.id.editTextDataVenda);
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("vendas").child(userId);

        // Carrega as vendas do cliente do Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
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

        // Configura o EditText para exibir o DatePickerDialog ao ser clicado
        editTextDataVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddVendaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDataVenda.setText(dataSelecionada);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
    }

    // Método para salvar a nova venda no Firebase Realtime Database
    private void salvarVenda() {
        String valorVenda = editTextValorVenda.getText().toString().trim();
        String nomeCliente = editTextNomeVenda.getText().toString().trim();
        String dataVenda = editTextDataVenda.getText().toString().trim();

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

        String id = databaseReference.push().getKey();
        Venda venda = new Venda(vendaId, clienteId, valorVenda, nomeCliente, dataVenda);
        databaseReference.child(id).setValue(venda);

// Salva a nova venda no Firebase Realtime Database
        mDatabase.child("vendas").child(vendaId).setValue(venda)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        editTextValorVenda.setText("");
                        editTextNomeVenda.setText("");
                        editTextDataVenda.setText("");

                        // Limpa os campos de texto após a venda ser salva
                        Toast.makeText(AddVendaActivity.this, "Venda salva com sucesso", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddVendaActivity.this, "Erro ao salvar venda: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
