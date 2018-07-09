package ro.academyplus.swingy.model.characters;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.academyplus.swingy.controller.MapObserver;
import ro.academyplus.swingy.model.artifacts.Artifacts;

@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
@Entity
// doesn't work with hsql @Table(uniqueConstraints=@UniqueConstraint(columnNames
// = {"name"}))
public abstract class Hero extends Characters {
	Hero() {}

	@Column(unique = true)
	@Size(min = 2, max = 20)
	protected String name;
	
	private int experience;
	
//	@OneToOne(cascade = CascadeType.ALL)
	@Transient
	private MapObserver mapObs;
	
	private int x;
	private int y;
	protected int oldLvl;

	Hero(String name) {
		this.name = name;
		this.level = 1;
		this.experience = 0;
		oldLvl = level;
	}

	public void attack(Characters character) {
		System.out.println(this.getName() + " is attacking");
		int critical = 0;
		if (this.getType().equals("Elf")) {
			int random = new Random().nextInt(4);
			if (random == 3) {
				System.out.println("Ouch!");
				critical = this.level * 2;
			}
		}
		character.defend(this, this.attack + critical);
		int xpEarned = 0;
		if (character.getHp() <= 0) {
			if (character.getType().equals("Undead")) {
				xpEarned = (int) (Math.ceil((float) this.level / 2) * 250);
				this.experience += xpEarned;
			} else if (character.getType().equals("Orc")) {
				xpEarned = (int) (Math.ceil((float) this.level / 2) * 300);
				this.experience += xpEarned;
			}
			System.out.println("You earned " + xpEarned + " xp");
			if (this.experience >= (this.level * 1000 + Math.pow(this.level - 1, 2) * 450)) {
				leveledUp();
			}
		}
	}

	public void defend(Characters character, int damage) {
		int realDamage = damage - this.defence;

		if (realDamage <= 0) {
			realDamage = 1;
		}
		this.hp -= realDamage;
		System.out.println(character.getType() + " dealt " + realDamage + " damage");
		if (this.hp <= 0) {
			System.out.println(this.name + " died");
		}
	}

	private void leveledUp() {
		this.level += 1;
		int stats = this.level;

		System.out.println(this.name + " has leveled up. Current level: " + this.level);
		System.out.println("Attack: " + this.attack + " (+" + stats + ")");
		System.out.println("Defense: " + this.defence + " (+" + 1 + ")");
		System.out.println("Hp: " + this.hp + " (+" + stats + ")");
		this.attack += stats;
		this.defence += 1;
		this.hp += stats;
	}

	public void registerMap(MapObserver map) {
		mapObs = map;
	}

	public void updatePos(String nb) {
		switch (nb) {
		case "1":
			x--;
			break;
		case "2":
			y--;
			break;
		case "3":
			y++;
			break;
		case "4":
			x++;
			break;
		}
	}

	public void pickUp(Artifacts artifact, int stats) {
		switch (artifact.getType()) {
		case "Weapon":
			attack += stats;
			break;
		case "Armor":
			defence += stats;
			break;
		case "Helm":
			hp += stats;
			break;
		}
	}
	
	public boolean win() {
		if (this.getX() == 0 || this.getY() == 0 || this.getX() == this.getMapObs().getSize() - 1
				|| this.getY() == this.getMapObs().getSize() - 1) {
			return true;
		} else {
			return false;
		}
	}
}
