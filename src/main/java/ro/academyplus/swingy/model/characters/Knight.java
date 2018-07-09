package ro.academyplus.swingy.model.characters;

import javax.persistence.Entity;

import lombok.experimental.Builder;

@Entity
public class Knight extends Hero{
	
	public Knight() {}
	
	@Builder
	public Knight(String name) {
		super(name);
		this.attack += 7;
        this.defence += 2;
        this.hp += 50;
		this.type = "Knight";
	}
}
