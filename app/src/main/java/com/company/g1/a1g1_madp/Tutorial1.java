package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

public class Tutorial1 extends AppCompatActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tutorial1);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		int width = dm.widthPixels;
		int height = dm.heightPixels;

		getWindow().setLayout((int)(width*.8),(int)(height*.8));
	}
	public void nextScreen(View view) {
		Intent intent = new Intent(this, Tutorial2.class);
		startActivity(intent);
		finish();
	}
	public void startGame(View view) {
		Intent intent = new Intent(this, RegistrationActivity.class);
		startActivity(intent);
		finish();
	}
}
