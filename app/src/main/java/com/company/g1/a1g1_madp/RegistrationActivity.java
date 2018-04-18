package com.company.g1.a1g1_madp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity {

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

}
