package com.company.g1.a1g1_madp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.company.g1.a1g1_madp.game.Game;
import com.company.g1.a1g1_madp.game.entity.*;
import com.company.g1.a1g1_madp.utils.TextDrawable;

public class GameView extends SurfaceView implements Runnable {

	private Game game;
	private GameUI gameUI;
	public static EntityPaint textPaint = new EntityPaint(); // TODO

	private Thread renderThread = null;
	private SurfaceHolder holder;
	private Canvas canvas;
	private volatile boolean running = false;

	private Context context;

	private SparseArray<Drawable> cache;

	public GameView(Context context, Game game) {
		super(context);

		this.context = context;
		this.setZOrderOnTop(true);
		holder = getHolder();
		holder.setFormat(PixelFormat.TRANSLUCENT);
		this.game = game;

		game.addOnResumeListener(this::resume);
		game.addOnPauseListener(this::pause);

		AssetManager am = context.getApplicationContext().getAssets();
		Typeface typeface = Typeface.createFromAsset(am, "font/qishangbaxia.ttf");
		Typeface bold = Typeface.create(typeface,Typeface.BOLD);
		textPaint.setPaint(50, bold, getResources().getColor(R.color.colorBullet));

		cache = new SparseArray<>();
	}



	public void resume() {
		Log.d("gameview", "resume()");
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	@Override
	public void run() {
		while (running) {
			if (!holder.getSurface().isValid())
				continue;
			canvas = holder.lockCanvas();
			draw(canvas);
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		Log.d("gameview", "pause()");
		running = false;
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
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
		for (GameObject entity : game.getEntityRegister().getEntities())
			drawEntity(entity);

		gameUI.updateUI();
//		canvas.drawText(getResources().getString(R.string.money_now, game.getShopSystem().getMoney()), 50, 100, textPaint);

	}

	private void drawEntity(GameObject obj, Drawable drawable) {
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

	private void drawEntity(GameObject obj, int resourceID) {
		if (cache.get(resourceID) == null)
			cache.put(resourceID, ContextCompat.getDrawable(context, resourceID));
		Drawable drawable = cache.get(resourceID);
		if (drawable != null) drawEntity(obj, drawable);
	}

	private void drawEntity(GameObject obj) {
		if (obj instanceof Bullet) {
			Entity.EntityType type = ((Bullet) obj).getEntityType();
			drawEntity(obj, new TextDrawable(((Bullet) obj).getText(), textPaint));
		} else {
			drawEntity(obj, getResourceID(obj));
		}
	}

	private int getResourceID(GameObject obj) {
		if (obj instanceof Spaceship)
			return R.drawable.madgirl;
		else if (obj instanceof Enemy)
			if(((Enemy)obj).getEntityType() == Entity.EntityType.CHINESE)
				return R.drawable.dse_chi;
			else if(((Enemy)obj).getEntityType() == Entity.EntityType.ENGLISH)
				return R.drawable.dse_eng;
			else if(((Enemy)obj).getEntityType() == Entity.EntityType.MATHS)
				return R.drawable.dse_maths;
			else return 0;
		else if (obj instanceof Bullet)
			return R.drawable.bullet;
		else if (obj instanceof Tower)
			return R.drawable.tower;
		return 0;
	}

	private static class EntityPaint extends Paint {
		void setPaint(int size, Typeface typeface, int color) {
			setTypeface(typeface);
			setColor(color);
			setTextSize(size);
			setAntiAlias(true);
			setStyle(Paint.Style.FILL);
		}
	}

	public void setGameUI(GameUI gameUI) {
		this.gameUI = gameUI;
	}
}
