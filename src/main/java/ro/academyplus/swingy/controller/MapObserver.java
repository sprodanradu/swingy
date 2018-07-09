package ro.academyplus.swingy.controller;

import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ro.academyplus.swingy.model.characters.Hero;



@Getter
@Setter
//@ToString(includeFieldNames = true)
//@Entity
public class MapObserver {
//	@Id
	protected int id;
//	@OneToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
	private Hero hero;
	private int size;
//	@Column(columnDefinition = "LONGVARCHAR")
	private String map[][];
	private int[] lastPos;

	public MapObserver() {}
	
	public MapObserver(int size) {
		if (size > 20) {
			size = 20;
		}
		this.size = size;
		map = new String[size][size];
		createMap();
	}
	
	public void createMap() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				map[i][j] = ".";
			}
		}
		putVillains();
		map[size / 2][size / 2] = "H";
	}
	
	public void registerHero(Hero hero) {
		this.hero = hero;
		this.hero.registerMap(this);
		this.hero.setX(size/2);
		this.hero.setY(size/2);
		lastPos = new int[]{size/2, size/2};
	}

	public void putVillains() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int random = new Random().nextInt(2);
				if (random == 0) {
					map[i][j] = "\u2020";
				}
			}
		}
	}
	
	public void updateMap(int x, int y) {
		map[lastPos[0]][lastPos[1]] = ".";
		map[hero.getX()][hero.getY()] = "H";
		lastPos[0] = hero.getX();
		lastPos[1] = hero.getY();
	}
	
	public void goBack() {
		hero.setX(lastPos[0]);
		hero.setY(lastPos[1]);
	}
	
	
}
