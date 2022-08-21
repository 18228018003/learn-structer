package com.zdy.learn.test;

import java.util.*;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/1 21:02
 */
public class Kruskal {
    public static class MySets{
        public HashMap<Node, List<Node>> setMap;
        public MySets(List<Node> nodes){
            for (Node node : nodes) {
                ArrayList<Node> set = new ArrayList<>();
                set.add(node);
                setMap.put(node,set);
            }
        }
        /*判断from所在集合和to所在集合是否是同一个集合*/
        public boolean isSameSet(Node from,Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);
            return fromSet == toSet;
        }

        /*把用户提供的两个集合 结合为一个集合*/
        public void union(Node from, Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);

            for (Node node : toSet) {
                fromSet.add(node);
                setMap.put(node,fromSet);
            }
        }

    }

    public static class MyComparator implements Comparator<Edge>{

        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight-o2.weight;
        }
    }
    public HashSet<Edge> kruskal(Graph graph){
        ArrayList<Node> list = new ArrayList<>(graph.nodes.values());
        MySets mySets = new MySets(list);
        HashSet<Edge> result = new HashSet<>();
        PriorityQueue<Edge> edgesQ = new PriorityQueue<>(new MyComparator());
        edgesQ.addAll(graph.edges);
        while (!edgesQ.isEmpty()){
            Edge cur = edgesQ.poll();
            if (!mySets.isSameSet(cur.from,cur.to)){
                result.add(cur);
                mySets.union(cur.from,cur.to);
            }
        }
        return result;
    }
}
