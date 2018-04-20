package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
	LinearLayout l1,l2;
	Animation uptodown,downtoup;

	private MediaPlayer mediaPlayer;

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

		mediaPlayer = MediaPlayer.create(this, R.raw.sandstorm);
		if (mediaPlayer != null) {
			mediaPlayer.setLooping(true);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mediaPlayer.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mediaPlayer.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer.release();
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
	}

	public void story(View view) {
	}

	public void startAboutUs(View view) {
		Intent intent = new Intent(this, AboutUS.class);
		startActivity(intent);
	}
}
