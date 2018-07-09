package ro.academyplus.swingy.storage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HsqlConnect {
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence");
	private static final EntityManager em = emf.createEntityManager();
  
	private HsqlConnect(){}
	    
	public static EntityManager getEm() {
		return em;
	}	
	public static void closeConnection() {
		em.close();
	    emf.close();
	}
}

