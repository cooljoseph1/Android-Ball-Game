package com.example.jc056596.ballgame2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.opengl.GLSurfaceView;
import android.support.v4.content.res.ResourcesCompat;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.LinkedList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private LinkedList<CharacterSprite> characterSprites;
    private Ball ball;
    private Paint linePaint;


    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Resources res = getResources();
        ball = new Ball(BitmapFactory.decodeResource(getResources(), R.drawable.ball));
        ball.setPos(500, 200);
        System.out.println(ball.getRadius());
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(8);

        characterSprites = new LinkedList<CharacterSprite>();
        characterSprites.add(ball);
        characterSprites.add(new Line(1000, 0, 1000, 1200, linePaint));
        characterSprites.add(new Line(100, 0, 100, 1200, linePaint));
        characterSprites.add(new Line(550, 700, 550, 1000, linePaint));
        characterSprites.add(new Line(548,705,552,705, linePaint));
        characterSprites.add(new Line(100, 4, 1000, 4, linePaint));
        characterSprites.add(new Line(1000, 1100, 550, 1000, linePaint));
        characterSprites.add(new Line(100, 1100, 550, 1000, linePaint));


        thread.setRunning(true);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            thread.setRunning(false);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void update() {
        for (CharacterSprite chrSprite : characterSprites) {
            chrSprite.update(characterSprites);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            canvas.save();
            canvas.translate(-ball.getLeft() + getPivotX(), -ball.getTop() + getPivotY());
            canvas.drawColor(Color.WHITE);
            for (CharacterSprite chrSprite : characterSprites) {
                chrSprite.draw(canvas);
            }
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            MainThread.setGravity(50);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {
            MainThread.setGravity(10);
        }
        return true;
    }
}