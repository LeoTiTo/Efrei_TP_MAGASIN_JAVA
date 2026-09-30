/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionMagasin;

/**
 *
 * @author lochu
 */
public class Produit {
    private int id;
    private String nom;
    private int prix;
    private int quantité;

    public Produit(int id, String nom, int prix, int quantité) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.quantité = quantité;
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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }    

    public void afficherDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", prix=" + prix + ", quantit\u00e9=" + quantité + '}';
    }
    
}
