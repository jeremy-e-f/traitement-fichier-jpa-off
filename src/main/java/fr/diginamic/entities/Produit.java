package fr.diginamic.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * Représente le concept d'un produit
 * @author DIGINAMIC
 *
 */
@Entity
@Table(name="PRODUIT")
public class Produit implements Comparable<Produit>{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;										/** Identifiant */
	
	@Column(name="NOM", nullable= false)
	private String nom;									/** Nom du produit */
	
	@ManyToOne
	@JoinColumn(name="ID_CAT")
	private Categorie categorie;						/** Catégorie du produit */
	
	@ManyToOne
	@JoinColumn(name="ID_MQ")
	private Marque marque;								/** Marque du produit */
	
	@ManyToMany
	@JoinTable(name="PROD_ING",
	joinColumns= @JoinColumn(name="ID_PROD", referencedColumnName="ID"),
	inverseJoinColumns= @JoinColumn(name="ID_ING", referencedColumnName="ID"))
	private List<Ingredient> listeIngredients;		/** Liste des ingrédients du produit */
	
	@ManyToMany
	@JoinTable(name="PROD_ALL",
	joinColumns= @JoinColumn(name="ID_PROD", referencedColumnName="ID"),
	inverseJoinColumns= @JoinColumn(name="ID_ALL", referencedColumnName="ID"))
	private List<Allergene> listeAllergenes;		/** Liste des allergènes présents dans le produit */
	
	@ManyToMany
	@JoinTable(name="PROD_ADD",
	joinColumns= @JoinColumn(name="ID_PROD", referencedColumnName="ID"),
	inverseJoinColumns= @JoinColumn(name="ID_ADD", referencedColumnName="ID"))
	private List<Additif> listeAdditifs;			/** Liste des additifs présents dans le produit */
	
	@Column(name="SCORE_NUTRITIONNEL")
	private char scoreNutritionnel;					/** Score nutritionnel du produit */
	
	/**
	 *  Différents autres attributs représentants les teneurs du produit
	 */
	
	@Column(name="ENERGIE")
	private Double energie;
	
	@Column(name="GRAISSE")
	private Double graisse;
	
	@Column(name="GRAISSE_SATUREE")
	private Double graisseSaturee;
	
	@Column(name="HYDRATES_CARBONES")
	private Double hydratesCarbones;
	
	@Column(name="SUCRES")
	private Double sucres;
	
	@Column(name="FIBRES")
	private Double fibres;
	
	@Column(name="PROTEINES")
	private Double proteines;
	
	@Column(name="SEL")
	private Double sel;
	
	@Column(name="VIT_A")
	private Double vitA;
	
	@Column(name="VIT_D")
	private Double vitD;

	@Column(name="VIT_E")
	private Double vitE;

	@Column(name="VIT_K")
	private Double vitK;

	@Column(name="VIT_C")
	private Double vitC;

	@Column(name="VIT_B1")
	private Double vitB1;

	@Column(name="VIT_B2")
	private Double vitB2;

	@Column(name="VIT_PP")
	private Double vitPP;

	@Column(name="VIT_B6")
	private Double vitB6;

	@Column(name="VIT_B9")
	private Double vitB9;

	@Column(name="VIT_B12")
	private Double vitB12;

	@Column(name="CALCIUM")
	private Double calcium;
	
	@Column(name="MAGNESIUM")
	private Double magnesium;
	
	@Column(name="FER")
	private Double fer;
	
	@Column(name="BETA_CAROTENE")
	private Double betaCarotene;
	
	@Column(name="PRESENCE_HUILE_PALME")
	private boolean presenceHuilePalme;
	
	@Column(name="POURCENTAGE_FRUITS_LEGUMES")
	private Double pourcentageFruitsLegumes;
	
	/**
	 * Constructeurs
	 * 
	 */
	public Produit(){
	}
	
	public Produit(String nom, Categorie categorie, Marque marque, char scoreNutritionnel, ArrayList<Ingredient> listeIngredients,
			ArrayList<Allergene> listeAllergenes, ArrayList<Additif> listeAdditifs, Double energie, Double graisse,
			Double graisseSaturee, Double hydratesCarbones, Double sucres, Double fibres, Double proteines,
			Double sel) {
		this.nom= nom;
		this.categorie = categorie;
		this.marque = marque;
		this.scoreNutritionnel = scoreNutritionnel;
		this.listeIngredients = listeIngredients;
		this.listeAllergenes = listeAllergenes;
		this.listeAdditifs = listeAdditifs;
		this.energie = energie;
		this.graisse = graisse;
		this.graisseSaturee = graisseSaturee;
		this.hydratesCarbones = hydratesCarbones;
		this.sucres = sucres;
		this.fibres = fibres;
		this.proteines = proteines;
		this.sel = sel;
	}

	public Produit(int id, String nom, Categorie categorie, Marque marque, char scoreNutritionnel, List<Ingredient> listeIngredients,
			List<Allergene> listeAllergenes, List<Additif> listeAdditifs, Double energie,
			Double graisse, Double graisseSaturee, Double hydratesCarbones, Double sucres, Double fibres,
			Double proteines, Double sel, Double vitA, Double vitD, Double vitE, Double vitK, Double vitC,
			Double vitB1, Double vitB2, Double vitPP, Double vitB6, Double vitB9, Double vitB12, Double calcium,
			Double magnesium, Double fer, Double betaCarotene, boolean presenceHuilePalme,
			Double pourcentageFruitsLegumes) {
		this.id = id;
		this.nom = nom;
		this.categorie = categorie;
		this.marque = marque;
		this.listeIngredients = listeIngredients;
		this.listeAllergenes = listeAllergenes;
		this.listeAdditifs = listeAdditifs;
		this.scoreNutritionnel = scoreNutritionnel;
		this.energie = energie;
		this.graisse = graisse;
		this.graisseSaturee = graisseSaturee;
		this.hydratesCarbones = hydratesCarbones;
		this.sucres = sucres;
		this.fibres = fibres;
		this.proteines = proteines;
		this.sel = sel;
		this.vitA = vitA;
		this.vitD = vitD;
		this.vitE = vitE;
		this.vitK = vitK;
		this.vitC = vitC;
		this.vitB1 = vitB1;
		this.vitB2 = vitB2;
		this.vitPP = vitPP;
		this.vitB6 = vitB6;
		this.vitB9 = vitB9;
		this.vitB12 = vitB12;
		this.calcium = calcium;
		this.magnesium = magnesium;
		this.fer = fer;
		this.betaCarotene = betaCarotene;
		this.presenceHuilePalme = presenceHuilePalme;
		this.pourcentageFruitsLegumes = pourcentageFruitsLegumes;
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
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/** Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter
	 * @return the categorie
	 */
	public Categorie getCategorie() {
		return categorie;
	}

	/** Setter
	 * @param categorie the categorie to set
	 */
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	/** Getter
	 * @return the marque
	 */
	public Marque getMarque() {
		return marque;
	}

	/** Setter
	 * @param marque the marque to set
	 */
	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	/** Getter
	 * @return the listeIngredients
	 */
	public List<Ingredient> getListeIngredients() {
		return listeIngredients;
	}

	/** Setter
	 * @param listeIngredients the listeIngredients to set
	 */
	public void setListeIngredients(List<Ingredient> listeIngredients) {
		this.listeIngredients = listeIngredients;
	}

	/** Getter
	 * @return the listeAllergenes
	 */
	public List<Allergene> getListeAllergenes() {
		return listeAllergenes;
	}

	/** Setter
	 * @param listeAllergenes the listeAllergenes to set
	 */
	public void setListeAllergenes(List<Allergene> listeAllergenes) {
		this.listeAllergenes = listeAllergenes;
	}

	/** Getter
	 * @return the listeAdditifs
	 */
	public List<Additif> getListeAdditifs() {
		return listeAdditifs;
	}

	/** Setter
	 * @param listeAdditifs the listeAdditifs to set
	 */
	public void setListeAdditifs(List<Additif> listeAdditifs) {
		this.listeAdditifs = listeAdditifs;
	}

	/** Getter
	 * @return the scoreNutritionnel
	 */
	public char getScoreNutritionnel() {
		return scoreNutritionnel;
	}

	/** Setter
	 * @param scoreNutritionnel the scoreNutritionnel to set
	 */
	public void setScoreNutritionnel(char scoreNutritionnel) {
		this.scoreNutritionnel = scoreNutritionnel;
	}

	/** Getter
	 * @return the energie
	 */
	public Double getEnergie() {
		return energie;
	}

	/** Setter
	 * @param energie the energie to set
	 */
	public void setEnergie(Double energie) {
		this.energie = energie;
	}

	/** Getter
	 * @return the graisse
	 */
	public Double getGraisse() {
		return graisse;
	}

	/** Setter
	 * @param graisse the graisse to set
	 */
	public void setGraisse(Double graisse) {
		this.graisse = graisse;
	}

	/** Getter
	 * @return the graisseSaturee
	 */
	public Double getGraisseSaturee() {
		return graisseSaturee;
	}

	/** Setter
	 * @param graisseSaturee the graisseSaturee to set
	 */
	public void setGraisseSaturee(Double graisseSaturee) {
		this.graisseSaturee = graisseSaturee;
	}

	/** Getter
	 * @return the hydratesCarbones
	 */
	public Double getHydratesCarbones() {
		return hydratesCarbones;
	}

	/** Setter
	 * @param hydratesCarbones the hydratesCarbones to set
	 */
	public void setHydratesCarbones(Double hydratesCarbones) {
		this.hydratesCarbones = hydratesCarbones;
	}

	/** Getter
	 * @return the sucres
	 */
	public Double getSucres() {
		return sucres;
	}

	/** Setter
	 * @param sucres the sucres to set
	 */
	public void setSucres(Double sucres) {
		this.sucres = sucres;
	}

	/** Getter
	 * @return the fibres
	 */
	public Double getFibres() {
		return fibres;
	}

	/** Setter
	 * @param fibres the fibres to set
	 */
	public void setFibres(Double fibres) {
		this.fibres = fibres;
	}

	/** Getter
	 * @return the proteines
	 */
	public Double getProteines() {
		return proteines;
	}

	/** Setter
	 * @param proteines the proteines to set
	 */
	public void setProteines(Double proteines) {
		this.proteines = proteines;
	}

	/** Getter
	 * @return the sel
	 */
	public Double getSel() {
		return sel;
	}

	/** Setter
	 * @param sel the sel to set
	 */
	public void setSel(Double sel) {
		this.sel = sel;
	}

	/** Getter
	 * @return the vitA
	 */
	public Double getVitA() {
		return vitA;
	}

	/** Setter
	 * @param vitA the vitA to set
	 */
	public void setVitA(Double vitA) {
		this.vitA = vitA;
	}

	/** Getter
	 * @return the vitD
	 */
	public Double getVitD() {
		return vitD;
	}

	/** Setter
	 * @param vitD the vitD to set
	 */
	public void setVitD(Double vitD) {
		this.vitD = vitD;
	}

	/** Getter
	 * @return the vitE
	 */
	public Double getVitE() {
		return vitE;
	}

	/** Setter
	 * @param vitE the vitE to set
	 */
	public void setVitE(Double vitE) {
		this.vitE = vitE;
	}

	/** Getter
	 * @return the vitK
	 */
	public Double getVitK() {
		return vitK;
	}

	/** Setter
	 * @param vitK the vitK to set
	 */
	public void setVitK(Double vitK) {
		this.vitK = vitK;
	}

	/** Getter
	 * @return the vitC
	 */
	public Double getVitC() {
		return vitC;
	}

	/** Setter
	 * @param vitC the vitC to set
	 */
	public void setVitC(Double vitC) {
		this.vitC = vitC;
	}

	/** Getter
	 * @return the vitB1
	 */
	public Double getVitB1() {
		return vitB1;
	}

	/** Setter
	 * @param vitB1 the vitB1 to set
	 */
	public void setVitB1(Double vitB1) {
		this.vitB1 = vitB1;
	}

	/** Getter
	 * @return the vitB2
	 */
	public Double getVitB2() {
		return vitB2;
	}

	/** Setter
	 * @param vitB2 the vitB2 to set
	 */
	public void setVitB2(Double vitB2) {
		this.vitB2 = vitB2;
	}

	/** Getter
	 * @return the vitPP
	 */
	public Double getVitPP() {
		return vitPP;
	}

	/** Setter
	 * @param vitPP the vitPP to set
	 */
	public void setVitPP(Double vitPP) {
		this.vitPP = vitPP;
	}

	/** Getter
	 * @return the vitB6
	 */
	public Double getVitB6() {
		return vitB6;
	}

	/** Setter
	 * @param vitB6 the vitB6 to set
	 */
	public void setVitB6(Double vitB6) {
		this.vitB6 = vitB6;
	}

	/** Getter
	 * @return the vitB9
	 */
	public Double getVitB9() {
		return vitB9;
	}

	/** Setter
	 * @param vitB9 the vitB9 to set
	 */
	public void setVitB9(Double vitB9) {
		this.vitB9 = vitB9;
	}

	/** Getter
	 * @return the vitB12
	 */
	public Double getVitB12() {
		return vitB12;
	}

	/** Setter
	 * @param vitB12 the vitB12 to set
	 */
	public void setVitB12(Double vitB12) {
		this.vitB12 = vitB12;
	}

	/** Getter
	 * @return the calcium
	 */
	public Double getCalcium() {
		return calcium;
	}

	/** Setter
	 * @param calcium the calcium to set
	 */
	public void setCalcium(Double calcium) {
		this.calcium = calcium;
	}

	/** Getter
	 * @return the magnesium
	 */
	public Double getMagnesium() {
		return magnesium;
	}

	/** Setter
	 * @param magnesium the magnesium to set
	 */
	public void setMagnesium(Double magnesium) {
		this.magnesium = magnesium;
	}

	/** Getter
	 * @return the fer
	 */
	public Double getFer() {
		return fer;
	}

	/** Setter
	 * @param fer the fer to set
	 */
	public void setFer(Double fer) {
		this.fer = fer;
	}

	/** Getter
	 * @return the betaCarotene
	 */
	public Double getBetaCarotene() {
		return betaCarotene;
	}

	/** Setter
	 * @param betaCarotene the betaCarotene to set
	 */
	public void setBetaCarotene(Double betaCarotene) {
		this.betaCarotene = betaCarotene;
	}

	/** Getter
	 * @return the presenceHuilePalme
	 */
	public boolean isPresenceHuilePalme() {
		return presenceHuilePalme;
	}

	/** Setter
	 * @param presenceHuilePalme the presenceHuilePalme to set
	 */
	public void setPresenceHuilePalme(boolean presenceHuilePalme) {
		this.presenceHuilePalme = presenceHuilePalme;
	}

	/** Getter
	 * @return the pourcentageFruitsLegumes
	 */
	public Double getPourcentageFruitsLegumes() {
		return pourcentageFruitsLegumes;
	}

	/** Setter
	 * @param pourcentageFruitsLegumes the pourcentageFruitsLegumes to set
	 */
	public void setPourcentageFruitsLegumes(Double pourcentageFruitsLegumes) {
		this.pourcentageFruitsLegumes = pourcentageFruitsLegumes;
	}

	@Override
	public String toString() {
		return "Produit [nom=" + nom + ", categorie=" + categorie + ", marque=" + marque + ", scoreNutritionnel="
				+ scoreNutritionnel + ", listeIngredients=" + listeIngredients + ", listeAllergenes=" + listeAllergenes
				+ ", listeAdditifs=" + listeAdditifs + ", energie=" + energie + ", graisse=" + graisse
				+ ", graisseSaturee=" + graisseSaturee + ", hydratesCarbones=" + hydratesCarbones + ", sucres=" + sucres
				+ ", fibres=" + fibres + ", proteines=" + proteines + ", sel=" + sel + "]";
	}
	
	/**
	 * Comparateur en fonction du score nutritionnel
	 */
	@Override
	public int compareTo(Produit p) {
		return ((Character)p.getScoreNutritionnel()).compareTo(this.getScoreNutritionnel());
	}

}
