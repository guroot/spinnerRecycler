package com.example.fletch.databaseproject.Model.Enseignant;

public class Enseignant {
    private int id;
    private String nom ="";

    public Enseignant(int id) {
        this.id = id;
    }

    public Enseignant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String toString(){
        return nom;
    }
}
