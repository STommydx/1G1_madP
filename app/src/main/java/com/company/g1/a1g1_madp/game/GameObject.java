package com.company.g1.a1g1_madp.game;

public abstract class GameObject{

    protected float x;
	protected float y;
	protected float height;
	protected float width;
	protected float radius;

    GameObject(float x, float y, float height, float width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width  = width;
        this.radius = height / 2;
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
}
