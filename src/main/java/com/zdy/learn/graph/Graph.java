package com.zdy.learn.graph;

import java.util.HashMap;
import java.util.HashSet;

/**
 * description
 *
 * @author 周德永
 * @date 2021/10/30 23:51
 */
public class Graph {
    public HashMap<Integer,Node> nodes;
    public HashSet<Edge> edges;

    public Graph(){
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
