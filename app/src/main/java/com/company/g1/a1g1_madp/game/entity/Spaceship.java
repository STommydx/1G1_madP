package com.company.g1.a1g1_madp.game.entity;

public class Spaceship extends MovableObject {

	// Static field should suffice.
	// However, they can't be overridden,
	// superclass method will be messed up
	// Therefore, need to retain and assign to the instance field.
	// A better solution is much much much welcomed!

	private final static float SHIP_SPEED = 10;
	private final static float SHIP_HEIGHT = 100;
	private final static float SHIP_WIDTH = 100;
//    private Class               bulletClass = Bullet.class;


	/*
	 * There's a memory leak problem with handler:
	 * it stops the instance from being garbage-collected,
	 * thus causing memory leaks.
	 * https://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html
	 */

	public Spaceship(int x, int y) {
		super(x - SHIP_WIDTH / 2,
				y - SHIP_HEIGHT * 1.5f,
				SHIP_HEIGHT, SHIP_WIDTH, SHIP_SPEED);
	}

	//    public void  setRotation(float rotation) { this.rotation = rotation; }
	//    public float getRotation()               { return rotation; }
}
