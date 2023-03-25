package com.example.wesell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView mClientsListView;
    private ClientAdapter mClientAdapter;
    private DatabaseReference mDatabaseReference;
    private ArrayList<Client> mClientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseApp.initializeApp(this);

        mClientsListView = findViewById(R.id.client_item);
        FloatingActionButton addClientButton = findViewById(R.id.add_client_button);

        mClientsList = new ArrayList<>();
        mClientAdapter = new ClientAdapter(this, mClientsList);
        mClientsListView.setAdapter(mClientAdapter);

        mDatabaseReference = FirebaseUtils.getDatabase().child("clients");
        mDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Client client = dataSnapshot.getValue(Client.class);
                mClientsList.add(client);
                mClientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                Client client = dataSnapshot.getValue(Client.class);
                int index = mClientsList.indexOf(client);
                mClientsList.set(index, client);
                mClientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Client client = dataSnapshot.getValue(Client.class);
                mClientsList.remove(client);
                mClientAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                // Não é necessário implementar essa função para a nossa aplicação
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Não é necessário implementar essa função para a nossa aplicação
            }
        });

        mClientsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = mClientsList.get(position);
                Intent intent = new Intent(HomeActivity.this, EditClientActivity.class);
                intent.putExtra("client_id", client.getId());
                startActivity(intent);
            }
        });

        addClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddClientActivity.class);
                startActivity(intent);
            }
        });
    }
}
