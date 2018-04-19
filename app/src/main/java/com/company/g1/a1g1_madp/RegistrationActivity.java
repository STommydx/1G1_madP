package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

	static final int REQUEST_IMAGE_CAPTURE = 1;
	private String mCurrentPhotoPath = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, StageActivity.class);
		intent.putExtra("STAGE_NUMBER", 1);
		intent.putExtra("STAGE_STATE", StageActivity.STAGE_NEW);
		intent.putExtra("GAME_IMAGE", mCurrentPhotoPath);
		startActivity(intent);
		finish();
	}

	public void dispatchTakePictureIntent(View view) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {

			}
			if (photoFile != null) {
				Uri photoURI = FileProvider.getUriForFile(this, "com.company.g1.fileprovider", photoFile);
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
				startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			Bitmap imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
			ImageView mImageView = findViewById(R.id.imageView);
			mImageView.setImageBitmap(imageBitmap);
		}
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);

		mCurrentPhotoPath = image.getAbsolutePath();
		return image;
	}

}
