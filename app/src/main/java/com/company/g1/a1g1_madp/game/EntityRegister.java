package com.company.g1.a1g1_madp.game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityRegister {

	private List<MovableObject> entityList;
	private List<Enemy> enemyList;
	private List<Bullet> bulletList;
	private List<Tower> towerList;
	private Spaceship spaceship;

	EntityRegister() {
		entityList = new CopyOnWriteArrayList<>();
		enemyList = new CopyOnWriteArrayList<>();
		bulletList = new CopyOnWriteArrayList<>();
		towerList = new CopyOnWriteArrayList<>();
	}

	public void registerEntity(MovableObject entity) {
		entityList.add(entity);
		entity.addOnRemoveListener(() -> entityList.remove(entity));
	}

	public void registerEnemy(Enemy enemyEntity) {
		registerEntity(enemyEntity);
		enemyList.add(enemyEntity);
		enemyEntity.addOnRemoveListener(() -> enemyList.remove(enemyEntity));
	}

	public void registerBullet(Bullet bulletEntity) {
		registerEntity(bulletEntity);
		bulletList.add(bulletEntity);
		bulletEntity.addOnRemoveListener(() -> bulletList.remove(bulletEntity));
	}

	public void registerSpaceship(Spaceship spaceship) {
		registerEntity(spaceship);
		this.spaceship = spaceship;
	}

	public void registerTower(Tower tower) {
		registerEntity(tower);
		towerList.add(tower);
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

	public List<MovableObject> getEntities() {
		return entityList;
	}
}
