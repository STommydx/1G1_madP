package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

	private MediaPlayer mediaPlayer;
	private boolean playing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ConstraintLayout lin = findViewById(R.id.l0);
		lin.setBackgroundColor(Color.parseColor("#FFA000"));
		ConstraintLayout l1 = findViewById(R.id.topPanel);
		ImageView l2 = findViewById(R.id.imageView8);
		Animation uptodown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
		Animation downtoup = AnimationUtils.loadAnimation(this, R.anim.downtoup);
		l1.setAnimation(uptodown);
		l2.setAnimation(downtoup);

		mediaPlayer = MediaPlayer.create(this, R.raw.sandstorm);
		if (mediaPlayer != null) {
			mediaPlayer.setLooping(true);
		}

		playing = true;

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mediaPlayer != null) mediaPlayer.pause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mediaPlayer != null) mediaPlayer.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) mediaPlayer.release();
	}

	public void toggleMusic(View view) {
		if (playing) {
			if (mediaPlayer != null) mediaPlayer.pause();
			((ImageButton) view).setImageResource(R.drawable.ic_volume_off_black_24dp);
			playing = false;
		} else {
			if (mediaPlayer != null) mediaPlayer.start();
			((ImageButton) view).setImageResource(R.drawable.ic_volume_up_black_24dp);
			playing = true;
		}
	}

	public void startGame(View view) {

		Intent intent = new Intent(this, Tutorial1.class);
		startActivity(intent);
	}

	public void story(View view) {
	}

	public void startAboutUs(View view) {
		Intent intent = new Intent(this, AboutUS.class);
		startActivity(intent);
	}
}
