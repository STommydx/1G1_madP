package com.company.g1.a1g1_madp.game;

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
    private static Grid[][] grids = new Grid[M][N];
    private static int gridWidth;
    private static int gridHeight;

    private Game game;

    CollisionSystem(Game context) {
        game = context;
        for(int i = 0; i < M; i++) {
            for(int j = 0; j < N; j++) {
                grids[i][j] = new Grid();
            }
        }
	    gridWidth = game.getLayoutWidth() / N;
	    gridHeight = game.getLayoutHeight() / M;
    }

    void detectCollision() {

    	// detect bound collision
	    for(MovableObject entity: game.getEntityRegister().getEntities()){
		    EnumSet<BOUND> bounds = EnumSet.noneOf(BOUND.class);
		    if (entity.getX() < 0)  {
			    bounds.add(BOUND.LEFT);
		    } else if (entity.getX() + entity.getWidth() > game.getLayoutWidth())
			    bounds.add(BOUND.RIGHT);
		    if (entity.getY() < 0)  {
			    bounds.add(BOUND.TOP);
		    } else if (entity.getY() + entity.getHeight() > game.getLayoutHeight())
			    bounds.add(BOUND.BOTTOM);
		    if(!bounds.isEmpty())
			    entity.fireOutOfBound(bounds);
	    }

        resetGridState();
        // Broad phase
        for(MovableObject entity: game.getEntityRegister().getEntities())
            findGridId(entity);
        // Narrow phase
        for(int i = 0; i < M; i++) {
            for(int j = 0; j < N; j++) {
                grids[i][j].detectCollisionInGrid();
            }
        }
    }

    void resetGridState() {
        for(int i = 0; i < M; i++) {
            for(int j = 0; j < N; j++) {
                grids[i][j].bullets.clear();
                grids[i][j].enemies.clear();
            }
        }
    }

    class Grid {

        List<Bullet> bullets = new ArrayList<>();
        List<Enemy>  enemies = new ArrayList<>();

        void addToList(GameObject object) {
            if (object instanceof Bullet)
                bullets.add((Bullet)object);
            else if (object instanceof Enemy)
                enemies.add((Enemy)object);
            // else
            //    throw new RuntimeException("Object not supported for collision detection.");
        }

        void detectCollisionInGrid() {
            for(Enemy enemy: enemies)
                for(Bullet bullet : bullets)
                    if(enemy.getHitBox().intersect(bullet.getHitBox()))
                        enemy.onHit();
        }
    }

    // update() and detectCollision() must run sequentially!
    void findGridId(GameObject object) {
        int i = (int) Math.floor(object.getY() / gridHeight);
        int j = (int) Math.floor(object.getX() / gridWidth);
//        Log.d("I", String.valueOf(object.y / gridHeight));

        grids[i][j].addToList(object);
        int h = (int)(Math.min(Math.floor((object.getY() + object.getHeight()) / gridHeight),M-1));
        int k = (int)(Math.min(Math.floor((object.getX() + object.getWidth()) / gridWidth),N-1));

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

	enum BOUND {
		LEFT, TOP, RIGHT, BOTTOM
	}

}
