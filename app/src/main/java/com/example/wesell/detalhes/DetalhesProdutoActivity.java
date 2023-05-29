package com.example.wesell.detalhes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wesell.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DetalhesProdutoActivity extends AppCompatActivity {

    private EditText editTextNomeProduto;
    private EditText editTextPreco;
    private Button btnAtualizarProduto;
    private Button btnDeletarProduto;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);

        editTextNomeProduto = findViewById(R.id.editTextNomeProduto);
        editTextPreco = findViewById(R.id.editTextPreco);
        btnAtualizarProduto = findViewById(R.id.btnAtualizarProduto);
        btnDeletarProduto = findViewById(R.id.btnDeletarProduto);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("produtos").child(userId);

        btnAtualizarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarAtualizacaoProduto();
            }
        });

        btnDeletarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmarDelecaoProduto();
            }
        });
    }

    private void confirmarAtualizacaoProduto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Atualização");
        builder.setMessage("Tem certeza que deseja atualizar o produto?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                atualizarProduto();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void confirmarDelecaoProduto() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Deleção");
        builder.setMessage("Tem certeza que deseja deletar o produto?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletarProduto();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }

    private void atualizarProduto() {
        String produtoId = getIntent().getStringExtra("produtoId");
        DatabaseReference produtoRef = databaseReference.child(produtoId);

        String nome = editTextNomeProduto.getText().toString();
        String precoString = editTextPreco.getText().toString();

        if (!TextUtils.isEmpty(nome) && !TextUtils.isEmpty(precoString)) {
            double preco = Double.parseDouble(precoString);

            Map<String, Object> atualizacao = new HashMap<>();
            atualizacao.put("nome", nome);
            atualizacao.put("preco", preco);

            produtoRef.updateChildren(atualizacao)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(DetalhesProdutoActivity.this, "Produto atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetalhesProdutoActivity.this, "Falha ao atualizar o produto.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(DetalhesProdutoActivity.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletarProduto() {
        String produtoId = getIntent().getStringExtra("produtoId");
        DatabaseReference produtoRef = databaseReference.child(produtoId);

        produtoRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetalhesProdutoActivity.this, "Produto deletado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetalhesProdutoActivity.this, "Falha ao deletar o produto.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
