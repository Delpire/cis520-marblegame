package com.project3.cis520.marblegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by cadel_000 on 12/13/2014.
 */
public class Ball {

    public int radius;
    public float x;
    public float y;
    private int canvasWidth;
    private int canvasHeight;
    public boolean isMoving = false;
    public boolean hasWon = false;

    MazeDrawer maze;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Ball(int width, int height, MazeDrawer maze){

        canvasWidth = width;
        canvasHeight = height;

        radius = 15;
        x = 20;
        y = 20;

        this.maze = maze;
    }

    public void update(float move_x, float move_y) {

        float new_x = x - move_x;
        float new_y = y + move_y;

        isMoving = false;

        if(new_x >= canvasWidth - maze.cellWidth && new_y >= canvasHeight - maze.cellWidth){
            hasWon = true;
        }

        if (new_x + radius >= canvasWidth || new_x - radius <= 0) {
            new_x = x;
        }

        if (new_y + radius >= canvasHeight || new_y - radius <= 0) {
            new_y = y;
        }

        if (maze.checkCollisionX((int) x, (int) new_x, (int) y, (int) radius)) {
            new_x = x;
        }

        if (maze.checkCollisionY((int) x, (int) y, (int) new_y, (int) radius)) {
            new_y = y;
        }

        if(new_x != x || new_y != y){
            isMoving = true;
        }
        x = new_x;
        y = new_y;
    }

    public void render(Canvas canvas){

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x, y, radius, paint);

    }
}
