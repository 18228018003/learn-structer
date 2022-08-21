package com.zdy.learn.tanxin;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * description
 *
 * @author 周德永
 * @date 2021/10/31 23:33
 */
public class IPO {
    public static class Node {
        public int p;
        public int c;

        public Node(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

    public static class MinCostComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.c - o2.c;
        }
    }

    public static class MaxProfitComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o2.p - o1.p;
        }
    }

    public static int findMaximizedCaptial(int k, int w, int[] profits, int[] captical) {
        PriorityQueue<Node> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        PriorityQueue<Node> minCostQ = new PriorityQueue<>(new MinCostComparator());
        /*所有项目扔到被锁池中，花费组织的小根堆*/
        for (int i = 0; i < profits.length; i++) {
            minCostQ.add(new Node(profits[i], captical[i]));
        }
        for (int i = 0; i < k; i++) {
            /*能力所及的项目全解锁*/
            while (!minCostQ.isEmpty() && minCostQ.peek().c <= w) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return w;
            }
            w += maxProfitQ.poll().p;
        }
        return w;
    }
}
