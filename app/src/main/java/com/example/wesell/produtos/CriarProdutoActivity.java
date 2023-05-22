package com.example.wesell.produtos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wesell.R;

public class CriarProdutoActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etPreco;
    private Button btnCriarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_produto);

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

        // Criar o objeto Produto com as informações fornecidas
        Produto produto = new Produto(nome, preco);
        produto.salvarProdutoNoFirebase();

        // Limpar os campos após criar o produto
        etNome.setText("");
        etPreco.setText("");
    }
}
