package ro.academyplus.swingy.controller;

import java.util.Random;
import java.util.Scanner;

import ro.academyplus.swingy.model.artifacts.Armor;
import ro.academyplus.swingy.model.artifacts.Artifacts;
import ro.academyplus.swingy.model.artifacts.Helm;
import ro.academyplus.swingy.model.artifacts.Weapon;
import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.model.characters.Orc;
import ro.academyplus.swingy.model.characters.Undead;
import ro.academyplus.swingy.model.characters.Villain;
import ro.academyplus.swingy.storage.HsqlDb;
import ro.academyplus.swingy.util.MapGen;
import ro.academyplus.swingy.view.consoleView.ConsoleV;
import ro.academyplus.swingy.view.consoleView.Print;

public class GameController {
	private Hero hero;
	private Villain villain;
	private MapObserver map;

	public GameController(Hero hero) {
		this.hero = hero;
		this.map = hero.getMapObs();
	}

	public MapObserver move(String nb) {
		hero.updatePos(nb);
		if (map.getMap()[hero.getX()][hero.getY()] == "\u2020") {
			int random = new Random().nextInt(2);
			if (random == 0) {
				villain = new Orc(hero.getLevel());
			} else {
				villain = new Undead(hero.getLevel());
			}
			System.out.println(
					"You have encountered an enemy: " + villain.getType() + "\nLevel: " + villain.getLevel() + "\n");
			fightRun();
		} else {
			map.updateMap(hero.getY(), hero.getX());
		}
		return map;
	}

	public void fightRun() {
		Print.printFightOp();
		Scanner stdin = new Scanner(System.in);
		String option = stdin.nextLine();
		switch (option) {
		case "1":
			fight();
			if(hero.getLevel() != hero.getOldLvl()) {
				hero.setOldLvl(hero.getLevel());
				map = MapGen.mapGen(hero);
			}
			break;
		case "2":
			System.out.println("You escaped!\n");
			map.goBack();
			ConsoleV.move(hero, hero.getMapObs(), this);
			break;
		default:
			System.out.println("Invalid choice. Retry\n");
			map.goBack();
			ConsoleV.move(hero, hero.getMapObs(), this);
			break;
		}
	
	}

	private void artifact() {
		int random = new Random().nextInt(2);
		Artifacts artifact = null;
		int stats = new Random().nextInt(3) + 1;
		if (random == 1) {
			System.out.println("An artifact dropped!");
			int random2 = new Random().nextInt(3);
			switch (random2) {
			case 0:
				artifact = new Weapon("Weapon\n", stats);
				System.out.println(artifact.getType());
				System.out.println("If you equip this artifact, you will gain " + stats + " attack");
				break;
			case 1:
				artifact = new Armor("Armor", stats);
				System.out.println(artifact.getType());
				System.out.println("If you equip this artifact, you will gain " + stats + " defence");
				break;
			case 2:
				artifact = new Helm("Helm", stats);
				System.out.println(artifact.getType());
				System.out.println("If you equip this artifact, you will gain " + stats + " hp");
				break;
			case 3:
				hero.setHp(hero.getHp() + hero.getLevel() + 1);
				System.out.println("You found a health potion! Current health: " + hero.getHp());
				return;
			}
			System.out.println("Will you keep it ?");
			System.out.println("1.Yes");
			System.out.println("2.No");
			Scanner in = new Scanner(System.in);
			while (in.hasNextLine()) {
				String arg = in.nextLine();
				if (arg.matches("\\s*[1-2]\\s*")) {
					Integer nb = Integer.parseInt(arg.trim());
					if (nb == 1) {
						hero.pickUp(artifact, stats);
						System.out.println(artifact.getType() + " equipped");
						break;
					} else if (nb == 2) {
						break;
					} else {
						System.out.println("Incorrect choice");

					}
				} else {
					System.out.println("Incorrect choice");
				}
			}
		}
	}

	public void fight() {
		int random = new Random().nextInt(2);
		if (random == 0) {
			System.out.println("Enemy starts !");
			while (hero.getHp() > 0 && villain.getHp() > 0) {
				villain.attack(hero);
				villain.attack(hero);
				if (hero.getHp() > 0) {
					hero.attack(villain);
				}
			}
		} else {
			System.out.println("You start !");
			while (hero.getHp() > 0 && villain.getHp() > 0) {
				hero.attack(villain);
				if (villain.getHp() > 0) {
					villain.attack(hero);
				}
			}
		}
		if (hero.getHp() <= 0) {
			System.out.println("\nGame Over\n");
			// if (!bIsGUI) {
			HsqlDb.getHsql().stop();
			System.exit(0);
			// }
		} else if (villain.getHp() <= 0) {
			HsqlDb.getHsql().updateHero(hero);
			map.updateMap(hero.getY(), hero.getX());
			System.out.println("You win!\n");
			artifact();
		}
	}

}
