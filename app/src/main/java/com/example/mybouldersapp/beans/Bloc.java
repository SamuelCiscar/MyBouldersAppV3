package com.example.mybouldersapp.beans;

import java.io.Serializable;

public class Bloc implements Serializable {
    private String id;
    private String nom;
    private String photoUrl;
    private String cotation;
    private String localisation;
    private String commentaire;

    private String videoUrl;
    private String uid;  // Ajout du champ uid

    // Constructeur
    public Bloc(String id, String nom, String photoUrl, String cotation, String localisation, String commentaire, String uid) {
        this.id = id;
        this.nom = nom;
        this.photoUrl = photoUrl;
        this.cotation = cotation;
        this.localisation = localisation;
        this.commentaire = commentaire;
        //this.videoUrl = video;
        this.uid = uid;  // Initialisation du champ uid
    }

    // Constructeur sans arguments
    public Bloc() {
        // Constructeur vide requis par Firebase pour la désérialisation
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getCotation() {
        return cotation;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public String getUid() {
        return uid;  // Getter pour le champ uid
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCotation(String cotation) {
        this.cotation = cotation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setUid(String uid) {
        this.uid = uid;  // Setter pour le champ uid
    }
}
