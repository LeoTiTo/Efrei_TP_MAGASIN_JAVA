/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package gestionMagasin;

import java.util.ArrayList;

/**
 *
 * @author lochu
 */
public class Magasin {
    private final ArrayList<Produit> produits = new ArrayList<>();

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public void afficherProduitsDisponibles() {
        boolean disponible = false;
        for (Produit produit : produits) {
            if (produit.getQuantité() > 0) {
                produit.afficherDetails();
                disponible = true;
            }
        }
        if (!disponible) {
            System.out.println("Aucun produit disponible.");
        }
    }

    // Compatibilite avec le nom utilise dans le fichier initial.
    public void afficherProduitDisponibles() {
        afficherProduitsDisponibles();
    }

    public Produit trouverProduitParNom(String nom) {
        if (nom == null) { return null; }
        for (Produit produit : produits) {
            if (produit.getNom().equalsIgnoreCase(nom.trim())) {
                return produit;
            }
        }
        return null;
    }
}
