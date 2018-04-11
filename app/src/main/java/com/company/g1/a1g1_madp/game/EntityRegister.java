package com.company.g1.a1g1_madp.game;

import org.w3c.dom.Entity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EntityRegister {

	List<MovableObject> entityList;
	List<Enemy> enemyList;
	List<Bullet> bulletList;
	Spaceship spaceship;

	EntityRegister() {
		entityList = new CopyOnWriteArrayList<>();
		enemyList = new CopyOnWriteArrayList<>();
		bulletList = new CopyOnWriteArrayList<>();
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

	public List<Enemy> getEnemies() {
		return enemyList;
	}

	public List<Bullet> getBullets() {
		return bulletList;
	}

	public Spaceship getSpaceship() {
		return spaceship;
	}

	public List<MovableObject> getEntities() {
		return entityList;
	}
}
