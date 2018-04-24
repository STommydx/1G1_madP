package com.company.g1.a1g1_madp.game;

import android.os.Handler;
import com.company.g1.a1g1_madp.game.entity.Bullet;
import com.company.g1.a1g1_madp.game.entity.Enemy;
import com.company.g1.a1g1_madp.game.entity.GameObject;
import com.company.g1.a1g1_madp.game.entity.Spaceship;

public class SpawnSystem {

	private final static long ENEMY_SPAWN_RATE = 1000;
	private int layoutWidth;

	private Handler spawnHandler = new Handler();
	private Runnable spawnRunnable = new Runnable() {
		@Override
		public void run() {
			float x = (float) (Math.random() * layoutWidth * 0.9);
			Enemy mEnemy = new Enemy(x, 0);
			mEnemy.addOnHitListener(source -> {
				if (source instanceof Spaceship || source instanceof Bullet) {
					context.getShopSystem().setMoney(context.getShopSystem().getMoney() + mEnemy.getScore());
				}
			});
			entityRegister.registerEnemy(mEnemy);
			spawnHandler.postDelayed(this, ENEMY_SPAWN_RATE);
		}
	};

	private EntityRegister entityRegister;
	private Game context;

	SpawnSystem(Game context) {
		this.context = context;
		entityRegister = context.getEntityRegister();
		layoutWidth = context.getLayoutWidth();
	}

	void startSpawning() {
		spawnHandler.postDelayed(spawnRunnable, ENEMY_SPAWN_RATE);
	}

	void stopSpawning() {
		spawnHandler.removeCallbacks(spawnRunnable);
	}

}
