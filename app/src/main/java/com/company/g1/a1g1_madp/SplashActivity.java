package com.company.g1.a1g1_madp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

	TimerTask mTimerTask;
	SplashActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ConstraintLayout li = (ConstraintLayout)this.findViewById(R.id.logo);
		ImageView logo = (ImageView)findViewById(R.id.companylogo);
		Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		li.setBackgroundColor(Color.parseColor("#e0e15a"));
		logo.startAnimation(myFadeInAnimation);
	}

	@Override
	protected void onResume() {
		super.onResume();

		instance = this;
		mTimerTask = new TimerTask() {
			@Override
			public void run() {
				Intent mIntent = new Intent(instance, MainActivity.class);
				startActivity(mIntent);
				finish();
			}
		};

		Timer mTimer = new Timer();
		mTimer.schedule(mTimerTask, 3000);

	}

	protected void onPause() {
		super.onPause();
		mTimerTask.cancel();
	}


}
