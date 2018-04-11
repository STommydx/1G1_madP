package com.company.g1.a1g1_madp.game;

import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SpawnSystem {

	private final static long ENEMY_SPAWN_RATE = 1000;

	private CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
	private Handler spawnHandler  = new Handler();
	private Runnable spawnRunnable = new Runnable(){
		@Override
		public void run() {
			float x = (float) (Math.random() * GameObject.LAYOUT_WIDTH * 0.9);
			Enemy mEnemy = new Enemy(x, 0);
			mEnemy.addOnRemoveListener(() -> enemies.remove(mEnemy));
			enemies.add(mEnemy);
			spawnHandler.postDelayed(this, ENEMY_SPAWN_RATE);
		}
	};

	void startSpawning() {
		spawnHandler.postDelayed(spawnRunnable, ENEMY_SPAWN_RATE);
	}

	void stopSpawning() {
		spawnHandler.removeCallbacks(spawnRunnable);
	}

	List<Enemy> getEnemies() {
		return enemies;
	}

}
