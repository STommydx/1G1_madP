package com.company.g1.a1g1_madp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.company.g1.a1g1_madp.game.Game;

public class GameUI {
	private Game game;
	private Activity context;

	// UI Components
	private ConstraintLayout uiLayout;
	private ConstraintLayout pauseLayout;
	private ImageButton pauseButton;
	private PopupWindow pauseWindow;
	private Button option1;
	private Button option2;
	private TextView timeRemain;
	private TextView stageLabel;
	private TextView scoreLabel;
	String timerString;

	// UI state
	private boolean isImmersive = false;
	private boolean isPaused = false;

	GameUI(GameActivity _context, Game game) {
		this.context = (Activity) _context;
		this.game = game;

		uiLayout = context.findViewById(R.id.uiLayout);
		pauseButton = context.findViewById(R.id.pauseButton);
		pauseButton.setOnClickListener(view -> togglePauseUi());
		pauseWindow = new PopupWindow(context);

		timeRemain = context.findViewById(R.id.timeRemain);
		stageLabel = context.findViewById(R.id.stageLabel);
		scoreLabel = context.findViewById(R.id.scoreLabel);


		// Popup stuff
		pauseLayout = (ConstraintLayout) context.getLayoutInflater().inflate(R.layout.popup_pause, null);
		option1 = pauseLayout.findViewById(R.id.option1);
		option1.setOnClickListener(view -> _context.finish());
		option2 = pauseLayout.findViewById(R.id.option2);
		option2.setOnClickListener(view -> {
			Intent intent = new Intent(_context, RegistrationActivity.class);
			_context.startActivity(intent);
			_context.finish();
		});
		pauseWindow.setContentView(pauseLayout);

		((SeekBar) pauseLayout.findViewById(R.id.seekBarMusic)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				int max = seekBar.getMax();
				float normalized = 1.0f * i / max;
				_context.setMusicVolume(normalized);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		setImmersiveUi(true);
		uiLayout.setOnClickListener(v -> setImmersiveUi(true));
	}

	private void setImmersiveUi(boolean immersive) {
		if (immersive) {
			uiLayout.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_FULLSCREEN
							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		} else {
			uiLayout.setSystemUiVisibility(
					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
							| View.SYSTEM_UI_FLAG_FULLSCREEN);
		}
	}

	private void togglePauseUi() {
		if (isPaused) {
			// Unpause game
			setImmersiveUi(true);
			setPausedUi(false);
			game.resume();
		} else {
			// Pause game
			setImmersiveUi(false);
			setPausedUi(true);
			game.pause();
		}
		// Flip state
		isPaused = !isPaused;
		isImmersive = !isImmersive;

	}

	private void setPausedUi(boolean pause) {
		if (pause) {
			pauseWindow.showAtLocation(uiLayout, Gravity.CENTER, 0, -50);
		} else {
			pauseWindow.dismiss();
		}
	}

	public ConstraintLayout getPauseLayout() {
		return pauseLayout;
	}

	public void updateUI() {
		String timerLabel = String.format(timerString,(game.getRemainMilliseconds() / 1000));
		String score = String.format("Score: %d", game.getShopSystem().getMoney());

		timeRemain.post(() -> timeRemain.setText(timerLabel));
		scoreLabel.post(() -> scoreLabel.setText(score));
	}

	public void setBackground(int bgResID, int bgColorResID) {
		context.findViewById(R.id.imageView17).setBackground(ContextCompat.getDrawable(context, bgResID));
		uiLayout.setBackgroundColor(ContextCompat.getColor(context, bgColorResID));
	}

	public void setPaint(Paint paint) {
		timeRemain.getPaint().set(paint);
		stageLabel.getPaint().set(paint);
		scoreLabel.getPaint().set(paint);

		// dunno why paint can't set the color of TextView
		timeRemain.setTextColor(paint.getColor());
		stageLabel.setTextColor(paint.getColor());
		scoreLabel.setTextColor(paint.getColor());
	}

	public void setStageLabel(String stageName) {
		stageLabel.post(() -> stageLabel.setText(stageName));
	}

	public void setTimerLabel(String timerLabel) {
		this.timerString = timerLabel;
	}
}
