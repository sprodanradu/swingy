package ro.academyplus.swingy.model.characters;

import java.util.Random;

public abstract class Villain extends Characters{
	
	Villain(int level) {
		int random = new Random().nextInt(3);
		this.level = level + random - 1;
		if (this.level == 0) {
			this.level = 1;
		}
	}

	public void attack(Characters character) {
        System.out.println("The enemy is attacking");
        character.defend(this, this.attack);
    }

    public void defend(Characters character, int damage) {
        int realDamage = damage - this.defence;

        if (realDamage <= 0) {
            realDamage = 1;
        }
        this.hp -= realDamage;
        System.out.println("You dealt " + realDamage + " damage to " + this.type);
        if (hp <= 0) {
            System.out.println("The enemy died");
        }
    }
}
