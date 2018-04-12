package com.company.g1.a1g1_madp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.company.g1.a1g1_madp.game.*;

public class GameView extends SurfaceView implements Runnable {

	private Game game;
	private Paint backgroundPaint = new Paint();
	private Paint moneyPaint = new Paint();

	private Thread renderThread = null;
	private SurfaceHolder holder;
	private Canvas canvas;
	private volatile boolean running = false;

	private Context context;

	public GameView(Context context, Game game) {
		super(context);

		this.context = context;

		holder = getHolder();

		this.game = game;

		game.addOnResumeListener(this::resume);
		game.addOnPauseListener(this::pause);

		backgroundPaint.setColor(getResources().getColor(R.color.colorBackground));
		moneyPaint.setColor(getResources().getColor(R.color.colorUIText));
		moneyPaint.setTextSize(50);
	}

	public void resume() {
		running = true;
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
		canvas.drawColor(backgroundPaint.getColor());
		for (MovableObject movableObject : game.getEntityRegister().getEntities())
			drawEntity(movableObject);
		canvas.drawText("$" + game.getMoney(), 50, 100, moneyPaint);
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
		else if (obj instanceof Tower)
			return R.drawable.tower;
		return 0;
	}

}
