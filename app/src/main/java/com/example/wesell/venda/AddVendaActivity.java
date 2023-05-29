package com.example.wesell.venda;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;
import com.example.wesell.produtos.Produto;
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
    private Spinner spinnerProdutos;
    private EditText editTextDataVenda;
    private EditText editTextQuantidadeVenda;
    private Button buttonSalvarVenda;
    private RecyclerView recyclerViewVendas;

    private String clienteId;

    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private VendaAdapter vendaAdapter;
    private List<Venda> listaVendas;
    private List<Produto> listaProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_venda);

        clienteId = getIntent().getStringExtra("clienteId");

        recyclerView = findViewById(R.id.recyclerViewVendas);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextValorVenda = findViewById(R.id.editTextValorVenda);
        spinnerProdutos = findViewById(R.id.spinnerProdutos);
        editTextDataVenda = findViewById(R.id.editTextDataVenda);
        editTextQuantidadeVenda = findViewById(R.id.editTextQuantidadeVenda);
        buttonSalvarVenda = findViewById(R.id.buttonSalvarVenda);
        recyclerViewVendas = findViewById(R.id.recyclerViewVendas);

        buttonSalvarVenda.setOnClickListener(view -> salvarVenda());

        listaVendas = new ArrayList<>();
        vendaAdapter = new VendaAdapter(listaVendas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewVendas.setLayoutManager(layoutManager);
        recyclerViewVendas.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVendas.setAdapter(vendaAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("vendas").child(userId).child(clienteId);

        mDatabase.orderByChild("clienteId").equalTo(clienteId).addValueEventListener(new ValueEventListener() {
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

        editTextDataVenda.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddVendaActivity.this, (view, year, month, dayOfMonth) -> {
                String dataSelecionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                editTextDataVenda.setText(dataSelecionada);
            }, ano, mes, dia);
            datePickerDialog.show();
        });

        // Load produtos from Firebase
        listaProdutos = new ArrayList<>();
        DatabaseReference produtosRef = FirebaseDatabase.getInstance().getReference("produtos").child(userId);
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProdutos.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Produto produto = dataSnapshot.getValue(Produto.class);
                    listaProdutos.add(produto);
                }

                // Set up spinner adapter
                List<String> nomesProdutos = new ArrayList<>();
                for (Produto produto : listaProdutos) {
                    nomesProdutos.add(produto.getNome());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddVendaActivity.this, android.R.layout.simple_spinner_item, nomesProdutos);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerProdutos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddVendaActivity.this, "Erro ao carregar os produtos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarVenda() {
        String valorVenda = editTextValorVenda.getText().toString().trim();
        String nomeProduto = spinnerProdutos.getSelectedItem().toString();
        String dataVenda = editTextDataVenda.getText().toString().trim();
        String quantidadeVenda = editTextQuantidadeVenda.getText().toString().trim();

        if (TextUtils.isEmpty(valorVenda)) {
            editTextValorVenda.setError("Informe o valor da venda");
            return;
        }
        if (TextUtils.isEmpty(dataVenda)) {
            editTextDataVenda.setError("Informe a data da venda");
            return;
        }
        if (TextUtils.isEmpty(quantidadeVenda)) {
            editTextQuantidadeVenda.setError("Informe a quantidade da venda");
            return;
        }

        String vendaId = mDatabase.push().getKey();
        Venda venda = new Venda(vendaId, clienteId, valorVenda, nomeProduto, dataVenda, quantidadeVenda);

        mDatabase.child(vendaId).setValue(venda)
                .addOnSuccessListener(aVoid -> {
                    editTextValorVenda.setText("");
                    editTextQuantidadeVenda.setText("");
                    Toast.makeText(AddVendaActivity.this, "Venda salva com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddVendaActivity.this, "Erro ao salvar venda: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
