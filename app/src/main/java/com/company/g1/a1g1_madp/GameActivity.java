package com.company.g1.a1g1_madp;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.company.g1.a1g1_madp.game.Game;
import com.company.g1.a1g1_madp.game.ShopSystem;
import com.company.g1.a1g1_madp.game.SoundSystem;
import com.company.g1.a1g1_madp.game.entity.Entity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
	private Game game;
	private GameUI gameUI;
	final int PITCH_OFFSET = 5;   // Accelerometer Y-axis offset
	SensorManager sensorManager;
	Sensor accelerometer;
	BottomSheetBehavior mBottomSheetBehavior;

	SensorEventListener sensorEventListener = new SensorEventListener() {
		@Override
		public final void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		@Override
		public final void onSensorChanged(SensorEvent event) {
			game.updateDeviceAcceleration(-event.values[0], event.values[1] - PITCH_OFFSET);
		}
	};

	private MediaPlayer bgmPlayer;
	private AnimatorSet animatorSet;

	private static final int BOTTOM_SHEET_HEIGHT = 48;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		Intent intent = getIntent();
		int stage = intent.getIntExtra("STAGE_NUMBER", 0);
		((TextView) findViewById(R.id.textView2)).setText(getResources().getString(R.string.stage_name, stage));
		final String mCurrentPhotoPath = intent.getStringExtra("GAME_IMAGE");

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getRealMetrics(dm);

		int scaledHeight = (int) (BOTTOM_SHEET_HEIGHT * dm.density);

		View bottomSheet = findViewById(R.id.bottom_sheet);
		mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
		mBottomSheetBehavior.setPeekHeight(scaledHeight);
		mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				if(newState == BottomSheetBehavior.STATE_EXPANDED||newState == BottomSheetBehavior.STATE_DRAGGING||newState == BottomSheetBehavior.STATE_SETTLING){
					// game.pause();
				}
				else{
					// game.resume();
				}
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {

			}
		});

		Button buyTower = findViewById(R.id.button5);
		buyTower.setOnClickListener(view -> game.getShopSystem().buyItem(ShopSystem.ShopItem.ADD_TOWER));
		buyTower.setText(getResources().getString(R.string.shop_money, ShopSystem.ShopItem.ADD_TOWER.getMoney()));

		Button buyRate = findViewById(R.id.button7);
		buyRate.setOnClickListener(view -> game.getShopSystem().buyItem(ShopSystem.ShopItem.UPGRADE_RATE));
		buyRate.setText(getResources().getString(R.string.shop_money, ShopSystem.ShopItem.UPGRADE_RATE.getMoney()));

		Button buySize = findViewById(R.id.button9);
		buySize.setOnClickListener(view -> game.getShopSystem().buyItem(ShopSystem.ShopItem.UPGRADE_SIZE));
		buySize.setText(getResources().getString(R.string.shop_money, ShopSystem.ShopItem.UPGRADE_SIZE.getMoney()));

		Button shootfaster = findViewById(R.id.option3);
		shootfaster.setOnClickListener(view -> game.getShopSystem().buyItem(ShopSystem.ShopItem.UPGRADE_SPEED));
		shootfaster.setText(getResources().getString(R.string.shop_money, ShopSystem.ShopItem.UPGRADE_SPEED.getMoney()));

		Button shootslower = findViewById(R.id.option4);
		// shootslower.setOnClickListener(view -> game.updateFireRate(25));

		game = new Game(dm.heightPixels - scaledHeight, dm.widthPixels, stage);

		game.getSoundSystem().setPlaySoundListener(this::playSound);

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
			nextIntent.putExtra("GAME_IMAGE", mCurrentPhotoPath);
			startActivity(nextIntent);
			finish();
		});
		game.start();

		GameView gameView = new GameView(this, game);
		((ConstraintLayout) findViewById(R.id.gameLayout)).addView(gameView);
		gameView.setOnTouchListener(new GameHandler(game));

		gameUI = new GameUI(this, game);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
			accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		else
			sensorManager = null;

		bgmPlayer = MediaPlayer.create(this, R.raw.bgm);
		bgmPlayer.setLooping(true);

		LinearLayout switchLayout = findViewById(R.id.switchLayout);
		animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.switchuifade);
		animatorSet.setTarget(switchLayout);

		switchLayout.setOnClickListener(v -> animatorSet.start());
	}

	@Override
	protected void onResume() {
		super.onResume();
		game.resume();
		sensorManager.registerListener(
				sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		if (bgmPlayer != null) bgmPlayer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		game.pause();
		sensorManager.unregisterListener(sensorEventListener);
		if (bgmPlayer != null) bgmPlayer.pause();
	}

	public void setMusicVolume(float volume) {
		if (bgmPlayer != null) bgmPlayer.setVolume(volume, volume);
	}

	public void playSound(SoundSystem.SoundType soundType) {
		Random random = new Random();
		switch (soundType) {
			case fireBullet:
				int[] soundList = {R.raw.keyboard_key, R.raw.keyboard_key2};
				playSound(soundList[random.nextInt(soundList.length)]);
				break;
		}
	}

	public void playSound(int id) {
		MediaPlayer soundPlayer = MediaPlayer.create(this, id);
		soundPlayer.setOnCompletionListener(MediaPlayer::release);
		SeekBar seekBar = gameUI.getPauseLayout().findViewById(R.id.seekBarVolume);
		float normalized = 1.0f * seekBar.getProgress() / seekBar.getMax();
		soundPlayer.setVolume(normalized, normalized);
		soundPlayer.start();
	}

	public void setChinese(View view) {
		game.setFireType(Entity.EntityType.CHINESE);
		animatorSet.start();
	}

	public void setEnglish(View view) {
		game.setFireType(Entity.EntityType.ENGLISH);
		animatorSet.start();
	}

	public void setMaths(View view) {
		game.setFireType(Entity.EntityType.MATHS);
		animatorSet.start();
	}

}
