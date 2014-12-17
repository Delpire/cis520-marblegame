package com.project3.cis520.marblegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by cadel_000 on 12/15/2014.
 */
public class MazeDrawer {

    private DisjointMaze maze;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Canvas canvas;
    private int width;
    private int height;
    private int currentX;
    private int currentY;
    public int cellWidth;
    private boolean firstDraw;

    private boolean pixelArray[][];

    public MazeDrawer(DisjointMaze m, int size, Canvas canvas) {
        maze = m;
        cellWidth = size;
        currentX = cellWidth;
        currentY = cellWidth;
        this.canvas = canvas;
        width = canvas.getWidth();
        height = canvas.getHeight();
        firstDraw = true;
        pixelArray = new boolean[height][width];
    }

    public void render(Canvas canvas) {
        currentX = cellWidth;
        currentY = cellWidth;
        this.canvas = canvas;
        paint.setColor(Color.BLACK);

        int numberOfCorners = maze.corners.size();
        for (int i = 0; i < numberOfCorners; i++)
            DrawWallIfUp(i);

        firstDraw = false;

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(width - cellWidth, height - cellWidth, width, height, paint);
    }

    private void DrawWallIfUp(int index) {
        Corner corner = maze.corners.get(index);

        if (corner.right.IsUp)
            DrawVerticalWall();

        if (corner.bottom.IsUp)
            DrawHorizontalWall();

        MoveToNextCell(index);
    }

    private void MoveToNextCell(int index) {
        if (IsLastCellInRow(index))
            GoToNextRow();
        else
            currentX += cellWidth;
    }

    private void DrawVerticalWall() {
        int endX = currentX;
        int endY = currentY - cellWidth;
        canvas.drawLine(currentX, currentY, endX, endY, paint);
        if(firstDraw) {
            for (int i = endY; i <= currentY; i++) {
                pixelArray[i][currentX] = true;
            }
        }
    }

    private void DrawHorizontalWall() {
        int endX = currentX - cellWidth;
        int endY = currentY;
        canvas.drawLine(currentX, currentY, endX, endY, paint);
        if(firstDraw) {
            for (int i = endX; i <= currentX; i++) {
                pixelArray[currentY][i] = true;
            }
        }
    }

    private void GoToNextRow() {
        currentY += cellWidth;
        currentX = cellWidth;
    }

    private boolean IsLastCellInRow(int index) {
        return (index + 1) % maze.width == 0;
    }

    public boolean checkCollisionX(int x, int newX, int y, int radius) {
        int leftX = Math.max(Math.min(x, newX) - radius, 0);
        int rightX = Math.min(Math.max(x, newX) + radius, canvas.getWidth());
        int topY = Math.max(y - radius, 0);
        int bottomY = Math.min(y + radius, canvas.getHeight());

        for (int i = topY; i <= bottomY; i++)
        {
            for (int j = leftX; j <= rightX; j++)
            {
                if (pixelArray[i][j])
                    return true;
            }
        }

        return false;
    }

    public boolean checkCollisionY(int x, int y, int newY, int radius) {
        int leftX = Math.max(x - radius, 0);
        int rightX = Math.min(x + radius, canvas.getWidth());
        int topY = Math.max(Math.min(y, newY) - radius, 0);
        int bottomY = Math.min(Math.max(y, newY) + radius, canvas.getHeight());

        for (int i = topY; i <= bottomY; i++) {
            for (int j = leftX; j <= rightX; j++) {
                if (pixelArray[i][j])
                    return true;
            }
        }

        return false;
    }
}
