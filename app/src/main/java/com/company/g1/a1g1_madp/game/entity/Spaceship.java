package com.company.g1.a1g1_madp.game.entity;

public class Spaceship extends MovableObject {

	private final static float SHIP_SPEED = 10;
	private final static float SHIP_HEIGHT = 200;
	private final static float SHIP_WIDTH = 200;

	public Spaceship(int x, int y) {
		super(x - SHIP_WIDTH / 2,
				y - SHIP_HEIGHT * 1.5f,
				SHIP_HEIGHT, SHIP_WIDTH, SHIP_SPEED);
	}
}
