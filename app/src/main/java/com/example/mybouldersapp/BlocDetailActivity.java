package com.example.mybouldersapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mybouldersapp.beans.Bloc;
import com.squareup.picasso.Picasso;

public class BlocDetailActivity extends AppCompatActivity {

    private static final String TAG = "BlocDetailActivity";

    private Bloc bloc;
    private EditText edtNomBlocDetail, edtCotationDetail, edtLocalisationDetail, edtCommentaireDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloc_detail);

        // Récupérer les données du bloc depuis l'intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedBloc")) {
            bloc = (Bloc) intent.getSerializableExtra("selectedBloc");

            // Log bloc details
            Log.d(TAG, "Received Bloc: " + bloc);

            // Vérifiez si l'objet Bloc est null
            if (bloc != null) {
                // Afficher les données du bloc dans l'interface utilisateur
                displayBlocDetails();

                // Ajouter un écouteur sur le bouton d'édition
                Button btnModifier = findViewById(R.id.btnModifier);
                btnModifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        enableEditing();
                    }
                });

                // Ajouter un écouteur sur le bouton d'ajout de vidéo
                Button btnAjouterVideo = findViewById(R.id.btnAjouterVideo);
                btnAjouterVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });
            } else {
                Log.e(TAG, "Bloc is null");
                Toast.makeText(this, "Error: Bloc data is null", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Log.e(TAG, "Intent has no selectedBloc extra");
            Toast.makeText(this, "Error: No Bloc data found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Méthode pour afficher les détails du bloc dans l'interface utilisateur
    private void displayBlocDetails() {
        ImageView imgBlocDetail = findViewById(R.id.imgBlocDetail);
        EditText edtNomBlocDetail = findViewById(R.id.edtNomBlocDetail);
        EditText edtCotationDetail = findViewById(R.id.edtCotationDetail);
        EditText edtLocalisationDetail = findViewById(R.id.edtLocalisationDetail);
        EditText edtCommentaireDetail = findViewById(R.id.edtCommentaireDetail);

        // Afficher les détails du bloc
        Picasso.get().load(bloc.getPhotoUrl()).into(imgBlocDetail);
        edtNomBlocDetail.setText(bloc.getNom());
        edtCotationDetail.setText(bloc.getCotation());
        edtLocalisationDetail.setText(bloc.getLocalisation());
        edtCommentaireDetail.setText(bloc.getCommentaire());
    }

    // Méthode pour activer le mode d'édition des champs de texte
    private void enableEditing() {
        // Rendre les EditTexts éditables
        edtNomBlocDetail = findViewById(R.id.edtNomBlocDetail);
        edtCotationDetail = findViewById(R.id.edtCotationDetail);
        edtLocalisationDetail = findViewById(R.id.edtLocalisationDetail);
        edtCommentaireDetail = findViewById(R.id.edtCommentaireDetail);

        edtNomBlocDetail.setText(bloc.getNom());
        edtCotationDetail.setText(bloc.getCotation());
        edtLocalisationDetail.setText(bloc.getLocalisation());
        edtCommentaireDetail.setText(bloc.getCommentaire());

        edtNomBlocDetail.setEnabled(true);
        edtCotationDetail.setEnabled(true);
        edtLocalisationDetail.setEnabled(true);
        edtCommentaireDetail.setEnabled(true);

        // Changer le texte du bouton en "Enregistrer"
        Button btnModifier = findViewById(R.id.btnModifier);
        btnModifier.setText("Enregistrer");
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    // Méthode pour enregistrer les modifications apportées aux champs de texte
    private void saveChanges() {
        // Récupérer les valeurs modifiées depuis les EditTexts
        String nomBloc = edtNomBlocDetail.getText().toString().trim();
        String cotation = edtCotationDetail.getText().toString().trim();
        String localisation = edtLocalisationDetail.getText().toString().trim();
        String commentaire = edtCommentaireDetail.getText().toString().trim();

        // Mettre à jour les données du bloc
        bloc.setNom(nomBloc);
        bloc.setCotation(cotation);
        bloc.setLocalisation(localisation);
        bloc.setCommentaire(commentaire);

        // Afficher les détails mis à jour dans l'interface utilisateur
        displayBlocDetails();

        // Désactiver le mode d'édition et changer le texte du bouton en "Modifier"
        Button btnModifier = findViewById(R.id.btnModifier);
        btnModifier.setText("Modifier");
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditing();
            }
        });

        // Désactiver les EditTexts
        edtNomBlocDetail.setEnabled(false);
        edtCotationDetail.setEnabled(false);
        edtLocalisationDetail.setEnabled(false);
        edtCommentaireDetail.setEnabled(false);
    }

    // Méthode pour ouvrir la galerie de l'utilisateur pour sélectionner une vidéo
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri videoUri = null;
            if (data.getClipData() != null) {
                // Prendre seulement la première vidéo pour simplification
                videoUri = data.getClipData().getItemAt(0).getUri();
            } else if (data.getData() != null) {
                videoUri = data.getData();
            }

            if (videoUri != null) {
                VideoView videoView = findViewById(R.id.videoView);
                videoView.setVideoURI(videoUri);
                videoView.start();  // Commencer à jouer la vidéo
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Météo");
        menu.add(0, 2, 0, "Map");
        menu.add(0, 3, 0, "Mes blocs");
        menu.add(0, 4, 0, "Mon profil");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 1) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == 2) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == 3) {
            Intent intent = new Intent(this, BlocsActivity.class);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == 4) {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
