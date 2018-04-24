package com.company.g1.a1g1_madp;

import android.view.MotionEvent;
import android.view.View;
import com.company.g1.a1g1_madp.game.Game;

public class GameHandler implements View.OnTouchListener {
	static private final int DRAG_THRESHOLD = 10;
	private Game game;

	private int ptr1_old_x, ptr1_old_y,
			ptr2_old_x, ptr2_old_y,
			mid_pt_x, mid_pt_y,
			ptr_org_x, ptr_org_y;
	private float angle = 0f;

	GameHandler(Game game) {
		this.game = game;
	}


	private void onRotation(float angle) {
		game.updateDeviceRotation(angle);
	}


	private void onDrag(float x, float y) {
		game.updateDeviceDragPoint(x, y);
	}

	private void onClick(int x) {
		if (x >= game.getLayoutWidth() / 2)
			game.setFireType(game.getFireType().next());
		else
			game.setFireType(game.getFireType().previous());
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		view.performClick();
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				ptr_org_x = (int) event.getX(0);
				ptr_org_y = (int) event.getY(0);
				ptr1_old_x = ptr_org_x;
				ptr1_old_y = ptr_org_y;
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
				if (event.getPointerCount() == 1) {
					// Start drag
					int ptr_new_x = (int) event.getX(0);
					int ptr_new_y = (int) event.getY(0);

					onDrag(ptr_new_x, ptr_new_y);

					ptr1_old_x = ptr_new_x;
					ptr1_old_y = ptr_new_y;
				}
				if (event.getPointerCount() == 2) {
					// Start rotation
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

					onRotation(angle);
				}
				break;
			case MotionEvent.ACTION_UP:
				int deltaX = ptr_org_x - (int) event.getX(0);
				int deltaY = ptr_org_y - (int) event.getY(0);
				if (Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2)) < DRAG_THRESHOLD)
					onClick(ptr_org_x);
		}
		return true;
	}
}