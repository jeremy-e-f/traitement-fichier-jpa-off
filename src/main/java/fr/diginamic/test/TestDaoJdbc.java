package fr.diginamic.test;

import org.junit.Test;

import fr.diginamic.daos.ProduitDao;
import fr.diginamic.daos.jdbc.ProduitDaoJdbc;
import fr.diginamic.entities.Produit;

/**
 * Test des Daos JDBC
 * @author DIGINAMIC
 *
 */
public class TestDaoJdbc {

	/** Test extraction d'un produit */
	@Test
	public void produitDaoGetById(){
		ProduitDao produitDao= new ProduitDaoJdbc();
		Produit produit= null;
		produit = produitDao.getByNameMarque("Pepites de graines germés", "BIScru");
		System.out.println(produit);
		assert produit!= null;
	}
	
	
}
