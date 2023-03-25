package com.example.wesell;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditClientActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPhoneEditText;
    private Button mSaveButton;
    private DatabaseReference mDatabaseReference;
    private String mClientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        mNameEditText = findViewById(R.id.edit_name_edit_text);
        mEmailEditText = findViewById(R.id.edit_email_edit_text);
        mPhoneEditText = findViewById(R.id.edit_phone_edit_text);
        mSaveButton = findViewById(R.id.edit_client_button);

        mClientId = getIntent().getStringExtra("client_id");

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("clients");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // recupera o objeto Client do snapshot
                    Client client = dataSnapshot.getValue(Client.class);
                    mNameEditText.setText(client.getName());
                    mEmailEditText.setText(client.getEmail());
                    mPhoneEditText.setText(client.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EditClientActivity", "Erro ao buscar informações do cliente", error.toException());
                Toast.makeText(EditClientActivity.this, "Erro ao buscar informações do cliente", Toast.LENGTH_SHORT).show();
            }

        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClient();
            }
        });
    }

    private void saveClient() {
        String name = mNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String phone = mPhoneEditText.getText().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        Client client = new Client(mClientId, name, phone, email);
        mDatabaseReference.setValue(client);

        Toast.makeText(this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
