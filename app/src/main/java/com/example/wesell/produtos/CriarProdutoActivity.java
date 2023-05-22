package com.example.wesell.produtos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CriarProdutoActivity extends AppCompatActivity {

    private Button btnCriarProduto;
    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;
    private List<Produto> produtos;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_produto);

        btnCriarProduto = findViewById(R.id.btn_criar_produto);
        recyclerView = findViewById(R.id.recyclerview_produtos);

        produtos = new ArrayList<>();
        produtoAdapter = new ProdutoAdapter(produtos);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(produtoAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        // Inicializar a referência do banco de dados Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("produtos").child(userId);

        btnCriarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAddProdutoActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Carregar os produtos existentes no Firebase e adicioná-los à lista
        childEventListener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Produto produto = dataSnapshot.getValue(Produto.class);
                produtos.add(produto);
                produtoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Not implemented
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Not implemented
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Not implemented
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Not implemented
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Parar de ouvir as alterações no Firebase
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
        }
    }

    private void abrirAddProdutoActivity() {
        Intent intent = new Intent(this, AddProdutoActivity.class);
        startActivity(intent);
    }
}
