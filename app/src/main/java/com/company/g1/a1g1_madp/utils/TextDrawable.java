/**
 * Temporary class for testing bullet type system
 * Fine details need to be adjusted
 */

package com.company.g1.a1g1_madp.utils;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TextDrawable extends Drawable {

	private final String text;
	private final Paint paint;

	public TextDrawable(String text) {
		this.text = text;
		paint = new Paint();

		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.YELLOW);
	}

	@Override
	public void draw(@NonNull Canvas canvas) {
		Rect bounds = getBounds();
		paint.setTextSize(bounds.bottom - bounds.top);
		canvas.drawText(text, bounds.left, bounds.top, paint);
	}

	@Override
	public void setAlpha(int i) {
		paint.setAlpha(i);
	}

	@Override
	public void setColorFilter(@Nullable ColorFilter colorFilter) {
		paint.setColorFilter(colorFilter);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}
}
