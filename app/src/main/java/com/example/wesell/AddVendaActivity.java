package com.example.wesell;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVendaActivity extends AppCompatActivity {

    private EditText editTextValorVenda;
    private Button buttonSalvarVenda;

    private String clienteId;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

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
        buttonSalvarVenda = findViewById(R.id.buttonSalvarVenda);

        // Configura o botão para salvar a venda
        buttonSalvarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarVenda();
            }
        });
    }

    // Método para salvar a nova venda no Firebase Realtime Database
    private void salvarVenda() {
        String valorVenda = editTextValorVenda.getText().toString().trim();

        if (TextUtils.isEmpty(valorVenda)) {
            editTextValorVenda.setError("Informe o valor da venda");
            return;
        }

        // Cria um novo objeto Venda
        String vendaId = mDatabase.child("vendas").push().getKey();
        Venda venda = new Venda(vendaId, clienteId, valorVenda);

        // Salva a nova venda no Firebase Realtime Database
        mDatabase.child("vendas").child(vendaId).setValue(venda)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddVendaActivity.this, "Venda salva com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
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
