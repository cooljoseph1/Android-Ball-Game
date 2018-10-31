package com.example.jc056596.ballgame2;

import android.graphics.Canvas;

import java.util.Collection;

public class Line implements CharacterSprite {
    private float a;
    private float b;
    private float c;
    private float startX;
    private float startY;
    private float endX;
    private float endY;


    @Override
    public float getLeft() {
        return 0;
    }

    @Override
    public float getTop() {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public float getRight() {
        return 0;
    }

    @Override
    public float getBottom() {
        return 0;
    }

    @Override
    public float getPadding() {
        return 0;
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    public void update(Collection<CharacterSprite> characterSpriteCollection) {

    }

    public float getDistance(float x, float y) {
        //returns squared distance of the point to the line
        return (float) Math.pow(a * x + b * y + c, 2) / ((a * a) + (b * b));
    }
}
