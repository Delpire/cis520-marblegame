package com.project3.cis520.marblegame;

/**
 * Created by cadel_000 on 12/15/2014.
 */
public class DisjointSet {

    public int nodes [];

    public DisjointSet(int numberOfNodes)
    {
        nodes = new int[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++)
            nodes[i] = -1;
    }

    public int Find(int otherNode)
    {
        return nodes[otherNode] < 0 ? otherNode : Find(nodes[otherNode]);
    }

    public void Union(int node1, int node2)
    {
        if (nodes[node1] > nodes[node2])
        {
            nodes[node1] = node2;
            nodes[node2]--;
        }
        else
        {
            nodes[node2] = node1;
            nodes[node1]--;
        }
    }

}
