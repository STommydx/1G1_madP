package com.company.g1.a1g1_madp.game;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.EnumSet;

public abstract class MovableObject extends GameObject {

    private float speed;
    private float theta;

	private ArrayList<Runnable> callbacks;
	private ArrayList<OutOfBoundListener> listeners;

	// An even longer parameter! Ewwwwwwww.
	MovableObject(float x, float y, float height, float width, float speed, float theta) {
		super(x, y, height, width);
		this.speed = speed;
		this.theta = theta;
		callbacks = new ArrayList<>();
		listeners = new ArrayList<>();
	}

    // Parameter so long! Eww.
    MovableObject(float x, float y, float height, float width, float speed) {
        this(x, y, height, width, speed, 0f); // Pointing upwards by default
    }

	// Update per time unit, 1 time unit = 1 game tick
    void update() {
        float vX = (float)(speed * Math.sin(Math.toRadians(theta)));
        float vY = (float)(speed * -Math.cos(Math.toRadians(theta)));
        x += vX;
        y += vY;
    }

    void update(float aX, float aY) {
        float vX = speed * -aX;
        float vY = speed * aY;
        x += vX;
        y += vY;
    }

    void fireOutOfBound(EnumSet<CollisionSystem.BOUND> bounds) {
		for (OutOfBoundListener listener: listeners)
			listener.onOutOfBound(bounds);
    }

    void addOutOfBoundListener(OutOfBoundListener listener) {
    	listeners.add(listener);
    }

    Rect getHitBox() {
        return new Rect((int)x,(int)y,(int)(x+width), (int)(y+height));
    }

	void removeSelf() {
		for (Runnable r: callbacks) r.run();
	}

	void addOnRemoveListener(Runnable r) {
		callbacks.add(r);
	}

	public float getTheta() {
    	return theta;
	}

	void setTheta(float theta) {
		this.theta = theta;
	}

	public interface OutOfBoundListener {

    	void onOutOfBound(EnumSet<CollisionSystem.BOUND> bounds);

	}

}
