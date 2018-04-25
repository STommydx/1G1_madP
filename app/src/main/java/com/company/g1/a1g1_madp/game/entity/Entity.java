package com.company.g1.a1g1_madp.game.entity;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Entity extends MovableObject {

	private final EntityType entityType;

	Entity(float x, float y, float height, float width, float speed, float theta, EntityType entityType) {
		super(x, y, height, width, speed, theta);
		this.entityType = entityType;
	}

	Entity(float x, float y, float height, float width, float speed, float theta) {
		super(x, y, height, width, speed, theta);
		this.entityType = EntityType.getRandomType();
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public enum EntityType {
		CHINESE,
		ENGLISH,
		MATHS;

		private static final List<EntityType> VALUES = Arrays.asList(values());
		private static final int SIZE = VALUES.size();
		private static final Random RAND = new Random();

		public static EntityType getRandomType() {
			int x = RAND.nextInt(SIZE);
			return VALUES.get(x);
		}

		private static EntityType[] vals = values();

		public EntityType next() {
			return vals[(this.ordinal()+1) % vals.length];
		}

		public EntityType previous() {
			return vals[(this.ordinal()+vals.length-1) % vals.length];
		}
	}

}
