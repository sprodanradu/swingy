package ro.academyplus.swingy.model.artifacts;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Weapon extends Artifacts{
	
	public Weapon(String type, int value){
		super(type, value);
	}
}
