package com.example.jc056596.ballgame2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Collection;

public interface CharacterSprite {
    public float getLeft();
    public float getTop();
    public float getWidth();
    public float getHeight();
    public float getRight();
    public float getBottom();
    public float getSquaredDistance(float x, float y);
    public float padding();
    public void draw(Canvas canvas);
    public void update(Collection<CharacterSprite> characterSpriteCollection);

}
