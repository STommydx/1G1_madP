package com.company.g1.a1g1_madp.game.entity;

import com.company.g1.a1g1_madp.game.CollisionSystem;

import java.util.ArrayList;
import java.util.EnumSet;

public abstract class MovableObject extends GameObject {

	private float speed;

	private ArrayList<OutOfBoundListener> listeners;

	// An even longer parameter! Ewwwwwwww.
	MovableObject(float x, float y, float height, float width, float speed, float theta) {
		super(x, y, height, width, theta);
		this.speed = speed;
		listeners = new ArrayList<>();
	}

	// Parameter so long! Eww.
	MovableObject(float x, float y, float height, float width, float speed) {
		super(x, y, height, width);
		this.speed = speed;
		listeners = new ArrayList<>();
	}

	// Update per time unit, 1 time unit = 1 game tick
	public void update() {
		float vX = (float) (speed * Math.sin(Math.toRadians(theta)));
		float vY = (float) (speed * -Math.cos(Math.toRadians(theta)));
		x += vX;
		y += vY;
	}

	void update(float aX, float aY) {
		float vX = speed * -aX;
		float vY = speed * aY;
		x += vX;
		y += vY;
	}

	public void fireOutOfBound(EnumSet<CollisionSystem.BOUND> bounds) {
		for (OutOfBoundListener listener : listeners)
			listener.onOutOfBound(bounds);
	}

	public void addOutOfBoundListener(OutOfBoundListener listener) {
		listeners.add(listener);
	}

	public interface OutOfBoundListener {

		void onOutOfBound(EnumSet<CollisionSystem.BOUND> bounds);

	}

}
