package com.zdy.learn.test;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/1 20:47
 */
public class Edge {
    public int weight;
    public Node from;
    public Node to;

    public Edge(int weight,Node from,Node to){
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

}
