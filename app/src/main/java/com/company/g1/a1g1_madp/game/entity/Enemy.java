package com.company.g1.a1g1_madp.game.entity;

public class Enemy extends MovableObject {

	private final static float ENEMY_SPEED = 10;
	private final static float ENEMY_HEIGHT = 100;
	private final static float ENEMY_WIDTH = 100;
	private static final int ENEMY_SCORE = 200;

	private int score;

	public Enemy(float x, float y, int score) {
		super(x, y, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, 180f); // Moving downwards
		this.score = score;
		addOutOfBoundListener(bounds -> removeSelf());
		addOnHitListener(source -> removeSelf());
	}

	public Enemy(float x, float y) {
		this(x, y, ENEMY_SCORE);
	}

	public int getScore() {
		return score;
	}

}
