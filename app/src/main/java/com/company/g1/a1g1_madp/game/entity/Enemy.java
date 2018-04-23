package com.company.g1.a1g1_madp.game.entity;

public class Enemy extends Entity {

	private final static float ENEMY_SPEED = -5;
	private final static float ENEMY_HEIGHT = 200;
	private final static float ENEMY_WIDTH = 137;	// DSE paper about 0.68:1 lol
	private static final int ENEMY_SCORE = 200;

	private int score;

	public Enemy(float x, float y, int score) {
		super(x, y, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED, 0f);
		this.score = score;
		addOutOfBoundListener(bounds -> removeSelf());
		addOnHitListener(source -> {
			if (source instanceof Bullet) {
				if (((Bullet) source).getEntityType() == getEntityType()) {
					removeSelf();
				}
			} else if (source instanceof Spaceship) {
				removeSelf();
			}
		});
	}

	public Enemy(float x, float y) {
		this(x, y, ENEMY_SCORE);
	}

	public int getScore() {
		return score;
	}

}
