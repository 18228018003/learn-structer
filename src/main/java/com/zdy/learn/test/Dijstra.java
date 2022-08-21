package com.zdy.learn.test;

import java.util.*;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/1 21:56
 */
public class Dijstra {

    public static void bfs(Node node)
    {
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.val);
            for (Node next : cur.nexts) {
                if (!set.contains(next)){
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

    public static void dfs(Node node)
    {
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.push(node);
        set.add(node);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)){
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    break;
                }
            }
        }
    }

    public static HashMap<Node,Integer> dijstra(Node node){
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        HashSet<Node> selectedNodes = new HashSet<>();
        distanceMap.put(node,0);

        Node minNode = getUnSelectedMinNode(distanceMap,selectedNodes);
        while (minNode != null){
            Integer distance = distanceMap.get(minNode);
            for (Edge next : minNode.edges) {
                Node cur = next.to;
                if (!distanceMap.containsKey(cur)){
                    distanceMap.put(cur,next.weight+distance);
                }
                distanceMap.put(cur,Math.min(distanceMap.get(cur),next.weight+distance));
            }
            selectedNodes.add(minNode);
            minNode = getUnSelectedMinNode(distanceMap,selectedNodes);
        }
        return distanceMap;
    }

    private static Node getUnSelectedMinNode(HashMap<Node, Integer> distanceMap, HashSet<Node> selectedNodes) {
        Node minNode = null;
        Integer minDistance = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distanceMap.entrySet()) {
            Node node = entry.getKey();
            Integer distance = entry.getValue();
            if (!selectedNodes.contains(node) && distance < minDistance){
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
    }
}
