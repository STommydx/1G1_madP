package com.company.g1.a1g1_madp.game;


public class Bullet extends MovableObject {

    Bullet(float x, float y, float height, float width, float speed, float theta) {
        super(x, y, height, width, speed, theta);
        addOutOfBoundListener(bounds -> removeSelf());// causing incorrect behaviour for bouncy bullet
    }

}
