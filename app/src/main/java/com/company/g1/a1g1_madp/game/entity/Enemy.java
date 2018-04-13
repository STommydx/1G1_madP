package com.company.g1.a1g1_madp.game.entity;

public class Enemy extends MovableObject {

	private final static float ENEMY_SPEED = 10;
	private final static float ENEMY_HEIGHT = 100;
	private final static float ENEMY_WIDTH = 100;

	public Enemy(float x, float y) {
		super(x, y, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, 180f); // Moving downwards
		addOutOfBoundListener(bounds -> removeSelf());
		addOnHitListener(source -> removeSelf());
	}

}
