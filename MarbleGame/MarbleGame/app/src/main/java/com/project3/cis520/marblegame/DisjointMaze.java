package com.project3.cis520.marblegame;

import java.util.List;

/**
 * Created by cadel_000 on 12/15/2014.
 */
public class DisjointMaze {

    //int size;
    public int width;
    public int height;
    public List<Corner> corners;

    public DisjointMaze( List<Corner> c, int h, int w, int size)
    {
        corners = c;
        //this.size = size;
        height = h / size;
        width = w / size;
    }

}
