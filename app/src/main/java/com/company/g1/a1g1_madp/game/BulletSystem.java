package com.company.g1.a1g1_madp.game;

import android.os.Handler;

public class BulletSystem {

	private static long FIRE_RATE = 200; // default fire rate
	private final static float  BULLET_OFFSET = 10;     // How far is the bullet spawned from the ship

	private EntityRegister entityRegister;
	private Spaceship spaceship;
	private long fireRate;

	private Handler bulletHandler  = new Handler();
	private Runnable bulletRunnable = new Runnable() {
		@Override
		public void run() {
			float shipCenterX = spaceship.getX() + spaceship.getRadius();
			float shipCenterY = spaceship.getY() + spaceship.getRadius();
			float bulletX = (float)(shipCenterX - Bullet.BULLET_WIDTH / 2
					+ (spaceship.getRadius() + BULLET_OFFSET) * Math.cos(Math.toRadians(spaceship.getTheta())));
			float bulletY = (float)(shipCenterY - Bullet.BULLET_HEIGHT / 2
					+ (spaceship.getRadius() + BULLET_OFFSET) * Math.sin(Math.toRadians(spaceship.getTheta())));
			Bullet mBullet = new Bullet(bulletX, bulletY, spaceship.getTheta());
			entityRegister.registerBullet(mBullet);
			bulletHandler.postDelayed(this, fireRate);
		}
	};

	BulletSystem(EntityRegister register) {
		entityRegister = register;
		spaceship = register.getSpaceship();
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
