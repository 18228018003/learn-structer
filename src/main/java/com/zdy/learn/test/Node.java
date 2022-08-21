package com.zdy.learn.test;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/1 20:47
 */
public class Node {
    public int val;
    public int in;
    public int out;
    public List<Node> nexts;
    public List<Edge> edges;

    public Node(int val){
        this.val = val;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
