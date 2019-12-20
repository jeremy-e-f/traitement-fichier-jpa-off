package fr.diginamic.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

/**
 * Représente le concept d'ingrédient d'un produit
 * @author DIGINAMIC
 *
 */

@Entity
@Table(name="INGREDIENT")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;							/** Identifiant */
	
	@Column(name="LIBELLE", nullable= false, unique = true, columnDefinition = "TEXT")
	private String libelle;					/** Libellé de l'ingrédient */
	
	@ManyToMany
	@JoinTable(name="PROD_ING",
	joinColumns= @JoinColumn(name="ID_ING", referencedColumnName="ID"),
	inverseJoinColumns= @JoinColumn(name="ID_PROD", referencedColumnName="ID"))
	private List<Produit> listeProduits; 	/** Liste de produits qui possèdent cet ingrédient */
	
	/**
	 * Constructeurs
	 */
	public Ingredient() {
	}
	
	public Ingredient(String libelle) {
		this.libelle = libelle;
	}
	
	public Ingredient(int id, String libelle) {
		this(libelle);
		this.id = id;
	}
	
	/** Getter
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/** Setter
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Getter
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/** Setter
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/** Getter
	 * @return the listeProduits
	 */
	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	/** Setter
	 * @param listeProduits the listeProduits to set
	 */
	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", libelle=" + libelle + "]";
	}
	
	@Override
	public boolean equals(Object o){
		return !(o instanceof Ingredient) && libelle.toLowerCase().equals(((Ingredient)o).libelle.toLowerCase());
	}

}
