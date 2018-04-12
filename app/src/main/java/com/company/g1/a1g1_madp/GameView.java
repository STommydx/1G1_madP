package com.company.g1.a1g1_madp;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.company.g1.a1g1_madp.game.*;

import java.util.List;

public class GameView extends SurfaceView implements Runnable {

	Game game;
	Paint paint = new Paint();
	Paint paint2 = new Paint();

	// Bitmap spaceshipBitmap;

	Thread renderThread = null;
	SurfaceHolder holder;
	Canvas canvas;
	volatile boolean running = false;

	private Context context;

	public GameView(Context context, Game game) {
		super(context);

		this.context = context;

		holder = getHolder();
		// Stupid
		paint.setColor(Color.BLUE);
		paint2.setColor(0xfc00ff00);

		this.game = game;

		game.addOnResumeListener(() -> resume());
		game.addOnPauseListener(() -> pause());

	}

	void loadImageResources() {
		// Bitmap _bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle_vector_2);
		// spaceshipBitmap = Bitmap.createScaledBitmap(_bitmap, (int) spaceship.getWidth(), (int) spaceship.getHeight(), false);
	}

	public void resume() {
		running = true;
		loadImageResources();
		renderThread = new Thread(this);
		renderThread.start();
	}

	@Override
	public void run() {
		while (running) {
			if (!holder.getSurface().isValid())  // What does this do?
				continue;
			canvas = holder.lockCanvas();
			draw(canvas);   // This part deviates from the copy source, is it ok?
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		running = false;
		// No idea what's going on
		while (true) {
			try {
				renderThread.join();
				return;
			} catch (InterruptedException e) {
				// retry
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		for (MovableObject movableObject: game.getEntityRegister().getEntities())
			drawEntity(movableObject);
	}

	private void drawEntity(MovableObject obj, Drawable drawable) {
		RectF rectf = new RectF();
		rectf.top = obj.getY();
		rectf.left = obj.getX();
		rectf.right = obj.getX() + obj.getWidth();
		rectf.bottom = obj.getY() + obj.getHeight();
		Rect rect = new Rect();
		rectf.round(rect);
		drawable.setBounds(rect);
		canvas.save();
		int midX = (rect.left + rect.right) / 2;
		int midY = (rect.top + rect.bottom) / 2;
		canvas.rotate(obj.getTheta(), midX, midY);
		drawable.draw(canvas);
		canvas.restore();
	}

	private void drawEntity(MovableObject obj, int resourceID) {
		Drawable drawable = ContextCompat.getDrawable(context, resourceID);
		if (drawable != null) drawEntity(obj, drawable);
	}

	private void drawEntity(MovableObject obj) {
		drawEntity(obj, getResourceID(obj));
	}

	private int getResourceID(MovableObject obj) {
		if (obj instanceof Spaceship)
			return R.drawable.ship;
		else if (obj instanceof Enemy)
			return R.drawable.enemy;
		else if (obj instanceof Bullet)
			return R.drawable.bullet;
		return 0;
	}

}
