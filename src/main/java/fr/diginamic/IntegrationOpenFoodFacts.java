package fr.diginamic;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.daos.AdditifDao;
import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.daos.CategorieDao;
import fr.diginamic.daos.IngredientDao;
import fr.diginamic.daos.MarqueDao;
import fr.diginamic.daos.ProduitDao;
import fr.diginamic.daos.jdbc.AdditifDaoJdbc;
import fr.diginamic.daos.jdbc.AllergeneDaoJdbc;
import fr.diginamic.daos.jdbc.CategorieDaoJdbc;
import fr.diginamic.daos.jdbc.IngredientDaoJdbc;
import fr.diginamic.daos.jdbc.MarqueDaoJdbc;
import fr.diginamic.daos.jdbc.ProduitDaoJdbc;
import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.entities.Produit;
import fr.diginamic.entities.Stock;
import fr.diginamic.jdbc.ConnectionJDBC;

public class IntegrationOpenFoodFacts {

	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	/** Entity Manager */
	private static EntityManager em;

	public static void main(String[] args) {
		/** Nom du fichier .CSV contenant les informations à intégrer */
		String nomFichier= "D:/Work/Programmation/JAVA/traitement-fichier-jpa-off/src/main/resources/open-food-facts.csv";
		
		LOG.debug("Opération d'intégration commencée.");
		long debut= System.currentTimeMillis();
		
		/** Création des tables avec JPA */
		LOG.debug("Création des tables...");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu_off");
		em = entityManagerFactory.createEntityManager();
		em.close();
		LOG.debug("Tables créées avec succès.");
		
		/** Extraction des ressources depuis le fichier CSV */
		RessourcesFile refFile= new RessourcesFile(nomFichier);
		/*for(Produit p : refFile.getStock().getListeProduits()){
			System.out.println(p);
		}*/
		
		/** Enregistrement des ressources extraites dans la base de donnéees avec JDBC */
		EnregistrementStockJdbc(refFile);
		
		/** FIN **/
		long fin= System.currentTimeMillis();
		LOG.debug("Intégration terminée en: "+(fin-debut)+" ms.");
	}
	
	/**
	 * Permet d'enregistrer les données extraites dans la base de données en utilisant JDBC
	 */
	public static void EnregistrementStockJdbc(RessourcesFile refFile){
		/** On initialise la connection à la base de données */
		Connection connection= ConnectionJDBC.getInstance();
		Long debut= 0L, fin= 0L;
		/** On recupère les ressources extraites dans un objet de type Stock */
		Stock stock= refFile.getStock();
		
		try {
			/** On désactive l'auto-commit */
			connection.setAutoCommit(false);
			
			/**
			 * Enregistrement des ingrédients
			 */
			LOG.debug("Enregistrement des ingrédients...");
			debut= System.currentTimeMillis();
			IngredientDao ingDao= new IngredientDaoJdbc(connection);
			for(Ingredient ing : stock.getListeIngredients().values()){
				ingDao.insert(ing);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des allergènes
			 */
			LOG.debug("Enregistrement des allergènes...");
			debut= System.currentTimeMillis();
			AllergeneDao allDao= new AllergeneDaoJdbc(connection);
			for(Allergene all : stock.getListeAllergenes().values()){
				allDao.insert(all);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des additifs
			 */
			LOG.debug("Enregistrement des additifs...");
			debut= System.currentTimeMillis();
			AdditifDao addDao= new AdditifDaoJdbc(connection);
			for(Additif add : stock.getListeAdditifs().values()){
				addDao.insert(add);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des marques
			 */
			LOG.debug("Enregistrement des marques...");
			debut= System.currentTimeMillis();
			MarqueDao mqDao= new MarqueDaoJdbc(connection);
			for(Marque mq : stock.getListeMarques().values()){
				mqDao.insert(mq);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des catégories
			 */
			LOG.debug("Enregistrement des catégories...");
			debut= System.currentTimeMillis();
			CategorieDao catDao= new CategorieDaoJdbc(connection);
			for(Categorie cat : stock.getListeCategories().values()){
				catDao.insert(cat);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des produits
			 */
			LOG.debug("Enregistrement des produits...");
			debut= System.currentTimeMillis();
			ProduitDao prodDao= new ProduitDaoJdbc(connection);
			int i= 0, taille= stock.getListeProduits().size();
			for(Produit prod : stock.getListeProduits()){
				if(i%200== 0){
					LOG.debug("=> "+Math.floor((double)i/taille*10000)/100+"% ("+i+"/"+taille+") lignes");
				}
				prodDao.insert(prod);
				i++;
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			/** Si la séquence s'est déroulée jusqu'au bout, on valide le tout */
			connection.commit();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			/** Sinon, on annule l'intégration */
			try {
				connection.rollback();
			} catch (SQLException e1) {
				LOG.error(e1.getMessage());
			}
		} finally {
			/** On ferme la connection */
			try {
				connection.close();
			} catch (SQLException e) {
				LOG.error(e.getMessage());
			}
		}
		
	}

}
