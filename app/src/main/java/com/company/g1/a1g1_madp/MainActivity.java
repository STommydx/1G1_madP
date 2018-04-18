package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void startGame(View view) {
		Intent intent = new Intent(this, StageActivity.class);
		intent.putExtra("STAGE_NUMBER", 1);
		intent.putExtra("STAGE_STATE", StageActivity.STAGE_NEW);
		startActivity(intent);
	}

	public void story(View view) {
	}

	public void startAboutUs(View view) {
	}
}
