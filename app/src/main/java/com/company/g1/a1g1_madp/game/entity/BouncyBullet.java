package com.company.g1.a1g1_madp.game.entity;

// BUGGY!

public class BouncyBullet extends Bullet {

	private int bouncedCount = 0;

	BouncyBullet(float x, float y, float height, float width, float speed, float theta) {
		super(x, y, height, width, speed, theta);
		addOutOfBoundListener(bounds -> {
			if (bouncedCount == 1)
				removeSelf();
			else {
				setTheta(getTheta() - 180);
				bouncedCount++;
			}
		});
	}

}
