package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
	LinearLayout l1,l2;
	Animation uptodown,downtoup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout lin = (LinearLayout) findViewById(R.id.l0);
		lin.setBackgroundColor(Color.parseColor("#FFA000"));
		l1 = (LinearLayout) findViewById(R.id.l1);
		l2 = (LinearLayout) findViewById(R.id.l2);
		uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
		downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
		l1.setAnimation(uptodown);
		l2.setAnimation(downtoup);
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, StageActivity.class);
		intent.putExtra("STAGE_NUMBER", 1);
		intent.putExtra("STAGE_STATE", StageActivity.STAGE_NEW);
		startActivity(intent);
	}

	public void story(View view) {
	}

	public void startAboutUs(View view) {
		Intent intent = new Intent(this, AboutUS.class);
		startActivity(intent);
	}
}
