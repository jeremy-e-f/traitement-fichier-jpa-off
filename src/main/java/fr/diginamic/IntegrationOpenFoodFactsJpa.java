package fr.diginamic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.daos.ProduitDao;
import fr.diginamic.daos.jpa.ProduitDaoJpa;
import fr.diginamic.entities.Produit;
import fr.diginamic.entities.Stock;

public class IntegrationOpenFoodFactsJpa {

	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	/** Entity Manager */
	private static EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		/** Nom du fichier .CSV contenant les informations � int�grer */
		String nomFichier= AppSettings.FILESOURCENAME;
		
		LOG.debug("Op�ration d'int�gration commenc�e.");
		long debut= System.currentTimeMillis();
		
		/** Cr�ation des tables avec JPA */
		LOG.debug("Cr�ation des tables...");
		entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		
		LOG.debug("Tables cr��es avec succ�s.");
		
		/** Extraction des ressources depuis le fichier CSV */
		RessourcesFile refFile= new RessourcesFile(nomFichier);
		
		/** Enregistrement des ressources extraites dans la base de donn�ees avec JPA */
		EnregistrementStockJpa(refFile);
		
		/** FIN **/
		long fin= System.currentTimeMillis();
		LOG.debug("Int�gration termin�e en: "+(fin-debut)+" ms.");
		
		em.close();
	}
	
	/**
	 * Permet d'enregistrer les donn�es extraites dans la base de donn�es en utilisant JPA
	 */
	public static void EnregistrementStockJpa(RessourcesFile refFile){
		Long debut= 0L, fin= 0L;
		int i= 0, taille= 0;
		/** On recup�re les ressources extraites dans un objet de type Stock */
		Stock stock= refFile.getStock();
		
		/** Enregistrement des donn�es */
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		/**
		 * Enregistrement des produits et des classes d�pendantes
		 */
		LOG.debug("Enregistrement des produits...");
		debut= System.currentTimeMillis();
		ProduitDao prodDao= new ProduitDaoJpa(em);
		i= 0; 
		taille= stock.getListeProduits().size();
		
		try {

			for(Produit prod : stock.getListeProduits()){
				if(i%200== 0){
					LOG.debug("=> "+Math.floor((double)i/taille*10000)/100+"% ("+i+"/"+taille+") lignes");
				}
				prodDao.insert(prod);
				i++;
				if(i==1000) break;
			}
			
			/** Si la s�quence s'est d�roul�e jusqu'au bout, on valide le tout */
			et.commit();
			
		} catch (Exception e) {
			
			LOG.error(e.getMessage());
			/** Sinon, on annule l'int�gration */
			et.rollback();
		
		} finally {
			fin= System.currentTimeMillis();
			LOG.debug("=> 100% ("+taille+"/"+taille+") lignes");
			LOG.debug("Effectu� avec succ�s en "+(fin-debut)+" ms.");
			
			em.close();
		}
		
	}

}
