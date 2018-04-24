package com.company.g1.a1g1_madp.game;

import android.os.Handler;
import android.util.Log;
import com.company.g1.a1g1_madp.game.entity.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Need to check if game object and handler is destroyed properly when pause and stop
 */

public class Game {
	private boolean running;
	private CollisionSystem collisionSystem;
	private Spaceship spaceship;
	private Handler handler = new Handler();

	private static final int TICK_TIME = 15;
	private static final int STAGE_TIME = 30;
	private static final int END_TICK = STAGE_TIME * 1000 / TICK_TIME;
	private static final int WIN_CONDITION = 2000;

	private int ticks;
	private int stage;

	private SpawnSystem spawnSystem;
	private BulletSystem bulletSystem;
	private SoundSystem soundSystem;
	private ShopSystem shopSystem;
	private EntityRegister entityRegister;

	private ArrayList<Runnable> resumeCallback, pauseCallback;
	private ArrayList<GameStopListener> stopCallback;

	private int layoutWidth, layoutHeight;

	// Renderer runs separately in it's own thread
	// Good idea bad idea?
	private Runnable gameLoop = new Runnable() {
		@Override
		public void run() {
			for (MovableObject entity : entityRegister.getMovableEntities())
				entity.update();
			collisionSystem.detectCollision();

			ticks++;
			if (ticks >= END_TICK) {
				stop();
				return;
			}

			handler.postDelayed(this, TICK_TIME);
		}
	};

	public Game(int height, int width, int stage) {

		this.stage = stage;

		running = false;
		layoutHeight = height;
		layoutWidth = width;

		entityRegister = new EntityRegister(this);
		bulletSystem = new BulletSystem(this);

		spawnSystem = new SpawnSystem(this);
		collisionSystem = new CollisionSystem(this);
		soundSystem = new SoundSystem(this);
		shopSystem = new ShopSystem(this);

		resumeCallback = new ArrayList<>();
		pauseCallback = new ArrayList<>();
		stopCallback = new ArrayList<>();

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
		bulletSystem.registerFire(spaceship, new BulletSystem.FireProperty(40, 20, 20, Entity.EntityType.CHINESE));

		/*

		// Add two random towers for testing
		Tower first = new Tower(layoutWidth / 3, layoutHeight / 3, 150, 150);
		entityRegister.registerTower(first);
		bulletSystem.registerFire(first, new BulletSystem.FireProperty(10, 40));

		Tower second = new Tower(layoutWidth / 3 * 2, layoutHeight / 3 * 2, 150, 150);
		entityRegister.registerTower(second);
		bulletSystem.registerFire(second, new BulletSystem.FireProperty(30, 5));

		*/

		ticks = 0;

		// resume();
	}

	public void resume() {
		Log.d("gamecycle", "resume()");
		if (running) return;
		running = true;
		spawnSystem.startSpawning();
		bulletSystem.startFiring();
		handler.postDelayed(gameLoop, TICK_TIME);
		for (Runnable r : resumeCallback) r.run();
	}

	public void addOnResumeListener(Runnable listener) {
		resumeCallback.add(listener);
	}

	public void pause() {
		Log.d("gamecycle", "pause()");
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

	public void stop() {
		Log.i("gamecycle", "stop()");
		pause();

		boolean win = shopSystem.getMoney() >= WIN_CONDITION;
		Result result = new Result(shopSystem.getMoney(), win);

		for (GameStopListener r : stopCallback) r.onStop(result);
	}

	public void addOnStopListener(GameStopListener listener) {
		stopCallback.add(listener);
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

	public int getLayoutHeight() {
		return layoutHeight;
	}

	public int getLayoutWidth() {
		return layoutWidth;
	}

	public ShopSystem getShopSystem() {
		return shopSystem;
	}

	public int getRemainMilliseconds() {
		return (END_TICK - ticks) * TICK_TIME;
	}

	public SoundSystem getSoundSystem() {
		return soundSystem;
	}

	public void genNewTower() {
		Random random = new Random();
		int x = layoutWidth / 3 + random.nextInt(layoutWidth / 3);
		int y = layoutHeight / 3 + random.nextInt(layoutHeight / 3);
		Tower first = new Tower(x, y, 150, 150);
		entityRegister.registerTower(first);
		bulletSystem.registerFire(first, new BulletSystem.FireProperty(30, 40));
	}

	public Entity.EntityType getFireType() {
		return bulletSystem.getFireProperty(spaceship).getBulletType();
	}

	public void setFireType(Entity.EntityType type) {
		bulletSystem.getFireProperty(spaceship).setBulletType(type);
	}

	public void upgradeBulletRate() {
		BulletSystem.FireProperty fireProperty = bulletSystem.getFireProperty(spaceship);
		if (fireProperty.getRate() > 1)
			fireProperty.setRate(fireProperty.getRate() * 2 / 3);
	}

	public void upgradeBulletSize() {
		BulletSystem.FireProperty fireProperty = bulletSystem.getFireProperty(spaceship);
		fireProperty.setSize(fireProperty.getSize() * 3 / 2);
	}

	public void upgradeBulletSpeed() {
		BulletSystem.FireProperty fireProperty = bulletSystem.getFireProperty(spaceship);
		fireProperty.setSpeed(fireProperty.getSpeed() * 3 / 2);
	}

	public void killAll() {
		for (Enemy enemy : entityRegister.getEnemies()) {
			enemy.removeSelf();
		}
	}

	public interface GameStopListener {
		void onStop(Result result);
	}

	public class Result {
		private final int score;
		private final boolean win;

		public Result(int score, boolean win) {
			this.score = score;
			this.win = win;
		}

		public int getScore() {
			return score;
		}

		public boolean isWin() {
			return win;
		}
	}

}
