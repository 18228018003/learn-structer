package com.zdy.learn.test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/1 21:22
 */
public class Prim {
    private static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }
    public static Set<Edge> primMST(Graph graph) {
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        HashSet<Node> set = new HashSet<>();
        HashSet<Edge> result = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!set.contains(node)){
                set.add(node);
                queue.addAll(node.edges);
                while (!queue.isEmpty()){
                    Edge cur = queue.poll();
                    Node toNode = cur.to;
                    if (!set.contains(toNode)){
                        set.add(toNode);
                        result.add(cur);
                        queue.addAll(toNode.edges);
                    }
                }
            }
        }
        return result;
    }
}
