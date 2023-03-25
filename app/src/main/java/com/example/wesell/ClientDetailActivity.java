package com.example.wesell;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClientDetailActivity extends AppCompatActivity {

    private TextView clientNameTextView, clientEmailTextView;
    private ImageView clientImageView;
    private String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);

        clientNameTextView = findViewById(R.id.client_name);
        clientEmailTextView = findViewById(R.id.client_email);
        clientImageView = findViewById(R.id.client_image);

        clientId = getIntent().getStringExtra("client_id");

        DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference().child("clients").child(clientId);
        clientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Client client = snapshot.getValue(Client.class);
                    clientNameTextView.setText(client.getName());
                    clientEmailTextView.setText(client.getEmail());
                    if(client.getImageUrl() != null){
                        Picasso.get().load(client.getImageUrl()).into(clientImageView);
                    } else {
                        clientImageView.setImageResource(R.drawable.ic_person);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // handle error
            }
        });
    }
}
