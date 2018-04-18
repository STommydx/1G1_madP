package com.company.g1.a1g1_madp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.TextView;
import com.company.g1.a1g1_madp.game.Game;

public class GameActivity extends AppCompatActivity {
	private Game game;
	final int PITCH_OFFSET = 5;   // Accelerometer Y-axis offset
	SensorManager sensorManager;
	Sensor accelerometer;
	SensorEventListener sensorEventListener = new SensorEventListener() {
		@Override
		public final void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public final void onSensorChanged(SensorEvent event) {
			game.updateDeviceAcceleration(-event.values[0], event.values[1] - PITCH_OFFSET);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();
		int stage = intent.getIntExtra("STAGE_NUMBER", 0);
		((TextView) findViewById(R.id.textView2)).setText(getResources().getString(R.string.stage_name, stage));

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getRealMetrics(dm);

		game = new Game(dm.heightPixels, dm.widthPixels, stage);

		game.addOnStopListener((result) -> {
			Intent nextIntent = new Intent(this, StageActivity.class);
			int newStage = stage;
			if (result.isWin()) {
				newStage++;
				nextIntent.putExtra("STAGE_STATE", StageActivity.STAGE_WIN);
			} else {
				nextIntent.putExtra("STAGE_STATE", StageActivity.STAGE_LOSE);
			}
			nextIntent.putExtra("STAGE_NUMBER", newStage);
			nextIntent.putExtra("STAGE_SCORE", result.getScore());
			startActivity(nextIntent);
			finish();
		});
		game.start();

		GameView gameView = new GameView(this, game);
		((ConstraintLayout) findViewById(R.id.gameLayout)).addView(gameView);
		gameView.setOnTouchListener(new GameHandler(game));

		new GameUI(this, game);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		else
			sensorManager = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		game.resume();
		sensorManager.registerListener(
				sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		super.onPause();
		game.pause();
		sensorManager.unregisterListener(sensorEventListener);
	}
}
