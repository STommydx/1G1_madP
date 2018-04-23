package com.company.g1.a1g1_madp.game.entity;

import android.graphics.Rect;

import java.util.ArrayList;

public abstract class GameObject {

	protected float x;
	protected float y;
	protected float height;
	protected float width;
	protected float radius;
	protected float theta;
	private ArrayList<Runnable> callbacks;
	private ArrayList<OnHitListener> listeners;

	GameObject(float x, float y, float height, float width, float theta) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.radius = height / 2;
		this.theta = theta;
		callbacks = new ArrayList<>();
		listeners = new ArrayList<>();
	}

	GameObject(float x, float y, float height, float width) {
		this(x, y, height, width, 0f); // Pointing upwards by default
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getRadius() {
		return radius;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getTheta() {
		return theta;
	}

	public void setTheta(float theta) {
		this.theta = theta;
	}

	public void addOnRemoveListener(Runnable r) {
		callbacks.add(r);
	}

	public void removeSelf() {
		for (Runnable r : callbacks) r.run();
	}

	public void fireOnHit(GameObject source) {
		for(OnHitListener listener: listeners)
			listener.onHit(source);
	}

	public void addOnHitListener(OnHitListener listener) {
		listeners.add(listener);
	}

	public Rect getHitBox() {
		return new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
	}

	public interface OnHitListener {
		void onHit(GameObject source);
	}

}
