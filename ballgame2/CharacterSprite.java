package com.example.jc056596.ballgame2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Collection;

public interface CharacterSprite {
    float getLeft();
    float getTop();
    float getWidth();
    float getHeight();
    float getRight();
    float getBottom();
    void draw(Canvas canvas);
    void update(Collection<CharacterSprite> characterSpriteCollection);

}
