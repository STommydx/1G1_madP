package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StageActivity extends AppCompatActivity {

	public static final int STAGE_WIN = 1;
	public static final int STAGE_NEW = 0;
	public static final int STAGE_LOSE = -1;

	private int stage;
	private int state;
	private int score;

	private String mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage);

		Intent intent = getIntent();
		stage = intent.getIntExtra("STAGE_NUMBER", 0);
		state = intent.getIntExtra("STAGE_STATE", STAGE_NEW);
		score = intent.getIntExtra("STAGE_SCORE", 0);
		mCurrentPhotoPath = intent.getStringExtra("GAME_IMAGE");

		Button nextButton = findViewById(R.id.button3);

		if (state == STAGE_NEW)
			nextButton.setText(getResources().getString(R.string.stage_new));
		else if (state == STAGE_LOSE)
			nextButton.setText(getResources().getString(R.string.stage_again));
		else if (state == STAGE_WIN)
			nextButton.setText(getResources().getString(R.string.stage_next));

		TextView scoreView = findViewById(R.id.textView3);

		if (state == STAGE_NEW)
			scoreView.setText(getResources().getString(R.string.stage_welcome));
		else
			scoreView.setText(getResources().getString(R.string.stage_score, score));

	}

	public void startGame(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		intent.putExtra("STAGE_NUMBER", stage);
		intent.putExtra("GAME_IMAGE", mCurrentPhotoPath);
		startActivity(intent);
		finish();
	}
}
