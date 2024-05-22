package com.example.mybouldersapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mybouldersapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    private EditText etMail;
    private EditText etPassword;
    private Button btnConnect;
    private FirebaseAuth auth;
    private CheckBox connectCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        // Initialize the etMail and etPassword edit text fields.
        etMail = binding.etMail;
        etPassword = binding.etPassword;
        // Initialize the btnConnect button.
        btnConnect = binding.btnConnect;
        connectCheck = binding.connectCheck;
        // Vérifier l'état de la case à cocher
        boolean shouldStayConnected = connectCheck.isChecked();
        // Vérifiez si l'utilisateur est déjà connecté
        if (auth.getCurrentUser() != null) {
            // Utilisateur déjà connecté et la persistance est activée, redirigez-le vers SecondActivity
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
            finish();
        }
        btnConnect.setOnClickListener(v -> {
            // Vérifiez si l'adresse e-mail et le mot de passe sont vides
            if (TextUtils.isEmpty(etMail.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(MainActivity.this, "Veuillez entrer votre adresse e-mail et votre mot de passe.", Toast.LENGTH_SHORT).show();
                return;
            }
            // Vérifiez si l'utilisateur existe
            auth.signInWithEmailAndPassword(etMail.getText().toString(), etPassword.getText().toString()).addOnSuccessListener(authResult -> {
                if (authResult.getAdditionalUserInfo().isNewUser()) {
                    // L'utilisateur est un nouvel utilisateur
                } else {
                    // L'utilisateur est un utilisateur existant
                }
                if (connectCheck.isChecked()) {
                    // Utilisateur souhaite rester connecté
                    // pas besoin de désactiver la persistance car Firebase Auth gère cela automatiquement
                } else {
                    // L'utilisateur ne souhaite pas rester connecté, déconnectez-le
                    auth.signOut();
                }
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                finish();
            }).addOnFailureListener(e -> {
                // La connexion a échouée
                Toast.makeText(MainActivity.this, "Échec de la connexion: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

        // S'enregistrer
        binding.register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);

            startActivity(intent);
            finish();
        });
    }
    @Override
    public void onClick(View v) {
    }
}