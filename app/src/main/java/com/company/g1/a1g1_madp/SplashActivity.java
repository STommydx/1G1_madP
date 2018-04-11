package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

	TimerTask mTimerTask;
	SplashActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
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
