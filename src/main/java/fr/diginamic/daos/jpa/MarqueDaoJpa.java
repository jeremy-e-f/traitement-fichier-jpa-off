package fr.diginamic.daos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.AppSettings;
import fr.diginamic.daos.MarqueDao;
import fr.diginamic.entities.Marque;

public class MarqueDaoJpa implements MarqueDao {

	/** Entity Manager */
	private EntityManager em;
	
	public MarqueDaoJpa(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public MarqueDaoJpa(EntityManager em){
		this.em= em;
	}
	
	@Override
	public List<Marque> extraire(){
		try{
			TypedQuery<Marque> query = em.createQuery("select mq from Marque mq", Marque.class);
			return query.getResultList();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Marque getById(int id){
		try{
			TypedQuery<Marque> query = em.createQuery("select mq from Marque mq where mq.id= ?1", Marque.class);
			query.setParameter(1, id);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Marque getByName(String name){
		try{
			TypedQuery<Marque> query = em.createQuery("select mq from Marque mq where mq.nom like ?1", Marque.class);
			query.setParameter(1, name);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public int insert(Marque marque){
		/** Si l'objet n'est pas managé ou n'a pas d'id dans la base de données */
		if(marque.getId()== 0){
			Marque mq= this.getByName(marque.getNom());
			/** Si l'objet est pas trouvable dans la base de données */
			if(mq!= null){
				marque.setId(mq.getId());  
			/** Sinon on l'ajoute dans la base de données */
			}else{
				em.persist(marque);
			}
		}
		return marque.getId();
	}

	@Override
	public int update(Marque marque){
		Marque mq= em.find(Marque.class, marque.getId());
		if(mq!= null){
			mq.setNom(marque.getNom());
		}
		return 1;
	}

	@Override
	public boolean delete(Marque marque){
		Marque mq= em.find(Marque.class, marque.getId());
		if(mq!= null){
			em.remove(mq);
		}
		return true;
	}

}
