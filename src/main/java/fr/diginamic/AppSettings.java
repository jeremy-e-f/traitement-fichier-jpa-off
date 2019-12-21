package fr.diginamic;

import java.util.ResourceBundle;

/**
 * Classe permettant de charger les paramètres de l'application
 * @author DIGINAMIC
 *
 */
public class AppSettings {
	
	/** Nom du fichier .properties, contenant les paramètres de l'application */
	private final static String NOMFICHIERCONFIG= "settings";
	
	public final static String PUNAME; 				/** Persistence Unit */
	
	public final static String FILESOURCENAME;		/** Fichier source dont les données sont extraites */
	
	static{
		/** On récupère les paramètres dans le fichier NOMFICHIERCONFIG */
		ResourceBundle monFichierConf = ResourceBundle.getBundle(NOMFICHIERCONFIG);
		PUNAME = monFichierConf.getString("persistenceUnitName");
		FILESOURCENAME = monFichierConf.getString("fileSourceName");
	}

}
