package com.project3.cis520.marblegame;

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

    Maze maze;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Ball(int width, int height, Maze maze){

        canvasWidth = width;
        canvasHeight = height;

        radius = 20;
        x = 100;
        y = 100;

        this.maze = maze;
    }

    public void update(float move_x, float move_y){

        float new_x = x - move_x;
        float new_y = y + move_y;

        if(new_x + radius >= canvasWidth || new_x - radius <= 0){
            new_x = x;
        }

        if(new_y + radius >= canvasHeight || new_y - radius <= 0){
            new_y = y;
        }

        if(maze.checkCollisionX(x, new_x, y, radius)){
            new_x = x;
        }

        if(maze.checkCollisionY(y, new_y, x, radius)){
            new_y = y;
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
