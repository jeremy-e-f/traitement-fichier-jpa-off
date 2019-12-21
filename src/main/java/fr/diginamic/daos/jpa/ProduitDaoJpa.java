package fr.diginamic.daos.jpa;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.AppSettings;
import fr.diginamic.daos.AdditifDao;
import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.daos.CategorieDao;
import fr.diginamic.daos.IngredientDao;
import fr.diginamic.daos.MarqueDao;
import fr.diginamic.daos.ProduitDao;
import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.entities.Produit;
import fr.diginamic.exceptions.FunctionalException;

public class ProduitDaoJpa implements ProduitDao {
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	/** Entity Manager */
	private EntityManager em;
	
	public ProduitDaoJpa(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public ProduitDaoJpa(EntityManager em){
		this.em= em;
	}
	
	@Override
	public Produit getByNameMarque(String nomProduit, String nomMarque) {
		
		/** On vérifie les paramètres */
		if(nomProduit== null || nomMarque== null){
			LOG.error("Nom du produit ou de la marque manquante.");
			return null;
		}
		nomProduit= nomProduit.trim();
		nomMarque= nomMarque.trim();
		
		try{
			TypedQuery<Produit> query = em.createQuery("select p from Produit p JOIN p.marque mq WHERE p.nom like ?1 AND mq.nom like ?2", Produit.class);
			query.setParameter(1, nomProduit);
			query.setParameter(2, nomMarque);
			return query.getSingleResult();
		}catch(NoResultException nre){
			
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int getId(String nomProduit, String nomMarque) {
		Produit produit= getByNameMarque(nomProduit, nomMarque);
		if(produit!= null){
			return produit.getId();
		}
		return 0;
	}

	@Override
	public void insert(Produit produit) throws FunctionalException, SQLException{
		if(produit== null || produit.getMarque()== null){
			throw new FunctionalException("Valeur nulle!");
		}
		
		/** On vérifie si le produit est trouvable dans ma base de données */
		Produit prod= this.getByNameMarque(produit.getNom(), produit.getMarque().getNom());
		/** S'il ne l'est pas, on l'ajoute */
		if(prod== null){
		
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
			
			em.persist(produit);
		}else{
			produit.setId(prod.getId());
		}
	}

	@Override
	public int update(Produit produit) throws FunctionalException{
		if(produit== null){
			throw new FunctionalException("Valeur nulle!");
		}
		em.merge(produit);
		return 1;
	}

	@Override
	public boolean delete(Produit produit) throws FunctionalException {
		if(produit== null){
			throw new FunctionalException("Valeur nulle!");
		}
		Produit prod= em.find(Produit.class, produit.getId());
		if(prod!= null){
			em.remove(prod);
		}
		return true;
	}

}
