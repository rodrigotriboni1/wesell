package com.example.wesell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.wesell.cliente.ClienteActivity;
import com.example.wesell.produtos.ProdutoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private CardView clienteView;
    private CardView vendasView;
    private TextView txtNomeUsuario;
    private TextView txtTotalVendas;

    private DatabaseReference userRef;
    private DatabaseReference vendasRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clienteView = findViewById(R.id.clienteView);
        vendasView = findViewById(R.id.vendasView);
        txtNomeUsuario = findViewById(R.id.txtNomeUsuario);
        txtTotalVendas = findViewById(R.id.txtTotalVendas);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
            vendasRef = FirebaseDatabase.getInstance().getReference().child("vendas").child(userId);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String nomeUsuario = dataSnapshot.child("nome").getValue(String.class);
                        if (nomeUsuario != null) {
                            String mensagemBoasVindas = getResources().getString(R.string.txt_nome_usuario, nomeUsuario);
                            txtNomeUsuario.setText(mensagemBoasVindas);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Trate o cancelamento da leitura do banco de dados, se necessário
                }
            });


            clienteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
                    startActivity(intent);
                }
            });

            vendasView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ProdutoActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}
