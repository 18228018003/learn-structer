package com.zdy.learn.graph;

import java.util.*;

/**
 *  克鲁斯卡尔算法：
 * @author 周德永
 * @date 2021/10/31 14:26
 */
public class Kruskal {

    public static class Mysets{
        public HashMap<Node, List<Node>> setMap;

        public Mysets(List<Node> nodes){
            for (Node cur : nodes) {
                ArrayList<Node> set = new ArrayList<>();
                set.add(cur);
                setMap.put(cur,set);
            }
        }
        /*判断from所在集合和to所在集合是否是同一个集合*/
        public boolean isSameSet(Node from,Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);
            return fromSet == toSet;
        }

        /*把用户提供的两个集合 结合为一个集合*/
        public void union(Node from,Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);

            for (Node toNode : toSet) {
                fromSet.add(toNode);
                setMap.put(toNode,fromSet);
            }
        }

    }
    public static class EdgeComparator implements Comparator<Edge>{

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }
    public static Set<Edge> krusKalMST(Graph graph){
        List<Node> list = new ArrayList<>(graph.nodes.values());
        Mysets mysets = new Mysets(list);
        PriorityQueue<Edge> queue = new PriorityQueue<>(new EdgeComparator());
        queue.addAll(graph.edges);
        HashSet<Edge> result = new HashSet<>();
        while (!queue.isEmpty()){
            Edge cur = queue.poll();
            if (!mysets.isSameSet(cur.from,cur.to)){
                result.add(cur);
                mysets.union(cur.from,cur.to);
            }
        }
        return result;
    }
}
