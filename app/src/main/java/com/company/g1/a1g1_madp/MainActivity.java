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
		startActivity(new Intent(this, GameActivity.class));
	}

	public void story(View view) {
	}

	public void startAboutUs(View view) {
	}
}
