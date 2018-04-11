package com.company.g1.a1g1_madp.game;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.EnumSet;

abstract class MovableObject extends GameObject {

    float speed;
    float theta;

	private ArrayList<Runnable> callbacks;
	private ArrayList<OutOfBoundListener> listeners;

    // Parameter so long! Eww.
    MovableObject(float x, float y, float height, float width, float speed) {
        super(x, y, height, width);
        this.speed = speed;
        this.theta = -90f;  // Pointing upwards by default
	    callbacks = new ArrayList<>();
	    listeners = new ArrayList<>();
    }

    // Update per time unit, 1 time unit = 1 game tick
    void update() {
        float vX = (float)(speed * Math.cos(Math.toRadians(theta)));
        float vY = (float)(speed * Math.sin(Math.toRadians(theta)));
        x += vX;
        y += vY;
    }

    void update(float aX, float aY) {
        float vX = (float)(speed * -aX);
        float vY = (float)(speed *  aY);
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

	float getTheta() {
    	return theta;
	}

	public interface OutOfBoundListener {

    	void onOutOfBound(EnumSet<CollisionSystem.BOUND> bounds);

	}

}
