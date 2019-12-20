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
		/** Nom du fichier .CSV contenant les informations � int�grer */
		String nomFichier= "D:/Work/Programmation/JAVA/traitement-fichier-jpa-off/src/main/resources/open-food-facts.csv";
		
		LOG.debug("Op�ration d'int�gration commenc�e.");
		long debut= System.currentTimeMillis();
		
		/** Cr�ation des tables avec JPA */
		LOG.debug("Cr�ation des tables...");
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu_off");
		em = entityManagerFactory.createEntityManager();
		em.close();
		LOG.debug("Tables cr��es avec succ�s.");
		
		/** Extraction des ressources depuis le fichier CSV */
		RessourcesFile refFile= new RessourcesFile(nomFichier);
		/*for(Produit p : refFile.getStock().getListeProduits()){
			System.out.println(p);
		}*/
		
		/** Enregistrement des ressources extraites dans la base de donn�ees avec JDBC */
		EnregistrementStockJdbc(refFile);
		
		/** FIN **/
		long fin= System.currentTimeMillis();
		LOG.debug("Int�gration termin�e en: "+(fin-debut)+" ms.");
	}
	
	/**
	 * Permet d'enregistrer les donn�es extraites dans la base de donn�es en utilisant JDBC
	 */
	public static void EnregistrementStockJdbc(RessourcesFile refFile){
		/** On initialise la connection � la base de donn�es */
		Connection connection= ConnectionJDBC.getInstance();
		Long debut= 0L, fin= 0L;
		/** On recup�re les ressources extraites dans un objet de type Stock */
		Stock stock= refFile.getStock();
		
		try {
			/** On d�sactive l'auto-commit */
			connection.setAutoCommit(false);
			
			/**
			 * Enregistrement des ingr�dients
			 */
			LOG.debug("Enregistrement des ingr�dients...");
			debut= System.currentTimeMillis();
			IngredientDao ingDao= new IngredientDaoJdbc(connection);
			for(Ingredient ing : stock.getListeIngredients().values()){
				ingDao.insert(ing);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des allerg�nes
			 */
			LOG.debug("Enregistrement des allerg�nes...");
			debut= System.currentTimeMillis();
			AllergeneDao allDao= new AllergeneDaoJdbc(connection);
			for(Allergene all : stock.getListeAllergenes().values()){
				allDao.insert(all);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
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
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
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
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
			/**
			 * Enregistrement des cat�gories
			 */
			LOG.debug("Enregistrement des cat�gories...");
			debut= System.currentTimeMillis();
			CategorieDao catDao= new CategorieDaoJdbc(connection);
			for(Categorie cat : stock.getListeCategories().values()){
				catDao.insert(cat);
			}
			fin= System.currentTimeMillis();
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
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
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
			/** Si la s�quence s'est d�roul�e jusqu'au bout, on valide le tout */
			connection.commit();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
			/** Sinon, on annule l'int�gration */
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
