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
		/** Nom du fichier .CSV contenant les informations à intégrer */
		String nomFichier= AppSettings.FILESOURCENAME;
		
		LOG.debug("Opération d'intégration commencée.");
		long debut= System.currentTimeMillis();
		
		/** Création des tables avec JPA */
		LOG.debug("Création des tables...");
		entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		
		LOG.debug("Tables créées avec succès.");
		
		/** Extraction des ressources depuis le fichier CSV */
		RessourcesFile refFile= new RessourcesFile(nomFichier);
		
		/** Enregistrement des ressources extraites dans la base de donnéees avec JPA */
		EnregistrementStockJpa(refFile);
		
		/** FIN **/
		long fin= System.currentTimeMillis();
		LOG.debug("Intégration terminée en: "+(fin-debut)+" ms.");
		
		em.close();
	}
	
	/**
	 * Permet d'enregistrer les données extraites dans la base de données en utilisant JPA
	 */
	public static void EnregistrementStockJpa(RessourcesFile refFile){
		Long debut= 0L, fin= 0L;
		int i= 0, taille= 0;
		/** On recupère les ressources extraites dans un objet de type Stock */
		Stock stock= refFile.getStock();
		
		/** Enregistrement des données */
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		
		/**
		 * Enregistrement des produits et des classes dépendantes
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
			
			/** Si la séquence s'est déroulée jusqu'au bout, on valide le tout */
			et.commit();
			
		} catch (Exception e) {
			
			LOG.error(e.getMessage());
			/** Sinon, on annule l'intégration */
			et.rollback();
		
		} finally {
			fin= System.currentTimeMillis();
			LOG.debug("=> 100% ("+taille+"/"+taille+") lignes");
			LOG.debug("Effectué avec succès en "+(fin-debut)+" ms.");
			
			em.close();
		}
		
	}

}
