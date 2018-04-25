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

import com.company.g1.a1g1_madp.game.Game;
import com.company.g1.a1g1_madp.game.entity.*;
import com.company.g1.a1g1_madp.utils.TextDrawable;

public class GameView extends SurfaceView implements Runnable {

	private Game game;
	protected GameUI gameUI;

	private Thread renderThread = null;
	private SurfaceHolder holder;
	private Canvas canvas;
	private volatile boolean running = false;
	public StageResources stageResources;

	private Context context;

	private SparseArray<Drawable> cache;

	public GameView(Context context, Game game, GameUI gameUI, int stage) {
		super(context);

		this.context = context;

		this.gameUI = gameUI;
		this.stageResources = new StageResources(stage);
		stageResources.am = context.getApplicationContext().getAssets();
		stageResources.loadResources();

		gameUI.setBackground(stageResources.background, stageResources.backgroundColor);
		gameUI.setPaint(stageResources.textPaint);
		gameUI.setStageLabel(stageResources.stageName);
		gameUI.setTimerLabel(stageResources.timerString);
		Bullet.setBulletText(stageResources.text_chi,stageResources.text_eng,stageResources.text_maths);
		this.setZOrderOnTop(true);
		holder = getHolder();
		holder.setFormat(PixelFormat.TRANSLUCENT);
		this.game = game;

		game.addOnResumeListener(this::resume);
		game.addOnPauseListener(this::pause);

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
			drawEntity(obj, new TextDrawable(((Bullet) obj).getText(), stageResources.textPaint));
		} else {
			drawEntity(obj, getResourceID(obj));
		}
	}

	private int getResourceID(GameObject obj) {
		if (obj instanceof Spaceship)
			return R.drawable.madgirl;
		else if (obj instanceof Enemy)
			if(((Enemy)obj).getEntityType() == Entity.EntityType.CHINESE)
				return stageResources.enemy_chi;
			else if(((Enemy)obj).getEntityType() == Entity.EntityType.ENGLISH)
				return stageResources.enemy_eng;
			else if(((Enemy)obj).getEntityType() == Entity.EntityType.MATHS)
				return stageResources.enemy_maths;
			else return 0;
		else if (obj instanceof Bullet)
			return R.drawable.bullet;
		else if (obj instanceof Tower)
			return R.drawable.madboy;
		return 0;
	}

	/*
		Mapping:
		1 - Primary
		2 - Secondary
		3 - Space
 	*/

	class StageResources {

		int stage;
		AssetManager am;

		int enemy_chi, enemy_eng, enemy_maths, background, backgroundColor;
		EntityPaint textPaint = new EntityPaint();
		String stageName, text_chi, text_eng, text_maths, timerString;

		public StageResources(int stage) {
			this.stage = stage;
		}

		void loadResources() {
			if(stage == 1) {
				enemy_chi 		 = R.drawable.dse_chi;
				enemy_eng 		 = R.drawable.dse_eng;
				enemy_maths 	 = R.drawable.dse_maths;
				background 		 = R.drawable.board_primary;
				backgroundColor  = R.color.primaryBgColor;

				Typeface typeface = Typeface.createFromAsset(am, "font/qishangbaxia.ttf");
				Typeface bold = Typeface.create(typeface,Typeface.BOLD);
				textPaint.setPaint(50, bold, R.color.primaryBulletColor);

				stageName = getResources().getString(R.string.primary_stage_name);

				text_chi   = getResources().getString(R.string.primary_text_chi);
				text_eng   = getResources().getString(R.string.primary_text_eng);
				text_maths = getResources().getString(R.string.primary_text_maths);
				timerString = getResources().getString(R.string.time_remain);
			}

			else if (stage == 2) {
				enemy_chi 		 = R.drawable.dse_chi;
				enemy_eng 		 = R.drawable.dse_eng;
				enemy_maths 	 = R.drawable.dse_maths;
				background 		 = R.drawable.board_secondary;
				backgroundColor  = R.color.secondaryBgColor;

				Typeface bold = Typeface.create(Typeface.SERIF,Typeface.BOLD);
				textPaint.setPaint(50, bold, R.color.secondaryBulletColor);

				stageName = getResources().getString(R.string.dse_stage_name);

				text_chi   = getResources().getString(R.string.dse_text_chi);
				text_eng   = getResources().getString(R.string.dse_text_eng);
				text_maths = getResources().getString(R.string.dse_text_maths);
				timerString = getResources().getString(R.string.time_remain);
			}
			else {
				enemy_chi 		 = R.drawable.asso_acl;
				enemy_eng 		 = R.drawable.asso_eapp;
				enemy_maths 	 = R.drawable.asso_mid;
				background 		 = R.drawable.board_asso;
				backgroundColor  = R.color.assoBgColor;

				Typeface bold = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
				textPaint.setPaint(50, bold, R.color.assoBulletColor);

				stageName = getResources().getString(R.string.asso_stage_name);

				text_chi   = getResources().getString(R.string.asso_text_chi);
				text_eng   = getResources().getString(R.string.asso_text_eng);
				text_maths = getResources().getString(R.string.asso_text_maths);

				timerString = getResources().getString(R.string.asso_time_remain);

			}
		}

		private class EntityPaint extends Paint {
			void setPaint(int size, Typeface typeface, int colorResID) {
				setTypeface(typeface);
				setColor(ContextCompat.getColor(context, colorResID));

				setTextSize(size);
				setAntiAlias(true);
				setStyle(Paint.Style.FILL);
			}
		}
	}
}
