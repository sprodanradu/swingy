package ro.academyplus.swingy.storage;

import java.util.List;

import ro.academyplus.swingy.model.characters.Hero;

public class HsqlDb {
	private static HsqlDb hsqlDb;
	// private String create_hero = "CREATE TABLE IF NOT EXISTS HERO ( ID INTEGER
	// PRIMARY KEY, "
	// + "NAME TEXT UNIQUE, TYPE TEXT, LEVEL INTEGER, EXPERIENCE INTEGER, ATTACK
	// INTEGER, DEFENCE INTEGER, OLDLVL INTEGER, "
	// + "HP INTEGER, X INTEGER, Y INTEGER)";
	// private String create_maps = "CREATE TABLE IF NOT EXISTS MAPOBSERVER ( ID
	// INTEGER PRIMARY KEY, "
	// + "SIZE INTEGER, LASTPOS INTEGER ARRAY, MAP LONGVARCHAR ARRAY, HERO FOREIGN
	// KEY REFERENCES HERO(mapObs))";

	private HsqlDb() {
	}

	public static synchronized HsqlDb getHsql() {
		if (hsqlDb == null) {
			hsqlDb = new HsqlDb();
		}
		return hsqlDb;
	}

	// public void createDb() {
	// HsqlConnect.getEm().createNativeQuery(create_hero);
	// HsqlConnect.getEm().createNativeQuery(create_maps);
	// }

	public void stop() {
		HsqlConnect.closeConnection();
	}

	public void addHero(Hero hero) {
		if (!HsqlConnect.getEm().getTransaction().isActive()) {
			HsqlConnect.getEm().getTransaction().begin();
		}
		HsqlConnect.getEm().persist(hero);
		HsqlConnect.getEm().getTransaction().commit();
	}

	public List<Hero> listHero() {
		List<Hero> heroList = null;
		try {
			heroList = HsqlConnect.getEm().createQuery("FROM Hero ", Hero.class).getResultList();
		} catch (Exception e) {
		}

		return heroList;
	}

	public List<Hero> findWithName(String name) {
		List<Hero> hero = HsqlConnect.getEm().createQuery("SELECT c FROM Hero c WHERE c.name LIKE :name", Hero.class)
				.setParameter("name", name).getResultList();
		return hero;
	}

	public void updateHero(Hero hero) {

		try {
			HsqlConnect.getEm().getTransaction().begin();
			HsqlConnect.getEm().persist(hero);
			HsqlConnect.getEm().getTransaction().commit();
		} catch (Exception e) {
			System.out.println("SQL exception - connectToDB(): " + e.getMessage());
			System.exit(0);
		}
	}
}
