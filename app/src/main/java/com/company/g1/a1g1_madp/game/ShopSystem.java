package com.company.g1.a1g1_madp.game;

public class ShopSystem {

	private Game context;

	private int money = 1000;

	public ShopSystem(Game context) {
		this.context = context;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	private boolean checkAndBuy(int x) {
		if (money >= x) {
			money -= x;
			return true;
		}
		return false;
	}

	public void buyItem(ShopItem item) {
		if (checkAndBuy(item.getMoney())) {
			switch (item) {
				case ADD_TOWER:
					context.genNewTower();
					break;
				case UPGRADE_RATE:
					context.upgradeBulletRate();
					break;
				case UPGRADE_SIZE:
					context.upgradeBulletSize();
					break;
				case UPGRADE_SPEED:
					context.upgradeBulletSpeed();
					break;
				case KILL_ALL:
					context.killAll();
					break;
			}
		}
	}

	public enum ShopItem {
		ADD_TOWER(5000),
		UPGRADE_RATE(2000),
		UPGRADE_SIZE(1000),
		UPGRADE_SPEED(1000),
		KILL_ALL(20000);

		private int money;

		ShopItem(int money) {
			this.money = money;
		}

		public int getMoney() {
			return money;
		}
	}

}
