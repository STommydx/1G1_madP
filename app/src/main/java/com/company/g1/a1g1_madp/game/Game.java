package com.company.g1.a1g1_madp.game;

import android.os.Handler;
import com.company.g1.a1g1_madp.game.entity.MovableObject;
import com.company.g1.a1g1_madp.game.entity.Spaceship;
import com.company.g1.a1g1_madp.game.entity.Tower;

import java.util.ArrayList;

/**
 * Need to check if game object and handler is destroyed properly when pause and stop
 */

public class Game {
	private boolean running;
	private CollisionSystem collisionSystem;
	private Spaceship spaceship;
	private Handler handler = new Handler();
	private final int tick = 15;
	private int money = 1000;

	private SpawnSystem spawnSystem;
	private BulletSystem bulletSystem;
	private EntityRegister entityRegister;

	private ArrayList<Runnable> resumeCallback, pauseCallback;

	private int layoutWidth, layoutHeight;

	// Renderer runs separately in it's own thread
	// Good idea bad idea?
	private Runnable gameLoop = new Runnable() {
		@Override
		public void run() {
			for (MovableObject entity : entityRegister.getMovableEntities())
				entity.update();
			collisionSystem.detectCollision();
			handler.postDelayed(this, tick);
		}
	};

	public Game(int height, int width) {
		running = false;
		layoutHeight = height;
		layoutWidth = width;

		entityRegister = new EntityRegister();
		bulletSystem = new BulletSystem(entityRegister);

		spawnSystem = new SpawnSystem(this);
		collisionSystem = new CollisionSystem(this);

		resumeCallback = new ArrayList<>();
		pauseCallback = new ArrayList<>();

	}

	public void start() {
		spaceship = new Spaceship(layoutHeight / 2, layoutWidth / 2);
		spaceship.addOutOfBoundListener((bounds) -> {
			if (bounds.contains(CollisionSystem.BOUND.LEFT))
				spaceship.setX(0);
			if (bounds.contains(CollisionSystem.BOUND.RIGHT))
				spaceship.setX(layoutWidth - spaceship.getWidth());
			if (bounds.contains(CollisionSystem.BOUND.TOP))
				spaceship.setY(0);
			if (bounds.contains(CollisionSystem.BOUND.BOTTOM))
				spaceship.setY(layoutHeight - spaceship.getHeight());
		});
		entityRegister.registerSpaceship(spaceship);
		bulletSystem.registerFire(spaceship, new BulletSystem.FireProperty());

		// Add two random towers for testing
		Tower first = new Tower(layoutWidth / 3, layoutHeight / 3, 150, 150);
		entityRegister.registerTower(first);
		bulletSystem.registerFire(first, new BulletSystem.FireProperty(10, 40));

		Tower second = new Tower(layoutWidth / 3 * 2, layoutHeight / 3 * 2, 150, 150);
		entityRegister.registerTower(second);
		bulletSystem.registerFire(second, new BulletSystem.FireProperty(30, 5));
	}

	public void resume() {
		// Why is this part necessary?
		if (running) return;
		running = true;
		spawnSystem.startSpawning();
		bulletSystem.startFiring();
		handler.postDelayed(gameLoop, tick);
		for (Runnable r : resumeCallback) r.run();
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
		for (Runnable r : pauseCallback) r.run();
	}

	public void addOnPauseListener(Runnable listener) {
		pauseCallback.add(listener);
	}

	public void updateDeviceAcceleration(float ax, float ay) {
		spaceship.setVelocityX(10 * ax);
		spaceship.setVelocityY(10 * ay);
	}

	public void updateDeviceRotation(float angle) {
		entityRegister.getSpaceship().setTheta(angle);
	}

	public void updateDeviceDragPoint(float x, float y) {
		for (Tower tower : entityRegister.getTowers()) {
			if (tower.getHitBox().contains((int) x, (int) y)) {
				tower.setX(x - tower.getWidth() / 2);
				tower.setY(y - tower.getHeight() / 2);
				return;
			}
		}
	}

	public EntityRegister getEntityRegister() {
		return entityRegister;
	}

	public void updateFireRate(int rate) {
		bulletSystem.getFireProperty(spaceship).setRate(rate);
	}

	public int getLayoutHeight() {
		return layoutHeight;
	}

	public int getLayoutWidth() {
		return layoutWidth;
	}

	public int getMoney() {
		return money;
	}
}
