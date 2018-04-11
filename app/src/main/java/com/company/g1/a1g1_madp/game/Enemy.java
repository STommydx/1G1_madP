package com.company.g1.a1g1_madp.game;

import android.os.Handler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Enemy extends MovableObject{

    private final static float  ENEMY_SPEED      = 10;
    private final static float  ENEMY_HEIGHT     = 50;
    private final static float  ENEMY_WIDTH      = 50;

    private ArrayList<Runnable> callbacks;

    Enemy(float x, float y) {
        super(x, y, ENEMY_HEIGHT, ENEMY_WIDTH, ENEMY_SPEED);
        theta = 90f;    // Moving downwards
	    callbacks = new ArrayList<>();
        // enemies.add(this);
    }

    @Override
    void fireOutOfBound(EnumSet<CollisionSystem.BOUND> bounds) {
        super.fireOutOfBound(bounds);
        removeSelf();
    }

    void onHit() {
        removeSelf();
    }

}
