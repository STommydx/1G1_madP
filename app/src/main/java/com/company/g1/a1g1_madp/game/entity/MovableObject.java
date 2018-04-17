package com.company.g1.a1g1_madp.game.entity;

import android.graphics.Rect;
import com.company.g1.a1g1_madp.game.CollisionSystem;

import java.util.ArrayList;
import java.util.EnumSet;

public abstract class MovableObject extends GameObject {

	private float velocityX, velocityY;
	private float accelerationX, accelerationY;

	private ArrayList<OutOfBoundListener> listeners;

	// An even longer parameter! Ewwwwwwww.
	MovableObject(float x, float y, float height, float width, float speed, float theta) {
		super(x, y, height, width, theta);
		setSpeed(speed);
		listeners = new ArrayList<>();
	}

	// Parameter so long! Eww.
	MovableObject(float x, float y, float height, float width, float speed) {
		super(x, y, height, width);
		setSpeed(speed);
		listeners = new ArrayList<>();
	}

	// Update per time unit, 1 time unit = 1 game tick
	public void update() {
		velocityX += accelerationX;
		velocityY += accelerationY;
		x += velocityX;
		y += velocityY;
	}

	public void fireOutOfBound(EnumSet<CollisionSystem.BOUND> bounds) {
		for (OutOfBoundListener listener : listeners)
			listener.onOutOfBound(bounds);
	}

	public void addOutOfBoundListener(OutOfBoundListener listener) {
		listeners.add(listener);
	}

	public Rect getHitBox() {
		return new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
	}

	public void setSpeed(float speed) {
		setVelocity(speed, theta);
	}

	public void setVelocity(float speed, float direction) {
		velocityX = (float) (speed * Math.sin(Math.toRadians(direction)));
		velocityY = (float) (speed * -Math.cos(Math.toRadians(direction)));
	}

	public void setScalarAcceleration(float acceleration) {
		setAcceleration(acceleration, theta);
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

	public void setAcceleration(float acceleration, float direction) {
		accelerationX = (float) (acceleration * Math.sin(Math.toRadians(direction)));
		accelerationY = (float) (acceleration * -Math.cos(Math.toRadians(direction)));
	}

	public void setAccelerationX(float accelerationX) {
		this.accelerationX = accelerationX;
	}

	public void setAccelerationY(float accelerationY) {
		this.accelerationY = accelerationY;
	}

	public interface OutOfBoundListener {

		void onOutOfBound(EnumSet<CollisionSystem.BOUND> bounds);

	}

}
