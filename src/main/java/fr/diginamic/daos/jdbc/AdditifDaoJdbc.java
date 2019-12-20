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

import fr.diginamic.daos.AdditifDao;
import fr.diginamic.entities.Additif;
import fr.diginamic.jdbc.ConnectionJDBC;

public class AdditifDaoJdbc implements AdditifDao {

	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqSelect= null;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public AdditifDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public AdditifDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			preReqSelect = connection.prepareStatement("SELECT ID, LIBELLE FROM ADDITIF WHERE ID = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO ADDITIF(ID, LIBELLE) VALUES(?,?);");
			
			preReqUpdate = connection.prepareStatement("UPDATE ADDITIF SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM ADDITIF WHERE ID = ?;");
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
	public List<Additif> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet curseur= null;
		List<Additif> listeAdditifs= new ArrayList<Additif>();
		
		monStatement = connection.createStatement();
		curseur= monStatement.executeQuery("SELECT * FROM ADDITIF;");
		while(curseur.next()){
			listeAdditifs.add(new Additif(curseur.getInt("ID"), curseur.getString("LIBELLE")));
		}
		
		return listeAdditifs;
	}
	
	@Override
	public Additif getById(int id) throws SQLException {
		Additif additif= null;
		
		preReqSelect.setInt(1, id);
		ResultSet rs= preReqSelect.executeQuery();
		if(rs.next()){
			additif= new Additif(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		return additif;
	}

	@Override
	public void insert(Additif additif) throws SQLException {
		if(additif== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqInsert.setInt(i++, additif.getId());
		preReqInsert.setString(i++, additif.getLibelle());
		preReqInsert.executeUpdate();
	}

	@Override
	public int update(Additif additif) throws SQLException {
		int retour= 0;
		if(additif== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, additif.getLibelle());
		preReqUpdate.setInt(i++, additif.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Additif additif) throws SQLException {
		int retour= 0;
		
		if(additif== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, additif.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
