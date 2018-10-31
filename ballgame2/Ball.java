package com.example.jc056596.ballgame2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Collection;

public class Ball implements CharacterSprite {
    private float cx;
    private float cy;
    private float left;
    private float top;
    private float radius;
    private Paint paint;
    private Bitmap image;

    public Ball(Bitmap bmp) {
        /*Make sure that the bitmap has an equal height and width!!!!*/
        radius = bmp.getHeight() / 2;
        left = 0;
        top = 0;
        cx = left + radius;
        cy = top + radius;
        image = bmp;
        paint = null;

    }

    public Ball(float radius) {
        cx = 0;
        cy = 0;
        left = -radius;
        top = -radius;
        this.radius = radius;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

    }

    public Ball(float x, float y, float radius) {
        cx = x;
        cy = y;
        this.radius = radius;
        left = cx - radius;
        top = cx - radius;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        image = null;
    }

    public Ball(float x, float y, float radius, Paint paint) {
        cx = x;
        cy = y;
        this.radius = radius;
        left = cx - radius;
        top = cx - radius;

        this.paint = paint;
        image = null;
    }


    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public void setPos(float x, float y) {
        this.cx = x;
        this.cy = y;
    }

    public float[] getPos() {
        return new float[]{cx, cy};
    }

    public void setPos(float[] pos) {
        this.cx = pos[0];
        this.cy = pos[1];
    }

    @Override
    public float getLeft() {
        return left;
    }

    @Override
    public float getTop() {
        return top;
    }

    public float getWidth() {
        return 2 * radius;
    }

    public float getHeight() {
        return 2 * radius;
    }

    @Override
    public float getRight() {
        return 2 * radius + left;
    }

    @Override
    public float getBottom() {
        return 2 * radius + top;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap bmp) {
        image = bmp;
        radius = bmp.getHeight() / 2;
        left = cx - radius;
        top = cy - radius;
    }

    public boolean intersect(CharacterSprite chrSprite) {
        if(chrSprite.getSquaredDistance(cx,cy)<=Math.pow(radius+chrSprite.padding(),2)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public float padding() {
        return radius;
    }
    @Override
    public boolean getSquaredDistance(float x, float y) {
        return (x-cx)*(x-cx)+(y-cy)*(y-cy);
    }

    @Override
    public void draw(Canvas canvas) {
        if (image != null) {
            canvas.drawBitmap(image, left, top, paint);
        } else {
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    @Override
    public void update(Collection<CharacterSprite> characterSprites) {

        for (CharacterSprite chrSprite : characterSprites) {

        }
    }
}
