package com.company.g1.a1g1_madp.game.entity;


public class Bullet extends Entity {

	public Bullet(float x, float y, float height, float width, float speed, float theta, EntityType entityType) {
		super(x, y, height, width, speed, theta, entityType);
		addOutOfBoundListener(bounds -> removeSelf());// causing incorrect behaviour for bouncy bullet
	}

}
