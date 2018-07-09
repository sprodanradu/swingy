package ro.academyplus.swingy.util;

import ro.academyplus.swingy.controller.MapObserver;
import ro.academyplus.swingy.model.characters.Hero;

public class MapGen {
	
	public static MapObserver mapGen(Hero hero) {
		int size = (hero.getLevel() - 1) * 5 + 10 - (hero.getLevel() % 2);
		
		MapObserver map = new MapObserver(size);
		map.registerHero(hero);
		return map;
	}
}
