package com.company.g1.a1g1_madp.game;

import java.util.EnumSet;

// BUGGY!

public class BouncyBullet extends Bullet {

    int bouncedCount = 0;

    BouncyBullet(float x, float y, float theta) {
        super(x, y, theta);
    }

    @Override
    void fireOutOfBound(EnumSet<CollisionSystem.BOUND> bounds) {
        // to-do
        if(bouncedCount == 1)
            removeSelf();
        else {
            theta -= 180f;
            bouncedCount++;
        }
    }
}
