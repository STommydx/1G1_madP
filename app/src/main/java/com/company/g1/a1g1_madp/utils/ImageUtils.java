package com.company.g1.a1g1_madp.utils;

import android.graphics.Bitmap;

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

}
