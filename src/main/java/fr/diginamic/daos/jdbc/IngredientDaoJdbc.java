package fr.diginamic.daos.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.diginamic.daos.IngredientDao;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.jdbc.ConnectionJDBC;

public class IngredientDaoJdbc implements IngredientDao {

	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqSelectId= null;
	private PreparedStatement preReqSelectName= null;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public IngredientDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public IngredientDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			String generatedColumns[] = { "ID" };
			
			preReqSelectId = connection.prepareStatement("SELECT ID, LIBELLE FROM INGREDIENT WHERE ID = ?;");
			
			preReqSelectName = connection.prepareStatement("SELECT ID, LIBELLE FROM INGREDIENT WHERE LIBELLE = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT INTO INGREDIENT(LIBELLE) VALUES(?);", generatedColumns);
			
			preReqUpdate = connection.prepareStatement("UPDATE INGREDIENT SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM INGREDIENT WHERE ID = ?;");
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * On ferme les prestatements
	 */
	public void finalize(){
          try {
          	this.preReqSelectId.close();
          	this.preReqSelectName.close();
  			this.preReqInsert.close();
  			this.preReqUpdate.close();
  			this.preReqDelete.close();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
    }
	
	@Override
	public List<Ingredient> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet rs= null;
		List<Ingredient> listeIngredients= new ArrayList<Ingredient>();
		
		monStatement = connection.createStatement();
		rs= monStatement.executeQuery("SELECT * FROM INGREDIENT;");
		while(rs.next()){
			listeIngredients.add(new Ingredient(rs.getInt("ID"), rs.getString("LIBELLE")));
		}
		rs.close();
		
		return listeIngredients;
	}
	
	@Override
	public Ingredient getById(int id) throws SQLException {
		Ingredient ingredient= null;
		
		preReqSelectId.setInt(1, id);
		ResultSet rs= preReqSelectId.executeQuery();
		if(rs.next()){
			ingredient= new Ingredient(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		
		return ingredient;
	}
	
	@Override
	public Ingredient getByName(String name) throws SQLException {
		Ingredient ingredient= null;
		
		preReqSelectName.setString(1, name);
		ResultSet rs= preReqSelectName.executeQuery();
		if(rs.next()){
			ingredient= new Ingredient(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		rs.close();
		
		return ingredient;
	}

	@Override
	public int insert(Ingredient ingredient) throws SQLException {
		if(ingredient== null){
			throw new SQLException("Valeur nulle!");
		}
		
		/** Si l'index dans la base de données n'a pas été initialisé */
		if(ingredient.getId()== 0){
		
			/** On vérifie si l'objet n'existe pas déjà dans la base de données */
			Ingredient objExistant= this.getByName(ingredient.getLibelle());
			if(objExistant!= null){
				/** On le met à jour */
				ingredient.setId(objExistant.getId());
				return objExistant.getId();
			}else{
				/** Sinon on l'insère dans la base de données */
				preReqInsert.setString(1, ingredient.getLibelle());
				preReqInsert.executeUpdate();
				/** Et récupère son Id */
				ResultSet rs= preReqInsert.getGeneratedKeys();
				int id= 0;
				if(rs.next()){
					id= rs.getInt(1);
					/** On le met à jour */
					ingredient.setId(id);
				}
				rs.close();
				
				return id;
			}
		}else{
			return ingredient.getId();
		}
	}

	@Override
	public int update(Ingredient ingredient) throws SQLException {
		int retour= 0;
		if(ingredient== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, ingredient.getLibelle());
		preReqUpdate.setInt(i++, ingredient.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Ingredient ingredient) throws SQLException {
		int retour= 0;
		
		if(ingredient== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, ingredient.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
