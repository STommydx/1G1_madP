package com.company.g1.a1g1_madp.game;

import android.os.Handler;
import com.company.g1.a1g1_madp.game.entity.Bullet;
import com.company.g1.a1g1_madp.game.entity.MovableObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BulletSystem {

	private static final int FIRE_RATE = 10; // fire rate
	private static final float BULLET_OFFSET = 10;     // How far is the bullet spawned from the ship

	private EntityRegister entityRegister;
	private Map<MovableObject, FireProperty> fireList;
	private long now = 0;

	private Handler bulletHandler = new Handler();
	private Runnable bulletRunnable = new Runnable() {
		@Override
		public void run() {
			for (Map.Entry<MovableObject, FireProperty> entry : fireList.entrySet()) {

				int fireRate = entry.getValue().getRate();
				if (now % fireRate != 0) continue;

				MovableObject spaceship = entry.getKey();
				FireProperty property = entry.getValue();
				float shipCenterX = spaceship.getX() + spaceship.getRadius();
				float shipCenterY = spaceship.getY() + spaceship.getRadius();
				float bulletX = (float) (shipCenterX - property.getSize() / 2
						+ (spaceship.getRadius() + BULLET_OFFSET) * Math.sin(Math.toRadians(spaceship.getTheta())));
				float bulletY = (float) (shipCenterY - property.getSize() / 2
						+ (spaceship.getRadius() + BULLET_OFFSET) * -Math.cos(Math.toRadians(spaceship.getTheta())));
				Bullet mBullet = new Bullet(bulletX, bulletY, property.getSize(), property.getSize(), property.getSpeed(), spaceship.getTheta());
				entityRegister.registerBullet(mBullet);

			}

			now++;
			bulletHandler.postDelayed(this, FIRE_RATE);
		}
	};

	BulletSystem(EntityRegister register) {
		entityRegister = register;
		fireList = Collections.synchronizedMap(new HashMap<>());
	}

	void registerFire(MovableObject object, FireProperty fireProperty) {
		fireList.put(object, fireProperty);
	}

	void startFiring() {
		bulletHandler.postDelayed(bulletRunnable, FIRE_RATE);
	}

	void stopFiring() {
		bulletHandler.removeCallbacks(bulletRunnable);
	}

	FireProperty getFireProperty(MovableObject object) {
		return fireList.get(object);
	}

	public static class FireProperty {

		private static final int DEFAULT_SPEED = 20;
		private static final int DEFAULT_RATE = 20; // default fire rate = 10 * 20 = 200
		private static final int DEFAULT_SIZE = 40;

		private int size;
		private int speed;
		private int rate;

		public FireProperty(int size, int speed, int rate) {
			this.size = size;
			this.speed = speed;
			this.rate = rate;
		}

		public FireProperty(int size, int speed) {
			this(size, speed, DEFAULT_RATE);
		}

		public FireProperty(int size) {
			this(size, DEFAULT_SPEED);
		}

		public FireProperty() {
			this(DEFAULT_SIZE);
		}

		public int getRate() {
			return rate;
		}

		public int getSize() {
			return size;
		}

		public int getSpeed() {
			return speed;
		}

		public void setRate(int rate) {
			this.rate = rate;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public void setSpeed(int speed) {
			this.speed = speed;
		}
	}

}
