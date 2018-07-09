package ro.academyplus.swingy.model.characters;

import lombok.experimental.Builder;

public class Orc extends Villain{

	@Builder
	public Orc(int level) {
		super(level);
		this.type = "Orc";
		this.attack = 7 + this.level;
        this.defence = 2 + this.level;
        this.hp = 20 + this.level;
	}
	

}
