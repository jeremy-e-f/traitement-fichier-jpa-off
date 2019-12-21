package fr.diginamic.daos.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.diginamic.AppSettings;
import fr.diginamic.daos.AdditifDao;
import fr.diginamic.entities.Additif;

public class AdditifDaoJpa implements AdditifDao {

	/** Entity Manager */
	private EntityManager em;
	
	public AdditifDaoJpa(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(AppSettings.PUNAME);
		this.em = entityManagerFactory.createEntityManager();
	}
	
	public AdditifDaoJpa(EntityManager em){
		this.em= em;
	}
	
	@Override
	public List<Additif> extraire(){
		try{
			TypedQuery<Additif> query = em.createQuery("select add from Additif add", Additif.class);
			return query.getResultList();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Additif getById(int id){
		try{
			TypedQuery<Additif> query = em.createQuery("select add from Additif add where add.id= ?1", Additif.class);
			query.setParameter(1, id);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}
	
	@Override
	public Additif getByName(String name){
		try{
			TypedQuery<Additif> query = em.createQuery("select add from Additif add where add.libelle like ?1", Additif.class);
			query.setParameter(1, name);
			return query.getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}

	@Override
	public int insert(Additif additif){
		/** Si l'objet n'est pas managé ou n'a pas d'id dans la base de données */
		if(additif.getId()== 0){
			Additif add= this.getByName(additif.getLibelle());
			/** Si l'objet est pas trouvable dans la base de données */
			if(add!= null){
				additif.setId(add.getId());  
			/** Sinon on l'ajoute dans la base de données */
			}else{
				em.persist(additif);
			}
		}
		return additif.getId();
	}

	@Override
	public int update(Additif additif){
		Additif add= em.find(Additif.class, additif.getId());
		if(add!= null){
			add.setLibelle(additif.getLibelle());
		}
		return 1;
	}

	@Override
	public boolean delete(Additif additif){
		Additif add= em.find(Additif.class, additif.getId());
		if(add!= null){
			em.remove(add);
		}
		return true;
	}

}
