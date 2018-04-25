package com.company.g1.a1g1_madp.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageUtils {

	public static Bitmap normalize(Bitmap bitmap) {
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();
		if (height * 3 >= width * 4) {
			int newHeight = width / 3 * 4;
			return Bitmap.createBitmap(bitmap,0, (height - newHeight) / 2, width, newHeight);
		} else {
			int newWidth = height / 4 * 3;
			return Bitmap.createBitmap(bitmap,(width - newWidth) / 2, 0, newWidth, height);
		}
	}

	public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
		return Bitmap.createScaledBitmap(bitmap, width, height, true);
	}

	public static Bitmap keyBitmap(Bitmap background, Bitmap foreground, int x, int y) {
		Bitmap newBitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(), background.getConfig());
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawBitmap(background, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
		canvas.drawBitmap(foreground, x, y, new Paint(Paint.FILTER_BITMAP_FLAG));
		return newBitmap.copy(newBitmap.getConfig(), false);
	}

	public static Bitmap keyBitmap(Bitmap background, String text, int x, int y) {
		Bitmap newBitmap = Bitmap.createBitmap(background.getWidth(), background.getHeight(), background.getConfig());
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawBitmap(background, 0, 0, new Paint(Paint.FILTER_BITMAP_FLAG));
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		canvas.drawText(text, x, y, paint);
		return newBitmap.copy(newBitmap.getConfig(), false);
	}

}
