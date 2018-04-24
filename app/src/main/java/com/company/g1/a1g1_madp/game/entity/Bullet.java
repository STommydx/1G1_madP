package com.company.g1.a1g1_madp.game.entity;


public class Bullet extends Entity {

	private static final Text TEXT_CHI   = new Text("鵝鵝鵝曲項向天歌白毛浮綠水紅掌撥清波");
	private static final Text TEXT_ENG	 = new Text("ABCDEFG");
	private static final Text TEXT_MATHS = new Text("2+2=5");

	private String text;

	public Bullet(float x, float y, float height, float width, float speed, float theta, EntityType entityType) {
		super(x, y, height, width, speed, theta, entityType);
		addOutOfBoundListener(bounds -> removeSelf());// causing incorrect behaviour for bouncy bullet
		if(entityType == Entity.EntityType.CHINESE)
			this.text = String.valueOf(TEXT_CHI.getNextChar());
		else if(entityType == Entity.EntityType.ENGLISH)
			this.text = String.valueOf(TEXT_ENG.getNextChar());
		else if(entityType == Entity.EntityType.MATHS)
			this.text = String.valueOf(TEXT_MATHS.getNextChar());
		else
			this.text = "bug!";
	}

	public String getText() { return text; }

	private static class Text {
		private String text;
		private int i = 0;

		Text(String text) {
			this.text = text;
		}

		char getNextChar() {
			i++;
			if(i >= text.length())
				i = 0;
			return text.charAt(i);
		}
	}



}
