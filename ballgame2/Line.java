package com.example.jc056596.ballgame2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Collection;

public class Line implements CharacterSprite {
    private float a;
    private float b;
    private float c;
    private float startX;
    private float startY;
    private float endX;
    private float endY;
    private float top;
    private float bottom;
    private float left;
    private float right;

    private float cos2A;
    private float sin2A;

    private Paint paint;

    public Line(float startX, float startY, float endX, float endY) {
        this(startX, startY, endX, endY, null);
    }

    public Line(float startX, float startY, float endX, float endY, Paint paint) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.top = Math.min(endY, startY);
        this.bottom = Math.max(endY, startY);
        this.left = Math.min(startX, endX);
        this.right = Math.max(startX, endX);
        this.paint = paint;
        a = endY - startY;
        b = startX - endX;
        c = startY * endX - startX * endY;
        normalizeLine();
        cos2A = 2 * b * b - 1;
        sin2A = 2 * b * a;

    }

    public void normalizeLine() {
        float ab_norm = (float) Math.sqrt(a * a + b * b);
        a = a / ab_norm;
        b = b / ab_norm;
        c = c / ab_norm;
    }

    public float getCos2A() {
        return cos2A;
    }
    public float getSin2A() {
        return sin2A;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    public float getC() {
        return c;
    }

    @Override
    public float getLeft() {
        return this.left;
    }

    @Override
    public float getTop() {
        return this.top;
    }

    @Override
    public float getWidth() {
        return this.endX - this.startX;
    }

    @Override
    public float getHeight() {
        return this.bottom - this.top;
    }

    @Override
    public float getRight() {
        return this.right;
    }

    @Override
    public float getBottom() {
        return this.bottom;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(startX, startY, endX, endY, paint);
    }

    @Override
    public void update(Collection<CharacterSprite> characterSpriteCollection) {
    }

    public float getHeightDist(float x, float y) {
        //Returns the height a point is above a line.

        return (a * x) + (b * y) + c;
    }


    public float getDistanceSquared(float x, float y) {
        //returns squared distance of the point to the line
        //we don't need to divide by a**2 + b**2 because we normalized it.
        return (float) Math.pow(a * x + b * y + c, 2);
    }

    public boolean intersectBall(Ball ball) {
        /*Just don't use this.  There is a better implementation in the Ball class.*/
        return (getDistanceSquared(ball.getCx(), ball.getCy()) <= ball.getRadiusSquared());
    }
}
