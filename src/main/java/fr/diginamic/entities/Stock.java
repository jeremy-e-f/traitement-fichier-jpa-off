package fr.diginamic.entities;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Objet permettant de stocker en mémoire les données extraites
 * @author DIGINAMIC
 *
 */
public class Stock {
	/** Listes contenant les produits et les classes qui en dépendent */
	private ArrayList<Produit> listeProduits;				/** Liste des produits */
	private HashMap<String,Additif> listeAdditifs;			/** Liste des additifs */
	private HashMap<String,Ingredient> listeIngredients;	/** Liste des ingrédients */
	private HashMap<String,Allergene> listeAllergenes;		/** Liste des allergènes */
	private HashMap<String,Categorie> listeCategories;		/** Liste des catégories */
	private HashMap<String,Marque> listeMarques;			/** Liste des marques */
	
	/**
	 * Constructeur
	 */
	public Stock() {
		listeProduits= new ArrayList<Produit>();
		listeAdditifs= new HashMap<String, Additif>();
		listeIngredients= new HashMap<String, Ingredient>();
		listeAllergenes= new HashMap<String, Allergene>();
		listeCategories= new HashMap<String, Categorie>();
		listeMarques= new HashMap<String, Marque>();
	}
	
	/** Fonctions d'ajout */
	
	/**
	 * Ajouter un produit
	 */
	public Produit addProduit(Produit produit){
		listeProduits.add(produit);
		return produit;
	}
	
	/**
	 * Ajoute et renvoie un Allergène
	 * @param libelle
	 */
	public Allergene addAllergene(String libelle){
		Allergene element= getAllergene(libelle);
		if(element!= null){
			return element;
		}
		element= new Allergene(libelle);
		listeAllergenes.put(libelle, element);
		return element;
	}
	
	public Allergene getAllergene(String libelle){
		return listeAllergenes.get(libelle);
	}
	
	
	/**
	 * Ajoute et renvoie une Categorie
	 * @param libelle
	 */
	public Categorie addCategorie(String libelle){
		Categorie element= getCategorie(libelle);
		if(element!= null){
			return element;
		}
		element= new Categorie(libelle);
		listeCategories.put(libelle, element);
		return element;
	}
	
	public Categorie getCategorie(String libelle){
		return listeCategories.get(libelle);
	}
	
	/**
	 * Ajoute et renvoie une Ingredient
	 * @param libelle
	 */
	public Ingredient addIngredient(String libelle){
		Ingredient element= getIngredient(libelle);
		if(element!= null){
			return element;
		}
		element= new Ingredient(libelle);
		listeIngredients.put(libelle, element);
		return element;
	}
	
	public Ingredient getIngredient(String libelle){
		return listeIngredients.get(libelle);
	}
	
	/**
	 * Ajoute et renvoie une Additif
	 * @param libelle
	 */
	public Additif addAdditif(String libelle){
		Additif element= getAdditif(libelle);
		if(element!= null){
			return element;
		}
		element= new Additif(libelle);
		listeAdditifs.put(libelle, element);
		return element;
	}
	
	public Additif getAdditif(String libelle){
		return listeAdditifs.get(libelle);
	}
	
	/**
	 * Ajoute et renvoie une Marque
	 * @param nom
	 */
	public Marque addMarque(String nom){
		Marque element= getMarque(nom);
		if(element!= null){
			return element;
		}
		element= new Marque(nom);
		listeMarques.put(nom, element);
		return element;
	}
	
	public Marque getMarque(String nom){
		return listeMarques.get(nom);
	}

	/** Getter
	 * @return the listeProduits
	 */
	public ArrayList<Produit> getListeProduits() {
		return listeProduits;
	}

	/** Setter
	 * @param listeProduits the listeProduits to set
	 */
	public void setListeProduits(ArrayList<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	/** Getter
	 * @return the listeAdditifs
	 */
	public HashMap<String, Additif> getListeAdditifs() {
		return listeAdditifs;
	}

	/** Setter
	 * @param listeAdditifs the listeAdditifs to set
	 */
	public void setListeAdditifs(HashMap<String, Additif> listeAdditifs) {
		this.listeAdditifs = listeAdditifs;
	}

	/** Getter
	 * @return the listeIngredients
	 */
	public HashMap<String, Ingredient> getListeIngredients() {
		return listeIngredients;
	}

	/** Setter
	 * @param listeIngredients the listeIngredients to set
	 */
	public void setListeIngredients(HashMap<String, Ingredient> listeIngredients) {
		this.listeIngredients = listeIngredients;
	}

	/** Getter
	 * @return the listeAllergenes
	 */
	public HashMap<String, Allergene> getListeAllergenes() {
		return listeAllergenes;
	}

	/** Setter
	 * @param listeAllergenes the listeAllergenes to set
	 */
	public void setListeAllergenes(HashMap<String, Allergene> listeAllergenes) {
		this.listeAllergenes = listeAllergenes;
	}

	/** Getter
	 * @return the listeCategories
	 */
	public HashMap<String, Categorie> getListeCategories() {
		return listeCategories;
	}

	/** Setter
	 * @param listeCategories the listeCategories to set
	 */
	public void setListeCategories(HashMap<String, Categorie> listeCategories) {
		this.listeCategories = listeCategories;
	}

	/** Getter
	 * @return the listeMarques
	 */
	public HashMap<String, Marque> getListeMarques() {
		return listeMarques;
	}

	/** Setter
	 * @param listeMarques the listeMarques to set
	 */
	public void setListeMarques(HashMap<String, Marque> listeMarques) {
		this.listeMarques = listeMarques;
	}
	
}
