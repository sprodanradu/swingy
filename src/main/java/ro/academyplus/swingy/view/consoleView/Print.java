package ro.academyplus.swingy.view.consoleView;

import java.util.List;

import ro.academyplus.swingy.controller.MapObserver;
import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.storage.HsqlDb;

public class Print {
	public static void printMenu() {
		System.out.println("1.Create new hero\n" + "2.Select hero\n" + "3.Switch to GUI view\n" + "4.Exit");
	}

	public static void newHero() {
		System.out.println("Choose hero:\n" + "1.Elf\n" + "2.Knight\n" + "3.Wizard");
	}

	public static void name() {
		System.out.println("Name your hero:");
	}

	public static void printHero(Hero hero) {
		System.out.println(hero.getName() + "\nType: " + hero.getType() + "\nLevel: " + hero.getLevel()
				+ "\nExperience: " + hero.getExperience() + "\nAttack: " + hero.getAttack() + "\nDefence: "
				+ hero.getDefence() + "\nHP: " + hero.getHp() + "\n");
	}
	
	public static void printMap(MapObserver map) {
		for(int i = 0; i < map.getSize(); i++) {
			for(int j = 0; j < map.getSize(); j++) {
				System.out.print(map.getMap()[i][j]);
			}
			System.out.println("\n");
		}
	}
	
	public static void printMove() {
		System.out.println("Choose your direction:\n\n"
				+ "    1.North\n"
				+ "2.West   3.East\n"
				+ "    4.South"
				+ "\n\n"
				+ "    5.Exit");
	}
	
	public static void printFightOp() {
		System.out.println("1.Fight\n"
				+ "2.Run");
	}
}
