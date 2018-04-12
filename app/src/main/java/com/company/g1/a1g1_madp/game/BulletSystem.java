package com.company.g1.a1g1_madp.game;

import android.os.Handler;

public class BulletSystem {

	private final static float BULLET_SPEED = 20;
	private final static float BULLET_HEIGHT = 40;
	private final static float BULLET_WIDTH = 40;

	private static final long FIRE_RATE = 200; // default fire rate
	private static final float BULLET_OFFSET = 10;     // How far is the bullet spawned from the ship

	private EntityRegister entityRegister;
	private MovableObject spaceship;
	private long fireRate;

	private Handler bulletHandler  = new Handler();
	private Runnable bulletRunnable = new Runnable() {
		@Override
		public void run() {
			float shipCenterX = spaceship.getX() + spaceship.getRadius();
			float shipCenterY = spaceship.getY() + spaceship.getRadius();
			float bulletX = (float)(shipCenterX - BULLET_WIDTH / 2
					+ (spaceship.getRadius() + BULLET_OFFSET) * Math.sin(Math.toRadians(spaceship.getTheta())));
			float bulletY = (float)(shipCenterY - BULLET_HEIGHT / 2
					+ (spaceship.getRadius() + BULLET_OFFSET) * -Math.cos(Math.toRadians(spaceship.getTheta())));
			Bullet mBullet = new Bullet(bulletX, bulletY, BULLET_HEIGHT, BULLET_WIDTH, BULLET_SPEED, spaceship.getTheta());
			entityRegister.registerBullet(mBullet);
			bulletHandler.postDelayed(this, fireRate);
		}
	};

	BulletSystem(EntityRegister register, MovableObject parent) {
		entityRegister = register;
		spaceship = parent;
		fireRate = FIRE_RATE;
	}

	void startFiring() {
		bulletHandler.postDelayed(bulletRunnable, FIRE_RATE);
	}

	void stopFiring() {
		bulletHandler.removeCallbacks(bulletRunnable);
	}

	public void setFireRate(long rate) {
		fireRate = rate;
	}

}
