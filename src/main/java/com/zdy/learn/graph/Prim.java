package com.zdy.learn.graph;

import java.util.*;

/**
 * prim算法
 *
 * @author 周德永
 * @date 2021/10/31 15:36
 */
public class Prim {

    private static class EdgeComparator implements Comparator<Edge> {

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> primMST(Graph graph) {
        HashSet<Node> set = new HashSet<>();
        HashSet<Edge> result = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

        for (Node node : graph.nodes.values()) {
            /*node 是开始点*/
            if (!set.contains(node)) {
                set.add(node);
                priorityQueue.addAll(node.edges);
                while (!priorityQueue.isEmpty()) {
                    Edge edge = priorityQueue.poll();
                    Node to = edge.to;
                    if (!set.contains(to)) {
                        set.add(to);
                        result.add(edge);
                        priorityQueue.addAll(to.edges);
                    }
                }
            }
        }
        return result;
    }


    public static HashMap<Node, Integer> dijkstra(Node node) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        HashSet<Node> selectedNodes = new HashSet<>();
        distanceMap.put(node, 0);
        Node minNode = getMinNodeUnSelectedNodes(distanceMap, selectedNodes);
        while (minNode != null) {
            Integer distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    distanceMap.put(toNode, distance + edge.weight);
                }
                distanceMap.put(toNode,
                        Math.min(distanceMap.get(toNode), distance + edge.weight));
            }
            selectedNodes.add(minNode);
            minNode = getMinNodeUnSelectedNodes(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    private static Node getMinNodeUnSelectedNodes(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        Integer minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            Integer distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }

}
