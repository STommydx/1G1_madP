package com.company.g1.a1g1_madp.game;

import android.os.Handler;

public class Spaceship extends MovableObject {

    // Static field should suffice.
    // However, they can't be overridden,
    // superclass method will be messed up
    // Therefore, need to retain and assign to the instance field.
    // A better solution is much much much welcomed!

    private final static float  SHIP_SPEED    = 10;
    private final static float  SHIP_HEIGHT   = 100;
    private final static float  SHIP_WIDTH    = 100;
                  static long   FIRE_RATE     = 200;
    private final static float  BULLET_OFFSET = 10;     // How far is the bullet spawned from the ship
    private float               aX;
    private float               aY;
//    private Class               bulletClass = Bullet.class;


    /*
     * There's a memory leak problem with handler:
     * it stops the instance from being garbage-collected,
     * thus causing memory leaks.
     * https://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html
     */

    private Handler  bulletHandler  = new Handler();
    private Runnable bulletRunnable = new Runnable() {
        @Override
        public void run() {
            float shipCenterX = x + radius;
            float shipCenterY = y + radius;
            float bulletX = (float)(shipCenterX - Bullet.BULLET_WIDTH / 2
                    + (radius + BULLET_OFFSET) * Math.cos(Math.toRadians(theta)));
            float bulletY = (float)(shipCenterY - Bullet.BULLET_HEIGHT / 2
                    + (radius + BULLET_OFFSET) * Math.sin(Math.toRadians(theta)));
            new Bullet(bulletX, bulletY, theta);
            bulletHandler.postDelayed(this, FIRE_RATE);
        }
    };

    Spaceship() {
        super(LAYOUT_WIDTH / 2 - SHIP_WIDTH / 2,
                LAYOUT_HEIGHT - SHIP_HEIGHT * 1.5f,
                SHIP_HEIGHT,SHIP_WIDTH, SHIP_SPEED);
    }

    void startFiring() {
        bulletHandler.postDelayed(bulletRunnable, FIRE_RATE);
    }

    void stopFiring() {
        bulletHandler.removeCallbacks(bulletRunnable);
    }

    void setAcceleration(float aX, float aY) {
        this.aX = aX;
        this.aY = aY;
    }

    @Override
    void update() {
        super.update(aX, aY);
    }

    //    public void  setRotation(float rotation) { this.rotation = rotation; }
    //    public float getRotation()               { return rotation; }
}
