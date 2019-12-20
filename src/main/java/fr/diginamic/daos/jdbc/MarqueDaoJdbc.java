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

import fr.diginamic.daos.MarqueDao;
import fr.diginamic.entities.Marque;
import fr.diginamic.jdbc.ConnectionJDBC;

public class MarqueDaoJdbc implements MarqueDao {
	
	/**
	 * Connexion nous permettant d'accéder à la base de données
	 */
	private Connection connection;
	private PreparedStatement preReqSelect= null;
	private PreparedStatement preReqInsert= null;
	private PreparedStatement preReqUpdate= null;
	private PreparedStatement preReqDelete= null;
	
	private static final Logger LOG = LoggerFactory.getLogger("");
	
	public MarqueDaoJdbc(){
		this.connection= ConnectionJDBC.getInstance();
		initPreRequete();
	}
	
	public MarqueDaoJdbc(Connection connection){
		this.connection= connection;
		initPreRequete();
	}
	
	/**
	 * On prépare les prérequêtes pour accélérer les insertions et les mises à jours
	 */
	private void initPreRequete(){
		try {
			preReqSelect = connection.prepareStatement("SELECT ID, NOM FROM MARQUE WHERE ID = ?;");
			
			preReqInsert = connection.prepareStatement("INSERT IGNORE INTO MARQUE(ID, NOM) VALUES(?,?);");
			
			preReqUpdate = connection.prepareStatement("UPDATE MARQUE SET NOM = ? WHERE ID = ?;");
			
			preReqDelete = connection.prepareStatement("DELETE FROM MARQUE WHERE ID = ?;");
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
	public List<Marque> extraire() throws SQLException {
		Statement monStatement= null;
		ResultSet curseur= null;
		List<Marque> listeMarques= new ArrayList<Marque>();
		
		monStatement = connection.createStatement();
		curseur= monStatement.executeQuery("SELECT * FROM MARQUE;");
		while(curseur.next()){
			int idCat= curseur.getInt("ID");
			String nomCat= curseur.getString("NOM");
			
			listeMarques.add(new Marque(idCat, nomCat));
		}
		
		return listeMarques;
	}
	
	@Override
	public Marque getById(int id) throws SQLException {
		Marque marque= null;
		
		preReqSelect.setInt(1, id);
		ResultSet rs= preReqSelect.executeQuery();
		if(rs.next()){
			marque= new Marque(rs.getInt("ID"), rs.getString("NOM"));
		}
		return marque;
	}

	@Override
	public void insert(Marque marque) throws SQLException {
		if(marque== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqInsert.setInt(i++, marque.getId());
		preReqInsert.setString(i++, marque.getNom());
		preReqInsert.executeUpdate();
	}

	@Override
	public int update(Marque marque) throws SQLException {
		int retour= 0;
		if(marque== null){
			throw new SQLException("Valeur nulle!");
		}
		int i= 1;
		preReqUpdate.setString(i++, marque.getNom());
		preReqUpdate.setInt(i++, marque.getId());
		retour= preReqUpdate.executeUpdate();
		return retour;
	}

	@Override
	public boolean delete(Marque marque) throws SQLException {
		int retour= 0;
		
		if(marque== null){
			throw new SQLException("Valeur nulle!");
		}
		
		preReqDelete.setInt(1, marque.getId());
		retour= preReqDelete.executeUpdate();
			
		return retour!=0;
	}
}
