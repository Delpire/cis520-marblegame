package com.project3.cis520.marblegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by cadel_000 on 12/13/2014.
 */
public class Maze {

    int MazeArray[][];

    int VisitedCells[][];

    int w;
    int h;
    int size;

    private static final int COLLISIONBOX [] = {0, 0, 20, 0, -20, 0, 0, 20, 0, -20, 14, 14, 14, -14, -14, 14, -14, -14};

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Maze(float width, float height){

        size = 45;

        w = (int)width / size;
        h = (int)height / size;

        MazeArray = new int[w][h];

        VisitedCells = new int[w][h];

        for(int x = 0; x < w; x++) VisitedCells[x][0] = VisitedCells[x][h-1] = 1;
        for(int y = 0; y < h; y++) VisitedCells[0][y] = VisitedCells[w-1][y] = 1;

        //generate(1, 1);

    }

    public void generate(int x, int y){

        MazeArray[x][y] = 1;

        VisitedCells[x][y] = 1;

        while( VisitedCells[x][y+1] != 1 || VisitedCells[x+1][y] != 1 || VisitedCells[x][y-1] != 1 || VisitedCells[x-1][y] != 1){

            while(true){

                double random = Math.random();

                if(random < 0.25 && VisitedCells[x][y+1] != 1){

                   if(y + 3 < h){
                       VisitedCells[x][y+2] = 1;
                   }

                    generate(x, y + 1);
                    break;
                }
                else if(random >= 0.25 && random < 0.50 && VisitedCells[x+1][y] != 1){

                    if(x + 3 < w){
                        VisitedCells[x+2][y] = 1;
                    }

                    generate(x + 1, y);
                    break;
                }
                else if(random >= 0.50 && random < 0.75 && VisitedCells[x][y-1] != 1){

                    if(y - 3 >= 0){
                        VisitedCells[x][y-2] = 1;
                    }

                    generate(x, y - 1);
                    break;
                }
                else if(random >= 0.75 && random < 1.0 && VisitedCells[x-1][y] != 1){

                    if(x - 3 >= 0){
                        VisitedCells[x-2][y] = 1;
                    }

                    generate(x - 1, y);
                    break;
                }

            }
        }

    }

    public void render(Canvas canvas){

        paint.setStyle(Paint.Style.FILL);

        paint.setColor(Color.BLACK);

        for(int i = 0; i < w; i++){

            for(int j = 0; j < h; j++){

                if(MazeArray[i][j] == 0){

                    canvas.drawRect(i * size, j * size, i * size + size, j * size + size, paint);
                }
            }
        }
    }

    public boolean checkCollisionX(float x, float new_x, float y, int radius){

        int beginning_x;
        int ending_x;
        int beginning_y = (int)((y - radius) / size);
        int ending_y = (int)((y + radius) / size);

        if(new_x < x){
            beginning_x = (int)(new_x / size);
            ending_x = (int)(x / size);
        }
        else{
            beginning_x = (int)(x / size);
            ending_x = (int)(new_x / size);
        }

        for(int i = beginning_x - 1; i < ending_x + 2; i++){

            for(int j = beginning_y; j < ending_y + 1; j++){

                if(i >= 0 && j >= 0 && i < w && j < h){

                    if(MazeArray[i][j] == 1){
                        return true;
                    }
                }
            }
        }

        return false;

    }

    public boolean checkCollisionY(float y, float new_y, float x, int radius){

        int beginning_y;
        int ending_y;
        int beginning_x = (int)((x - radius) / size);
        int ending_x = (int)((x + radius) / size);

        if(new_y < y){
            beginning_y = (int)(new_y / size);
            ending_y = (int)(y / size);
        }
        else{
            beginning_y = (int)(y / size);
            ending_y = (int)(new_y / size);
        }

        for(int i = beginning_x; i < ending_x + 1; i++){

            for(int j = beginning_y - 1; j < ending_y + 2; j++){

                if(i >= 0 && j >= 0 && i < w && j < h){

                    if(MazeArray[i][j] == 1){
                        return true;
                    }
                }
            }
        }

        return false;

    }



}
