package ro.academyplus.swingy.view.consoleView;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import ro.academyplus.swingy.controller.GameController;
import ro.academyplus.swingy.controller.MapObserver;
import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.storage.HsqlConnect;
import ro.academyplus.swingy.storage.HsqlDb;
import ro.academyplus.swingy.util.CharacterBuilderFactory;
import ro.academyplus.swingy.util.MapGen;

public class ConsoleV {

	public static void go() {
		Print.printMenu();
		Scanner stdin = new Scanner(System.in);
		switch (stdin.next()) {
		case "1":
			newHero();
			break;
		case "2":
			oldHero();
			break;
		case "3":
			System.out.println("3");
			break;
		case "4":
			System.out.println("Exit");
			HsqlDb.getHsql().stop();
			System.exit(0);
			break;
		default:
			System.out.println("Invalid option. Retry");
			go();
		}
		stdin.close();
	}

	public static void newHero() {
		Print.newHero();
		Scanner stdin = new Scanner(System.in);
		String option = stdin.next();
		if (option.matches("[123]")) {
			Print.name();
			String name = stdin.next();
			Hero hero = null;
			switch (option) {
			case "1":
				hero = (Hero) CharacterBuilderFactory.newHero(name, "Elf");
				break;
			case "2":
				hero = (Hero) CharacterBuilderFactory.newHero(name, "Knight");
				break;
			case "3":
				hero = (Hero) CharacterBuilderFactory.newHero(name, "Wizard");
				break;
			default:
				System.out.println("Invalid option. Retry");
				go();
			}
			if (hero != null) {
				try {
//					HsqlDb.getHsql().createDb();
					HsqlDb.getHsql().addHero(hero);
					System.out.println(hero.getName() + " hero was created!" + "\n--------------------");
					System.out.println("\nReady to battle!" + "\n--------------------");
					MapObserver map = MapGen.mapGen(hero);
					GameController game = new GameController(hero);
					move(hero, map, game);
				} catch (Exception e) {
					Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
					Set<ConstraintViolation<Hero>> constraintViolations = validator.validate(hero);
					for (ConstraintViolation<Hero> v : constraintViolations) {
						System.out.println(v.getRootBeanClass().getSimpleName());
					}
					System.out.println("Hero already exists, please retry" + "\n--------------------");
					HsqlConnect.getEm().getTransaction().rollback();
					go();
				}
			} else {
				System.out.println("A problem occured, please retry" + "\n--------------------");
				go();
			}
		} else {
			System.out.println("Invalid option. Retry");
			go();
		}
//		stdin.close();
	}

	public static void oldHero() {
		List<Hero> heroList = HsqlDb.getHsql().listHero();
		if (heroList.isEmpty()) {
			System.out.println("No previous heroes" + "\n--------------------");
			go();
		} else {
			for (Hero hero : heroList) {
				Print.printHero(hero);
			}
		}
		System.out.println("Choose your hero: ");
		Scanner stdin = new Scanner(System.in);
		String heroChosen = stdin.nextLine();
		List<Hero> hero = HsqlDb.getHsql().findWithName(heroChosen);
		if (hero.isEmpty()) {
			System.out.println("\nNo such hero, try again" + "\n--------------------");
			oldHero();
		} else {
			System.out.println("\nReady to battle!" + "\n--------------------");
			for (Hero h : hero) {
				MapObserver map = MapGen.mapGen(h);
				GameController game = new GameController(h);
				move(h, map, game);
			}
		}
//		stdin.close();
	}

	public static void move(Hero hero, MapObserver map, GameController game) {
		Print.printHero(hero);
		Print.printMap(map);
		Print.printMove();
		Scanner stdin = new Scanner(System.in);
		String option = stdin.next();
		if (option.matches("[1234]")) {
			map = game.move(option);
			if (hero.win()) {
				System.out.println("Congratulations, you have reached your destination!");
				HsqlDb.getHsql().stop();
				System.exit(0);
			} else {
				move(hero, map, game);
			}
		} else if (option.matches("5")) {
			System.out.println("Exit");
			HsqlDb.getHsql().stop();
			System.exit(0);
		} else {
			System.out.println("Invalid option, try again");
			move(hero, map, game);
		}
		stdin.close();
	}
}
