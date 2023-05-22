package com.example.wesell.produtos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wesell.R;

public class AddProdutoActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etPreco;
    private Button btnCriarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);

        etNome = findViewById(R.id.et_nome_produto);
        etPreco = findViewById(R.id.et_preco_produto);
        btnCriarProduto = findViewById(R.id.btn_criar_produto);

        btnCriarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarProduto();
            }
        });
    }

    private void criarProduto() {
        String nome = etNome.getText().toString();
        double preco = Double.parseDouble(etPreco.getText().toString());

        Produto produto = criarProduto(nome, preco);
        produto.salvarProdutoNoFirebase();

        limparCampos();
    }

    private Produto criarProduto(String nome, double preco) {
        return new Produto(nome, preco);
    }

    private void limparCampos() {
        etNome.setText("");
        etPreco.setText("");
    }
}

