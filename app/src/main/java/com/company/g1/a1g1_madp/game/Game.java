package com.company.g1.a1g1_madp.game;

import android.content.Context;
import android.os.Handler;

import com.company.g1.a1g1_madp.GameView;

import java.util.ArrayList;
import java.util.List;

/**
 * Need to check if game object and handler is destroyed properly when pause and stop
 */

public class Game {
    private boolean  running;
    private CollisionSystem collisionSystem;
    private Spaceship spaceship;
    private Handler handler = new Handler();
    private final int tick = 15;

    private SpawnSystem spawnSystem;
    private BulletSystem bulletSystem;
    private EntityRegister entityRegister;

    private ArrayList<Runnable> resumeCallback, pauseCallback;

    private int layoutWidth, layoutHeight;

    // Renderer runs separately in it's own thread
    // Good idea bad idea?
    private Runnable gameLoop = new Runnable () {
        @Override
        public void run() {
            for(MovableObject entity : entityRegister.getEntities())
	            entity.update();
            collisionSystem.detectCollision();
            handler.postDelayed(this, tick);
        }
    };

    public Game(int height, int width) {
        this.running = false;
        layoutHeight = height;
        layoutWidth = width;

	    entityRegister = new EntityRegister();

	    spaceship = new Spaceship(height / 2, width / 2);
	    spaceship.addOutOfBoundListener((bounds) -> {
		    if(bounds.contains(CollisionSystem.BOUND.LEFT))
			    spaceship.setX(0);
		    if(bounds.contains(CollisionSystem.BOUND.RIGHT))
			    spaceship.setX(width - spaceship.getWidth());
		    if(bounds.contains(CollisionSystem.BOUND.TOP))
			    spaceship.setY(0);
		    if(bounds.contains(CollisionSystem.BOUND.BOTTOM))
			    spaceship.setY(height - spaceship.getHeight());
	    });
	    entityRegister.registerSpaceship(spaceship);

	    spawnSystem = new SpawnSystem(this);
	    bulletSystem = new BulletSystem(entityRegister);
	    collisionSystem = new CollisionSystem(this);

	    resumeCallback = new ArrayList<>();
	    pauseCallback = new ArrayList<>();

    }

    public void start() {
        // gameView = new GameView(context);
        // ((ConstraintLayout)((Activity)context).findViewById(R.id.gameLayout)).addView(gameView);
        // collisionSystem = new CollisionSystem(this);

        // spaceship = new Spaceship();
	    // entityRegister.registerSpaceship(spaceship);
        // gameView.spaceship = this.spaceship;
    }

    public void resume() {
        // Why is this part necessary?
        if (running) return;
        running = true;
        spawnSystem.startSpawning();
        bulletSystem.startFiring();
        handler.postDelayed(gameLoop, tick);
        for (Runnable r: resumeCallback) r.run();
    }

    public void addOnResumeListener(Runnable listener) {
    	resumeCallback.add(listener);
    }

    public void pause() {
        if (!running) return;
        running = false;
        spawnSystem.stopSpawning();
	    bulletSystem.stopFiring();
        handler.removeCallbacks(gameLoop);
        for (Runnable r: pauseCallback) r.run();
    }

    public void addOnPauseListener(Runnable listener) {
    	pauseCallback.add(listener);
    }

    public void updateDeviceAcceleration(float ax, float ay) {
		spaceship.setAcceleration(ax, ay);
    }

	public EntityRegister getEntityRegister() {
		return entityRegister;
	}

	public void updateFirerate(long rate) {
    	bulletSystem.setFireRate(rate);
	}

	public int getLayoutHeight() {
    	return layoutHeight;
	}

	public int getLayoutWidth() {
		return layoutWidth;
	}

}
