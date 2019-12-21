package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Marque;
import fr.diginamic.exceptions.FunctionalException;

public interface MarqueDao {

	/**
	 * Extrait la liste de tous les marques
	 * @return List<Marque>
	 * @throws SQLException 
	 */
	List<Marque> extraire() throws SQLException;
	
	/**
	 * Récupère une marque par son ID
	 * @return Marque
	 * @throws SQLException 
	 */
	Marque getById(int id) throws SQLException;
	
	/**
	 * Récupère une marque par son nom
	 * @return Marque
	 * @throws SQLException 
	 */
	Marque getByName(String name) throws SQLException;
	
	/**
	 * Insère une marque
	 * @param marque
	 * @return retourne l'Id de l'objet dans la base de données
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int insert(Marque marque) throws SQLException, FunctionalException;
	
	/**
	 * Met à jour la marque passée en paramètre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	int update(Marque marque) throws SQLException, FunctionalException;
	
	/**
	 * Supprime la marque passée en paramètre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 * @throws FunctionalException 
	 */
	boolean delete(Marque marque) throws SQLException, FunctionalException;
}
