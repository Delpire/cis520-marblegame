package com.project3.cis520.marblegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cadel_000 on 12/15/2014.
 */
public class MazeGenerator {

    public DisjointMaze Generate(int height, int width, int size)
    {
        int totalCells = (width/size)*(height/size);
        DisjointSet cells = new DisjointSet(totalCells);
        List<Corner> corners = ConstructWalls(width, height, size);

        List<Wall> walls = new ArrayList<Wall>();
        for (Corner corner : corners)
        {
            walls.add(corner.bottom);
            walls.add(corner.right);
        }

        RandomlyDestroyWalls(walls, cells);

        return new DisjointMaze(corners, height, width, size);
    }

    private void RandomlyDestroyWalls(List<Wall> walls, DisjointSet cells)
    {
        ShuffleWalls(walls);

        for (Wall wall : walls)
            RemoveIfNeeded(wall, cells);
    }

    private void RemoveIfNeeded(Wall wall, DisjointSet cells)
    {
        if (!wall.IsUp)
            return;

        int cell1Parent = cells.Find(wall.CellIndex1);
        int cell2Parent = cells.Find(wall.CellIndex2);

        if (cell1Parent != cell2Parent)
        {
            wall.KnockDown();
            cells.Union(cell1Parent, cell2Parent);
        }
    }

    private void ShuffleWalls(List<Wall> list)
    {
        Random random = new Random();
        int count = list.size();

        for (int i = 0; i < count; i++)
        {
            int swapIndex = random.nextInt(count);
            Wall temp = list.get(i);
            list.set(i, list.get(swapIndex));
            list.set(swapIndex, temp);
        }
    }

    private List<Corner> ConstructWalls(int width, int height, int size)
    {
        List corners = new ArrayList<Corner>();
        int numberOfCorners = (width/size)*(height/size);

        for (int i = 0; i < numberOfCorners; i++)
            AddRightAndBottomWallAsNeeded(corners, i, width/size, height/size);

        return corners;
    }

    private void AddRightAndBottomWallAsNeeded(List<Corner> corners, int setIndex, int width, int height)
    {
        Corner corner = new Corner();
        corner.right = NeedsRightWall(setIndex, width) ? new Wall(setIndex, setIndex + 1) : new Wall();
        corner.bottom = NeedsBottomWall(setIndex, width, height) ? new Wall(setIndex, setIndex + width) : new Wall();

        corners.add(corner);
    }

    private boolean NeedsRightWall(int index, int width)
    {
        return (index + 1)%width != 0;
    }

    private boolean NeedsBottomWall(int index, int width, int height)
    {
        return index < (width*(height-1));
    }

}
