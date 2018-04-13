package com.company.g1.a1g1_madp.game;

import com.company.g1.a1g1_madp.game.entity.Bullet;
import com.company.g1.a1g1_madp.game.entity.Enemy;
import com.company.g1.a1g1_madp.game.entity.GameObject;
import com.company.g1.a1g1_madp.game.entity.MovableObject;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Implements both broad phase and narrow phase detection.
 * In theory should be better than doing only narrow phase.
 * In theory... :)
 */
public class CollisionSystem {

	private final static int M = 5;
	private final static int N = 4;
	private static Grid[][] grids = new Grid[M * 3][N * 3];
	private static int gridWidth;
	private static int gridHeight;

	private Game game;

	CollisionSystem(Game context) {
		game = context;
		for (int i = 0; i < M * 3; i++) {
			for (int j = 0; j < N * 3; j++) {
				grids[i][j] = new Grid();
			}
		}
		gridWidth = game.getLayoutWidth() * 3 / N;
		gridHeight = game.getLayoutHeight() * 3 / M;
	}

	void detectCollision() {

		// detect bound collision
		for (MovableObject entity : game.getEntityRegister().getMovableEntities()) {
			EnumSet<BOUND> bounds = EnumSet.noneOf(BOUND.class);
			if (entity.getX() < 0) {
				bounds.add(BOUND.LEFT);
			} else if (entity.getX() + entity.getWidth() > game.getLayoutWidth())
				bounds.add(BOUND.RIGHT);
			if (entity.getY() < 0) {
				bounds.add(BOUND.TOP);
			} else if (entity.getY() + entity.getHeight() > game.getLayoutHeight())
				bounds.add(BOUND.BOTTOM);
			if (!bounds.isEmpty())
				entity.fireOutOfBound(bounds);
		}

		resetGridState();
		// Broad phase
		for (MovableObject entity : game.getEntityRegister().getMovableEntities())
			findGridId(entity);
		// Narrow phase
		for (int i = 0; i < M * 3; i++) {
			for (int j = 0; j < N * 3; j++) {
				grids[i][j].detectCollisionInGrid();
			}
		}
	}

	private void resetGridState() {
		for (int i = 0; i < M * 3; i++) {
			for (int j = 0; j < N * 3; j++) {
				grids[i][j].bullets.clear();
				grids[i][j].enemies.clear();
			}
		}
	}

	class Grid {

		List<Bullet> bullets = new ArrayList<>();
		List<Enemy> enemies = new ArrayList<>();

		void addToList(GameObject object) {
			if (object instanceof Bullet)
				bullets.add((Bullet) object);
			else if (object instanceof Enemy)
				enemies.add((Enemy) object);
			// else
			//    throw new RuntimeException("Object not supported for collision detection.");
		}

		void detectCollisionInGrid() {
			for (Enemy enemy : enemies)
				for (Bullet bullet : bullets)
					if (enemy.getHitBox().intersect(bullet.getHitBox()))
						enemy.onHit();
		}
	}

	// update() and detectCollision() must run sequentially!
	private void findGridId(GameObject object) {


		int i = (int) Math.floor((object.getY() + game.getLayoutHeight()) / gridHeight);
		int j = (int) Math.floor((object.getX() + game.getLayoutWidth()) / gridWidth);
//        Log.d("I", String.valueOf(object.y / gridHeight));

		grids[i][j].addToList(object);
		int h = (int) Math.floor((object.getY() + object.getHeight() + game.getLayoutHeight()) / gridHeight);
		int k = (int) Math.floor((object.getX() + object.getWidth() + game.getLayoutWidth()) / gridWidth);

		if (h != i && k != j) {
			grids[h][j].addToList(object);
			grids[i][k].addToList(object);
			grids[h][k].addToList(object);
		} else if (k != j) {
			grids[i][k].addToList(object);
		} else if (h != i) {
			grids[h][j].addToList(object);
		}
	}

	public enum BOUND {
		LEFT, TOP, RIGHT, BOTTOM
	}

}
