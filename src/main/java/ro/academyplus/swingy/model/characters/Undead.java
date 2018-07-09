package ro.academyplus.swingy.model.characters;

import lombok.experimental.Builder;

public class Undead extends Villain {

	@Builder
	public Undead(int level) {
		super(level);
		this.type = "Undead";
		this.attack = 5 + this.level;
		this.defence = 1 + this.level;
		this.hp = 10 + this.level;
	}

}
