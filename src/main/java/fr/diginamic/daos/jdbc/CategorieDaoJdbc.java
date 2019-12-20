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

import fr.diginamic.daos.CategorieDao;
import fr.diginamic.entities.Categorie;
import fr.diginamic.jdbc.ConnectionJDBC;

public class CategorieDaoJdbc implements CategorieDao {
	
	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqSelect= null;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public CategorieDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public CategorieDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			preReqSelect = connection.prepareStatement("SELECT ID, LIBELLE FROM CATEGORIE WHERE ID = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO CATEGORIE(ID, LIBELLE) VALUES(?,?);");
			
			preReqUpdate = connection.prepareStatement("UPDATE CATEGORIE SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM CATEGORIE WHERE ID = ?;");
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
	public List<Categorie> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet curseur= null;
		List<Categorie> listeCategories= new ArrayList<Categorie>();
		
		monStatement = connection.createStatement();
		curseur= monStatement.executeQuery("SELECT * FROM CATEGORIE;");
		while(curseur.next()){
			int idCat= curseur.getInt("ID");
			String libelleCat= curseur.getString("LIBELLE");
			
			listeCategories.add(new Categorie(idCat, libelleCat));
		}
		
		return listeCategories;
	}
	
	@Override
	public Categorie getById(int id) throws SQLException {
		Categorie categorie= null;
		
		preReqSelect.setInt(1, id);
		ResultSet rs= preReqSelect.executeQuery();
		if(rs.next()){
			categorie= new Categorie(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		return categorie;
	}

	@Override
	public void insert(Categorie categorie) throws SQLException {
		if(categorie== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqInsert.setInt(i++, categorie.getId());
		preReqInsert.setString(i++, categorie.getLibelle());
		preReqInsert.executeUpdate();
	}

	@Override
	public int update(Categorie categorie) throws SQLException {
		int retour= 0;
		if(categorie== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, categorie.getLibelle());
		preReqUpdate.setInt(i++, categorie.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Categorie categorie) throws SQLException {
		int retour= 0;
		
		if(categorie== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, categorie.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
