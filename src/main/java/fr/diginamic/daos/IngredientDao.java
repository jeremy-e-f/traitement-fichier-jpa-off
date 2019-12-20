package fr.diginamic.daos;

import java.sql.SQLException;
import java.util.List;

import fr.diginamic.entities.Ingredient;

public interface IngredientDao {

	/**
	 * Extrait la liste de tous les ingr�dients
	 * @return List<Ingredient>
	 * @throws SQLException 
	 */
	List<Ingredient> extraire() throws SQLException;
	
	/**
	 * R�cup�re un ingr�dient par son ID
	 * @return Ingredient
	 * @throws SQLException 
	 */
	Ingredient getById(int id) throws SQLException;
	
	/**
	 * R�cup�re un ingr�dient par son libell�
	 * @return Ingredient
	 * @throws SQLException 
	 */
	Ingredient getByName(String name) throws SQLException;
	
	/**
	 * Ins�re un nouvel ingr�dient
	 * @param ingredient
	 * @return retourne l'Id de l'objet dans la base de donn�es
	 * @throws SQLException 
	 */
	int insert(Ingredient ingredient) throws SQLException;
	
	/**
	 * Met � jour l'ingr�dient pass� en param�tre
	 * @param ingredient
	 * @return
	 * @throws SQLException 
	 */
	int update(Ingredient ingredient) throws SQLException;
	
	/**
	 * Supprime l'ingredient pass� en param�tre
	 * @param ingredient
	 * @return
	 * @throws SQLException 
	 */
	boolean delete(Ingredient ingredient) throws SQLException;
}
