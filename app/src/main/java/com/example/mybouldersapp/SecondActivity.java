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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;
    private static final int REQUEST_CODE_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        invalidateOptionsMenu();
        setContentView(binding.getRoot());

        binding.btMyBlc.setOnClickListener(v -> {
            Intent intent = new Intent(this, BlocsActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btAddBlc.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            } else {
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
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                savePhoto(photo);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "L'activité de la caméra a été annulée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePhoto(Bitmap photo) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] photoData = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        String photoId = UUID.randomUUID().toString();
        StorageReference storageReference = storage.getReference().child("photos/" + photoId + ".jpg");

        // Créez une référence à votre SecondActivity pour l'utiliser dans l'écouteur d'événement
        final SecondActivity activity = this;

        UploadTask uploadTask = storageReference.putBytes(photoData);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    // Si le téléchargement est réussi, récupérez l'URL de téléchargement de la photo
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String photoUrl = uri.toString();
                            // Ajoutez l'URL de téléchargement en tant qu'extra à l'intent pour AddBlocActivity
                            Intent intent = new Intent(activity, AddBlocActivity.class);
                            intent.putExtra("photoUrl", photoUrl);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(activity, "Erreur lors de l'enregistrement de la photo", Toast.LENGTH_SHORT).show();
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

