package com.zdy.learn.graph;

import java.util.ArrayList;


/**
 * description
 *
 * @author 周德永
 * @date 2021/10/30 23:42
 */
public class Node {
    public int value; /*值*/
    public int in;  /*入度*/
    public int out;/*出度*/
    public ArrayList<Node> nexts; /*当前点所有出边的连接点*/
    public ArrayList<Edge> edges;   /*当前点的所有出边*/

    public Node(int value){
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
