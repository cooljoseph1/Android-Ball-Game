package com.example.jc056596.ballgame2;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean running;
    public static Canvas canvas;
    public static float gravity = 10;
    public static float elasticity = 0.9f; //Energy lost when bouncing (higher value means less energy is lost
    public static float bounciness = 0.99f;  //Percent of energy that bounces
    public static float friction = 0.9f;
    public static int fps = 30;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public static void setGravity(float g) {
        gravity = g;
    }
    public static float getGravity() {
        return gravity;
    }

    @Override
    public void run() {
        while(running) {
            canvas=null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }

            } catch(Exception e) {
                e.printStackTrace();} finally {
                if(canvas!=null) {
                    try {surfaceHolder.unlockCanvasAndPost(canvas);}
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
