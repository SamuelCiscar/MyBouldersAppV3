package com.example.mybouldersapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddBlocActivity extends AppCompatActivity {
    EditText edtNomDuBloc, edtCotation, edtLocalisation, edtCommentaire;
    TextView txtDateAjout;
    FirebaseFirestore db;
    CollectionReference blocsCollection;
    FirebaseAuth auth;  // Ajouter une instance de FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bloc);

        // Initialisez vos champs
        edtNomDuBloc = findViewById(R.id.edtNomDuBloc);
        edtCotation = findViewById(R.id.edtCotation);
        edtLocalisation = findViewById(R.id.edtLocalisation);
        edtCommentaire = findViewById(R.id.edtCommentaire);
        txtDateAjout = findViewById(R.id.txtDateAjout);

        // Initialisez Firebase
        db = FirebaseFirestore.getInstance();
        blocsCollection = db.collection("blocs");
        auth = FirebaseAuth.getInstance();  // Initialisez FirebaseAuth

        // Obtenez l'URL de la photo de l'intent
        String photoUrl = getIntent().getStringExtra("photoUrl");
        if (photoUrl != null) {
            // Utilisez l'URL de la photo comme vous le souhaitez, par exemple pour afficher la photo
            // Ici, nous supposons que vous avez une ImageView appelée imgPhoto dans votre layout
            // Vous devez remplacer R.id.imgPhoto par l'identifiant de votre ImageView
            //ImageView imgPhoto = findViewById(R.id.imgPhoto);
            // Utilisez une bibliothèque comme Picasso ou Glide pour charger l'image à partir de l'URL
            // Voici un exemple d'utilisation de Picasso :
            //Picasso.get().load(photoUrl).into(imgPhoto);
        }

        // Remplissez automatiquement la date d'ajout avec la date du jour
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateAjout = dateFormat.format(new Date());
        txtDateAjout.setText(dateAjout);

        // Vous pouvez également remplir automatiquement la localisation ici si nécessaire

        // Gérez le bouton de validation
        Button btnValider = findViewById(R.id.btnValider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs des champs
                String nomBloc = edtNomDuBloc.getText().toString().trim();
                String cotation = edtCotation.getText().toString().trim();
                String localisation = edtLocalisation.getText().toString().trim();
                String commentaire = edtCommentaire.getText().toString().trim();

                // Récupérer l'UID de l'utilisateur connecté
                String uid = auth.getCurrentUser().getUid();

                // Créer un objet Map avec les données du formulaire
                Map<String, Object> blocData = new HashMap<>();
                blocData.put("nom", nomBloc);
                blocData.put("cotation", cotation);
                blocData.put("localisation", localisation);
                blocData.put("commentaire", commentaire);
                blocData.put("photoUrl", photoUrl); // Supposons que vous avez l'URL de la photo
                blocData.put("uid", uid); // Ajouter l'UID de l'utilisateur connecté

                // Ajouter les données à Firestore
                blocsCollection.add(blocData)
                        .addOnSuccessListener(documentReference -> {
                            // Succès de l'ajout
                            Toast.makeText(AddBlocActivity.this, "Bloc ajouté avec succès !", Toast.LENGTH_SHORT).show();
                            finish(); // Fermer l'activité après l'ajout
                        })
                        .addOnFailureListener(e -> {
                            // Échec de l'ajout
                            Toast.makeText(AddBlocActivity.this, "Erreur lors de l'ajout du bloc : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
