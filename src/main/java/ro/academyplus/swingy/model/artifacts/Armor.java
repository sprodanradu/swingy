package ro.academyplus.swingy.model.artifacts;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Armor extends Artifacts{
	
	public Armor(String type, int defence){
		super(type, defence);
	}
}
