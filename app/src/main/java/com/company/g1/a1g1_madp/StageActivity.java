package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.company.g1.a1g1_madp.utils.ImageUtils;

/*
	Mapping:
	1 - Primary
	2 - Secondary
	3 - Space
 */

public class StageActivity extends AppCompatActivity {

	public static final int STAGE_NEXT = 1;
	public static final int STAGE_NEW = 0;
	public static final int STAGE_RESTART = -1;
	public static final int STAGE_MENU = 2;

	private int stage;
	private int state;
	private int score;

	private String mCurrentPhotoPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stage);

		Intent intent = getIntent();
		stage = intent.getIntExtra("STAGE_NUMBER", 1);
		state = intent.getIntExtra("STAGE_STATE", STAGE_NEW);
		score = intent.getIntExtra("STAGE_SCORE", 0);
		mCurrentPhotoPath = intent.getStringExtra("GAME_IMAGE");

		Button nextButton = findViewById(R.id.button3);

		if (state == STAGE_NEW)
			nextButton.setText(getResources().getString(R.string.stage_new));
		else if (state == STAGE_RESTART)
			nextButton.setText(getResources().getString(R.string.stage_again));
		else if (state == STAGE_NEXT)
			nextButton.setText(getResources().getString(R.string.stage_next));
		else if (state == STAGE_MENU)
			nextButton.setText(R.string.main_menu);

		TextView scoreView = findViewById(R.id.textView3);

		if (state == STAGE_NEW)
			scoreView.setText(getResources().getString(R.string.stage_welcome));
		else
			scoreView.setText(getResources().getString(R.string.stage_score, score));

		TextView descriptionView = findViewById(R.id.textView12);

		if (state == STAGE_MENU)
			descriptionView.setText(R.string.stage_university);
		else if (stage == 1)
			descriptionView.setText(R.string.stage_primary);
		else if (stage == 2)
			descriptionView.setText(R.string.stage_dse);
		else if (stage == 3)
			descriptionView.setText(R.string.stage_space);

		Bitmap bitmap;

		if (mCurrentPhotoPath != null)
			bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
		else
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.placeholder);

		bitmap = ImageUtils.normalize(bitmap);
		bitmap = ImageUtils.scaleBitmap(bitmap, 300, 400);

		if (state == STAGE_MENU) {
			Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.studentcard); // replace this with university pic

			ImageView imageView = findViewById(R.id.imageView2);
			imageView.setImageBitmap(background);
		} else {
			Bitmap background;

			if (stage == 1) {
				background = BitmapFactory.decodeResource(getResources(), R.drawable.studentcard_primary);
				findViewById(R.id.stageLayout).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.primaryBgColor));
			}
			else if (stage == 2) {
				background = BitmapFactory.decodeResource(getResources(), R.drawable.studentcard_secondary);
				findViewById(R.id.stageLayout).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.secondaryBgColor));
			}
			else if (stage == 3) {
				background = BitmapFactory.decodeResource(getResources(), R.drawable.studentcard); // replace this with space id
				findViewById(R.id.stageLayout).setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.assoBulletColor));
			}
			else
				background = BitmapFactory.decodeResource(getResources(), R.drawable.studentcard);

			background = ImageUtils.scaleBitmap(background, 1388, 818);
			Bitmap bitmapFinal = ImageUtils.keyBitmap(background, bitmap, 100, 200);
			bitmapFinal = ImageUtils.keyBitmap(bitmapFinal, "Lorem Ipsum", 980, 410);

			ImageView imageView = findViewById(R.id.imageView2);
			imageView.setImageBitmap(bitmapFinal);
		}

	}

	public void startGame(View view) {
		if (state != STAGE_MENU){
			Intent intent = new Intent(this, GameActivity.class);
			intent.putExtra("STAGE_NUMBER", stage);
			intent.putExtra("GAME_IMAGE", mCurrentPhotoPath);
			startActivity(intent);
		}
		finish();
	}
}
