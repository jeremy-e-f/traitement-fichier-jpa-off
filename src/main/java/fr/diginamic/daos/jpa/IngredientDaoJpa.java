package fr.diginamic.daos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.AppSettings;
import fr.diginamic.daos.IngredientDao;
import fr.diginamic.entities.Ingredient;

public class IngredientDaoJpa implements IngredientDao {

	/** Entity Manager */
	private EntityManager em;
	
	public IngredientDaoJpa(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public IngredientDaoJpa(EntityManager em){
		this.em= em;
	}
	
	@Override
	public List<Ingredient> extraire(){
		try{
			TypedQuery<Ingredient> query = em.createQuery("select ing from Ingredient ing", Ingredient.class);
			return query.getResultList();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Ingredient getById(int id){
		try{
			TypedQuery<Ingredient> query = em.createQuery("select ing from Ingredient ing where ing.id= ?1", Ingredient.class);
			query.setParameter(1, id);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Ingredient getByName(String name){
		try{
			TypedQuery<Ingredient> query = em.createQuery("select ing from Ingredient ing where ing.libelle like ?1", Ingredient.class);
			query.setParameter(1, name);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public int insert(Ingredient ingredient){
		/** Si l'objet n'est pas managé ou n'a pas d'id dans la base de données */
		if(ingredient.getId()== 0){
			Ingredient ing= this.getByName(ingredient.getLibelle());
			/** Si l'objet est pas trouvable dans la base de données */
			if(ing!= null){
				ingredient.setId(ing.getId());
			/** Sinon on l'ajoute dans la base de données */
			}else{
				em.persist(ingredient);
			}
		}
		return ingredient.getId();
	}

	@Override
	public int update(Ingredient ingredient){
		Ingredient ing= em.find(Ingredient.class, ingredient.getId());
		if(ing!= null){
			ing.setLibelle(ingredient.getLibelle());
		}
		return 1;
	}

	@Override
	public boolean delete(Ingredient ingredient){
		Ingredient ing= em.find(Ingredient.class, ingredient.getId());
		if(ing!= null){
			em.remove(ing);
		}
		return true;
	}

}
