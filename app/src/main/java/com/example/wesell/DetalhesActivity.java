package com.example.wesell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetalhesActivity extends AppCompatActivity {

    private TextView tvNome, tvEmail, tvTelefone;
    private Button btnEditar, btnExcluir;

    private DatabaseReference databaseRef;
    private String idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        // Obtém a referência do banco de dados Firebase
        databaseRef = FirebaseDatabase.getInstance().getReference("clientes");

        // Obtém o ID do cliente selecionado da intent que abriu esta atividade
        idCliente = getIntent().getStringExtra("idCliente");

        // Obtém as referências dos elementos de interface do usuário
        tvNome = findViewById(R.id.tvNome);
        tvEmail = findViewById(R.id.tvEmail);
        tvTelefone = findViewById(R.id.tvTelefone);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        // Define o evento de clique do botão "Editar"
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a atividade AdicionarClienteActivity com o modo de edição
                Intent intent = new Intent(DetalhesActivity.this, AdicionarClienteActivity.class);
                intent.putExtra("idCliente", idCliente);
                intent.putExtra("modoEdicao", true);
                startActivity(intent);
            }
        });

        // Define o evento de clique do botão "Excluir"
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exclui o cliente selecionado do banco de dados Firebase
                databaseRef.child(idCliente).removeValue();
                // Retorna à atividade HomeActivity
                startActivity(new Intent(DetalhesActivity.this, HomeActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Obtém os dados do cliente selecionado do banco de dados Firebase
        databaseRef.child(idCliente).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Cliente cliente = snapshot.getValue(Cliente.class);

                // Exibe os dados do cliente nas TextViews correspondentes
                tvNome.setText(cliente.getNome());
                tvEmail.setText(cliente.getEmail());
                tvTelefone.setText(cliente.getTelefone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DetalhesActivity.this, "Erro ao obter dados do cliente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
