package com.zdy.learn.graph;

import java.util.*;

/**
 *  拓扑排序
 * @author 周德永
 * @date 2021/10/31 14:04
 */
public class TopologySort {
    public static List<Node> sortedTopology(Graph graph){
        /*key：某一个node*/
        /*value：剩余的入度*/
        HashMap<Node, Integer> inMap = new HashMap<>();
        /*入度为零的点 进去这个队列*/
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node,node.in);
            if (node.in == 0){
                zeroInQueue.add(node);
            }
        }
        /*拓扑排序的结果，依次加入result*/
        ArrayList<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()){
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next,inMap.get(next)-1);
                if (inMap.get(next) == 0){
                    zeroInQueue.add(next);
                }
            }

        }
        return result;
    }
}
