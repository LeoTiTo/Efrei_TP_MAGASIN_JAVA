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
public class Commande {
    private final int idCommande;
    private final Client client;
    private final ArrayList<Produit> produitsCommandes;
    private final int total;

    public Commande(int idCommande, Client client, Panier panier) {
        if (client == null || panier == null || panier.estVide()) {
            throw new IllegalArgumentException("Client requis et panier non vide requis.");
        }
        this.idCommande = idCommande;
        // Copies pour conserver les informations au moment de la commande.
        this.client = new Client(client.getId(), client.getNom(), client.getEmail());
        this.produitsCommandes = new ArrayList<>();
        for (Produit p : panier.getProduits()) {
            produitsCommandes.add(new Produit(p.getId(), p.getNom(), p.getPrix(), 1));
        }
        this.total = panier.calculerTotal();
    }

    public int getTotal() { return total; }

    public void afficherDetailsCommande() {
        System.out.println("Commande numero " + idCommande);
        client.afficherDetails();
        for (Produit produit : produitsCommandes) {
            produit.afficherDetails();
        }
        System.out.println("Total de la commande : " + total + " EUR");
    }

    @Override
    public String toString() {
        return "Commande " + idCommande + " : " + produitsCommandes
                + ", total = " + total + " EUR";
    }
}
