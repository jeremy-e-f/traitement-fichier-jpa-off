package fr.diginamic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.entities.Produit;
import fr.diginamic.entities.Stock;

/**
 * Permet d'importer et d'extraitre les ressources présentes dans le fichier .CSV 
 * Et stocke les données en mémoire dans un objet Stock
 * @author DIGINAMIC
 *
 */
public class RessourcesFile{
	/** LOGGER */
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	private Stock stock; 			/** Objet contenant les ressources extraites du fichier */
	
	private String nomFichier; 		/** Nom du fichier .csv contenant les ressources à extraire */
	
	/**
	 * Constructeur
	 */
	public RessourcesFile(String nomFichier){
		this.nomFichier= nomFichier;
		this.stock= extraireProduitsDAO();
	}

	/** Getter
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/** Getter
	 * @return the nomFichier
	 */
	public String getNomFichier() {
		return nomFichier;
	}

	/**
	 * Méthode permettant d'extraire les ressources contenu dans "fichier" passé en paramètre
	 * Renvoie un objet "Stock" contenant ces ressources
	 * @return
	 */
	public Stock extraireProduitsDAO(){
		Long debut= 0L, fin= 0L;
		debut= System.currentTimeMillis();
		LOG.debug("Extraction des ressources depuis le fichier \""+nomFichier+"\" commencée...");
		
		this.stock= new Stock();
		
		File file = new File(nomFichier);
		try {
			/** On extrait les lignes du fichier */
			List<String> lignes = FileUtils.readLines(file, "UTF-8");
			//for(int id= 1; id< 1000; id++){
			for(int id= 1; id< lignes.size(); id++){
				//System.out.println(lignes.get(id));
				/** On les décompose */
				String elements[]= lignes.get(id).split("\\|");
				
				/** On définit tous les éléments à inscrire */
				String nomProduit= elements[0].trim();
				char scoreNutritionnel= elements[1].trim().charAt(0);
				Categorie categorie= stock.addCategorie(elements[2].trim());
				Marque marque= stock.addMarque(elements[3].trim());
				String libellesIngredient= elements[4].trim();
				Double energie= null;
				Double graisse= null;
				Double graisseSaturee= null;
				Double hydratesCarbones= null;
				Double sucres= null;
				Double fibres= null;
				Double proteines= null;
				Double sel= null;
				Double vitA= null;
				Double vitD= null;
				Double vitE= null;
				Double vitK= null;
				Double vitC= null;
				Double vitB1= null;
				Double vitB2= null;
				Double vitPP= null;
				Double vitB6= null;
				Double vitB9= null;
				Double vitB12= null;
				Double calcium= null;
				Double magnesium= null; 
				Double fer= null;
				Double betaCarotene= null;
				boolean presenceHuilePalme= false;
				Double pourcentageFruitsLegumes= null;
				
				/** On traite les éléments */
				try{
					energie= ((elements[5].trim().equals(""))?null:Double.parseDouble(elements[5].trim()));
					graisse= ((elements[6].trim().equals(""))?null:Double.parseDouble(elements[6].trim()));
					graisseSaturee= ((elements[7].trim().equals(""))?null:Double.parseDouble(elements[7].trim()));
					hydratesCarbones= ((elements[8].trim().equals(""))?null:Double.parseDouble(elements[8].trim()));
					sucres= ((elements[9].trim().equals(""))?null:Double.parseDouble(elements[9].trim()));
					fibres= ((elements[10].trim().equals(""))?null:Double.parseDouble(elements[10].trim()));
					proteines= ((elements[11].trim().equals(""))?null:Double.parseDouble(elements[11].trim()));
					sel= ((elements[12].trim().equals(""))?null:Double.parseDouble(elements[12].trim()));
					vitA= ((elements[13].trim().equals(""))?null:Double.parseDouble(elements[13].trim()));
					vitD= ((elements[14].trim().equals(""))?null:Double.parseDouble(elements[14].trim()));
					vitE= ((elements[15].trim().equals(""))?null:Double.parseDouble(elements[15].trim()));
					vitK= ((elements[16].trim().equals(""))?null:Double.parseDouble(elements[16].trim()));
					vitC= ((elements[17].trim().equals(""))?null:Double.parseDouble(elements[17].trim()));
					vitB1= ((elements[18].trim().equals(""))?null:Double.parseDouble(elements[18].trim()));
					vitB2= ((elements[19].trim().equals(""))?null:Double.parseDouble(elements[19].trim()));
					vitPP= ((elements[20].trim().equals(""))?null:Double.parseDouble(elements[20].trim()));
					vitB6= ((elements[21].trim().equals(""))?null:Double.parseDouble(elements[21].trim()));
					vitB9= ((elements[22].trim().equals(""))?null:Double.parseDouble(elements[22].trim()));
					vitB12= ((elements[23].trim().equals(""))?null:Double.parseDouble(elements[23].trim()));
					calcium= ((elements[24].trim().equals(""))?null:Double.parseDouble(elements[24].trim()));
					magnesium= ((elements[25].trim().equals(""))?null:Double.parseDouble(elements[25].trim())); 
					fer= ((elements[26].trim().equals(""))?null:Double.parseDouble(elements[26].trim()));
					betaCarotene= ((elements[27].trim().equals(""))?null:Double.parseDouble(elements[27].trim()));
					presenceHuilePalme= ((elements[28].trim().equals("0"))?false:true);
					pourcentageFruitsLegumes= ((elements[29].trim().equals(""))?null:Double.parseDouble(elements[29].trim()));
				}catch(Exception e){
					LOG.error("\n"+e.getMessage());
				}
				
				String libellesAllergene= elements[30].trim();
				String libellesAdditif= elements[31].trim();
				
				/** On cree le nouveau produit avec ses dépendances */
				Produit produit= new Produit(id, nomProduit, categorie, marque, scoreNutritionnel, traitementIngredient(libellesIngredient), traitementAllergene(libellesAllergene), 
						traitementAdditif(libellesAdditif), energie, graisse, graisseSaturee, hydratesCarbones, sucres, fibres, proteines, sel, vitA ,vitD ,vitE ,vitK ,vitC ,
						vitB1 ,vitB2 ,vitPP ,vitB6 ,vitB9 ,vitB12 ,calcium ,magnesium, fer ,betaCarotene, presenceHuilePalme, pourcentageFruitsLegumes);
				
				/** On l'ajoute au stock */
				stock.addProduit(produit);
			}
			
			fin= System.currentTimeMillis();
			LOG.debug("Exécutée avec succès en "+(fin-debut)+" ms: "+lignes.size()+" lignes traitées.");
			
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return stock;
	}
	
	/** 
	 * Permet de décomposer la chaîne correspondant aux allergènes et de l'ajouter au stock le cas échéant
	 * Renvoie la liste des allergènes du produit.
	 */
	private ArrayList<Allergene> traitementAllergene(String libellesAllergene){
		ArrayList<Allergene> listeAllergenes= new ArrayList<Allergene>();
		String[] listeLibelles= null;
		/** On nettoie la chaine avant de la séparer */
		listeLibelles= libellesAllergene.replaceAll("\\[|\\]|fr:\\s*|\\*","").replace(", ",",").split(",");
		for(int i= 0; i< listeLibelles.length; i++){
			String libelle= listeLibelles[i].trim();
			if(!libelle.isEmpty()){
				listeAllergenes.add(this.stock.addAllergene(libelle.toLowerCase()));
			}
		}
		return listeAllergenes;
	}
	
	/** 
	 * Permet de décomposer la chaîne correspondant aux additifs et de l'ajouter au stock le cas échéant
	 * Renvoie la liste des additifs du produit.
	 */
	private ArrayList<Additif> traitementAdditif(String libellesAdditif){
		ArrayList<Additif> listeAdditifs= new ArrayList<Additif>();
		String[] listeLibelles= null;
		/** On nettoie la chaine avant de la séparer */
		listeLibelles= libellesAdditif.replaceAll("\\[|\\]","").replace(", ",",").split(",");
		for(int i= 0; i< listeLibelles.length; i++){
			String libelle= listeLibelles[i].trim();
			if(!libelle.isEmpty()){
				listeAdditifs.add(this.stock.addAdditif(libelle.toLowerCase()));
			}
		}
		return listeAdditifs;
	}
	
	/** 
	 * Permet de décomposer la chaîne correspondant aux ingrédients et de l'ajouter au stock le cas échéant
	 * Renvoie la liste des ingrédients du produit.
	 */
	private ArrayList<Ingredient> traitementIngredient(String libellesIngredient){
		ArrayList<Ingredient> listeIngredients= new ArrayList<Ingredient>();
		String[] listeLibelles= null;
		/** On nettoie la chaine avant de la séparer */
		listeLibelles= libellesIngredient.replaceAll("\\[|\\]|\\.|_|\\*|[0-9]+\\s*%|\\s*\\(\\s*|\\s*\\)\\s*","").split(",|;|\\s+-\\s+");
		for(int i= 0; i< listeLibelles.length; i++){
			String libelle= listeLibelles[i].replaceAll("\\s+[0-9]+$","").trim();
			if(!libelle.isEmpty()){
				listeIngredients.add(this.stock.addIngredient(libelle.toLowerCase()));
			}
		}
		return listeIngredients;
	}
	
}
