package ro.academyplus.swingy.model.characters;

import javax.persistence.Entity;

import lombok.experimental.Builder;

@Entity
public class Wizard extends Hero{
	public Wizard() {}
	
	@Builder
	public Wizard(String name) {
		super(name);
		this.attack += 5;
        this.defence += 3;
        this.hp += 75;
		this.type = "Wizard";
	}
}
