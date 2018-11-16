package com.example.jc056596.ballgame2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Collection;

public class Ball implements CharacterSprite {
    private float cx;
    private float cy;
    private float oldCx;
    private float oldCy;
    private float radius;
    private float radiusSquared;
    private float vx = 10;
    private float vy = 0;
    private Paint paint;
    private Bitmap image;
    private boolean onGround = false;
    private boolean onGroundLast = false;

    public Ball(Bitmap bmp) {
        /*Make sure that the bitmap has an equal height and width!!!!*/
        radius = bmp.getHeight() / 2;
        this.radiusSquared = radius * radius;
        cx = radius;
        cy = radius;
        oldCx = cx;
        oldCy = cy;
        image = bmp;
        paint = null;

    }

    public Ball(float radius) {
        cx = 0;
        cy = 0;
        oldCx = cx;
        oldCy = cy;
        this.radius = radius;
        this.radiusSquared = radius * radius;

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

    }

    public Ball(float x, float y, float radius) {
        cx = x;
        cy = y;
        oldCx = cx;
        oldCy = cy;
        this.radius = radius;
        this.radiusSquared = radius * radius;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        image = null;
    }

    public Ball(float x, float y, float radius, Paint paint) {
        cx = x;
        cy = y;
        oldCx = cx;
        oldCy = cy;
        this.radius = radius;
        this.radiusSquared = radius * radius;
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

    public float getVx() {
        return vx;
    }

    public void setVx(float vx) {
        this.vx = vx;
    }

    public float getVy() {
        return vy;
    }

    public void setVy(float vy) {
        this.vy = vy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float r) {
        this.radius = r;
        this.radiusSquared = radius * radius;
    }

    public float getRadiusSquared() {
        return radiusSquared;
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
        return cx - radius;
    }

    @Override
    public float getTop() {
        return cy - radius;
    }

    public float getWidth() {
        return 2 * radius;
    }

    public float getHeight() {
        return 2 * radius;
    }

    @Override
    public float getRight() {
        return radius + cx;
    }

    @Override
    public float getBottom() {
        return radius + cy;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap bmp) {
        image = bmp;
        radius = bmp.getHeight() / 2;
    }

    public boolean intersectLine(Line line) {
        return (line.getDistanceSquared(this.cx, this.cy) <= radiusSquared);
    }

    @Override
    public void draw(Canvas canvas) {
        if (image != null) {
            canvas.drawBitmap(image, getLeft(), getTop(), paint);
        } else {
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }

    @Override
    public void update(Collection<CharacterSprite> characterSprites) {

        onGround = onGroundLast;
        onGroundLast = false;

        oldCx = cx;
        oldCy = cy;

        cx += vx;
        cy += vy;
        vy += MainThread.gravity / MainThread.fps;

        Line closest = null;
        float distSquared = 0;
        do {
            closest = null;
            distSquared = 0;
            for (CharacterSprite chrSprite : characterSprites) {
                if (!(chrSprite instanceof Line)) {
                    continue;
                }
                Line line = (Line) chrSprite;

            /*if (line.getLeft() >= getRight() || line.getTop() >= getBottom() || line.getRight() <= getLeft() || line.getBottom() <= getTop()) {
                continue;
            }*/

                float newHeightDiff = line.getHeightDist(cx, cy);
                float oldHeightDiff = line.getHeightDist(oldCx, oldCy);

            /*if (((newHeightDiff <= 0) && (oldHeightDiff >= 0)) || ((newHeightDiff >= 0) && (oldHeightDiff <= 0))) {
                collideLine(line);
            }*/

                float distanceSquared = line.getDistanceSquared(cx, cy);
                if (distanceSquared <= radiusSquared) {
                    if (closest == null || distanceSquared <= distSquared) {
                        closest = line;
                        distSquared = distanceSquared;
                    }
                }
            }
            if (closest != null) {
                collideLine(closest);
            }
            System.out.println("Still Going");
            System.out.println(closest.getLeft() + " " + closest.getTop() + " " + distSquared + " " + radiusSquared);
        } while (closest != null);
        System.out.println("Done");
    }

    private void collideLine(Line line) {


        if (line.getLeft() > getRight() || line.getRight() < getLeft() || line.getTop() > getBottom() || line.getBottom() < getTop()) {
            return;
        }

        float signedRadius;
        if (line.getHeightDist(cx, cy) > 0) {
            signedRadius = -radius;
        } else {
            signedRadius = radius;
        }
        float temp_cy = cy;
        float temp_cx = cx * line.getCos2A() - 2 * line.getA() * (line.getB() * temp_cy + (line.getC() + signedRadius));
        cy = -temp_cy * line.getCos2A() - 2 * line.getB() * (line.getA() * cx + line.getC() + signedRadius);
        cx = temp_cx;


        float temporary_vx = vx * line.getCos2A() - vy * line.getSin2A();
        float temporary_vy = -vx * line.getSin2A() - vy * line.getCos2A();
        if (!onGround) {
            vx = (temporary_vx * MainThread.bounciness + vx * (1 - MainThread.bounciness)) * MainThread.elasticity;
            vy = (temporary_vy * MainThread.bounciness + vy * (1 - MainThread.bounciness)) * MainThread.elasticity;
        } else {
            vx = (temporary_vx * 0.5f + vx * 0.5f) * MainThread.friction;
            vy = (temporary_vy * 0.5f + vy * 0.5f) * MainThread.friction;
        }
        onGround = true;
        onGroundLast = true;


    }
}
