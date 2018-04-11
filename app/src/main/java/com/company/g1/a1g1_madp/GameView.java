package com.company.g1.a1g1_madp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.company.g1.a1g1_madp.R;
import com.company.g1.a1g1_madp.game.Bullet;
import com.company.g1.a1g1_madp.game.Enemy;
import com.company.g1.a1g1_madp.game.Game;
import com.company.g1.a1g1_madp.game.Spaceship;

import java.util.List;

public class GameView extends SurfaceView implements Runnable {

    Game game;
    Spaceship spaceship;
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    Bitmap bitmap;
    Thread renderThread = null;
    SurfaceHolder holder;
    Canvas canvas;
    volatile boolean running = false;

    public GameView(Context context, Game game) {
        super(context);
        holder = getHolder();
        // Stupid
        paint.setColor(Color.BLUE);
        paint2.setColor(0xfc00ff00);

        this.game = game;
        this.spaceship = game.getSpaceship();

		game.addOnResumeListener(() -> resume());
		game.addOnPauseListener(() -> pause());

    }

    void loadImageResources() {
        Bitmap _bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle_vector_2);
        bitmap = Bitmap.createScaledBitmap(_bitmap,(int)spaceship.getWidth(),(int)spaceship.getHeight(),false);
    }

    public void resume() {
        running = true;
        loadImageResources();
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {
        while(running) {
            if(!holder.getSurface().isValid())  // What does this do?
                continue;
            canvas = holder.lockCanvas();
            draw(canvas);   // This part deviates from the copy source, is it ok?
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        // No idea what's going on
        while(true) {
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
        drawSpaceship(spaceship);
        drawBullets(game.getBullets());
        drawEnemies(game.getEnemies());
    }

    private void drawSpaceship(Spaceship spaceship) {
        canvas.drawBitmap(bitmap,spaceship.getX(),spaceship.getY(),null);
//        float centerX = spaceship.x + spaceship.radius;
//        float centerY = spaceship.y + spaceship.radius;
//        canvas.drawCircle(centerX,centerY,spaceship.radius,paint);
    }

    private void drawBullets(List<Bullet> bulletList) {
        for(Bullet bullet : bulletList) {
            float centerX = bullet.getX() + bullet.getRadius();
            float centerY = bullet.getY() + bullet.getRadius();
            canvas.drawCircle(centerX,centerY,bullet.getRadius(), paint);
        }
    }

    private void drawEnemies(List<Enemy> enemyList) {
        for(Enemy enemy : enemyList) {
            float centerX = enemy.getX() + enemy.getRadius();
            float centerY = enemy.getY() + enemy.getRadius();
            canvas.drawCircle(centerX,centerY,enemy.getRadius(), paint);
        }
    }
}
