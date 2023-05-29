package com.example.wesell.produtos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wesell.R;
import com.example.wesell.RecyclerItemClickListener;
import com.example.wesell.detalhes.DetalhesProdutoActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProdutoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProdutoAdapter produtoAdapter;

    private Button btnAdicionarProduto;
    private List<Produto> produtoList;

    private FirebaseAuth mAuth;

    private DatabaseReference mdatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);

        recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        produtoList = new ArrayList<>();
        produtoAdapter = new ProdutoAdapter(produtoList);
        recyclerView.setAdapter(produtoAdapter);
        btnAdicionarProduto = findViewById(R.id.btnAdicionarProduto);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();

        mdatabase = FirebaseDatabase.getInstance().getReference("produtos").child(userId);

        databaseReference = FirebaseDatabase.getInstance().getReference("produtos").child(userId);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Produto produto = dataSnapshot.getValue(Produto.class);
                produtoList.add(produto);
                produtoAdapter.notifyItemInserted(produtoList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                Produto produto = dataSnapshot.getValue(Produto.class);
                int index = getProdutoIndex(produto.getProdutoId());
                if (index != -1) {
                    produtoList.set(index, produto);
                    produtoAdapter.notifyItemChanged(index);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Produto produto = dataSnapshot.getValue(Produto.class);
                int index = getProdutoIndex(produto.getProdutoId());
                if (index != -1) {
                    produtoList.remove(index);
                    produtoAdapter.notifyItemRemoved(index);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                // Não é necessário fazer nada neste caso.
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProdutoActivity.this, "Não foi possível carregar os produtos.", Toast.LENGTH_SHORT).show();
            }
        });

        btnAdicionarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarProduto();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Produto produtoSelecionado = produtoList.get(position);
                Intent intent = new Intent(ProdutoActivity.this, DetalhesProdutoActivity.class);
                intent.putExtra("produtoId", produtoSelecionado.getProdutoId());
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                // Lógica para lidar com o clique longo em um produto
            }
        }));
    }

    private void adicionarProduto() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Adicionar Produto");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText editTextNome = new EditText(this);
        editTextNome.setHint("Nome do produto");
        layout.addView(editTextNome);

        final EditText editTextPreco = new EditText(this);
        editTextPreco.setHint("Preço do produto");
        editTextPreco.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(editTextPreco);

        dialogBuilder.setView(layout);

        dialogBuilder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = editTextNome.getText().toString().trim();
                String precoStr = editTextPreco.getText().toString().trim();

                if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(precoStr)) {
                    double preco = Double.parseDouble(precoStr);

                    String produtoId = mdatabase.push().getKey();
                    Produto produto = new Produto(produtoId, nome, preco);
                    mdatabase.child(produtoId).setValue(produto);

                    Toast.makeText(ProdutoActivity.this, "Produto adicionado com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProdutoActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private int getProdutoIndex(String produtoId) {
        for (int i = 0; i < produtoList.size(); i++) {
            Produto produto = produtoList.get(i);
            if (produto.getProdutoId().equals(produtoId)) {
                return i;
            }
        }
        return -1;
    }
}
