package com.mengtu.structer.graph;

import java.util.Map;

public class Main {
    static Graph.WeightManager<Integer> weightManager = new Graph.WeightManager<Integer>() {
        @Override
        public int compare(Integer w1, Integer w2) {
            return w1.compareTo(w2);
        }

        @Override
        public Integer add(Integer w1, Integer w2) {
            return w1+w2;
        }

        @Override
        public Integer zero() {
            return null;
        }
    };
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }


    private static void test2() {
        ListGraph<String,Integer> graph = new ListGraph<>();
        graph.addEdge("a","b");
        graph.addEdge("b","c");
        graph.addEdge("b","d");
        graph.addEdge("c","d");
        graph.addEdge("c","f");
        graph.addEdge("d","e");
        graph.addEdge("d","f");
        graph.addEdge("e","f");
        graph.addEdge("e","a");
//        graph.dfs("a");
//        graph.bfs("a");
    }

    private static void test1() {
        ListGraph<String,Integer> graph = new ListGraph<>();
        graph.addEdge("V1","V0",9);
        graph.addEdge("V1","V2",3);
        graph.addEdge("V2","V0",2);
        graph.addEdge("V2","V3",5);
        graph.addEdge("V3","V4",1);
        graph.addEdge("V0","V4",0);
//        graph.print();
//        graph.bfs("V1");
//        graph.dfs("V1");
    }
    private static void test3() {
        ListGraph<String,Integer> graph = new ListGraph<>(weightManager);
        graph.addEdge("A","B",10);
        graph.addEdge("A","D",30);
        graph.addEdge("A","E",100);
        graph.addEdge("B","C",50);
        graph.addEdge("C","E",10);
        graph.addEdge("D","C",20);
        graph.addEdge("D","E",60);
//        graph.print();
//        graph.bfs("V1");
//        graph.dfs("V1");
        Map<String, Integer> map = graph.shortestPath("A");
        System.out.println(map);
    }
}
