package fr.diginamic.test;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import fr.diginamic.AppSettings;
import fr.diginamic.RessourcesFile;
import fr.diginamic.daos.AdditifDao;
import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.daos.CategorieDao;
import fr.diginamic.daos.IngredientDao;
import fr.diginamic.daos.MarqueDao;
import fr.diginamic.daos.ProduitDao;
import fr.diginamic.daos.jpa.AdditifDaoJpa;
import fr.diginamic.daos.jpa.AllergeneDaoJpa;
import fr.diginamic.daos.jpa.CategorieDaoJpa;
import fr.diginamic.daos.jpa.IngredientDaoJpa;
import fr.diginamic.daos.jpa.MarqueDaoJpa;
import fr.diginamic.daos.jpa.ProduitDaoJpa;
import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.entities.Produit;
import fr.diginamic.exceptions.FunctionalException;

/**
 * Test des Daos JDBC
 * @author DIGINAMIC
 *
 */
public class TestDaoJpa {
	
	/*@Test
	public void additifDaoGetById() throws SQLException, FunctionalException{
		AdditifDao addDao= new AdditifDaoJpa();
		Additif additif= null;
		additif = addDao.getById(1);
		System.out.println(additif);
		addDao.insert(additif);
		
		assert additif!= null;
	}*/
	
	@Test
	public void categorieDaoPersist() throws SQLException, FunctionalException{
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Categorie cat= null;
		
		try{
			et.begin();
			
			cat= new Categorie("effe");
			CategorieDao catDao= new CategorieDaoJpa(em);
			catDao.insert(cat);
			
			et.commit();
			
			System.out.println(cat);
			
		}catch(Exception e){
			System.err.println(e.getMessage());
			et.rollback();
		}finally{
			em.close();
		}
		
		assert cat.getId()!= 0;
		
		
	}
	
	/**
	 * Test enregistrement d'un produit avec JPA
	 * @throws SQLException
	 * @throws FunctionalException
	 */
	@Test
	public void produitDaoPersist() throws SQLException, FunctionalException{
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		Produit produit= null;
		
		/** Extraction des ressources depuis le fichier CSV */
		RessourcesFile refFile= new RessourcesFile(AppSettings.FILESOURCENAME);
		
		try{
			et.begin();
			
			produit= refFile.getStock().getListeProduits().get(0);
			
			MarqueDao mqDao= new MarqueDaoJpa(em);
			Marque marque= produit.getMarque();
			mqDao.insert(marque);
			System.err.println(marque);
			
			CategorieDao catDao= new CategorieDaoJpa(em);
			Categorie categorie= produit.getCategorie();
			catDao.insert(categorie);
			System.err.println(categorie);
			
			AdditifDao addDao= new AdditifDaoJpa(em);
			for(Additif additif : produit.getListeAdditifs()){
				addDao.insert(additif);
				System.err.println(additif);
			}
			
			AllergeneDao allDao= new AllergeneDaoJpa(em);
			for(Allergene allergene : produit.getListeAllergenes()){
				allDao.insert(allergene);
				System.err.println(allergene);
			}
			
			IngredientDao ingDao= new IngredientDaoJpa(em);
			for(Ingredient ingredient : produit.getListeIngredients()){
				ingDao.insert(ingredient);
				System.err.println(ingredient);
			}
			
			ProduitDao prodDao= new ProduitDaoJpa(em);
			prodDao.insert(produit);
			/*
			Marque marque= produit.getMarque();
			em.persist(marque);
			System.err.println(marque);
			
			Categorie categorie= produit.getCategorie();
			em.persist(categorie);
			System.err.println(categorie);
			
			for(Additif additif : produit.getListeAdditifs()){
				em.persist(additif);
				System.err.println(additif);
			}
			
			for(Allergene allergene : produit.getListeAllergenes()){
				em.persist(allergene);
				System.err.println(allergene);
			}
			
			for(Ingredient ingredient : produit.getListeIngredients()){
				em.persist(ingredient);
				System.err.println(ingredient);
			}
			
			em.persist(produit);*/
			System.err.println(produit);
			
			et.commit();
			
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			et.rollback();
		}finally{
			em.close();
		}
		
		System.err.println(produit);
		assert produit.getId()!= 0;
	}
	
	
}
