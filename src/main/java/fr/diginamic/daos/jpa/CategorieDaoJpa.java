package fr.diginamic.daos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.AppSettings;
import fr.diginamic.daos.CategorieDao;
import fr.diginamic.entities.Categorie;

public class CategorieDaoJpa implements CategorieDao {

	/** Entity Manager */
	private EntityManager em;
	
	public CategorieDaoJpa(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public CategorieDaoJpa(EntityManager em){
		this.em= em;
	}
	
	@Override
	public List<Categorie> extraire(){
		try{
			TypedQuery<Categorie> query = em.createQuery("select c from Categorie c", Categorie.class);
			return query.getResultList();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Categorie getById(int id){
		try{
			TypedQuery<Categorie> query = em.createQuery("select c from Categorie c where c.id= ?1", Categorie.class);
			query.setParameter(1, id);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Categorie getByName(String name){
		try{
			TypedQuery<Categorie> query = em.createQuery("select c from Categorie c where c.libelle like ?1", Categorie.class);
			query.setParameter(1, name);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public int insert(Categorie categorie){
		/** Si l'objet n'est pas managé ou n'a pas d'id dans la base de données */
		if(categorie.getId()== 0){
			Categorie cat= this.getByName(categorie.getLibelle());
			/** Si l'objet est pas trouvable dans la base de données */
			if(cat!= null){
				categorie.setId(cat.getId());
			/** Sinon on l'ajoute dans la base de données */
			}else{
				em.persist(categorie);
			}
		}
		return categorie.getId();
	}

	@Override
	public int update(Categorie categorie){
		Categorie cat= em.find(Categorie.class, categorie.getId());
		if(cat!= null){
			cat.setLibelle(categorie.getLibelle());
		}
		return 1;
	}

	@Override
	public boolean delete(Categorie categorie){
		Categorie cat= em.find(Categorie.class, categorie.getId());
		if(cat!= null){
			em.remove(cat);
		}
		return true;
	}

}
