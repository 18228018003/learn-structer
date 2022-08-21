package com.zdy.learn.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *  迪杰斯特拉算法 最短路劲算法
 * @author 周德永
 * @date 2021/10/31 16:14
 */
public class Dijkstra {
    public static HashMap<Node,Integer> dijkstra(Node node){
        /*从head出发到所有节点的最小距离*/
        /*key： 从head出发到达key*/
        /*value：从head出发到达key的最小距离*/
        /*如果在表中，没有T 的记录，含义是从head出发到T这个点的距离为正无穷*/
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(node,0);
        /*已经求过距离的节点，存在selectedNodes中，以后再也不管*/
        HashSet<Node> selectedNodes = new HashSet<>();

        Node minNode = getMinDistanceAndUnSelectedNode(distanceMap,selectedNodes);
        while (minNode != null){
            Integer distance = distanceMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode,distance+edge.weight);
                }
                distanceMap.put(toNode,Math.min(distanceMap.get(toNode),distance+edge.weight));
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnSelectedNode(distanceMap,selectedNodes);
        }
        return distanceMap;
    }

    private static Node getMinDistanceAndUnSelectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        int minDistance  = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            Integer distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance){
                minNode = node;
                minDistance = distance;
            }
        }
        return minNode;
    }
}
