package ro.academyplus.swingy.model.artifacts;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Artifacts {
	@NotNull
	private String type;
	@NotNull
	private int value;
	
	public Artifacts(String name, int value){
		this.type = name;
		this.value = value;
	}
}
