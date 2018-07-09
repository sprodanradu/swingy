package ro.academyplus.swingy.model.characters;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(includeFieldNames=true)
@MappedSuperclass
public abstract class Characters {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	@NotNull
	protected String type;
	protected int attack;
	protected int hp;
	protected int defence;
	protected int level;
	
	abstract public void attack(Characters character);
    abstract public void defend(Characters character, int damage);	
}
