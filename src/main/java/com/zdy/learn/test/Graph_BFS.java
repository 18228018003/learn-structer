package com.zdy.learn.test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * description
 *
 * @author 周德永
 * @date 2021/11/1 21:38
 */
public class Graph_BFS {

    public void bfs(Node node)
    {
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            for (Node next : cur.nexts) {
                if (!set.contains(next)){
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

    public void dfs(Node node)
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
}
