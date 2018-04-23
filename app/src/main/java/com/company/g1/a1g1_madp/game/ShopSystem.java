package com.company.g1.a1g1_madp.game;

import java.util.Random;

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
			}
		}
	}

	public enum ShopItem {
		ADD_TOWER(1000);

		private int money;

		ShopItem(int money) {
			this.money = money;
		}

		public int getMoney() {
			return money;
		}
	}

}
