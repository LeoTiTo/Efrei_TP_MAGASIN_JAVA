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
/** Un ajout correspond a une unite. La quantite du produit est le stock libre. */
public class Panier {
    private final ArrayList<Produit> produits = new ArrayList<>();

    public void ajouterProduit(Produit produit) {
        if (produit == null || produit.getQuantité() <= 0) {
            throw new IllegalArgumentException("Produit absent ou stock epuise.");
        }
        produits.add(produit);
        produit.setQuantité(produit.getQuantité() - 1);
    }

    public void supprimerProduit(Produit produit) {
        if (produits.remove(produit)) {
            produit.setQuantité(produit.getQuantité() + 1);
        }
    }

    public ArrayList<Produit> getProduits() {
        // Copie de la liste : le code appelant ne peut pas modifier notre liste.
        return new ArrayList<>(produits);
    }

    public boolean estVide() { return produits.isEmpty(); }

    public int calculerTotal() {
        int total = 0;
        for (Produit produit : produits) {
            total += produit.getPrix();
        }
        return total;
    }

    public void afficherPanier() {
        if (estVide()) {
            System.out.println("Le panier est vide.");
            return;
        }
        for (Produit produit : produits) {
            System.out.println(produit.getNom() + " : 1 unite, "
                    + produit.getPrix() + " EUR");
        }
        System.out.println("Total : " + calculerTotal() + " EUR");
    }

    public void viderApresCommande() {
        // Les unites reservees sont vendues : ne pas les remettre en stock.
        produits.clear();
    }

    public void annuler() {
        for (Produit produit : produits) {
            produit.setQuantité(produit.getQuantité() + 1);
        }
        produits.clear();
    }

    @Override
    public String toString() { return "Panier : " + produits; }
}
