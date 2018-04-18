package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class RegistrationActivity extends AppCompatActivity {

	static final int REQUEST_IMAGE_CAPTURE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, StageActivity.class);
		intent.putExtra("STAGE_NUMBER", 1);
		intent.putExtra("STAGE_STATE", StageActivity.STAGE_NEW);
		startActivity(intent);
		finish();
	}

	public void dispatchTakePictureIntent(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");
			ImageView mImageView = findViewById(R.id.imageView);
			mImageView.setImageBitmap(imageBitmap);
		}
	}

}
