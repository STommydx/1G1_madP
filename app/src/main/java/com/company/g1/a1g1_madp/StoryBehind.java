package com.company.g1.a1g1_madp;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryBehind extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story_behind);
		TextView sb = (TextView)findViewById(R.id.sb);
		sb.setMovementMethod(new ScrollingMovementMethod());
		final ImageView backgroundOne = (ImageView) findViewById(R.id.background_one);
		final ImageView backgroundTwo = (ImageView) findViewById(R.id.background_two);
		backgroundOne.setScaleType(ImageView.ScaleType.FIT_XY);
		backgroundTwo.setScaleType(ImageView.ScaleType.FIT_XY);

		final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setInterpolator(new LinearInterpolator());
		animator.setDuration(10000L);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				final float progress = (float) animation.getAnimatedValue();
				final float width = backgroundOne.getWidth();
				final float translationX = width * progress;
				backgroundOne.setTranslationX(translationX);
				backgroundTwo.setTranslationX(translationX - width);
			}
		});
		animator.start();
	}
}
