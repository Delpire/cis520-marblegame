package com.project3.cis520.marblegame;

/**
 * Created by cadel_000 on 12/15/2014.
 */
public class Wall {

    public int CellIndex1;
    public int CellIndex2;
    public boolean IsUp;

    public Wall(int cell1, int cell2)
    {
        CellIndex1 = cell1;
        CellIndex2 = cell2;
        IsUp = true;
    }

    public Wall()
    {
        IsUp = false;
    }

    public void KnockDown()
    {
        IsUp = false;
    }
}
