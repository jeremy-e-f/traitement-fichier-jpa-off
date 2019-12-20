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
	private PreparedStatement preReqSelect= null;
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
			preReqSelect = connection.prepareStatement("SELECT ID, LIBELLE FROM INGREDIENT WHERE ID = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO INGREDIENT(ID, LIBELLE) VALUES(?,?);");
			
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
          	this.preReqSelect.close();
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
		ResultSet curseur= null;
		List<Ingredient> listeIngredients= new ArrayList<Ingredient>();
		
		monStatement = connection.createStatement();
		curseur= monStatement.executeQuery("SELECT * FROM INGREDIENT;");
		while(curseur.next()){
			listeIngredients.add(new Ingredient(curseur.getInt("ID"), curseur.getString("LIBELLE")));
		}
		
		return listeIngredients;
	}
	
	@Override
	public Ingredient getById(int id) throws SQLException {
		Ingredient ingredient= null;
		
		preReqSelect.setInt(1, id);
		ResultSet rs= preReqSelect.executeQuery();
		if(rs.next()){
			ingredient= new Ingredient(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		return ingredient;
	}

	@Override
	public void insert(Ingredient ingredient) throws SQLException {
		if(ingredient== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqInsert.setInt(i++, ingredient.getId());
		preReqInsert.setString(i++, ingredient.getLibelle());
		preReqInsert.executeUpdate();
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
