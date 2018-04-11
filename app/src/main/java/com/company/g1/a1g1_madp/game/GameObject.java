package com.company.g1.a1g1_madp.game;

import java.util.EnumSet;

public abstract class GameObject{

    // Need new class for shared resources?
    static int LAYOUT_WIDTH;
    static int LAYOUT_HEIGHT;

    enum BOUND {
        LEFT, TOP, RIGHT, BOTTOM
    }

    float   x;
    float   y;
    float   height;
    float   width;
    float   radius;

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
}
