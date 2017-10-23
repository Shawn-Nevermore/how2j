package com.how2j.character;

public class Hero implements Comparable<Hero> {

	public int id;
	private String name;
	private float hp;
	private int damage;

	public Hero() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Hero(int id, String name, float hp, int damage) {
		super();
		this.id = id;
		this.name = name;
		this.hp = hp;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public int compareTo(Hero anotherHero) {
		if (damage < anotherHero.damage) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public String toString() {
		return "Hero [id=" + id + ", name=" + name + ", hp=" + hp + ", damage=" + damage + "]";
	}

}
