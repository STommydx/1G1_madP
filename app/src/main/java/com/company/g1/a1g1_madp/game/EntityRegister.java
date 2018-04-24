package com.company.g1.a1g1_madp.game;

import com.company.g1.a1g1_madp.game.entity.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityRegister {

	private List<GameObject> entityList;
	private List<MovableObject> moveList;
	private List<Enemy> enemyList;
	private List<Bullet> bulletList;
	private List<Tower> towerList;
	private Spaceship spaceship;
	private Game context;

	EntityRegister(Game context) {
		this.context = context;
		entityList = new CopyOnWriteArrayList<>();
		enemyList = new CopyOnWriteArrayList<>();
		bulletList = new CopyOnWriteArrayList<>();
		towerList = new CopyOnWriteArrayList<>();
		moveList = new CopyOnWriteArrayList<>();
	}

	public void registerEntity(GameObject entity) {
		entityList.add(entity);
		entity.addOnRemoveListener(() -> entityList.remove(entity));
	}

	public void registerMovableEntity(MovableObject entity) {
		registerEntity(entity);
		moveList.add(entity);
		entity.addOnRemoveListener(() -> moveList.remove(entity));
	}

	public void registerEnemy(Enemy enemyEntity) {
		registerMovableEntity(enemyEntity);
		enemyList.add(enemyEntity);
		enemyEntity.addOnRemoveListener(() -> enemyList.remove(enemyEntity));
	}

	public void registerBullet(Bullet bulletEntity) {
		registerMovableEntity(bulletEntity);
		bulletList.add(bulletEntity);
		bulletEntity.addOnRemoveListener(() -> bulletList.remove(bulletEntity));
	}

	public void registerSpaceship(Spaceship spaceship) {
		registerMovableEntity(spaceship);
		this.spaceship = spaceship;
	}

	public void registerTower(Tower tower) {
		registerMovableEntity(tower);
		towerList.add(tower);
		tower.addOnRemoveListener(() -> towerList.remove(tower));
	}

	public List<Enemy> getEnemies() {
		return enemyList;
	}

	public List<Bullet> getBullets() {
		return bulletList;
	}

	public Spaceship getSpaceship() {
		return spaceship;
	}

	public List<Tower> getTowers() {
		return towerList;
	}

	public List<GameObject> getEntities() {
		return entityList;
	}

	public List<MovableObject> getMovableEntities() {
		return moveList;
	}
}
