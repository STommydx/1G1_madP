package com.company.g1.a1g1_madp.game;


import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bullet extends MovableObject {

    private final static float  BULLET_SPEED    = 20;
            final static float  BULLET_HEIGHT   = 40;
            final static float  BULLET_WIDTH    = 40;

    static CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();

    Bullet(float x, float y, float theta) {
        super(x, y, BULLET_HEIGHT, BULLET_WIDTH, BULLET_SPEED);
        this.theta = theta;
        checkOutOfBound();
        bullets.add(this);
    }

    void removeSelf() {
        bullets.remove(this);
    }

    @Override
    void onOutOfBound(EnumSet<BOUND> bounds) {
        removeSelf();
    }
}
