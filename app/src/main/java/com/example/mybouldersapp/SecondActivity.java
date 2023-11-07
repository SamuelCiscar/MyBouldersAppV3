package com.example.mybouldersapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mybouldersapp.databinding.ActivitySecondBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {
    // Déclaration de la variable contenant les références des composants graphiques
    ActivitySecondBinding binding;
    private static final int REQUEST_CODE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Création de l'interface graphique
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        // Invalider le menu pour forcer la barre d'action à redessiner le menu
        invalidateOptionsMenu();
        setContentView(binding.getRoot());

        binding.btMyBlc.setOnClickListener(v -> {
            Intent intent = new Intent(this, BlocsActivity.class);
            startActivity(intent);
            finish();
        });

        // Définir le gestionnaire de clic pour le bouton "Ajouter un bloc"
        binding.btAddBlc.setOnClickListener(v -> {
            // Ouvrir l'appareil photo de l'utilisateur
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            } else {
                // L'application a déjà la permission d'accéder à la caméra
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                // L'image a été capturée avec succès
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                // Enregistrer la photo sur Firebase Storage
                savePhoto(photo);
            } else if (resultCode == RESULT_CANCELED) {
                // L'activité de la caméra a été annulée
                Toast.makeText(this, "L'activité de la caméra a été annulée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePhoto(Bitmap photo) {
        // Compressez la photo en un tableau d'octets
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] photoData = baos.toByteArray();

        // Créez une nouvelle référence de stockage pour la photo
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String photoId = UUID.randomUUID().toString();
        StorageReference storageReference = storage.getReference("photos/" + photoId + ".jpg");

        // Téléchargez la photo sur Firebase Storage et retournez un Task<Uri>
        UploadTask uploadTask = storageReference.putBytes(photoData);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Retourne l'URL de téléchargement
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    // L'URL de téléchargement est disponible dans downloadUri
                    Toast.makeText(SecondActivity.this, "Photo enregistrée avec succès", Toast.LENGTH_SHORT).show();
                } else {
                    // Erreur lors de l'enregistrement de la photo
                    Toast.makeText(SecondActivity.this, "Erreur lors de l'enregistrement de la photo", Toast.LENGTH_SHORT).show();
                }
            }
        });
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