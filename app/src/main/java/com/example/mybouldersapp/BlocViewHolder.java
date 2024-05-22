package com.example.mybouldersapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybouldersapp.beans.Bloc;
import com.squareup.picasso.Picasso;

public class BlocViewHolder extends RecyclerView.ViewHolder {
    private TextView txtNomBloc;
    private ImageView imgBloc;
    private BlocClickListener clickListener;
    private Bloc bloc;

    public BlocViewHolder(@NonNull View itemView, BlocClickListener clickListener) {
        super(itemView);
        txtNomBloc = itemView.findViewById(R.id.txtNomBloc);
        imgBloc = itemView.findViewById(R.id.imgBloc);
        this.clickListener = clickListener;

        // Ajouter un OnClickListener à l'élément de la liste
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && clickListener != null && bloc != null) {
                    clickListener.onBlocClick(bloc);
                }
            }
        });
    }

    // Méthode pour lier les données du bloc à la vue
    public void bind(Bloc bloc) {
        this.bloc = bloc;
        // Afficher les données du bloc dans l'élément de la liste
        txtNomBloc.setText(bloc.getNom());
        Picasso.get().load(bloc.getPhotoUrl()).into(imgBloc);
    }

    // Interface pour gérer les clics sur les éléments de la liste
    public interface BlocClickListener {
        void onBlocClick(Bloc bloc);
    }
}
