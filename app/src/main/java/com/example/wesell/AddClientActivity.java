package com.example.wesell;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddClientActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private EditText mEditTextPhone;
    private Button mButtonAddClient;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        mEditTextName = findViewById(R.id.add_name_edit_text);
        mEditTextPhone = findViewById(R.id.add_phone_edit_text);
        mButtonAddClient = findViewById(R.id.add_client_button);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("clients");

        mButtonAddClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addClient();
            }
        });
    }

    private void addClient() {
        String name = mEditTextName.getText().toString().trim();
        String phone = mEditTextPhone.getText().toString().trim();

        if (!TextUtils.isEmpty(name)) {
            String id = mDatabaseReference.push().getKey();

            Client client = new Client(id, name, phone);

            mDatabaseReference.child(id).setValue(client);

            Toast.makeText(this, "Cliente adicionado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            mEditTextName.setError("Por favor, digite o nome do cliente");
        }
    }
}
