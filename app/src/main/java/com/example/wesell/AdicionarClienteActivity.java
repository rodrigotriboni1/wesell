package com.example.wesell;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdicionarClienteActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtTelefone;
    private Button btnAdicionar;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cliente);

        databaseRef = FirebaseDatabase.getInstance().getReference("clientes");

        edtNome = findViewById(R.id.etNome);
        edtEmail = findViewById(R.id.etEmail);
        edtTelefone = findViewById(R.id.etTelefone);

        btnAdicionar = findViewById(R.id.btnSalvar);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarCliente();
            }
        });
    }

    private void adicionarCliente() {
        String nome = edtNome.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();

        if (nome.isEmpty()) {
            edtNome.setError("Por favor, digite um nome");
            edtNome.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edtEmail.setError("Por favor, digite um e-mail");
            edtEmail.requestFocus();
            return;
        }

        if (telefone.isEmpty()) {
            edtTelefone.setError("Por favor, digite um telefone");
            edtTelefone.requestFocus();
            return;
        }

        String id = databaseRef.push().getKey();
        Cliente cliente = new Cliente(id, nome, email, telefone);
        databaseRef.child(id).setValue(cliente);
        Toast.makeText(this, "Cliente adicionado com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }
}
