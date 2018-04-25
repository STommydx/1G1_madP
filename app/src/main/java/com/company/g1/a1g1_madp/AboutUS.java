package com.company.g1.a1g1_madp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUS extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		ImageView tv = (ImageView)findViewById(R.id.logoview);
		Animation swipe =	AnimationUtils.loadAnimation(this, R.anim.scroll_animation);
		tv.startAnimation(swipe);
		TextView tt = (TextView)findViewById(R.id.aboutustext);
		tt.setMovementMethod(new ScrollingMovementMethod());
	}
}
