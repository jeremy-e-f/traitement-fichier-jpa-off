package fr.diginamic.daos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.AppSettings;
import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.entities.Allergene;

public class AllergeneDaoJpa implements AllergeneDao {

	/** Entity Manager */
	private EntityManager em;
	
	public AllergeneDaoJpa(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public AllergeneDaoJpa(EntityManager em){
		this.em= em;
	}
	
	@Override
	public List<Allergene> extraire(){
		try{
			TypedQuery<Allergene> query = em.createQuery("select al from Allergene al", Allergene.class);
			return query.getResultList();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Allergene getById(int id){
		try{
			TypedQuery<Allergene> query = em.createQuery("select al from Allergene al where al.id= ?1", Allergene.class);
			query.setParameter(1, id);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Allergene getByName(String name){
		try{
			TypedQuery<Allergene> query = em.createQuery("select al from Allergene al where al.libelle like ?1", Allergene.class);
			query.setParameter(1, name);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public int insert(Allergene allergene){
		/** Si l'objet n'est pas managé ou n'a pas d'id dans la base de données */
		if(allergene.getId()== 0){
			Allergene all= this.getByName(allergene.getLibelle());
			/** Si l'objet est pas trouvable dans la base de données */
			if(all!= null){
				allergene.setId(all.getId());
			/** Sinon on l'ajoute dans la base de données */
			}else{
				em.persist(allergene);
			}
		}
		return allergene.getId();
	}

	@Override
	public int update(Allergene allergene){
		Allergene all= em.find(Allergene.class, allergene.getId());
		if(all!= null){
			all.setLibelle(allergene.getLibelle());
		}
		return 1;
	}

	@Override
	public boolean delete(Allergene allergene){
		Allergene all= em.find(Allergene.class, allergene.getId());
		if(all!= null){
			em.remove(all);
		}
		return true;
	}

}
