package com.company.g1.a1g1_madp.game;

import android.view.MotionEvent;
import android.view.View;

abstract public class OnRotationListener implements View.OnTouchListener {

	private int ptr1_old_x, ptr1_old_y,
			ptr2_old_x, ptr2_old_y,
			mid_pt_x, mid_pt_y;
	private float angle;

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				ptr1_old_x = (int) event.getX(0);
				ptr1_old_y = (int) event.getY(0);
				break;
			case MotionEvent.ACTION_POINTER_DOWN:

				if (event.getPointerCount() == 2) {
					ptr2_old_x = (int) event.getX(1);
					ptr2_old_y = (int) event.getY(1);
					mid_pt_x = (ptr1_old_x + ptr2_old_x) / 2;
					mid_pt_y = (ptr1_old_y + ptr2_old_y) / 2;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (event.getPointerCount() == 2) {
					int ptr1_new_x = (int) event.getX(0);
					int ptr1_new_y = (int) event.getY(0);
					int ptr2_new_x = (int) event.getX(1);
					int ptr2_new_y = (int) event.getY(1);

					double ptr1_old_theta = Math.atan2(ptr1_old_x - mid_pt_x, ptr1_old_y - mid_pt_y);
					double ptr1_new_theta = Math.atan2(ptr1_new_x - mid_pt_x, ptr1_new_y - mid_pt_y);
					double ptr2_old_theta = Math.atan2(ptr2_old_x - mid_pt_x, ptr2_old_y - mid_pt_y);
					double ptr2_new_theta = Math.atan2(ptr2_new_x - mid_pt_x, ptr2_new_y - mid_pt_y);

					double delta_theta_1 = Math.toDegrees(ptr1_old_theta - ptr1_new_theta);
					double delta_theta_2 = Math.toDegrees(ptr2_old_theta - ptr2_new_theta);

					if (delta_theta_1 > 180) delta_theta_1 -= 360;
					if (delta_theta_1 < -180) delta_theta_1 += 360;
					if (delta_theta_2 > 180) delta_theta_2 -= 360;
					if (delta_theta_2 < -180) delta_theta_2 += 360;

					angle += delta_theta_1 + delta_theta_2;

					ptr1_old_x = ptr1_new_x;
					ptr1_old_y = ptr1_new_y;
					ptr2_old_x = ptr2_new_x;
					ptr2_old_y = ptr2_new_y;
				}
				break;
		}
		onRotation();
		return true;
	}

	float getAngle() {
		return angle;
	}

	abstract public void onRotation();
}

