package com.example.mybouldersapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybouldersapp.beans.Bloc;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BlocsActivity extends AppCompatActivity {

    private FirestoreRecyclerAdapter<Bloc, BlocViewHolder> adapter;
    private FirebaseAuth auth;
    private static final String TAG = "BlocsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocs);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Vérifiez si l'utilisateur est connecté
        if (currentUser == null) {
            // Redirigez l'utilisateur vers l'activité de connexion
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String uid = currentUser.getUid();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = FirebaseFirestore.getInstance()
                .collection("blocs")
                .whereEqualTo("uid", uid);

        FirestoreRecyclerOptions<Bloc> options = new FirestoreRecyclerOptions.Builder<Bloc>()
                .setQuery(query, Bloc.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Bloc, BlocViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BlocViewHolder holder, int position, @NonNull Bloc model) {
                holder.bind(model);
            }

            @NonNull
            @Override
            public BlocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bloc, parent, false);
                return new BlocViewHolder(view, new BlocViewHolder.BlocClickListener() {
                    @Override
                    public void onBlocClick(Bloc bloc) {
                        Log.d(TAG, "Bloc clicked: " + bloc.getNom());
                        Intent intent = new Intent(BlocsActivity.this, BlocDetailActivity.class);
                        intent.putExtra("selectedBloc", bloc);
                        startActivity(intent);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Accueil");
        menu.add(0, 2, 0, "Météo");
        menu.add(0, 3, 0, "Map");
        menu.add(0, 4, 0, "Mon Profil");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            startActivity(new Intent(this, SecondActivity.class));
            finish();
        } else if (item.getItemId() == 2) {
            startActivity(new Intent(this, WeatherActivity.class));
            finish();
        } else if (item.getItemId() == 3) {
            startActivity(new Intent(this, MapActivity.class));
            finish();
        } else if (item.getItemId() == 4) {
            startActivity(new Intent(this, ProfilActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
