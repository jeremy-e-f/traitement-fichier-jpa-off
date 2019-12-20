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

import fr.diginamic.daos.AllergeneDao;
import fr.diginamic.entities.Allergene;
import fr.diginamic.jdbc.ConnectionJDBC;

public class AllergeneDaoJdbc implements AllergeneDao {

	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqSelect= null;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public AllergeneDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public AllergeneDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			preReqSelect = connection.prepareStatement("SELECT ID, LIBELLE FROM ALLERGENE WHERE ID = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO ALLERGENE(ID, LIBELLE) VALUES(?,?);");
			
			preReqUpdate = connection.prepareStatement("UPDATE ALLERGENE SET LIBELLE = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM ALLERGENE WHERE ID = ?;");
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
	public List<Allergene> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet curseur= null;
		List<Allergene> listeAllergenes= new ArrayList<Allergene>();
		
		monStatement = connection.createStatement();
		curseur= monStatement.executeQuery("SELECT * FROM ALLERGENE;");
		while(curseur.next()){
			listeAllergenes.add(new Allergene(curseur.getInt("ID"), curseur.getString("LIBELLE")));
		}
		
		return listeAllergenes;
	}
	
	@Override
	public Allergene getById(int id) throws SQLException {
		Allergene allergene= null;
		
		preReqSelect.setInt(1, id);
		ResultSet rs= preReqSelect.executeQuery();
		if(rs.next()){
			allergene= new Allergene(rs.getInt("ID"), rs.getString("LIBELLE"));
		}
		return allergene;
	}

	@Override
	public void insert(Allergene allergene) throws SQLException {
		if(allergene== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqInsert.setInt(i++, allergene.getId());
		preReqInsert.setString(i++, allergene.getLibelle());
		preReqInsert.executeUpdate();
	}

	@Override
	public int update(Allergene allergene) throws SQLException {
		int retour= 0;
		if(allergene== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, allergene.getLibelle());
		preReqUpdate.setInt(i++, allergene.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Allergene allergene) throws SQLException {
		int retour= 0;
		
		if(allergene== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, allergene.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}

}
