package com.company.g1.a1g1_madp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class Tutorial2 extends Activity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tutorial2);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int width = dm.widthPixels;
		int height = dm.heightPixels;

		getWindow().setLayout((int)(width*.8),(int)(height*.8));
	}
	public void nextScreen(View view) {
		Intent intent = new Intent(this, Tutorial3.class);
		startActivity(intent);
		finish();
	}
	public void backScreen(View view) {
		Intent intent = new Intent(this, Tutorial1.class);
		startActivity(intent);
		finish();
	}
	public void startGame(View view) {
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
		finish();
	}
}