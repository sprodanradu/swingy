package ro.academyplus.swingy.model.characters;

import javax.persistence.Entity;

import lombok.experimental.Builder;

@Entity
public class Elf extends Hero{
	
	public Elf() {}

	@Builder
	public Elf(String name) {
		super(name);
		this.attack += 10;
        this.defence += 15;
        this.hp += 25;
		this.type = "Elf";
	}
	
}
