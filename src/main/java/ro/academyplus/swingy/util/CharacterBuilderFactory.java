package ro.academyplus.swingy.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import ro.academyplus.swingy.model.characters.Characters;
import ro.academyplus.swingy.model.characters.Elf;
import ro.academyplus.swingy.model.characters.Hero;
import ro.academyplus.swingy.model.characters.Knight;
import ro.academyplus.swingy.model.characters.Orc;
import ro.academyplus.swingy.model.characters.Undead;
import ro.academyplus.swingy.model.characters.Wizard;

public class CharacterBuilderFactory {
	private static Characters character;

	// HsqlConnect.getEm().getTransaction().begin();
	public static Characters newHero(String name, String type) {

		switch (type) {
		case "Elf":
			character = Elf.builder().name(name).build();
			break;
		case "Knight":
			character = Knight.builder().name(name).build();
			break;
		case "Wizard":
			character = Wizard.builder().name(name).build();
			break;
		default:
			break;
		}
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Hero>> constraintViolations = validator.validate((Hero) character);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Hero> v : constraintViolations) {
				System.out.println(
						v.getRootBeanClass().getSimpleName() + "." + v.getPropertyPath() + " " + v.getMessage());
			}
			return null;
		}
		return character;
	}

	public static Characters newVillain(int level, String type) {
		switch (type) {
		case "Orc":
			character = Orc.builder().level(level).build();
			break;
		case "Undead":
			character = Undead.builder().level(level).build();
			break;
		default:
			break;
		}
		return character;
	}
}
