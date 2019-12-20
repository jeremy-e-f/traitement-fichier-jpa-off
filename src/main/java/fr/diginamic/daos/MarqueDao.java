package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Marque;

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
	 * Insère une nouvelle marque
	 * @param marque
	 * @throws SQLException 
	 */
	void insert(Marque marque) throws SQLException;
	
	/**
	 * Met à jour la marque passée en paramètre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 */
	int update(Marque marque) throws SQLException;
	
	/**
	 * Supprime la marque passée en paramètre
	 * @param marque
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Marque marque) throws SQLException;
}
