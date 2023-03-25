package com.example.wesell;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    private static DatabaseReference mDatabase;

    // Retorna a referência do banco de dados
    public static DatabaseReference getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        return mDatabase;
    }

    // Adiciona um novo cliente ao banco de dados
    public static void addClient(Client client) {
        DatabaseReference databaseRef = getDatabase().child("clients").push();
        client.setId(databaseRef.getKey());
        databaseRef.setValue(client);
    }

    // Atualiza um cliente existente no banco de dados
    public static void updateClient(Client client) {
        DatabaseReference databaseRef = getDatabase().child("clients").child(client.getId());
        databaseRef.setValue(client);
    }

    // Remove um cliente do banco de dados
    public static void removeClient(Client client) {
        DatabaseReference databaseRef = getDatabase().child("clients").child(client.getId());
        databaseRef.removeValue();
    }
}
